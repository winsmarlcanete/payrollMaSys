package Entity;

public class Reports {
    private int report_id;
    private int user_id;
    private String report_type;
    private String creation_date;

    //Constructor

    public Reports(int report_id, int user_id, String report_type, String creation_date){
        this.report_id = report_id;
        this.user_id = user_id;
        this.report_type = report_type;
        this.creation_date = creation_date;
    }

    //Getters and Setters
    public int getReport_id(){return report_id;}
    public int getUser_id(){return user_id;}
    public String getReport_type(){return report_type;}
    public String getCreation_date(){return creation_date;}


}
