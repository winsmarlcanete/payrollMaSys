package Module.Attendance.Backup;

import Config.JDBC;
import Entity.Timecard;

import java.sql.*;
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
        String sql = "UPDATE payrollmsdb.timecards SET time_out = ? " +
                "WHERE employee_id = ? AND date = CURDATE()"; // curdate() called to ensure same date attendance

        if (checkEmployee(employee_id)) {
            System.out.println("Employee ID exists!");

            Connection conn;
            try{
                conn = JDBC.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setTime(1, Time.valueOf(LocalTime.now()));
                stmt.setInt(2,employee_id);

                stmt.executeUpdate();

            }catch(SQLException e){
                e.printStackTrace();

            }

        } else {
            System.out.println("Employee ID does not exist.");
        }

    }

    public static void main(String[] args){
        int input_employee_id = 1;
        recordTimein(input_employee_id);



    }
}
