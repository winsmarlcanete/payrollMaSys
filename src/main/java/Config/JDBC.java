package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
    private final static String URL = "jdbc:mysql://127.0.0.1:3306/sample_schema";
    private final static String USER = "root";
    private final static String PASSWORD = "wpc";
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database!");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        getConnection();  // Test connection
    }

}
