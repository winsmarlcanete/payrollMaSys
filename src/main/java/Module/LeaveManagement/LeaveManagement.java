package Module.LeaveManagement;

import Config.JDBC;

import java.sql.*;
import java.time.LocalDate;


import static Module.Attendance.Backup.AttendanceBackup.checkEmployee;

public class LeaveManagement {
    public static void updateLeave(int employee_id, String leave_type, int remaining_sil){
        String sql = "INSERT INTO `payrollmsdb`.`leavemanagement` (employee_id, leave_type, " +
                "creation_date, remaining_sil) VALUES (?, ?, ?, ?)";

        //Check first if employee exists
        if (checkEmployee(employee_id)) {
            System.out.println("Employee ID exists!");

            Connection conn;
            try{
                conn = JDBC.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1,employee_id);
                stmt.setString(2, leave_type);
                stmt.setDate(3, Date.valueOf(LocalDate.now()));
                stmt.setInt(4, remaining_sil);
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

    public static void main(String[] args){
        int input_employee_id = 1;
        String input_leave_type = "Maternity";
        int input_remaining_sil = 3;
        updateLeave(input_employee_id,input_leave_type,input_remaining_sil);
    }


}
