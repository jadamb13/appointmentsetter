package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Timestamp;

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
    private Timestamp start;
    private Timestamp end;

    public Appointment(int appointmentId, int customerId, int contactId, int userId, String title, String description,
                       String location, String contactName, String type, Timestamp start, Timestamp end) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.contactId = contactId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactName = contactName;
        this.type = type;
        this.start = start;
        this.end = end;

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

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
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

}
