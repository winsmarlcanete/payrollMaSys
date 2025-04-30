package Module.Registration.UserRegistration;

import Config.JDBC;
import Entity.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class UserRegistration {
    public static void registerUser(User user){
        String sql = "INSERT INTO payrollmsdb.users " +
                "(first_name, last_name, email, password, access_level, " +
                "creation_date, account_status, user_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn;
        Date date = new Date(System.currentTimeMillis());
        try{
            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getFirst_name());
            stmt.setString(2, user.getLast_name());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getAccess_level());
            stmt.setDate(6,date);
            stmt.setInt(7, user.getAccount_status());
            stmt.setString(8, user.getUser_name());


            stmt.executeUpdate();
            stmt.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();

        }

    }

    public static void main (String[] args){





    }

}
