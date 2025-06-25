package Entity;

import Config.ZkFinger;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Time;

public class Employee {
    private int employee_id;
    private String last_name;
    private String first_name;
    private String middle_name;
    private String tin_number;
    private String philhealth_number;
    private BigDecimal philhealth_percentage;
    private String pagibig_number;
    private BigDecimal pagibig_percentage;
    private String sss_number;
    private BigDecimal sss_percentage;
    private BigDecimal efund_amount;
    private BigDecimal other_deductions;
    private BigDecimal salary_adj_percentage;
    private BigDecimal allowance_amount;
    private BigDecimal other_comp_amount;
    private BigDecimal pay_rate;
    private String employment_status;
    private String department;
    private Time shift_start;
    private Time shift_end;
    private ZkFinger.FingerprintTemplate fingerprint;

    // Constructor

    public Employee(int employee_id, String last_name, String first_name, String middle_name, String tin_number, String philhealth_number, String pagibig_number, BigDecimal pay_rate, String employment_status, String department, Time shift_start, Time shift_end ){
        this.employee_id = employee_id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.tin_number = tin_number;
        this.philhealth_number = philhealth_number;
        this.pagibig_number = pagibig_number;
        this.pay_rate = pay_rate;
        this.employment_status = employment_status;
        this.department = department;
        this.shift_start = shift_start;
        this.shift_end = shift_end;
    }

    public Employee(int employee_id, String last_name, String first_name, BigDecimal pay_rate, String department){
            this.employee_id = employee_id;
            this.last_name = last_name;
            this.first_name = first_name;
            this.pay_rate = pay_rate;
            this.department = department;

    }

    public Employee(int employee_id, String last_name, String first_name, String middle_name, BigDecimal pay_rate, String department, String employment_status){
        this.employee_id = employee_id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.pay_rate = pay_rate;
        this.department = department;
        this.employment_status = employment_status;

    }

    public Employee(String first_name, String last_name, String middle_name, String department, String employment_status, BigDecimal pay_rate, String tin_number, String philhealth_number, BigDecimal philhealth_percentage, String pagibig_number,BigDecimal pagibig_percentage, String sss_number, BigDecimal sss_percentage, BigDecimal efund_amount, BigDecimal other_deductions, Time shift_start, Time shift_end, ZkFinger.FingerprintTemplate fingerprint){
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.department = department;
        this.employment_status = employment_status;
        this.pay_rate = pay_rate;
        this.tin_number = tin_number;
        this.philhealth_number = philhealth_number;
        this.philhealth_percentage = philhealth_percentage;
        this.pagibig_percentage = pagibig_percentage;
        this.sss_percentage = sss_percentage;
        this.efund_amount = efund_amount;
        this.other_deductions = other_deductions;
        this.pagibig_number = pagibig_number;
        this.sss_number = sss_number;
        this.shift_start = shift_start;
        this.shift_end = shift_end;
        this.fingerprint = fingerprint;
    }

    public Employee(int employee_id, String last_name, String first_name, String middle_name, String department,
                    String employment_status, BigDecimal pay_rate, BigDecimal sss_percentage, BigDecimal philhealth_percentage,
                    BigDecimal pagibig_percentage, BigDecimal efund_amount, BigDecimal other_deductions, Time shift_start, Time shift_end) {
        this.employee_id = employee_id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.department = department;
        this.employment_status = employment_status;
        this.pay_rate = pay_rate;
        this.sss_percentage = sss_percentage;
        this.philhealth_percentage = philhealth_percentage;
        this.pagibig_percentage = pagibig_percentage;
        this.efund_amount = efund_amount;
        this.other_deductions = other_deductions;
        this.shift_start = shift_start;
        this.shift_end = shift_end;
    }

    public Employee(String firstName, String lastName, String middleName, String department,
                    String employmentStatus, BigDecimal rate, String tin, String philhealth,
                    BigDecimal philhealthPercentage, String pagibig, BigDecimal pagibigPercentage,
                    String sss, BigDecimal sssPercentage, BigDecimal efundAmount, BigDecimal otherDeductions,
                    BigDecimal salaryAdjPercentage, BigDecimal allowanceAmount, BigDecimal otherCompAmount,
                    Time shiftStart, Time shiftEnd) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.middle_name = middleName;
        this.department = department;
        this.employment_status = employmentStatus;
        this.pay_rate = rate;
        this.tin_number = tin;
        this.philhealth_number = philhealth;
        this.philhealth_percentage = philhealthPercentage;
        this.pagibig_number = pagibig;
        this.pagibig_percentage = pagibigPercentage;
        this.sss_number = sss;
        this.sss_percentage = sssPercentage;
        this.efund_amount = efundAmount;
        this.other_deductions = otherDeductions;
        this.salary_adj_percentage = salaryAdjPercentage;
        this.allowance_amount = allowanceAmount;
        this.other_comp_amount = otherCompAmount;
        this.shift_start = shiftStart;
        this.shift_end = shiftEnd;
    }

    public Employee(int employee_id, String last_name, String first_name, String middle_name, String department,
                    String employment_status, BigDecimal pay_rate, BigDecimal sss_percentage, BigDecimal philhealth_percentage,
                    BigDecimal pagibig_percentage, BigDecimal efund_amount, BigDecimal other_deductions, Time shift_start,
                    Time shift_end, BigDecimal salary_adj_percentage, BigDecimal allowance_amount, BigDecimal other_comp_amount) {
        this.employee_id = employee_id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.department = department;
        this.employment_status = employment_status;
        this.pay_rate = pay_rate;
        this.sss_percentage = sss_percentage;
        this.philhealth_percentage = philhealth_percentage;
        this.pagibig_percentage = pagibig_percentage;
        this.efund_amount = efund_amount;
        this.other_deductions = other_deductions;
        this.shift_start = shift_start;
        this.shift_end = shift_end;
        this.salary_adj_percentage = salary_adj_percentage;
        this.allowance_amount = allowance_amount;
        this.other_comp_amount = other_comp_amount;
    }





    //Getters and Setters
    public int getEmployee_id() {return employee_id;}
    public String  getLast_name(){return last_name;}
    public String getFirst_name(){return  first_name;}
    public String getMiddle_name(){return middle_name;}
    public  String getTin_number(){return tin_number;}
    public String getPhilhealth_number(){return philhealth_number;}
    public BigDecimal getPhilhealth_percentage() {return philhealth_percentage;}
    public String getPagibig_number() {return pagibig_number;}
    public BigDecimal getPagibig_percentage() {return pagibig_percentage;}
    public String getSss_number(){return sss_number;}
    public BigDecimal getSss_percentage() {return sss_percentage;}
    public BigDecimal getEfund_amount() {return efund_amount;}
    public BigDecimal getOther_deductions() {return other_deductions;}
    public BigDecimal getSalary_adj_percentage() {return salary_adj_percentage;}
    public BigDecimal getAllowance_amount() {return allowance_amount;}
    public BigDecimal getOther_comp_amount() {return other_comp_amount;}
    public BigDecimal getPay_rate(){return pay_rate;}
    public String getEmployment_status(){return employment_status;}
    public String getDepartment(){return department;}
    public Time getShift_start(){return shift_start;}
    public Time getShift_end(){return shift_end;}
    public ZkFinger.FingerprintTemplate getFingerprint(){return  fingerprint;}


}
