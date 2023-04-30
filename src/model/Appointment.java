package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

    private int appointmentId;
    private int customerId;
    private int contactId;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contactName;
    private String type;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;

    public Appointment(int appointmentId, int customerId, int contactId, int userId, String title, String description,
                       String location, String contactName, String type, String startDate, String endDate,
                       String startTime, String endTime) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.contactId = contactId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactName = contactName;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public int getAppointmentId() {
        return appointmentId;
    }


    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contactName;
    }

    public void setContact(String contactName) {
        this.contactName = contactName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate(){
        return startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     Sets "Type" of Appointment that can be selected for new or updated Appointments.

     @return appointmentTypes list of String objects representing types of Appointments

     */
    public static ObservableList<String> getAppointmentTypes(){
        ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
        appointmentTypes.add("De-Briefing");
        appointmentTypes.add("Planning Session");
        appointmentTypes.add("Strategy Session");
        appointmentTypes.add("Brainstorm Session");
        appointmentTypes.add("Other");
        return appointmentTypes;
    }

    public static ObservableList<String> getAppointmentTimes() {
        ObservableList<String> timeList = FXCollections.observableArrayList();

        // Define the EST time range we want to use
        LocalTime estStartTime = LocalTime.of(8, 0);
        LocalTime estEndTime = LocalTime.of(22, 0);

        // Get the user's timezone
        ZoneId userZone = ZoneId.systemDefault();

        // Get the current time in the user's timezone
        ZonedDateTime now = ZonedDateTime.now(userZone);

        // Convert the user's time to EST
        ZonedDateTime estTime = now.withZoneSameInstant(ZoneId.of("America/New_York"));

        // Calculate the time difference between EST and the user's timezone
        ZoneOffset offset = estTime.getOffset();
        int offsetHours = offset.getTotalSeconds() / 3600;

        // Calculate the start and end times in the user's timezone
        LocalTime userStartTime = estStartTime.plusHours(offsetHours);
        LocalTime userEndTime = estEndTime.plusHours(offsetHours);

        // Generate the list of times in the user's timezone
        LocalTime currentTime = userStartTime;
        while (currentTime.isBefore(userEndTime)) {
            String timeString = currentTime.format(DateTimeFormatter.ofPattern("h:mm a"));
            timeList.add(timeString);
            currentTime = currentTime.plusMinutes(15);
        }

        /*
        // add 10:00 pm (EST) as the last time to the list
        if (end.withZoneSameInstant(userZone).toLocalTime().isAfter(userTime)) {
            String lastTime = end.withZoneSameInstant(userZone).toLocalTime().format(formatter);
            timesList.add(lastTime);
        }
        */

        return timeList;
    }



}
