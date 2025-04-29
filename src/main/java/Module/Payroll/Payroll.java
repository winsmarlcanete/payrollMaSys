package Module.Payroll;

import Config.JDBC;
import Entity.Employee;
import Entity.PayrollClass;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Payroll {
    public static List<Integer> retreiveAllEmployee(){
        List<Integer> employeeIds = new ArrayList<>();
        Connection conn;

        try{
            String sql = "SELECT `employees`.`employee_id`\n" +
                    "FROM `payrollmsdb`.`employees`;";
            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int employeeId = rs.getInt("employee_id");
                employeeIds.add(employeeId);
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeIds;
    }

    public static void createPayroll(Employee emp){

        Connection conn;
        try{
            String sql = "String sql = \"INSERT INTO `payrollmsdb`.`payroll` (\" +\n" +
                    "    \"`employee_id`, \" +\n" +
                    "    \"`period_start`, `period_end`, `days_present`, `overtime_hours`, `nd_hours`, \" +\n" +
                    "    \"`sholiday_hours`, `lholiday_hours`, `late_minutes`, \" +\n" +
                    "    \"`overtime_amount`, `nd_amount`, `sholiday_amount`, `lholiday_amount`, `late_amount`, `wage`, \" +\n" +
                    "    \"`philhealth_deduction`, `sss_deduction`, `pagibig_deduction`, `efund_deduction`, `other_deduction`, \" +\n" +
                    "    \"`salary_adjustment`, `allowance_adjustment`, `other_compensations`, \" +\n" +
                    "    \"`total_deduction`, `gross_pay`, `net_pay`) \" +\n" +
                    "\"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\";\n";

            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);


            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updatePayroll(){

    }

    public static void viewPayroll(){

    }
    public static void main (String[] args){
        int input_employee_id = 1;
        String input_period_start = "2025-03-01";
        String input_period_end = "2025-03-31";
        float input_days_present = 22.5f;
        float input_overtime_hours = 10.5f;
        float input_nd_hours = 2.0f;
        float input_sholiday_hours = 4.0f;
        float input_lholiday_hours = 3.0f;
        float input_late_minutes = 15.0f;

        List<Integer> ids = retreiveAllEmployee();

        for (Integer id : ids) {
            System.out.println(id);
        }

    }

}
