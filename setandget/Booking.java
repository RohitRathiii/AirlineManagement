package model;

public class Booking {
    int userId;
    int scheduleId;
    int noOfTickets;
    
    public Booking(int userId, int scheduleId, int noOfTickets) {
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.noOfTickets = noOfTickets;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public int getUserId() {
        return userId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    @Override
    public String toString() {
        return "Booking [userId=" + userId + ", scheduleId=" + scheduleId + ", noOfTickets=" + noOfTickets + "]";
    }
}
