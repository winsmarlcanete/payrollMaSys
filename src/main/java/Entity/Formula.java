package Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class Formula {


    public static Double computeDaysPresent(Double hoursClocked){
        return hoursClocked/8;
    }
    public static double computeOvertimeHours(double hoursClocked) {
        return Math.max(0, hoursClocked - 8);
    }

    public static double computeNightDifferentialHours(LocalTime startTime, LocalTime endTime, double hoursClocked) {
        double ndHours = 0;
        LocalTime nightStart = LocalTime.of(22, 0); // 10 PM
        LocalTime nightEnd = LocalTime.of(6, 0);   // 6 AM

        if (startTime.isBefore(nightEnd)) {
            ndHours += Math.min(Duration.between(startTime, nightEnd).toHours(), hoursClocked);
        }
        if (endTime.isAfter(nightStart)) {
            ndHours += Math.min(Duration.between(nightStart, endTime).toHours(), hoursClocked);
        }

        return ndHours;
    }

    public static double computeSpecialHolidayHours(Date date, double hoursClocked, boolean isSpecialHoliday) {
        return isSpecialHoliday ? hoursClocked : 0;
    }

    public static double computeLegalHolidayHours(Date date, double hoursClocked, boolean isLegalHoliday) {
        return isLegalHoliday ? hoursClocked : 0;
    }

    public static double computeLateMinutes(LocalTime expectedStart, LocalTime actualStart) {
        if (actualStart.isAfter(expectedStart)) {
            return Duration.between(expectedStart, actualStart).toMinutes();
        }
        return 0;
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

    // Helper method to check if a date is a special holiday
    public static boolean checkSpecialHoliday(java.sql.Date date) {
        List<LocalDate> specialHolidays = List.of(
                LocalDate.of(2023, 2, 25),  // EDSA Revolution Anniversary
                LocalDate.of(2023, 4, 8),   // Black Saturday
                LocalDate.of(2023, 8, 21),  // Ninoy Aquino Day
                LocalDate.of(2023, 11, 1),  // All Saints' Day
                LocalDate.of(2023, 12, 8),  // Feast of the Immaculate Conception
                LocalDate.of(2023, 12, 31)  // New Year's Eve
        );

        LocalDate localDate = date.toLocalDate();
        return specialHolidays.contains(localDate);
    }

    // Helper method to check if a date is a legal holiday
    public static boolean checkLegalHoliday(java.sql.Date date) {
        List<LocalDate> legalHolidays = List.of(
                LocalDate.of(2023, 1, 1),   // New Year's Day
                LocalDate.of(2023, 4, 6),   // Maundy Thursday
                LocalDate.of(2023, 4, 7),   // Good Friday
                LocalDate.of(2023, 5, 1),   // Labor Day
                LocalDate.of(2023, 6, 12),  // Independence Day
                LocalDate.of(2023, 8, 28),  // National Heroes Day (last Monday of August, variable)
                LocalDate.of(2023, 11, 30), // Bonifacio Day
                LocalDate.of(2023, 12, 25), // Christmas Day
                LocalDate.of(2023, 12, 30)  // Rizal Day
        );

        LocalDate localDate = date.toLocalDate();
        return legalHolidays.contains(localDate);
    }

}
