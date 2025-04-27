package Module.Payroll;

import Config.JDBC;
import Entity.PayrollSlip;
import java.sql.*;

public class Payroll {
    public static void createPayroll(PayrollSlip payrollslip){

        String sql = """
        INSERT INTO payrollmsdb.payroll (
        employee_id, period_start, period_end, days_present,
        overtime_hours, nd_hours, sholiday_hours, lholiday_hours, late_minutes,
        overtime_amount, nd_amount, sholiday_amount, lholiday_amount, late_amount,
        wage, philhealth_deduction, sss_deduction, pagibig_deduction, efund_deduction,
        other_deduction, salary_adjustment, allowance_adjustment, other_compensations,
        total_deduction, gross_pay, net_pay, sub_total, grand_total
        ) VALUES (
        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
        )
        """;


        Connection conn;

        try{
            conn = JDBC.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, payrollslip.getEmployee_id());
            stmt.setString(2, payrollslip.getPeriod_start());
            stmt.setString(3, payrollslip.getPeriod_end());
            stmt.setFloat(4, payrollslip.getDays_present());
            stmt.setFloat(5, payrollslip.getOvertime_hours());
            stmt.setFloat(6, payrollslip.getNd_hours());
            stmt.setFloat(7, payrollslip.getSholiday_hours()); // straight sholiday_hours
            stmt.setFloat(8, payrollslip.getLholiday_hours()); // straight lholiday_hours
            stmt.setFloat(9, payrollslip.getLate_minutes());
            stmt.setFloat(10, payrollslip.getOvertime_amount());
            stmt.setFloat(11, payrollslip.getNd_amount());
            stmt.setFloat(12, payrollslip.getSholiday_amount());
            stmt.setFloat(13, payrollslip.getLholiday_amount());
            stmt.setFloat(14, payrollslip.getLate_amount());
            stmt.setFloat(15, payrollslip.getWage());
            stmt.setFloat(16, payrollslip.getPhilhealth_deduction());
            stmt.setFloat(17, payrollslip.getSss_deduction());
            stmt.setFloat(18, payrollslip.getPagibig_deduction());
            stmt.setFloat(19, payrollslip.getEfund_deduction());
            stmt.setFloat(20, payrollslip.getOther_deduction());
            stmt.setFloat(21, payrollslip.getSalary_adjustment());
            stmt.setFloat(22, payrollslip.getAllowance_adjustment());
            stmt.setFloat(23, payrollslip.getOther_compensations());
            stmt.setFloat(24, payrollslip.getTotal_deduction());
            stmt.setFloat(25, payrollslip.getGross_pay());
            stmt.setFloat(26, payrollslip.getNet_pay());
            stmt.setFloat(27, payrollslip.getSub_total());
            stmt.setFloat(28, payrollslip.getGrand_total());

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
        float input_overtime_amount = 1200.0f;
        float input_nd_amount = 250.0f;
        float input_sholiday_amount = 600.0f;
        float input_lholiday_amount = 450.0f;
        float input_late_amount = 75.0f;
        float input_wage = 20000.0f;
        float input_philhealth_deduction = 800.0f;
        float input_sss_deduction = 1200.0f;
        float input_pagibig_deduction = 100.0f;
        float input_efund_deduction = 150.0f;
        float input_other_deduction = 50.0f;
        float input_salary_adjustment = 500.0f;
        float input_allowance_adjustment = 300.0f;
        float input_other_compensations = 200.0f;
        float input_total_deduction = 2800.0f;
        float input_gross_pay = 23000.0f;
        float input_net_pay = 20200.0f;
        float input_sub_total = 24000.0f;
        float input_grand_total = 24000.0f;

        PayrollSlip payrollslip = new PayrollSlip(
                input_employee_id, input_period_start, input_period_end,
                input_days_present, input_overtime_hours, input_nd_hours, input_sholiday_hours, input_lholiday_hours,
                input_late_minutes, input_overtime_amount, input_nd_amount, input_sholiday_amount, input_lholiday_amount,
                input_late_amount, input_wage, input_philhealth_deduction, input_sss_deduction, input_pagibig_deduction,
                input_efund_deduction, input_other_deduction, input_salary_adjustment, input_allowance_adjustment,
                input_other_compensations, input_total_deduction, input_gross_pay, input_net_pay, input_sub_total, input_grand_total
        );

        createPayroll(payrollslip);
    }

}
