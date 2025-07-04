package Module.Security;

import Algorithms.sha256;
import Config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPassword {
    public static void saveOtp(String email, String otp){
        String hashedOtp = sha256.stringToSHA256(otp); // Hash the OTP

        try (Connection conn = JDBC.getConnection()) {
            String query = "UPDATE users SET otp = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, hashedOtp);
            stmt.setString(2, email);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("No user found with that email.");
            } else {
                System.out.println("OTP updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean verifyOtp(String email, String enteredOtp) {
        String hashedEnteredOtp = sha256.stringToSHA256(enteredOtp); // Hash the entered OTP

        try (Connection conn = JDBC.getConnection()) {
            String query = "SELECT otp FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedOtp = rs.getString("otp");
                return hashedEnteredOtp.equals(storedOtp); // Compare hashed OTPs
            } else {
                System.out.println("No user found with that email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean verifySecurityAnswer(String email, String providedAnswer) {
        // Hash the provided answer using SHA-256
        String hashedProvidedAnswer = sha256.stringToSHA256(providedAnswer);

        try (Connection conn = JDBC.getConnection()) {
            // SQL query to retrieve the hashed security answer for the given email
            String query = "SELECT security_answer FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHashedAnswer = rs.getString("security_answer");
                // Compare the hashed provided answer with the stored hashed answer
                return hashedProvidedAnswer.equals(storedHashedAnswer);
            } else {
                System.out.println("No user found with the email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace for debugging
        }

        return false; // Return false if verification fails
    }


}
