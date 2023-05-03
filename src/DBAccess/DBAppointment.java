package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/** A class to communicate with the DB about Appointments. */
public class DBAppointment {

    private static ObservableList<Appointment> appointmentList = getAllAppointmentsFromDb();

    /**
     Queries DB to gather data and create Appointment objects to be added to ObservableList.

     @return appointmentList list of Appointment objects representing data in the DB

     */
    public static ObservableList<Appointment> getAllAppointmentsFromDb(){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        try{
            // SQL statement
            String sql = "SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID, User_ID, appointments.Contact_ID\n" +
                    "FROM client_schedule.appointments JOIN contacts WHERE appointments.Contact_ID = contacts.Contact_ID;";

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

                // Convert Timestamp to LocalDate and LocalTime
                LocalDate startDate = start.toLocalDateTime().toLocalDate();
                LocalDate endDate = end.toLocalDateTime().toLocalDate();
                LocalTime startTime = start.toLocalDateTime().toLocalTime();
                LocalTime endTime = end.toLocalDateTime().toLocalTime();

                // Convert LD and LT to DateTimeFormatter strings for display in UI
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

                Appointment a = new Appointment(appointmentId, customerId, contactId, userId, title, description, location,
                        contact, type, dateFormatter.format(startDate), dateFormatter.format(endDate),
                        timeFormatter.format(startTime), timeFormatter.format(endTime));
                appointmentList.add(a);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return appointmentList;
    }

    /**
     Gathers data entered by user and combines with generated fields to insert new Appointments into DB.

     @param customerId ID representing customer associated with appointment
     @param contactId ID representing Contact associated with appointment
     @param userId ID of user creating the appointment
     @param title title of appointment
     @param description description of appointment
     @param location location of appointment
     @param type type of appointment
     @param start start Timestamp of appointment
     @param end end Timestamp of appointment

     @return alertFlag boolean value representing if there are alerts preventing the insertion

     */
    public static boolean insertAppointment(int customerId, int contactId, int userId, String title, String description,
                                         String location, String type, Timestamp start, Timestamp end) {
        boolean alertFlag = false;
        try {
            // Get maximum Appointment ID already in table and set new Appointment ID to (max + 1)
            String max_sql = "SELECT MAX(Appointment_ID) as MAX FROM appointments";
            PreparedStatement maxPs = JDBC.getConnection().prepareStatement(max_sql);
            ResultSet rs = maxPs.executeQuery();
            rs.next();
            int appointmentId = rs.getInt("MAX") + 1;

            // Prepared statement to insert new Appointment into DB
            String sql = "INSERT INTO appointments VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Same every time
            Timestamp createDate = new Timestamp(System.currentTimeMillis());
            String createdBy = "script";
            String lastUpdatedBy = "script";
            Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setTimestamp(6, start);
            ps.setTimestamp(7, end);
            ps.setTimestamp(8, createDate);
            ps.setString(9, createdBy);
            ps.setTimestamp(10, lastUpdate);
            ps.setString(11, lastUpdatedBy);
            ps.setInt(12, customerId);
            ps.setInt(13, userId);
            ps.setInt(14, contactId);


            // Checks for overlapping appointments
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a MM-dd-yyyy");
            LocalDateTime newApptStartTime = start.toLocalDateTime();
            LocalDateTime newApptEndTime = end.toLocalDateTime();

            for(Appointment a : getAllAppointmentsFromDb()) {
                LocalDateTime thisApptStart = LocalDateTime.parse(a.getStartTime() + " " + a.getStartDate(), formatter);
                LocalDateTime thisApptEnd = LocalDateTime.parse(a.getEndTime() + " " + a.getEndDate(), formatter);
                // if this appointment belongs to the same customer
                if (a.getCustomerId() == customerId && thisApptStart.getYear() == newApptStartTime.getYear()) {
                    // and new appt would start during this already scheduled appt
                    if ((newApptStartTime.compareTo(thisApptStart) > 0) && (newApptStartTime.compareTo(thisApptEnd) < 0)) {
                        alertFlag = true;
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Overlapping appointments");
                        alert.setTitle("Error scheduling appointment");
                        alert.setContentText("An appointment cannot be scheduled at the specified time because it " +
                                "overlaps with an appointment already scheduled for this customer.");
                        alert.show();
                    } else if (newApptEndTime.compareTo(thisApptStart) > 0) { // or end of new appt goes beyond the start of this already scheduled appt
                        alertFlag = true;
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Overlapping appointments");
                        alert.setTitle("Error scheduling appointment");
                        alert.setContentText("An appointment cannot be scheduled at the specified time because it " +
                                "overlaps with an appointment already scheduled for this customer.");
                        alert.show();

                    } else if (newApptStartTime.compareTo(thisApptStart) == 0) { // or start of new appt is equal to the start of this already scheduled appt
                        alertFlag = true;
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Overlapping appointments");
                        alert.setTitle("Error scheduling appointment");
                        alert.setContentText("An appointment cannot be scheduled at the specified time because it " +
                                "overlaps with an appointment already scheduled for this customer.");
                        alert.show();

                        /* Test Statements
                        System.out.println("or start of new appt is equal to the start of this already scheduled appt:");
                        System.out.println();
                        // Test statements
                        System.out.println("a.getCustomerId: " + a.getCustomerId());
                        System.out.println("Param customer ID: " + customerId);
                        System.out.println();
                        System.out.println("newApptStartTime: " + newApptStartTime);
                        System.out.println("thisApptStart: " + thisApptStart);
                        System.out.println("newApptEndTime: " + newApptEndTime);
                        System.out.println("thisApptEnd: " + thisApptEnd);
                        System.out.println();
                        System.out.println("newApptStartTime.isAfter(thisApptStart): " + newApptStartTime.isAfter(thisApptStart));
                        System.out.println("newApptStartTime.isBefore(thisApptEnd): " + newApptStartTime.isBefore(thisApptEnd));
                        System.out.println("newApptEndTime.isAfter(thisApptStart): " + newApptEndTime.isAfter(thisApptStart));
                         */
                    }else{
                        alertFlag = false;
                    }
                } // end 1st 'if' statement
            } // end for loop
            if (!alertFlag) {
                ps.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return alertFlag;
    }

    /**
     Gathers data entered by user and combines with generated fields to update Appointment in DB.

     @param customerId ID representing customer associated with appointment
     @param contactId ID representing Contact associated with appointment
     @param userId ID of user creating the appointment
     @param title title of appointment
     @param description description of appointment
     @param location location of appointment
     @param type type of appointment
     @param start start Timestamp of appointment
     @param end end Timestamp of appointment

     */
    public static void updateAppointmentInDb(int appointmentId, int customerId, int contactId, int userId, String title, String description,
                                             String location, String type, Timestamp start, Timestamp end){
       try {

            String sql = "Update appointments SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, " +
                    "Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?," +
                    "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

            // Same every time
            Timestamp createDate = new Timestamp(System.currentTimeMillis());
            String createdBy = "script";
            String lastUpdatedBy = "script";
            Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setTimestamp(6, start);
            ps.setTimestamp(7, end);
            ps.setTimestamp(8, createDate);
            ps.setString(9, createdBy);
            ps.setTimestamp(10, lastUpdate);
            ps.setString(11, lastUpdatedBy);
            ps.setInt(12, customerId);
            ps.setInt(13, userId);
            ps.setInt(14, contactId);
            ps.setInt(15, appointmentId);
            ps.execute();


        String contact = Contact.getContactNameById(contactId);

        // Convert Timestamp to LocalDate and LocalTime
        LocalDate startDate = start.toLocalDateTime().toLocalDate();
        LocalDate endDate = end.toLocalDateTime().toLocalDate();
        LocalTime startTime = start.toLocalDateTime().toLocalTime();
        LocalTime endTime = end.toLocalDateTime().toLocalTime();

        // Convert LD and LT to DateTimeFormatter strings for display in UI
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

       }catch (SQLException e){
           System.out.println("Caught ye boi: " + e.getMessage());;
       }
    }

    /**
     Deletes Appointment from database using the Appointment ID.

     @param appointmentId Appointment ID (Primary Key) to remove from database

     */
    public static void deleteAppointmentInDb(int appointmentId){
        String customer = "N/A";
        String appointmentType = "N/A";
        // Get appointment type for Appointment ID
        for (Appointment a : getAllAppointmentsFromDb()){
            if(a.getAppointmentId() == appointmentId){

                appointmentType = a.getType();
                int custId = a.getCustomerId();
                customer = Customer.getCustomerNameById(custId);
            }
        }
        try {

            String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel the " + appointmentType + " appointment with " + customer + "?");
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Appointment will be deleted");

            Optional<ButtonType> result = confirm.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                    ps.execute();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Appointment deleted");
                    alert.setContentText("The " + appointmentType + " appointment with Appointment ID: " + appointmentId +
                            " has been canceled and removed from the schedule.");
                    alert.show();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     Counts and groups Appointments by Type.

     @return appointmentsByType ObservableList of AppointmentType objects representing
     types of appointments and the number of each type

     */
    public static ObservableList<AppointmentType> getAppointmentsByType(){
        ObservableList<AppointmentType> appointmentsByType = FXCollections.observableArrayList();
        try{
            // Get the types of appointments and count of each type
            String sql = "SELECT DISTINCT Type, COUNT(Type) FROM client_schedule.appointments\n" +
                    "GROUP BY Type";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String type = rs.getString("Type");
                int count = rs.getInt("Count(Type)");
                AppointmentType at = new AppointmentType(type, count);
                appointmentsByType.add(at);
            }
        }catch (SQLException e){
            System.out.println("Caught you DBAppointment: " + e.getMessage());
        }

        return appointmentsByType;
    }

    /**
     Finds MAX Appointment_ID from database.

     @return nextAppointmentID next integer after value of MAX(Appointment_ID)

     */
    public static int getNextAppointmentId(){
        int nextAppointmentId = -1;
        try{
            // Get maximum Appointment ID already in table and set new Appointment ID to (max + 1)
            String max_sql = "SELECT MAX(Appointment_ID) as MAX FROM appointments";
            PreparedStatement maxPs = JDBC.getConnection().prepareStatement(max_sql);
            ResultSet rs = maxPs.executeQuery();
            rs.next();
            nextAppointmentId = rs.getInt("MAX") + 1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nextAppointmentId;
    }
}
