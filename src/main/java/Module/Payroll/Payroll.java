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

    public static void updatePayroll(){

    }

    public static void viewPayroll(){

    }
    public static void main (String[] args){
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
        String period_start = sqlDate.toString(); //Date picker
        String period_end = sqlDate.toString(); // Date picker
        List<Employee> emp = retreiveAllEmployee();

        for (Employee employees  : emp) {
            createPayroll(employees, period_start, period_end);
        }



    }

}
