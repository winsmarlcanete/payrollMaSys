package Entity;

public class Employee {
    private int employee_id;
    private String last_name;
    private String first_name;
    private String middle_name;
    private int tin_number;
    private int philhealth_number;
    private float pay_rate;
    private String employment_status;
    private String department;
    private String shift_start;
    private String shift_end;

    // Constructor

    public Employee(int employee_id, String last_name, String first_name, String middle_name, int tin_number, int philhealth_number, float pay_rate, String employment_status, String department, String shift_start, String shift_end ){
        this.employee_id = employee_id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.tin_number = tin_number;
        this.philhealth_number = philhealth_number;
        this.pay_rate = pay_rate;
        this.employment_status = employment_status;
        this.department = department;
        this.shift_start = shift_start;
        this.shift_end = shift_end;
    }

    //Getters and Setters
    public int getEmployee_id() {return employee_id;}
    public String  getLast_name(){return last_name;}
    public String getFirst_name(){return  first_name;}
    public String getMiddle_name(){return middle_name;}
    public  int getTin_number(){return tin_number;}
    public int getPhilhealth_number(){return philhealth_number;}
    public float getPay_rate(){return pay_rate;}
    public String getEmployment_status(){return employment_status;}
    public String getDepartment(){return department;}
    public String getShift_start(){return shift_start;}
    public String getShift_end(){return shift_end;}
}
