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
        Employee emp1 = new Employee(
                182, "Del Rosario", "Marvin", "Santos",
                "001-456-789-002", "12-987654321-2", "2345-6789-0123",
                520, "Regular", "Finance",
                "08:00:00", "16:00:00"
        );

        Employee emp2 = new Employee(
                183, "Lopez", "Angela", "Marie",
                "002-789-123-003", "12-112233445-3", "3456-7890-1234",
                480, "Probationary", "Marketing",
                "10:00:00", "18:00:00"
        );

        Employee emp3 = new Employee(
                184, "Cruz", "Jonathan", "Reyes",
                "003-321-654-004", "12-556677889-4", "4567-8901-2345",
                550, "Regular", "IT Department",
                "07:00:00", "15:00:00"
        );

        Employee emp4 = new Employee(
                185, "Garcia", "Patricia", "Anne",
                "004-654-987-005", "12-998877665-5", "5678-9012-3456",
                495, "Contractual", "Customer Service",
                "11:00:00", "19:00:00"
        );

        Employee emp5 = new Employee(
                186, "Morales", "Kevin", "Luis",
                "005-987-321-006", "12-443322110-6", "6789-0123-4567",
                530, "Regular", "Engineering",
                "06:00:00", "14:00:00"
        );

        registerEmployee(emp1);
        registerEmployee(emp2);
        registerEmployee(emp3);
        registerEmployee(emp4);
        registerEmployee(emp5);


    }

}
