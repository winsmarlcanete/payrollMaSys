package Entity;

import java.sql.Date;

public class LeaveManagement {
    private int leaveId;
    private int employeeId;
    private String leaveType;
    private Date dateTaken;
    private int remainingSil;

    // Constructor
    public LeaveManagement(int leaveId, int employeeId, String leaveType, Date dateTaken, int remainingSil) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.dateTaken = dateTaken;
        this.remainingSil = remainingSil;
    }

    // Getters
    public int getLeaveId() {
        return leaveId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public int getRemainingSil() {
        return remainingSil;
    }


    // Setters
    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    public void setRemainingSil(int remainingSil) {
        this.remainingSil = remainingSil;
    }
}