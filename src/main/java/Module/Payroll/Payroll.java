package Module.Payroll;

import Config.JDBC;
import Entity.Employee;
import Entity.PayrollClass;
import Entity.Formula;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Payroll {
    public static List<Employee> retrieveAllEmployee(){
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
                BigDecimal payRate = rs.getBigDecimal("pay_rate");
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

    public static PayrollClass createPayroll(Employee emp, String period_start, String period_end){

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

            return new PayrollClass(emp.getEmployee_id(), period_start,period_end, emp.getPay_rate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updatePayroll(PayrollClass Payroll){

        Connection conn;
        try{
            String sql = "UPDATE payrollmsdb.payroll SET " +
                    "days_present = ?, " +
                    "overtime_hours = ?, " +
                    "nd_hours = ?, " +
                    "sholiday_hours = ?, " +
                    "lholiday_hours = ?, " +
                    "late_minutes = ?, " +
                    "wage = ? " +
                    "WHERE payroll_id = ?";

            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, Payroll.getDays_present());
            stmt.setDouble(2, Payroll.getOvertime_hours());
            stmt.setDouble(3, Payroll.getNd_hours());
            stmt.setDouble(4, Payroll.getSholiday_hours());
            stmt.setDouble(5, Payroll.getLholiday_hours());
            stmt.setDouble(6, Payroll.getLate_minutes());
            stmt.setBigDecimal(7, Payroll.getPayrate());
            stmt.setInt(8, Payroll.getPayroll_id());

            stmt.executeUpdate();
            stmt.close();

            String sql1 = "UPDATE payrollmsdb.payroll SET " +
                    "late_amount = ? " +
                    "WHERE employee_id = ?";


            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setBigDecimal(1,Formula.computeLateAmount(Payroll.getPayrate(),Payroll.getLate_minutes()));
            stmt1.setInt(2, Payroll.getEmployee_id());
            stmt1.executeUpdate();
            stmt1.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void viewPayroll(){

    }
    public static void main (String[] args){
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
        String period_start = sqlDate.toString(); //Date picker
        String period_end = sqlDate.toString(); // Date picker

        List<Employee> emplist = retrieveAllEmployee();
        Map<Integer, PayrollClass> payrollMap = new HashMap<>();

        for (Employee employee  : emplist) {
            PayrollClass payroll = createPayroll(employee, period_start, period_end);
            payrollMap.put(employee.getEmployee_id(), payroll);
        }

        System.out.println(payrollMap.get(182));



        double days_present = 20.0;
        double overtime_hours = 10.5;
        double nd_hours = 6.0;
        double sholiday_hours = 2.5;
        double lholiday_hours = 1.0;
        double late_minutes = 15.0;
        BigDecimal pay_rate = new BigDecimal("2000");
        payrollMap.get(182).setPayrate(pay_rate);
        payrollMap.get(182).setLate_minutes(15.0);
        updatePayroll(payrollMap.get(182));






    }

}
