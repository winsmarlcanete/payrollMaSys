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
                "(last_name, first_name, middle_name, tin_number, philhealth_number, philhealth_percentage, " +
                "pagibig_number, pagibig_percentage, sss_number, sss_percentage, efund_amount, other_deductions, " +
                "salary_adj_percentage, allowance_amount, other_comp_amount, pay_rate, employment_status, department, " +
                "shift_start, shift_end, fingerprint) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn;
        try {
            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set values for the prepared statement
            stmt.setString(1, emp.getLast_name());
            stmt.setString(2, emp.getFirst_name());
            stmt.setString(3, emp.getMiddle_name());
            stmt.setString(4, emp.getTin_number());
            stmt.setString(5, emp.getPhilhealth_number());
            stmt.setBigDecimal(6, emp.getPhilhealth_percentage());
            stmt.setString(7, emp.getPagibig_number());
            stmt.setBigDecimal(8, emp.getPagibig_percentage());
            stmt.setString(9, emp.getSss_number());
            stmt.setBigDecimal(10, emp.getSss_percentage());
            stmt.setBigDecimal(11, emp.getEfund_amount());
            stmt.setBigDecimal(12, emp.getOther_deductions());
            stmt.setBigDecimal(13, emp.getSalary_adj_percentage());
            stmt.setBigDecimal(14, emp.getAllowance_amount());
            stmt.setBigDecimal(15, emp.getOther_comp_amount());
            stmt.setBigDecimal(16, emp.getPay_rate());
            stmt.setString(17, emp.getEmployment_status());
            stmt.setString(18, emp.getDepartment());
            stmt.setTime(19, emp.getShift_start());
            stmt.setTime(20, emp.getShift_end());
            stmt.setBytes(21, emp.getFingerprint() != null ?
                    java.util.Arrays.copyOf(emp.getFingerprint().getTemplate(), emp.getFingerprint().getSize()) : null);

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
