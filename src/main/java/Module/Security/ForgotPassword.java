package Module.Security;

import Algorithms.sha256;
import Config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
