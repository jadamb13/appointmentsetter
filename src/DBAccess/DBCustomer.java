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
    public static ObservableList<Customer> getAllCustomersFromDb(){
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

    public static void insertCustomer(int customerId, String customerName, String address, String postalCode,
                                         String phone, int divisionId) {
        try {

            // Prepared statement to insert new Appointment into DB
            String sql = "INSERT INTO customers VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Same every time
            Timestamp createDate = new Timestamp(System.currentTimeMillis());
            String createdBy = "script";
            String lastUpdatedBy = "script";
            Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setString(2, customerName);
            ps.setString(3, address);
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            ps.setTimestamp(6, createDate);
            ps.setString(7, createdBy);
            ps.setTimestamp(8, lastUpdate);
            ps.setString(9, lastUpdatedBy);
            ps.setInt(10, divisionId);

            ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void deleteCustomerInDb(int customerId){
        try {
            // Get maximum Appointment ID already in table and set new Appointment ID to (max + 1)
            String sql = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.execute();

        }catch(SQLException e){
            System.out.println("Caught yas.");
        }
    }

    public static int getNextCustomerId(){
        int nextCustomerId = -1;
        try{
            // Get maximum Customer ID already in table and set new Appointment ID to (max + 1)
            String max_sql = "SELECT MAX(Customer_ID) as MAX FROM customers";
            PreparedStatement maxPs = JDBC.getConnection().prepareStatement(max_sql);
            ResultSet rs = maxPs.executeQuery();
            rs.next();
            nextCustomerId = rs.getInt("MAX") + 1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nextCustomerId;
    }
}
