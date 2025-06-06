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
                    "WHERE employee_id = ?";

            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, Payroll.getDays_present());
            stmt.setDouble(2, Payroll.getOvertime_hours());
            stmt.setDouble(3, Payroll.getNd_hours());
            stmt.setDouble(4, Payroll.getSholiday_hours());
            stmt.setDouble(5, Payroll.getLholiday_hours());
            stmt.setDouble(6, Payroll.getLate_minutes());
            stmt.setBigDecimal(7, Payroll.getPayrate());
            stmt.setInt(8, Payroll.getEmployee_id());

            stmt.executeUpdate();
            stmt.close();

            //Calculation

            Payroll.setLate_amount(Formula.computeLateAmount(Payroll.getPayrate(), Payroll.getLate_minutes()));
            Payroll.setWage(Formula.computeWage(Payroll.getPayrate(),Payroll.getLate_amount(),Payroll.getDays_present()));
            Payroll.setOvertime_amount(Formula.computeOvertimeAmount(Payroll.getPayrate(), Payroll.getOvertime_hours()));
            Payroll.setNd_amount(Formula.computeNightDifferentialAmount(Payroll.getPayrate(), Payroll.getNd_hours()));
            Payroll.setSholiday_amount(Formula.computeSpecialHolidayAmount(Payroll.getPayrate(), Payroll.getSholiday_hours()));
            Payroll.setLholiday_amount(Formula.computeLegalHolidayAmount(Payroll.getPayrate(), Payroll.getLholiday_hours()));
            Payroll.setGross_pay(Formula.computeTotalGrossAmount(Payroll.getWage(),Payroll.getOvertime_amount(), Payroll.getNd_amount(), Payroll.getSholiday_amount(), Payroll.getLholiday_amount()));
            Payroll.setTotal_deduction(Formula.computeTotalDeductionAmount(Payroll.getSss_deduction(), Payroll.getPhilhealth_deduction(),Payroll.getPagibig_deduction(), Payroll.getEfund_deduction(), Payroll.getOther_deduction()));
            Payroll.setNet_pay(Formula.computeNetPay(Payroll.getGross_pay(), Payroll.getTotal_deduction(), Payroll.getAllowance_adjustment(), Payroll.getSalary_adjustment(), Payroll.getOther_compensations()));

            String sql1 = "UPDATE payrollmsdb.payroll SET " +
                    "overtime_amount = ?, " +
                    "nd_amount = ?, " +
                    "sholiday_amount = ?, " +
                    "lholiday_amount = ?, " +
                    "late_amount = ?, " +
                    "wage = ?, " +
                    "philhealth_deduction = ?, " +
                    "sss_deduction = ?, " +
                    "pagibig_deduction = ?, " +
                    "efund_deduction = ?, " +
                    "other_deduction = ?, " +
                    "salary_adjustment = ?, " +
                    "allowance_adjustment = ?, " +
                    "other_compensations = ?, " +
                    "total_deduction = ?, " +
                    "gross_pay = ?, " +
                    "net_pay = ? " +
                    "WHERE employee_id = ?";


            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setBigDecimal(1, Payroll.getOvertime_amount());
            stmt1.setBigDecimal(2, Payroll.getNd_amount());
            stmt1.setBigDecimal(3, Payroll.getSholiday_amount());
            stmt1.setBigDecimal(4, Payroll.getLholiday_amount());
            stmt1.setBigDecimal(5, Payroll.getLate_amount());
            stmt1.setBigDecimal(6, Payroll.getWage());
            stmt1.setBigDecimal(7, Payroll.getPhilhealth_deduction());
            stmt1.setBigDecimal(8, Payroll.getSss_deduction());
            stmt1.setBigDecimal(9, Payroll.getPagibig_deduction());
            stmt1.setBigDecimal(10, Payroll.getEfund_deduction());
            stmt1.setBigDecimal(11, Payroll.getOther_deduction());
            stmt1.setBigDecimal(12, Payroll.getSalary_adjustment());
            stmt1.setBigDecimal(13, Payroll.getAllowance_adjustment());
            stmt1.setBigDecimal(14, Payroll.getOther_compensations());
            stmt1.setBigDecimal(15, Payroll.getTotal_deduction());
            stmt1.setBigDecimal(16, Payroll.getGross_pay());
            stmt1.setBigDecimal(17, Payroll.getNet_pay());
            stmt1.setInt(18, Payroll.getEmployee_id());


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
        payrollMap.get(182).setDays_present(days_present);
        payrollMap.get(182).setOvertime_hours(overtime_hours);
        payrollMap.get(182).setNd_hours(nd_hours);
        payrollMap.get(182).setSholiday_hours(sholiday_hours);
        payrollMap.get(182).setLholiday_hours(lholiday_hours);
        payrollMap.get(182).setLate_minutes(late_minutes);


        updatePayroll(payrollMap.get(182));






    }

}
