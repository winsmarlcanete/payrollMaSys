package Entity;

public class Userlog {
    private int log_id;
    private int user_id;
    private String action;
    private String time_stamp;
    private String user_name;
    private String action_time;

    // Constructor
    public Userlog(int log_id, int user_id, String action, String time_stamp, String user_name, String action_time){
        this.log_id = log_id;
        this.user_id = user_id;
        this.action = action;
        this.time_stamp = time_stamp;
        this.user_name = user_name;
        this.action_time = action_time;
    }

    //Getters and Setters
    public int getLog_id(){return log_id;}
    public int getUser_id(){return user_id;}
    public String getAction(){return action;}
    public String getTime_stamp(){return time_stamp;}
    public String getUser_name(){return user_name;}
    public String getAction_time(){return action_time;}
}
