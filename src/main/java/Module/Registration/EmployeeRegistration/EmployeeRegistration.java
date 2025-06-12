package Module.Registration.EmployeeRegistration;

import Entity.Employee;
import Config.JDBC;
import com.zkteco.biometric.FingerprintSensorEx;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class EmployeeRegistration {

    public static void registerEmployee(Employee emp){
        String sql = "INSERT INTO payrollmsdb.employees " +
                "(last_name, first_name, middle_name, tin_number, " +
                "philhealth_number, pagibig_number, sss_number, pay_rate, employment_status, department, shift_start, shift_end, fingerprint) " +
                "VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?)";
        Connection conn;
        try{
            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, emp.getLast_name());
            stmt.setString(2, emp.getFirst_name());
            stmt.setString(3, emp.getMiddle_name());
            stmt.setString(4, emp.getTin_number());
            stmt.setString(5, emp.getPhilhealth_number());
            stmt.setString(6,emp.getPagibig_number());
            stmt.setString(7,emp.getSss_number());
            stmt.setBigDecimal(8, emp.getPay_rate());
            stmt.setString(9, emp.getEmployment_status());
            stmt.setString(10, emp.getDepartment());
            Time shiftStart =emp.getShift_start(); //convert string to time
            stmt.setTime(11, shiftStart);
            Time shiftEnd = emp.getShift_end();
            stmt.setTime(12, shiftEnd);
            stmt.setBytes(13,java.util.Arrays.copyOf(emp.getFingerprint().getTemplate(), emp.getFingerprint().getSize()));


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
