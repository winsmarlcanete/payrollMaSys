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
    private String pagibig_number;
    private String sss_number;
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

    public Employee(String first_name, String last_name, String middle_name, String department, String employment_status, BigDecimal pay_rate, String tin_number, String philhealth_number, String pagibig_number, String sss_number, Time shift_start, Time shift_end, ZkFinger.FingerprintTemplate fingerprint){
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.department = department;
        this.employment_status = employment_status;
        this.pay_rate = pay_rate;
        this.tin_number = tin_number;
        this.philhealth_number = philhealth_number;
        this.pagibig_number = pagibig_number;
        this.sss_number = sss_number;
        this.shift_start = shift_start;
        this.shift_end = shift_end;
        this.fingerprint = fingerprint;
    }

    //Getters and Setters
    public int getEmployee_id() {return employee_id;}
    public String  getLast_name(){return last_name;}
    public String getFirst_name(){return  first_name;}
    public String getMiddle_name(){return middle_name;}
    public  String getTin_number(){return tin_number;}
    public String getPhilhealth_number(){return philhealth_number;}
    public String getPagibig_number() {return pagibig_number;}
    public String getSss_number(){return sss_number;}
    public BigDecimal getPay_rate(){return pay_rate;}
    public String getEmployment_status(){return employment_status;}
    public String getDepartment(){return department;}
    public Time getShift_start(){return shift_start;}
    public Time getShift_end(){return shift_end;}
    public ZkFinger.FingerprintTemplate getFingerprint(){return  fingerprint;}


}
