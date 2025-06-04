package Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Formula {



    public static BigDecimal computeLateAmount(BigDecimal pay_rate, double late_minutes) {
        return pay_rate
                .divide(new BigDecimal("8"), 2, RoundingMode.HALF_UP)
                .divide(new BigDecimal("60"), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(late_minutes));
    }

    public static double computeWage(double pay_rate, double late_amount, double days_present ) {
        return (pay_rate*days_present) - (late_amount);
    }

    public static double computeOvertimeAmount(double pay_rate, double overtime_hours) {
        return (pay_rate/8) * (1.25) * (overtime_hours);
    }

    public static double computeNightDifferentialAmount(double pay_rate, double nd_hours) {

        return (pay_rate/8) * 0.1 * (nd_hours);
    }

    public static double computeSpecialHolidayAmount(double pay_rate,  double sholiday_hours) {

        return (pay_rate/8) * 1.3 * (sholiday_hours);
    }

    public static double computeLegalHolidayAmount(double pay_rate, double lholiday_hours) {

        return (pay_rate/8) * (lholiday_hours);
    }

    public static double computerTotalGrossAmount(double wage, double overtime_amount, double nd_amount,
                                                  double sholiday_amount, double lholiday_amount) {
        return wage + overtime_amount + nd_amount + sholiday_amount + lholiday_amount;
    }

    public static double computeTotalDeductionAmount(double sss_deduction, double philhealth_deduction,
                                                     double pagibig_deduction, double efund_deduction, double other_deduction){
        return sss_deduction + philhealth_deduction + pagibig_deduction + efund_deduction + other_deduction;
    }

    public static double computerNetPay(double gross_pay, double total_deduction, double
            allowance_adjustment, double salary_adjustment, double other_compensations){
        return (gross_pay - total_deduction) + allowance_adjustment + salary_adjustment + other_compensations;
    }

}
