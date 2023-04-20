package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/** A class to communicate with the DB about Customers. */
public class DBCustomer {

    /**
     Queries DB to gather data and create Customer objects to be added to ObservableList.

     @return customerList list of Customer objects representing data in the DB

     */
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try{

            String sql = "SELECT * FROM client_schedule.customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");

                /* Not using now; may use later */
                //Timestamp createDate = rs.getTimestamp("Create_Date");
                //String createdBy = "script";
                //Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                //String lastUpdatedBy = "script";

                Customer c = new Customer(customerId, customerName, address, postalCode, phone, divisionId);
                customerList.add(c);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return customerList;
    }

}
