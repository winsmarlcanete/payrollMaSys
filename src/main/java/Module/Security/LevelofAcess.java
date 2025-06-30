package Module.Security;

import Config.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LevelofAcess {
    public static int checkAccess(String email) {
        int accessLevel = -1; // Default value if no access level is found
        Connection conn = null;

        try {
            conn = JDBC.getConnection(); // Assuming JDBC.getConnection() is implemented to get a database connection

            String query = "SELECT access_level FROM payrollmsdb.users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                accessLevel = rs.getInt("access_level");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error checking access level: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }

        return accessLevel;
    }
}
