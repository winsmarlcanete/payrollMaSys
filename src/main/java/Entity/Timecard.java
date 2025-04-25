package Entity;

public class Timecard {
    private int timecard_id;
    private int employee_id;

    private String date;
    private String time_in;
    private String time_out;

    private float hours_clocked;
    private  float minutes_clocked;

    //Constructor
    public Timecard(int timecard_id, int employee_id, String date, String time_in, String time_out, float hours_clocked, float minutes_clocked){
        this.timecard_id = timecard_id;
        this.employee_id = employee_id;
        this.date = date;
        this.time_in = time_in;
        this.time_out = time_out;
        this.hours_clocked = hours_clocked;
        this.minutes_clocked = minutes_clocked;
    }
    //Getters and Setters
    public int getTimecard_id(){return timecard_id;}
    public int getEmployee_id(){return employee_id;}

    public String getDate(){return date;}
    public String getTime_in(){return time_in;}
    public String getTime_out(){return time_out;}

    public float getHours_clocked(){return hours_clocked;}
    public float getMinutes_clocked(){return minutes_clocked;}

}
