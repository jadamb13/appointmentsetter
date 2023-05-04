package model;

import DBAccess.DBAppointment;
import DBAccess.DBContact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class representing a Contact object */
public class Contact {
    private int contactId;
    private String contactName;
    private String contactEmail;


    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /* Getters and Setters */
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     Creates list of names representing all Contact objects.

     @return contactNames list of String objects representing names of all Contacts

     */
    public static ObservableList<String> getAllContactNames(){
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contact c : DBContact.getAllContacts()){
            contactNames.add(c.getContactName());
        }
        return contactNames;
    }

    /**
     Gets Contact ID associated with a name.

     @param name name of Contact
     @return c.getContact() contactID of contact with the parameter name; or -1 if none found

     */
    public static int getContactIdByName(String name){
        for (Contact c : DBContact.getAllContacts()){
            if(c.contactName.equals(name)){
                return c.getContactId();
            }

        }
        return -1;
    }

    /**
     Gets Name associated with a Contact ID.

     @param id contact id
     @return c.getContactName() Contact name associated with the parameter id

     */
    public static String getContactNameById(int id){
        for (Contact c : DBContact.getAllContacts()){
            if(c.getContactId() == id){
                return c.getContactName();
            }
        }
        return "";
    }

    /**
     Gets ObservableList of Appointments associated with contact.

     @param newValue name of Contact selected from combo box
     @return contactAppointments ObservableList of Appointments associated with Contact

     */
    public static ObservableList<Appointment> getContactAppointments(String newValue) {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        if (newValue != null) {
            Contact contact = null;

            // Find which Contact object is responsible for appointment
            for (Contact c : DBContact.getAllContacts()) {
                if (c.getContactName().equals(newValue)) {
                    contact = c;
                }
            }
            if(contact != null){
                // If appointment is associated with this Contact, add to contactAppointments
                for (Appointment a : DBAppointment.getAllAppointmentsFromDb()) {
                    if (a.getContact().equals(contact.getContactName())) {
                        contactAppointments.add(a);
                    }
                }
            }
        }
        return contactAppointments;
    }

}
