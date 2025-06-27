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

    public static String processTimeCard(int employee_id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String employeeName = "";

        try {
            conn = JDBC.getConnection();

            // Get employee name
            String nameQuery = "SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM payrollmsdb.employees WHERE employee_id = ?";
            stmt = conn.prepareStatement(nameQuery);
            stmt.setInt(1, employee_id);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                return "Employee ID does not exist.";
            }

            employeeName = rs.getString("full_name");

            // Check today's timecard
            String timecardQuery = "SELECT time_in, time_out FROM payrollmsdb.timecards WHERE employee_id = ? AND date = CURDATE()";
            stmt = conn.prepareStatement(timecardQuery);
            stmt.setInt(1, employee_id);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                // No timecard exists - record time in
                recordTimein(employee_id);
                return "Welcome " + employeeName + "! Time in recorded.";
            }

            Time timeIn = rs.getTime("time_in");
            Time timeOut = rs.getTime("time_out");

            if (timeIn != null && timeOut != null) {
                // Both time in and out exist
                return "Timecard already exists for " + employeeName + " today.";
            }

            if (timeIn != null && timeOut == null) {
                // Has time in but no time out - record time out
                recordTimeOut(employee_id);
                return "Goodbye " + employeeName + "! Time out recorded.";
            }

            return "Invalid timecard state for " + employeeName;

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error processing timecard: " + e.getMessage();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){



    }
}
