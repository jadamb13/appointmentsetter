package model;

/** Class representing an Appointment Month object */
public class AppointmentMonth {

    private String month;
    private int countByMonth;

    public AppointmentMonth(String month, int countByMonth) {
        this.month = month;
        this.countByMonth = countByMonth;
    }

    /* Getters and Setters */
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getCountByMonth() {
        return countByMonth;
    }

    public void setCountByMonth(int countByMonth) {
        this.countByMonth = countByMonth;
    }
}
