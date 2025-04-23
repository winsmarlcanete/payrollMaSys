package Module.E201File;
import Config.JDBC;

import java.sql.*;

public class E201File {

    public static void displayEmployeeGeneral(){
        String sql = "SELECT `employees`.`employee_id`,\n" +
                "    `employees`.`last_name`,\n" +
                "    `employees`.`first_name`,\n" +
                "    `employees`.`middle_name`,\n" +
                "    `employees`.`tin_number`,\n" +
                "    `employees`.`philhealth_number`,\n" +
                "    `employees`.`pagibig_number`,\n" +
                "    `employees`.`pay_rate`,\n" +
                "    `employees`.`employment_status`,\n" +
                "    `employees`.`department`,\n" +
                "    `employees`.`shift_start`,\n" +
                "    `employees`.`shift_end`\n" +
                "FROM `payrollmsdb`.`employees`;\n";

        Connection conn;

        try{
            conn = JDBC.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int employeeId = rs.getInt("employee_id");
                String lastName = rs.getString("last_name");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String tinNumber = rs.getString("tin_number");
                String philhealthNumber = rs.getString("philhealth_number");
                String pagibigNumber = rs.getString("pagibig_number");
                double payRate = rs.getDouble("pay_rate");
                String employmentStatus = rs.getString("employment_status");
                String department = rs.getString("department");
                Time shiftStart = rs.getTime("shift_start");
                Time shiftEnd = rs.getTime("shift_end");

                System.out.println("Employee ID: " + employeeId);
                System.out.println("Name: " + lastName + ", " + firstName + " " + middleName);
                System.out.println("TIN: " + tinNumber);
                System.out.println("PhilHealth: " + philhealthNumber);
                System.out.println("Pagibig: " + pagibigNumber);
                System.out.println("Pay Rate: " + payRate);
                System.out.println("Employment Status: " + employmentStatus);
                System.out.println("Department: " + department);
                System.out.println("Shift: " + shiftStart + " to " + shiftEnd);
                System.out.println("--------------------------------------------");
            }


            rs.close();
            stmt.close();
            conn.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args){
        displayEmployeeGeneral();
    }

}
