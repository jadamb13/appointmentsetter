package model;

/** Class representing an Appointment Type object */
public class AppointmentType {

    private String type;
    private int countOfType;

    public AppointmentType(String type, int countOfType) {
        this.type = type;
        this.countOfType = countOfType;
    }

    /* Getters and Setters */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCountOfType() {
        return countOfType;
    }

    public void setCountOfType(int countOfType) {
        this.countOfType = countOfType;
    }
}
