package Module.Payroll;

import Config.JDBC;
import Entity.Employee;
import Entity.PayrollClass;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Payroll {
    public static List<Employee> retreiveAllEmployee(){
        List<Employee> employees = new ArrayList<>();
        Connection conn;

        try{
            String sql = "SELECT `employees`.`employee_id`,\n" +
                    "    `employees`.`last_name`,\n" +
                    "    `employees`.`first_name`,\n" +
                    "    `employees`.`pay_rate`,\n" +
                    "    `employees`.`department`\n" +
                    "FROM `payrollmsdb`.`employees`;";
            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int employeeId = rs.getInt("employee_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                BigDecimal payRateB = rs.getBigDecimal("pay_rate");
                Double payRate = payRateB.doubleValue();
                String department = rs.getString("department");

                Employee emp = new Employee(employeeId, lastName, firstName, payRate, department);
                employees.add(emp);
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    public static void createPayroll(Employee emp, String period_start, String period_end){

        Connection conn;
        try{
            String sql = "INSERT INTO payrollmsdb.payroll (employee_id, period_start, period_end) VALUES (?, ?, ?)";

            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,emp.getEmployee_id());
            stmt.setString(2, period_start);
            stmt.setString(3, period_end);


            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updatePayroll(double days_present, double overtime_hours,
                                     double nd_hours, double sholiday_hours, double lholiday_hours,
                                     double late_minutes, double wage, double sss_deduction,
                                     double philhealth_deduction, double pagibig_deduction, double efund_deduction,
                                     double other_deduction,double salary_adjustment, double allowance_adjustment, double other_compensations, int payroll_id){
        Connection conn;
        try{
            String sql = "UPDATE payrollmsdb.payroll SET " +
                    "days_present = ?, " +
                    "overtime_hours = ?, " +
                    "nd_hours = ?, " +
                    "sholiday_hours = ?, " +
                    "lholiday_hours = ?, " +
                    "late_minutes = ?, " +
                    "wage = ?, " +
                    "sss_deduction = ?, " +
                    "philhealth_deduction = ?, " +
                    "pagibig_deduction = ?, " +
                    "efund_deduction = ?, " +
                    "other_deduction = ?, " +
                    "salary_adjustment = ?, " +
                    "allowance_adjustment = ?, " +
                    "other_compensations = ? " +
                    "WHERE payroll_id = ?";

            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, days_present);
            stmt.setDouble(2, overtime_hours);
            stmt.setDouble(3, nd_hours);
            stmt.setDouble(4, sholiday_hours);
            stmt.setDouble(5, lholiday_hours);
            stmt.setDouble(6, late_minutes);
            stmt.setDouble(7, wage);
            stmt.setDouble(8, sss_deduction);
            stmt.setDouble(9, philhealth_deduction);
            stmt.setDouble(10, pagibig_deduction);
            stmt.setDouble(11, efund_deduction);
            stmt.setDouble(12, other_deduction);
            stmt.setDouble(13, salary_adjustment);
            stmt.setDouble(14, allowance_adjustment);
            stmt.setDouble(15, other_compensations);
            stmt.setInt(16, payroll_id);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void viewPayroll(){

    }
    public static void main (String[] args){
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
//        String period_start = sqlDate.toString(); //Date picker
//        String period_end = sqlDate.toString(); // Date picker
//        List<Employee> emp = retreiveAllEmployee();
//
//        for (Employee employees  : emp) {
//            createPayroll(employees, period_start, period_end);
//        }
        double days_present = 20.0;
        double overtime_hours = 10.5;
        double nd_hours = 6.0;
        double sholiday_hours = 2.5;
        double lholiday_hours = 1.0;
        double late_minutes = 15.0;
        double wage = 18000.0;
        double sss_deduction = 550.0;
        double philhealth_deduction = 350.0;
        double pagibig_deduction = 200.0;
        double efund_deduction = 120.0;
        double other_deduction = 180.0;
        double salary_adjustment = 300.0;
        double allowance_adjustment = 500.0;
        double other_compensations = 400.0;
        int payroll_id = 4;
        updatePayroll(
                days_present,
                overtime_hours,
                nd_hours,
                sholiday_hours,
                lholiday_hours,
                late_minutes,
                wage,
                sss_deduction,
                philhealth_deduction,
                pagibig_deduction,
                efund_deduction,
                other_deduction,
                salary_adjustment,
                allowance_adjustment,
                other_compensations,
                payroll_id
        );



    }

}
