package Module.Registration.EmployeeRegistration;

import Entity.Employee;
import Config.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class EmployeeRegistration {

    public static void registerEmployee(Employee emp){
        String sql = "INSERT INTO payrollmsdb.employees " +
                "(employee_id, last_name, first_name, middle_name, tin_number, " +
                "philhealth_number, pagibig_number, pay_rate, employment_status, department, shift_start, shift_end) " +
                "VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)";
        Connection conn;
        try{
            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, emp.getEmployee_id());
            stmt.setString(2, emp.getLast_name());
            stmt.setString(3, emp.getFirst_name());
            stmt.setString(4, emp.getMiddle_name());
            stmt.setString(5, emp.getTin_number());
            stmt.setString(6, emp.getPhilhealth_number());
            stmt.setString(7,emp.getPagibig_number());
            stmt.setDouble(8, emp.getPay_rate());
            stmt.setString(9, emp.getEmployment_status());
            stmt.setString(10, emp.getDepartment());
            Time shiftStart = Time.valueOf(emp.getShift_start()); //convert string to time
            stmt.setTime(11, shiftStart);
            Time shiftEnd = Time.valueOf(emp.getShift_end());
            stmt.setTime(12, shiftEnd);


            stmt.executeUpdate();
            stmt.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();

        }

    }

    public static void main (String[] args){
         int input_employee_id = 181;
         String input_last_name = "Serrano";
         String input_first_name = "Jerwin";
         String input_middle_name = "Ceejay";
         String input_tin_number = "000 – 123 – 456 – 001";
         String input_philhealth_number = "12-123456789-1";
         String input_pagibig_number = "1234-5678-9012";
         float input_pay_rate = 500;
         String input_employment_status = "Regular";
         String input_department = "Human Resource";
         String input_shift_start = "09:00:00";
         String input_shift_end = "17:00:00";

        Employee emp = new Employee(input_employee_id,input_last_name, input_first_name, input_middle_name,
                input_tin_number, input_philhealth_number, input_pagibig_number,input_pay_rate, input_employment_status,input_department,
                input_shift_start,input_shift_end);

        registerEmployee(emp);


    }

}
