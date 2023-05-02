package model;

import DBAccess.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Class representing an Appointment object */
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

    /* Getters and Setters */
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

    /**
     Counts and groups appointments by month.

     @return appointmentsByMonth ObservableList of AppointmentMonth objects representing
     unique months and the number of appointments for each month

     */
    public static ObservableList<AppointmentMonth> getAppointmentsByMonth(){
        ObservableList<AppointmentMonth> appointmentsByMonth = FXCollections.observableArrayList();
        List<String> months = new ArrayList<>();
        for (Appointment a : DBAppointment.getAllAppointmentsFromDb()){
            String monthNumber = a.getStartDate().substring(0, 2);
            //String year = a.getStartDate().substring(7, 11);
            String monthName = String.valueOf(Month.of(Integer.parseInt(monthNumber)));
            months.add(monthName);

        }
        List<String> uniqueMonths = new ArrayList<>();
        for (int i = 0; i < months.size(); i++){
            if (!uniqueMonths.contains(months.get(i))){
                uniqueMonths.add(months.get(i));
            }
        }
        List<Integer> frequencies = new ArrayList<>();
        for (int i = 0; i < uniqueMonths.size(); i++){
            frequencies.add(Collections.frequency(months, uniqueMonths.get(i)));
        }
        for (int i = 0; i < uniqueMonths.size(); i++){
            AppointmentMonth am = new AppointmentMonth(uniqueMonths.get(i), frequencies.get(i));
            appointmentsByMonth.add(am);
        }

        return appointmentsByMonth;
    }

    /**
     Determines appointments times available based on user ZonedDateTime and EST business hours.

     @return timeList list of String objects representing times available for appointments

     */
    public static ObservableList<String> getAppointmentTimes() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a"); // formatter for times added to timesList
        ObservableList<String> timesList = FXCollections.observableArrayList();

        // Get the current ZoneId, Date, and Time in the user's time zone and created ZonedDateTime
        ZoneId userZoneId = ZoneId.systemDefault();
        LocalDate userDate = LocalDate.now(userZoneId);
        LocalTime userTime = LocalTime.now(userZoneId);
        ZonedDateTime userZDT = ZonedDateTime.of(userDate, userTime, userZoneId).truncatedTo(ChronoUnit.SECONDS);

        // Get the current ZoneId, Date, and Time in the EST time zone and created ZonedDateTime
        ZoneId estZoneId = ZoneId.of("America/New_York");
        LocalDate estDate = LocalDate.now(estZoneId);
        LocalTime estTime = LocalTime.now(estZoneId);
        ZonedDateTime estZDT = ZonedDateTime.of(estDate, estTime, estZoneId).truncatedTo(ChronoUnit.SECONDS);

        /* Get difference between user's system time and EST */
        // Get the offsets for each time zone
        ZoneOffset userOffset = userZDT.getOffset();
        ZoneOffset estOffset = estZDT.getOffset();

        // Calculate the difference between the timezone offsets
        Duration offsetDifference = Duration.ofSeconds(userOffset.getTotalSeconds() - estOffset.getTotalSeconds());
        long minutesDifference = offsetDifference.toMinutes();
        double hoursDifference = offsetDifference.toHours();

        // LocalTime to determine end of loop that populates timesList
        LocalTime end;

        // Set times in list based on difference between time of User's local time and EST
        if(hoursDifference % 1 == 0){ // if the difference in timezone offset is a whole number
            // Last available appointment (10pm EST) converted to time of user
            end = LocalTime.of((22 + (int)hoursDifference) % 24, 0);
            if(22 + (int)hoursDifference > 24){
                // The date has changed
            }
        }else { // if the difference in timezone offset is fractional
            // Last available appointment (10pm EST) converted to time of user including quarter hours
            end = LocalTime.of((22 + (int)hoursDifference) % 24, (int)minutesDifference/60);
            if(22 + (int)hoursDifference > 24){
                // The date has changed
            }
        }

        // Determine start time of timeList loop by using the user's current time and rounding to the next quarter hour
        LocalTime ltNow = userZDT.toLocalDateTime().toLocalTime();
        int minutesToNextQuarterHour = 15 - ltNow.getMinute() % 15;

        LocalTime time = ltNow.plusMinutes(minutesToNextQuarterHour); // first time to be added to timesList and incremented
        for(int i = 0; i < 48; i++){ // 48 possible appointment times between 8am-10pm EST
            LocalTime ltStartWithoutSeconds = time.truncatedTo(ChronoUnit.MINUTES);
            LocalTime endWithoutSeconds = end.truncatedTo(ChronoUnit.MINUTES);
            if(ltStartWithoutSeconds.compareTo(endWithoutSeconds) == 0){ // local time corresponding with 10pm EST has been reached
                String timeString = time.format(formatter);
                timesList.add(timeString);
                break; // break out of for loop after adding last time to list
            }

            // Format time for timesList
            String timeString = time.format(formatter);
            timesList.add(timeString);
            time = time.plusMinutes(15); // add appointment times in 15 minute increments
        }
        return timesList;
    }

}
