package Module.E201File;
import Algorithms.QuicksortClass;
import Config.JDBC;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static Object[][] getEmployeeTableData() {
        List<Object[]> dataList = new ArrayList<>();

        String query = """
    SELECT employee_id, last_name, first_name, middle_name,
           department, employment_status, shift_start, shift_end,
           pay_rate, tin_number, pagibig_number, pagibig_percentage,
           sss_number, sss_percentage, philhealth_number, philhealth_percentage,
           efund_amount, other_deductions, salary_adj_percentage, allowance_amount, other_comp_amount
    FROM employees""";

        try (Connection conn = JDBC.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("employee_id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("middle_name"),
                        rs.getString("department"),
                        rs.getString("employment_status"),
                        rs.getTime("shift_start"),
                        rs.getTime("shift_end"),
                        rs.getDouble("pay_rate"),
                        rs.getString("tin_number"),
                        rs.getString("pagibig_number"),
                        rs.getDouble("pagibig_percentage"),
                        rs.getString("sss_number"),
                        rs.getDouble("sss_percentage"),
                        rs.getString("philhealth_number"),
                        rs.getDouble("philhealth_percentage"),
                        rs.getDouble("efund_amount"),
                        rs.getDouble("other_deductions"),
                        rs.getDouble("salary_adj_percentage"),
                        rs.getDouble("allowance_amount"),
                        rs.getDouble("other_comp_amount")
                };

                dataList.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Object[][] data = dataList.toArray(new Object[0][]);

        // Sort by last name (column index 1)
        QuicksortClass.quicksort(data, 0, data.length - 1, 1);

        return data;
    }


    public static void updateEmployeeData(int employeeId, String lastName, String firstName, String middleName,
                                          String department, String employmentStatus, Time shiftStart, Time shiftEnd,
                                          BigDecimal payRate, String tinNumber, String pagibigNumber, BigDecimal pagibigPercentage,
                                          String sssNumber, BigDecimal sssPercentage, String philhealthNumber, BigDecimal philhealthPercentage,
                                          BigDecimal efundAmount, BigDecimal otherDeductions, BigDecimal salaryAdjPercentage,
                                          BigDecimal allowanceAmount, BigDecimal otherCompAmount) {
        String query = """
        UPDATE employees
        SET last_name = ?, first_name = ?, middle_name = ?, department = ?, employment_status = ?,
            shift_start = ?, shift_end = ?, pay_rate = ?, tin_number = ?, pagibig_number = ?, pagibig_percentage = ?,
            sss_number = ?, sss_percentage = ?, philhealth_number = ?, philhealth_percentage = ?, efund_amount = ?,
            other_deductions = ?, salary_adj_percentage = ?, allowance_amount = ?, other_comp_amount = ?
        WHERE employee_id = ?
    """;

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, lastName);
            pstmt.setString(2, firstName);
            pstmt.setString(3, middleName);
            pstmt.setString(4, department);
            pstmt.setString(5, employmentStatus);
            pstmt.setTime(6, shiftStart);
            pstmt.setTime(7, shiftEnd);
            pstmt.setBigDecimal(8, payRate);
            pstmt.setString(9, tinNumber);
            pstmt.setString(10, pagibigNumber);
            pstmt.setBigDecimal(11, pagibigPercentage);
            pstmt.setString(12, sssNumber);
            pstmt.setBigDecimal(13, sssPercentage);
            pstmt.setString(14, philhealthNumber);
            pstmt.setBigDecimal(15, philhealthPercentage);
            pstmt.setBigDecimal(16, efundAmount);
            pstmt.setBigDecimal(17, otherDeductions);
            pstmt.setBigDecimal(18, salaryAdjPercentage);
            pstmt.setBigDecimal(19, allowanceAmount);
            pstmt.setBigDecimal(20, otherCompAmount);
            pstmt.setInt(21, employeeId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee data updated successfully.");
            } else {
                System.out.println("No employee found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        displayEmployeeGeneral();
    }

}
