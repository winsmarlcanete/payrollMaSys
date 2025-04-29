package Entity;

public class PayrollClass {

    private int employee_id;
    private String period_start;
    private String period_end;
    private double days_present;
    private double overtime_hours;
    private double nd_hours;
    private double sholiday_hours;
    private double lholiday_hours;
    private double late_minutes;
    private double overtime_amount;
    private double nd_amount;
    private double sholiday_amount;
    private double lholiday_amount;
    private double late_amount;
    private double wage;
    private double philhealth_deduction;
    private double sss_deduction;
    private double pagibig_deduction;
    private double efund_deduction;
    private double other_deduction;
    private double salary_adjustment;
    private double allowance_adjustment;
    private double other_compensations;
    private double total_deduction;
    private double gross_pay;
    private double net_pay;
    private double sub_total;
    private double grand_total;

    // Constructor

    public PayrollClass(int employee_id, String period_start, String period_end, double days_present,
                        double overtime_hours, double nd_hours, double sholiday_hours, double lholiday_hours, double late_minutes, double overtime_amount, double nd_amount, double sholiday_amount,
                        double lholiday_amount, double late_amount, double wage, double philhealth_deduction, double sss_deduction, double pagibig_deduction, double efund_deduction, double other_deduction,
                        double salary_adjustment, double allowance_adjustment, double other_compensations, double total_deduction, double gross_pay, double net_pay, double sub_total, double grand_total){

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

    public double getOvertime_amount() {
        return overtime_amount;
    }

    public double getNd_amount() {
        return nd_amount;
    }

    public double getSholiday_amount() {
        return sholiday_amount;
    }

    public double getLholiday_amount() {
        return lholiday_amount;
    }

    public double getLate_amount() {
        return late_amount;
    }

    public double getWage() {
        return wage;
    }

    public double getPhilhealth_deduction() {
        return philhealth_deduction;
    }

    public double getSss_deduction() {
        return sss_deduction;
    }

    public double getPagibig_deduction() {
        return pagibig_deduction;
    }

    public double getEfund_deduction() {
        return efund_deduction;
    }

    public double getOther_deduction() {
        return other_deduction;
    }

    public double getSalary_adjustment() {
        return salary_adjustment;
    }

    public double getAllowance_adjustment() {
        return allowance_adjustment;
    }

    public double getOther_compensations() {
        return other_compensations;
    }

    public double getTotal_deduction() {
        return total_deduction;
    }

    public double getGross_pay() {
        return gross_pay;
    }

    public double getNet_pay() {
        return net_pay;
    }

    public double getSub_total() {
        return sub_total;
    }

    public double getGrand_total() {
        return grand_total;
    }
}
