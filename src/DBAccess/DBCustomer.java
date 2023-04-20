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

public class DBCustomer {

    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try{
            // SQL statement
            String sql = "SELECT * FROM client_schedule.customers";

            // Create a PreparedStatement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Execute query and get results
            ResultSet rs = ps.executeQuery();

            // Work through result set one row at a time
            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                //Timestamp createDate = rs.getTimestamp("Create_Date");
                //String createdBy = "script";
                //Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                //String lastUpdatedBy = "script";
                int divisionId = rs.getInt("Division_ID");

                Customer c = new Customer(customerId, customerName, address, postalCode, phone, divisionId);
                customerList.add(c);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return customerList;
    }

}
