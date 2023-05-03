package model;

public class AppointmentDay {
    String dayOfWeek;
    int numberOfAppointmentsOnDay;

    public AppointmentDay(String dayOfWeek, int numberOfAppointmentsOnDay) {
        this.dayOfWeek = dayOfWeek;
        this.numberOfAppointmentsOnDay = numberOfAppointmentsOnDay;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getNumberOfAppointmentsOnDay() {
        return numberOfAppointmentsOnDay;
    }

    public void setNumberOfAppointmentsOnDay(int numberOfAppointmentsOnDay) {
        this.numberOfAppointmentsOnDay = numberOfAppointmentsOnDay;
    }
}
