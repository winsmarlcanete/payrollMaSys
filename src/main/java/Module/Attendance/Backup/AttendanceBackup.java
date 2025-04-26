package Module.Attendance.Backup;

import Config.JDBC;
import Entity.Timecard;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceBackup {


    public static boolean checkEmployee(int employee_id){
        boolean exists = false;
        String sql = "SELECT 1\n" +
                "FROM `payrollmsdb`.`employees` WHERE employee_id = ?";
        Connection conn;
        try{
            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, employee_id);
            ResultSet rs = stmt.executeQuery();
            exists = rs.next();

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public static void recordTimein(int employee_id){
        String sql = "INSERT INTO payrollmsdb.timecards " +
                "(employee_id, date, time_in) " +
                "VALUES  (?,?, ?)";
        //Check first if employee exists
        if (checkEmployee(employee_id)) {
            System.out.println("Employee ID exists!");

            Connection conn;
            try{
                conn = JDBC.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1,employee_id);
                stmt.setDate(2, Date.valueOf(LocalDate.now())); // today's date
                stmt.setTime(3, Time.valueOf(LocalTime.now())); // current time
                stmt.executeUpdate();
                stmt.close();
                conn.close();

            }catch(SQLException e){
                e.printStackTrace();

            }

        } else {
            System.out.println("Employee ID does not exist.");
        }


    }


    public static void recordTimeOut(int employee_id){
        Connection conn;

        if (checkEmployee(employee_id)) {
            System.out.println("Employee ID exists!");

            try{
                conn = JDBC.getConnection();

                //Fetch time_in
                String sql1 = "SELECT time_in FROM `payrollmsdb`.`timecards` WHERE employee_id = ? AND date = CURDATE()";
                PreparedStatement stmt1 = conn.prepareStatement(sql1);
                stmt1.setInt(1, employee_id);
                ResultSet rs = stmt1.executeQuery();

                if (rs.next()) {

                    Time timeInSQL = rs.getTime("time_in");
                    LocalTime timeIn = timeInSQL.toLocalTime();
                    LocalTime timeOut = LocalTime.now();

                    //Calculate clocked hrs and min
                    Duration workedDuration = Duration.between(timeIn, timeOut);
                    float totalMinutes = workedDuration.toMinutes();
                    float hoursClocked = totalMinutes / 60;


                    String sql2 = "UPDATE`payrollmsdb`.`timecards` SET time_out = ?, hours_clocked = ?, " +
                            "minutes_clocked = ? WHERE employee_id = ? AND date = CURDATE()";

                    PreparedStatement stmt2 = conn.prepareStatement(sql2);
                    stmt2.setTime(1, Time.valueOf(timeOut));
                    stmt2.setFloat(2, hoursClocked);
                    stmt2.setFloat(3, totalMinutes);
                    stmt2.setInt(4, employee_id);

                    stmt2.executeUpdate();

                } else {
                    System.out.println("No time_in record found for today.");
                }

            }catch(SQLException e){
                e.printStackTrace();

            }

        } else {
            System.out.println("Employee ID does not exist.");
        }

    }

    public static void main(String[] args){
        int input_employee_id = 1;
        recordTimeOut(input_employee_id);



    }
}
