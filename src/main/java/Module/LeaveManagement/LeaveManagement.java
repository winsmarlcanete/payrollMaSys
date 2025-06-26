package Module.LeaveManagement;

import Config.JDBC;

import java.sql.*;
import java.time.LocalDate;

import static Module.Attendance.Backup.AttendanceBackup.checkEmployee;

public class LeaveManagement {
    public static void updateLeaveEmployee(int leave_id, String leave_type, int remaining_sil) {
        String sql = "UPDATE leavemanagement SET leave_type = ?, creation_date = ?, remaining_sil = ? WHERE leave_id = ?";
        System.out.println("Executing updateLeaveEmployee...");
        System.out.println("Parameters: leave_id=" + leave_id + ", leave_type=" + leave_type + ", remaining_sil=" + remaining_sil);

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = JDBC.getConnection();
            System.out.println("Database connection established.");
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, leave_type);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setInt(3, remaining_sil);
            stmt.setInt(4, leave_id);

            System.out.println("Executing SQL: " + stmt.toString());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Leave record updated successfully!");
            } else {
                System.out.println("Leave ID not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error during updateLeaveEmployee:");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                System.out.println("Database resources closed.");
            } catch (SQLException e) {
                System.out.println("Error while closing resources:");
                e.printStackTrace();
            }
        }
    }

    public static void insertLeaveEmployee(int employee_id, String leave_type, int remaining_sil) {
        String sql = "INSERT INTO leavemanagement (employee_id, leave_type, creation_date, remaining_sil) VALUES (?, ?, ?, ?)";
        System.out.println("Executing insertLeaveEmployee...");
        System.out.println("Parameters: employee_id=" + employee_id + ", leave_type=" + leave_type + ", remaining_sil=" + remaining_sil);

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Check if employee exists
            System.out.println("Checking if employee exists...");
            if (checkEmployee(employee_id)) {
                System.out.println("Employee exists. Proceeding with insertion.");
                conn = JDBC.getConnection();
                System.out.println("Database connection established.");
                stmt = conn.prepareStatement(sql);

                stmt.setInt(1, employee_id);
                stmt.setString(2, leave_type);
                stmt.setDate(3, Date.valueOf(LocalDate.now()));
                stmt.setInt(4, remaining_sil);

                System.out.println("Executing SQL: " + stmt.toString());
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Leave record inserted successfully!");
                } else {
                    System.out.println("Failed to insert leave record!");
                }
            } else {
                System.out.println("Employee ID does not exist!");
            }
        } catch (SQLException e) {
            System.out.println("Error during insertLeaveEmployee:");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                System.out.println("Database resources closed.");
            } catch (SQLException e) {
                System.out.println("Error while closing resources:");
                e.printStackTrace();
            }
        }
    }
}