package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;
import model.Contact;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

    /**
     Gathers data entered by user and combines with generated fields to insert new Customer into DB.

     @param customerId ID representing customer
     @param customerName name of customer
     @param address address of customer
     @param postalCode postal code of customer
     @param phone phone number of customer
     @param divisionId ID representing division of country where customer lives

     */
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

    /**
     Gathers data entered by user and combines with generated fields to update Customer in DB.

     @param customerId ID representing customer
     @param customerName name of customer
     @param address address of customer
     @param postalCode postal code of customer
     @param phone phone number of customer
     @param divisionId ID representing division of country where customer lives

     */
    public static void updateCustomerInDb(int customerId, String customerName, String address, String postalCode,
                                          String phone, int divisionId){
        try {

            String sql = "Update customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, " +
                    "Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?," +
                    "Division_ID = ? WHERE Customer_ID = ?";

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
            ps.setInt(11, customerId);
            ps.execute();


        }catch (SQLException e){
            System.out.println("Caught ye updateCustomerInDb: " + e.getMessage());;
        }
    }

    /**
     Deletes Customer from database using the Customer ID.

     @param customerId Customer ID (Primary Key) to remove from database

     */
    public static void deleteCustomerInDb(int customerId){
        String customer = Customer.getCustomerNameById(customerId);


        try {
            String sql = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove " + customer + " as a customer? " +
                    "All appointments with this customer will also be canceled.");
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Customer will be deleted");

            Optional<ButtonType> result = confirm.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                // If user chooses to delete customer, delete all appointments associated with customer
                for (Appointment a : DBAppointment.getAllAppointmentsFromDb()) {
                    if (a.getCustomerId() == customerId) {
                        try{
                            String apptSql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
                            PreparedStatement ps2 = JDBC.getConnection().prepareStatement(apptSql);
                            ps2.setInt(1, a.getAppointmentId());
                            ps2.execute();
                        }catch (SQLException e){
                            System.out.println(e.getMessage());
                            System.out.println(e.getCause());
                        }
                    }

                }
                ps.execute();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer deleted");

            alert.setContentText("Customer record for " + customer + " has been deleted.");
            alert.show();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     Finds MAX Appointment_ID from database.

     @return nextCustomerId next integer after value of MAX(Customer_ID)

     */
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
