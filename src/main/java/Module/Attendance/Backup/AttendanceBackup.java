package Module.Attendance.Backup;

import Config.JDBC;
import Entity.Timecard;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceBackup {
    public static void recordTimein(int employee_id){
        String sql = "INSERT INTO payrollmsdb.timecards " +
                "(employee_id, date, time_in) " +
                "VALUES (?, ?, ?)";

        Connection conn;
        try{
            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, employee_id);
            stmt.setDate(2, Date.valueOf(LocalDate.now())); // today's date
            stmt.setTime(3, Time.valueOf(LocalTime.now())); // current time
            stmt.executeUpdate();
            stmt.close();
            conn.close();

        }catch(SQLException e){
            e.printStackTrace();

        }
    }

    public static void main(String[] args){
        int input_employee_id = 1;
        recordTimein(input_employee_id);


    }
}
