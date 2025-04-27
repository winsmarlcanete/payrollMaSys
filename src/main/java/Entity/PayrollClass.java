package Entity;

public class PayrollClass {

    private int employee_id;
    private String period_start;
    private String period_end;
    private float days_present;
    private float overtime_hours;
    private float nd_hours;
    private float sholiday_hours;
    private float lholiday_hours;
    private float late_minutes;
    private float overtime_amount;
    private float nd_amount;
    private float sholiday_amount;
    private float lholiday_amount;
    private float late_amount;
    private float wage;
    private float philhealth_deduction;
    private float sss_deduction;
    private float pagibig_deduction;
    private float efund_deduction;
    private float other_deduction;
    private float salary_adjustment;
    private float allowance_adjustment;
    private float other_compensations;
    private float total_deduction;
    private float gross_pay;
    private float net_pay;
    private float sub_total;
    private float grand_total;

    // Constructor

    public PayrollClass(int employee_id, String period_start, String period_end, float days_present,
                        float overtime_hours, float nd_hours, float sholiday_hours, float lholiday_hours, float late_minutes, float overtime_amount, float nd_amount, float sholiday_amount,
                        float lholiday_amount, float late_amount, float wage, float philhealth_deduction, float sss_deduction, float pagibig_deduction, float efund_deduction, float other_deduction,
                        float salary_adjustment, float allowance_adjustment, float other_compensations, float total_deduction, float gross_pay, float net_pay, float sub_total, float grand_total){

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

    //Getters and Setters


    public int getEmployee_id(){return employee_id;}



    public String getPeriod_start() {
        return period_start;
    }

    public String getPeriod_end() {
        return period_end;
    }

    public float getDays_present() {
        return days_present;
    }

    public float getOvertime_hours() {
        return overtime_hours;
    }

    public float getNd_hours() {
        return nd_hours;
    }

    public float getSholiday_hours() {
        return sholiday_hours;
    }

    public float getLholiday_hours() {
        return lholiday_hours;
    }

    public float getLate_minutes() {
        return late_minutes;
    }

    public float getOvertime_amount() {
        return overtime_amount;
    }

    public float getNd_amount() {
        return nd_amount;
    }

    public float getSholiday_amount() {
        return sholiday_amount;
    }

    public float getLholiday_amount() {
        return lholiday_amount;
    }

    public float getLate_amount() {
        return late_amount;
    }

    public float getWage() {
        return wage;
    }

    public float getPhilhealth_deduction() {
        return philhealth_deduction;
    }

    public float getSss_deduction() {
        return sss_deduction;
    }

    public float getPagibig_deduction() {
        return pagibig_deduction;
    }

    public float getEfund_deduction() {
        return efund_deduction;
    }

    public float getOther_deduction() {
        return other_deduction;
    }

    public float getSalary_adjustment() {
        return salary_adjustment;
    }

    public float getAllowance_adjustment() {
        return allowance_adjustment;
    }

    public float getOther_compensations() {
        return other_compensations;
    }

    public float getTotal_deduction() {
        return total_deduction;
    }

    public float getGross_pay() {
        return gross_pay;
    }

    public float getNet_pay() {
        return net_pay;
    }

    public float getSub_total() {
        return sub_total;
    }

    public float getGrand_total() {
        return grand_total;
    }
}
