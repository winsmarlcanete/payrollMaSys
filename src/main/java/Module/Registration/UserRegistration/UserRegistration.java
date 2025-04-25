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
                "(user_id, first_name, last_name, email, password, access_level, " +
                "creation_date, account_status, user_name, otp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn;
        Date date = new Date(System.currentTimeMillis());
        try{
            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getUser_id());
            stmt.setString(2, user.getFirst_name());
            stmt.setString(3, user.getLast_name());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getAccess_level());
            stmt.setDate(7,date);
            stmt.setInt(8, user.getAccount_status());
            stmt.setString(9, user.getUser_name());
            stmt.setInt(10, user.getOtp());

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();

        }

    }

    public static void main (String[] args){
        int input_user_id = 1;
        String input_first_name = "Marc";
        String input_last_name = "Supank";
        String input_email = "Gilgamesh@gmail.com";
        String input_password = "123456";
        int input_access_level = 1;
        int input_account_status = 2;
        String input_user_name = "Meraki";
        int input_otp = 6969;

        User user = new User(
                input_user_id,
                input_first_name,
                input_last_name,
                input_email,
                input_password,
                input_access_level,
                input_account_status,
                input_user_name,
                input_otp
        );

        registerUser(user);


    }

}
