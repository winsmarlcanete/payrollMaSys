package Module.Registration.UserRegistration;

import Config.JDBC;
import Entity.User; // Make sure this import points to your updated User class

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRegistration {
    public static boolean registerUser(User user){ // Changed return type from void to boolean
        // Updated SQL to include security_question and security_answer_hash
        String sql = "INSERT INTO payrollmsdb.users " +
                "(first_name, last_name, email, password, access_level, " +
                "creation_date, account_status, user_name, security_question, security_answer) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Added two placeholders

        Connection conn = null; // Initialize to null
        PreparedStatement stmt = null; // Initialize to null
        Date date = new Date(System.currentTimeMillis());

        try {
            conn = JDBC.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getFirstName()); // Use getFirstName()
            stmt.setString(2, user.getLastName());  // Use getLastName()
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPasswordHash()); // Use getPasswordHash()
            stmt.setInt(5, user.getAccessLevel()); // Use getAccessLevel()
            stmt.setDate(6, date);
            stmt.setInt(7, user.getAccountStatus()); // Use getAccountStatus()
            stmt.setString(8, user.getUserName()); // Use getUserName()
            stmt.setString(9, user.getSecurityQuestion()); // Set security question
            stmt.setString(10, user.getSecurityAnswerHash()); // Set security answer hash


            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully: " + user.getEmail());
                return true; // Registration successful
            } else {
                System.out.println("User registration failed for: " + user.getEmail() + " (no rows affected)");
                return false; // Registration failed (no rows affected)
            }
        } catch(SQLException e){
            // Print stack trace for debugging purposes
            e.printStackTrace();
            System.err.println("SQL Error during user registration: " + e.getMessage());
            // More specific error handling could go here, e.g., checking for duplicate entry errors
            return false; // Registration failed due to SQL exception
        } finally {
            // Close resources in a finally block to ensure they are closed even if an exception occurs
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static List<String> checkUserEmail() {
        String sql = "SELECT `email` FROM `payrollmsdb`.`users`;"; // Simplified query
        List<String> emails = new ArrayList<>();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                emails.add(rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }

    public static boolean isAccountActive(String email) {
        boolean isActive = false;
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT account_status FROM users WHERE email = ?")) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int status = rs.getInt("account_status");
                isActive = (status == 1); // 1 = active
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isActive;
    }

    public static String getPasswordByEmail(String email) {
        String sql = "SELECT `password` FROM `payrollmsdb`.`users` WHERE `email` = ?;";
        String password = null;

        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) { // No need for ResultSet in try-with-resources if only fetching one row

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) { // Use try-with-resources for ResultSet as well
                if (rs.next()) {
                    password = rs.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    public static void main (String[] args){
        // Main method can be used for testing, but typically not for production Swing apps.
    }

}
