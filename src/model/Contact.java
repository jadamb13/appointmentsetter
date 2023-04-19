package model;

import DBAccess.DBContact;
import DBAccess.DBCustomer;

public class Contact {
    private int contactId;
    private String contactName;
    private String contactEmail;

    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

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

    public static int getContactIdByName(String name){
        for (Contact c : DBContact.getAllContacts()){
            if(c.contactName.equals(name)){
                return c.getContactId();
            }

        }
        return -1;
    }
}
