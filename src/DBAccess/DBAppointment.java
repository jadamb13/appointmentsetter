package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBAppointment {

    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try{
            // SQL statement
            String sql = "SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID, User_ID, appointments.Contact_ID\n" +
                    "FROM client_appointments.appointments JOIN contacts WHERE appointments.Contact_ID = contacts.Contact_ID;";

            // Create a PreparedStatement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Execute query and get results
            ResultSet rs = ps.executeQuery();

            // Work through result set one row at a time
            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                int customerId = rs.getInt("Customer_ID");
                int contactId = rs.getInt("Contact_ID");
                int userId = rs.getInt("User_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");

                Appointment a = new Appointment(appointmentId, customerId, contactId, userId, title, description, location, contact, type, start, end);
                appointmentList.add(a);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return appointmentList;
    }

    public static void createAppointment(int customerId, int contactId, int userId, String title, String description,
                                         String location, String type, Timestamp start, Timestamp end) throws SQLException {
        String sql = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Timestamp createDate = new Timestamp(System.currentTimeMillis());
        String createdBy = "script";
        String lastUpdatedBy = "script";
        Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, createDate);
        ps.setString(8, createdBy);
        ps.setTimestamp(9, lastUpdate);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerId);
        ps.setInt(12, userId);
        ps.setInt(13, contactId);

        ps.execute();


    }
}
