package Config;

import java.sql.*;

public class JDBC {
    private final static String URL = "jdbc:mysql://127.0.0.1:3306/sample_schema";
    private final static String USER = "root";
    private final static String PASSWORD = "wpc";
    public static void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();  // Properly close connection
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
             conn = DriverManager.getConnection(URL, USER, PASSWORD);
             stmt  = conn.createStatement();
             rs = stmt.executeQuery("Select * from sample_table");

            while (rs.next()){
                System.out.println(rs.getString("sample_tablecol"));

            }
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            closeResources(conn,stmt, rs);
        }

    }

    public static void main(String[] args) {
        getConnection();


    }

}
