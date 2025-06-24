package Module.Security;

import Algorithms.sha256;
import Config.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserAuthenticator {

    public static boolean updatePassword(String email, String newPassword) {
        // Hash the new password using SHA-256
        String hashedPassword = sha256.stringToSHA256(newPassword);

        try (Connection conn = JDBC.getConnection()) {
            // SQL query to update the password for the user with the given email
            String query = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, hashedPassword); // Set the hashed password
            stmt.setString(2, email);         // Set the email

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully for email: " + email);
                return true; // Return true if the update was successful
            } else {
                System.out.println("No user found with the email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace for debugging
        }

        return false; // Return false if the update failed
    }
}
