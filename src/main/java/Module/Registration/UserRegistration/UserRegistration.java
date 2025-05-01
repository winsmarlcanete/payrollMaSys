package Module.Registration.UserRegistration;

import Config.JDBC;
import Entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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

    public static List<String> checkUserEmail() {
        String sql = "SELECT `users`.`email`,\n" +
                "    `users`.`password` FROM `payrollmsdb`.`users`;";
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

    public static String getPasswordByEmail(String email) {
        String sql = "SELECT `password` FROM `payrollmsdb`.`users` WHERE `email` = ?;";
        String password = null;

        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                password = rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    public static void main (String[] args){





    }

}
