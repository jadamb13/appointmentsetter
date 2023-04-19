package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContact {

    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        try{
            // SQL statement
            String sql = "SELECT * FROM client_appointments.contacts";

            // Create a PreparedStatement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Execute query and get results
            ResultSet rs = ps.executeQuery();

            // Work through result set one row at a time
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

    public static ObservableList<String> getContactNames(){
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contact c : DBContact.getAllContacts()){
            contactNames.add(c.getContactName());
        }
        return contactNames;
    }
}
