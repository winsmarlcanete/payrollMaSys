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

    public static void registerEmployee(Employee emp) {
        String sql = "INSERT INTO payrollmsdb.employees " +
                "(last_name, first_name, middle_name, tin_number, philhealth_number, pagibig_number, sss_number, " +
                "pay_rate, employment_status, department, shift_start, shift_end, fingerprint, " +
                "philhealth_percentage, pagibig_percentage, sss_percentage, efund_amount, other_deductions) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn;
        try {
            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Debugging values
            System.out.println("Last Name: " + emp.getLast_name());
            System.out.println("First Name: " + emp.getFirst_name());
            System.out.println("Middle Name: " + emp.getMiddle_name());
            System.out.println("TIN Number: " + emp.getTin_number());
            System.out.println("PhilHealth Number: " + emp.getPhilhealth_number());
            System.out.println("Pag-Ibig Number: " + emp.getPagibig_number());
            System.out.println("SSS Number: " + emp.getSss_number());
            System.out.println("Pay Rate: " + emp.getPay_rate());
            System.out.println("Employment Status: " + emp.getEmployment_status());
            System.out.println("Department: " + emp.getDepartment());
            System.out.println("Shift Start: " + emp.getShift_start());
            System.out.println("Shift End: " + emp.getShift_end());
            System.out.println("PhilHealth Percentage: " + emp.getPhilhealth_percentage());
            System.out.println("Pag-Ibig Percentage: " + emp.getPagibig_percentage());
            System.out.println("SSS Percentage: " + emp.getSss_percentage());
            System.out.println("E-Fund Amount: " + emp.getEfund_amount());
            System.out.println("Other Deductions: " + emp.getOther_deductions());

            stmt.setString(1, emp.getLast_name());
            stmt.setString(2, emp.getFirst_name());
            stmt.setString(3, emp.getMiddle_name());
            stmt.setString(4, emp.getTin_number());
            stmt.setString(5, emp.getPhilhealth_number());
            stmt.setString(6, emp.getPagibig_number());
            stmt.setString(7, emp.getSss_number());
            stmt.setBigDecimal(8, emp.getPay_rate());
            stmt.setString(9, emp.getEmployment_status());
            stmt.setString(10, emp.getDepartment());
            stmt.setTime(11, emp.getShift_start());
            stmt.setTime(12, emp.getShift_end());
            stmt.setBytes(13, java.util.Arrays.copyOf(emp.getFingerprint().getTemplate(), emp.getFingerprint().getSize()));
            stmt.setBigDecimal(14, emp.getPhilhealth_percentage());
            stmt.setBigDecimal(15, emp.getPagibig_percentage());
            stmt.setBigDecimal(16, emp.getSss_percentage());
            stmt.setBigDecimal(17, emp.getEfund_amount());
            stmt.setBigDecimal(18, emp.getOther_deductions());

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main (String[] args){




    }

}
