package Entity;

public class Timecard {
    private int timecard_id;
    private int employee_id;
    private String first_name;
    private String last_name;
    private String date;
    private String time_in;
    private String time_out;
    private String shift_start;
    private String shift_end;
    private float hours_clocked;
    private  float minutes_clocked;

    public Timecard(int timecard_id, int employee_id, String first_name,String last_name, String date, String time_in, String time_out,String shift_start, String shift_end, float hours_clocked, float minutes_clocked){
        this.timecard_id = timecard_id;
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date = date;
        this.time_in = time_in;
        this.time_out = time_out;
        this.shift_start = shift_start;
        this.shift_end = shift_end;
        this.hours_clocked = hours_clocked;
        this.minutes_clocked = minutes_clocked;
    }

    public int getTimecard_id(){return timecard_id;}
    public int getEmployee_id(){return employee_id;}
    public String getFirst_name(){return first_name;}
    public String getLast_name(){return last_name;}
    public String getDate(){return date;}
    public String getTime_in(){return time_in;}
    public String getTime_out(){return time_out;}
    public String getShift_start(){return shift_start;}
    public String getShift_end(){return shift_end;}
    public float getHours_clocked(){return hours_clocked;}
    public float getMinutes_clocked(){return minutes_clocked;}

}
