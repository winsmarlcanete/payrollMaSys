package Entity;

import java.math.BigDecimal;
import java.sql.Date;

public class PayrollClass {

    private int payroll_id;
    private int employee_id;
    private Date period_start;
    private Date period_end;
    private double days_present;
    private double overtime_hours;
    private double nd_hours;
    private double sholiday_hours;
    private double lholiday_hours;
    private double late_minutes;
    private BigDecimal overtime_amount;
    private BigDecimal nd_amount;
    private BigDecimal sholiday_amount;
    private BigDecimal lholiday_amount;
    private BigDecimal late_amount;
    private BigDecimal pay_rate;
    private BigDecimal wage;
    private BigDecimal philhealth_deduction;
    private BigDecimal sss_deduction;
    private BigDecimal pagibig_deduction;
    private BigDecimal efund_deduction;
    private BigDecimal other_deduction;
    private BigDecimal salary_adjustment;
    private BigDecimal allowance_adjustment;
    private BigDecimal other_compensations;
    private BigDecimal total_deduction;
    private BigDecimal gross_pay;
    private BigDecimal net_pay;
    private BigDecimal sub_total;
    private BigDecimal grand_total;

    // Constructor

    public PayrollClass(int payroll_id, int employee_id, Date period_start, Date period_end, double days_present,
                        double overtime_hours, double nd_hours, double sholiday_hours, double lholiday_hours, double late_minutes, BigDecimal overtime_amount, BigDecimal nd_amount, BigDecimal sholiday_amount,
                        BigDecimal lholiday_amount, BigDecimal late_amount, BigDecimal wage, BigDecimal philhealth_deduction, BigDecimal sss_deduction, BigDecimal pagibig_deduction, BigDecimal efund_deduction, BigDecimal other_deduction,
                        BigDecimal salary_adjustment, BigDecimal allowance_adjustment, BigDecimal other_compensations, BigDecimal total_deduction, BigDecimal gross_pay, BigDecimal net_pay, BigDecimal sub_total, BigDecimal grand_total){
        this.payroll_id = payroll_id;
        this.employee_id = employee_id;
        this.period_start = period_start;
        this.period_end = period_end;
        this.days_present = days_present;
        this.overtime_hours = overtime_hours;
        this.nd_hours = nd_hours;
        this.sholiday_hours = sholiday_hours;
        this.lholiday_hours = lholiday_hours;
        this.late_minutes = late_minutes;
        this.overtime_amount = overtime_amount;
        this.nd_amount = nd_amount;
        this.sholiday_amount = sholiday_amount;
        this.lholiday_amount = lholiday_amount;
        this.late_amount = late_amount;
        this.wage = wage;
        this.philhealth_deduction = philhealth_deduction;
        this.sss_deduction = sss_deduction;
        this.pagibig_deduction = pagibig_deduction;
        this.efund_deduction = efund_deduction;
        this.other_deduction = other_deduction;
        this.salary_adjustment = salary_adjustment;
        this.allowance_adjustment = allowance_adjustment;
        this.other_compensations = other_compensations;
        this.total_deduction = total_deduction;
        this.gross_pay = gross_pay;
        this.net_pay = net_pay;
        this.sub_total = sub_total;
        this.grand_total = grand_total;
    }

    public PayrollClass(int employee_id, Date period_start, Date period_end, double days_present,
                        double overtime_hours, double nd_hours, double sholiday_hours, double lholiday_hours, double late_minutes){
        this.employee_id = employee_id;
        this.period_start = period_start;
        this.period_end = period_end;
        this.days_present = days_present;
        this.overtime_hours = overtime_hours;
        this.nd_hours = nd_hours;
        this.sholiday_hours = sholiday_hours;
        this.lholiday_hours = lholiday_hours;
        this.late_minutes = late_minutes;
    }

    @Override
    public String toString() {
        return "PayrollClass{" +
                "employee_id=" + employee_id +
                ", period_start='" + period_start + '\'' +
                ", period_end='" + period_end + '\'' +
                '}';
    }

    public PayrollClass(int employee_id, Date period_start, Date period_end, BigDecimal pay_rate){
        this.employee_id = employee_id;
        this.period_start = period_start;
        this.period_end = period_end;
        this.pay_rate = pay_rate;
    }

    //Getters


    public int getEmployee_id(){return employee_id;}

    public int getPayroll_id(){return payroll_id;}

    public Date getPeriod_start() {
        return period_start;
    }

    public Date getPeriod_end() {
        return period_end;
    }

    public double getDays_present() {
        return days_present;
    }

    public double getOvertime_hours() {
        return overtime_hours;
    }

    public double getNd_hours() {
        return nd_hours;
    }

    public double getSholiday_hours() {
        return sholiday_hours;
    }

    public double getLholiday_hours() {
        return lholiday_hours;
    }

    public double getLate_minutes() {
        return late_minutes;
    }

    public BigDecimal getOvertime_amount() {
        return overtime_amount;
    }

    public BigDecimal getNd_amount() {
        return nd_amount;
    }

    public BigDecimal getSholiday_amount() {
        return sholiday_amount;
    }

    public BigDecimal getLholiday_amount() {
        return lholiday_amount;
    }

    public BigDecimal getLate_amount() {
        return late_amount;
    }

    public BigDecimal getPayrate() {return pay_rate;}
    public BigDecimal getWage() {return wage;}

    public BigDecimal getPhilhealth_deduction() {
        return philhealth_deduction;
    }

    public BigDecimal getSss_deduction() {
        return sss_deduction;
    }

    public BigDecimal getPagibig_deduction() {
        return pagibig_deduction;
    }

    public BigDecimal getEfund_deduction() {
        return efund_deduction;
    }

    public BigDecimal getOther_deduction() {
        return other_deduction;
    }

    public BigDecimal getSalary_adjustment() {
        return salary_adjustment;
    }

    public BigDecimal getAllowance_adjustment() {
        return allowance_adjustment;
    }

    public BigDecimal getOther_compensations() {
        return other_compensations;
    }

    public BigDecimal getTotal_deduction() {
        return total_deduction;
    }

    public BigDecimal getGross_pay() {
        return gross_pay;
    }

    public BigDecimal getNet_pay() {
        return net_pay;
    }

    public BigDecimal getSub_total() {
        return sub_total;
    }

    public BigDecimal getGrand_total() {
        return grand_total;
    }

    //Setters
    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public void setPayroll_id(int payroll_id) {
        this.payroll_id = payroll_id;
    }

    public void setPeriod_start(Date period_start) {
        this.period_start = period_start;
    }

    public void setPeriod_end(Date period_end) {
        this.period_end = period_end;
    }

    public void setDays_present(double days_present) {
        this.days_present = days_present;
    }

    public void setOvertime_hours(double overtime_hours) {
        this.overtime_hours = overtime_hours;
    }

    public void setNd_hours(double nd_hours) {
        this.nd_hours = nd_hours;
    }

    public void setSholiday_hours(double sholiday_hours) {
        this.sholiday_hours = sholiday_hours;
    }

    public void setLholiday_hours(double lholiday_hours) {
        this.lholiday_hours = lholiday_hours;
    }

    public void setLate_minutes(double late_minutes) {
        this.late_minutes = late_minutes;
    }

    public void setOvertime_amount(BigDecimal overtime_amount) {
        this.overtime_amount = overtime_amount;
    }

    public void setNd_amount(BigDecimal nd_amount) {
        this.nd_amount = nd_amount;
    }

    public void setSholiday_amount(BigDecimal sholiday_amount) {
        this.sholiday_amount = sholiday_amount;
    }

    public void setLholiday_amount(BigDecimal lholiday_amount) {
        this.lholiday_amount = lholiday_amount;
    }

    public void setLate_amount(BigDecimal late_amount) {
        this.late_amount = late_amount;
    }

    public void setPayrate(BigDecimal pay_rate) {
        this.pay_rate = pay_rate;
    }

    public void setWage(BigDecimal wage) {
        this.wage = wage;
    }

    public void setPhilhealth_deduction(BigDecimal philhealth_deduction) {
        this.philhealth_deduction = philhealth_deduction;
    }

    public void setSss_deduction(BigDecimal sss_deduction) {
        this.sss_deduction = sss_deduction;
    }

    public void setPagibig_deduction(BigDecimal pagibig_deduction) {
        this.pagibig_deduction = pagibig_deduction;
    }

    public void setEfund_deduction(BigDecimal efund_deduction) {
        this.efund_deduction = efund_deduction;
    }

    public void setOther_deduction(BigDecimal other_deduction) {
        this.other_deduction = other_deduction;
    }

    public void setSalary_adjustment(BigDecimal salary_adjustment) {
        this.salary_adjustment = salary_adjustment;
    }

    public void setAllowance_adjustment(BigDecimal allowance_adjustment) {
        this.allowance_adjustment = allowance_adjustment;
    }

    public void setOther_compensations(BigDecimal other_compensations) {
        this.other_compensations = other_compensations;
    }

    public void setTotal_deduction(BigDecimal total_deduction) {
        this.total_deduction = total_deduction;
    }

    public void setGross_pay(BigDecimal gross_pay) {
        this.gross_pay = gross_pay;
    }

    public void setNet_pay(BigDecimal net_pay) {
        this.net_pay = net_pay;
    }

    public void setSub_total(BigDecimal sub_total) {
        this.sub_total = sub_total;
    }

    public void setGrand_total(BigDecimal grand_total) {
        this.grand_total = grand_total;
    }

}
