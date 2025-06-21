package Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Formula {


    public static Double computeDaysPresent(Double hours_clocked){
        return hours_clocked/8;
    }
    public static BigDecimal computeLateAmount(BigDecimal pay_rate, double late_minutes) {

        if (pay_rate == null) pay_rate = BigDecimal.ZERO;

        return pay_rate
                .divide(new BigDecimal("8"), 2, RoundingMode.HALF_UP)
                .divide(new BigDecimal("60"), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(late_minutes));
    }

    public static BigDecimal computeWage(BigDecimal pay_rate, BigDecimal late_amount, double days_present ) {

        if (pay_rate == null) pay_rate = BigDecimal.ZERO;

        return pay_rate
                .multiply(new BigDecimal(days_present))
                .subtract(late_amount);
    }

    public static BigDecimal computeOvertimeAmount(BigDecimal pay_rate, double overtime_hours) {

        if (pay_rate == null) pay_rate = BigDecimal.ZERO;

        return pay_rate
                .divide(new BigDecimal("8"), 2, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("1.25"))
                .multiply(new BigDecimal(overtime_hours));
    }

    public static BigDecimal computeNightDifferentialAmount(BigDecimal pay_rate, double nd_hours) {

        if (pay_rate == null) pay_rate = BigDecimal.ZERO;

        return pay_rate
                .divide(new BigDecimal("8"), 2, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("0.1"))
                .multiply(BigDecimal.valueOf(nd_hours));
    }

    public static BigDecimal computeSpecialHolidayAmount(BigDecimal pay_rate,  double sholiday_hours) {

        if (pay_rate == null) pay_rate = BigDecimal.ZERO;

        return pay_rate
                .divide(new BigDecimal("8"), 2, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("1.3"))
                .multiply(BigDecimal.valueOf(sholiday_hours));
    }

    public static BigDecimal computeLegalHolidayAmount(BigDecimal pay_rate, double lholiday_hours) {

        if (pay_rate == null || pay_rate.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;

        return pay_rate
                .divide(new BigDecimal("8"), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(lholiday_hours));
    }

    public static BigDecimal computeTotalGrossAmount(BigDecimal wage, BigDecimal overtime_amount, BigDecimal nd_amount,
                                                  BigDecimal sholiday_amount, BigDecimal lholiday_amount) {

        if (wage == null) wage = BigDecimal.ZERO;
        if (overtime_amount == null) overtime_amount = BigDecimal.ZERO;
        if (nd_amount == null) nd_amount = BigDecimal.ZERO;
        if (sholiday_amount == null) sholiday_amount = BigDecimal.ZERO;
        if (lholiday_amount == null) lholiday_amount = BigDecimal.ZERO;

        return wage
                .add(overtime_amount)
                .add(nd_amount)
                .add(sholiday_amount)
                .add(lholiday_amount);
    }

    public static BigDecimal computeTotalDeductionAmount(BigDecimal sss_deduction, BigDecimal philhealth_deduction,
                                                     BigDecimal pagibig_deduction, BigDecimal efund_deduction, BigDecimal other_deduction){

        if (sss_deduction == null) sss_deduction = BigDecimal.ZERO;
        if (philhealth_deduction == null) philhealth_deduction = BigDecimal.ZERO;
        if (pagibig_deduction == null) pagibig_deduction = BigDecimal.ZERO;
        if (efund_deduction == null) efund_deduction = BigDecimal.ZERO;
        if (other_deduction == null) other_deduction = BigDecimal.ZERO;

        return sss_deduction
                .add(philhealth_deduction)
                .add(pagibig_deduction)
                .add(efund_deduction)
                .add(other_deduction);
    }

    public static BigDecimal computeNetPay(BigDecimal gross_pay, BigDecimal total_deduction, BigDecimal
            allowance_adjustment, BigDecimal salary_adjustment, BigDecimal other_compensations){

        if (total_deduction == null) total_deduction = BigDecimal.ZERO;
        if (allowance_adjustment == null) allowance_adjustment = BigDecimal.ZERO;
        if (salary_adjustment == null) salary_adjustment = BigDecimal.ZERO;
        if (other_compensations == null) other_compensations = BigDecimal.ZERO;

        return gross_pay
                .subtract(total_deduction)
                .add(allowance_adjustment)
                .add(salary_adjustment)
                .add(other_compensations);
    }

}
