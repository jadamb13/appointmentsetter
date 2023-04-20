package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** A class to communicate with the DB about Customers. */
public class DBContact {

    /**
     Queries DB to gather data and create Contact objects to be added to ObservableList.

     @return contactList list of Customer objects representing data in the DB

     */
    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        try{

            String sql = "SELECT * FROM client_schedule.contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String emailAddress = rs.getString("Email");

                Contact c = new Contact(contactId, contactName, emailAddress);
                contactList.add(c);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return contactList;
    }

    /**
     Creates list of names representing all Contact objects.

     @return contactNames list of String objects representing names of all Contacts

     */
    public static ObservableList<String> getContactNames(){
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contact c : getAllContacts()){
            contactNames.add(c.getContactName());
        }
        return contactNames;
    }
}
