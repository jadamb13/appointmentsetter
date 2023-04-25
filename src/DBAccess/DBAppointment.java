package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.AppointmentMonth;
import model.AppointmentType;
import model.Contact;

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

     */
    public static void insertAppointment(int customerId, int contactId, int userId, String title, String description,
                                         String location, String type, Timestamp start, Timestamp end) {
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

            ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    /**
     Gathers data entered by user and combines with generated fields to update Appointment in DB.

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

    public static void deleteAppointmentInDb(int appointmentId){
        try {
            // Get maximum Appointment ID already in table and set new Appointment ID to (max + 1)
            String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.execute();

        }catch(SQLException e){
            System.out.println("Caught yas.");
        }
    }

    public static ObservableList<Appointment> getCurrentWeekAppointments(){
        ObservableList<Appointment> weeklyAppointmentList = FXCollections.observableArrayList();

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
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy");

                Appointment a = new Appointment(appointmentId, customerId, contactId, userId, title, description, location,
                        contact, type, dateFormatter.format(startDate), dateFormatter.format(endDate),
                        timeFormatter.format(startTime), timeFormatter.format(endTime));
                weeklyAppointmentList.add(a);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return weeklyAppointmentList;
    }

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

    public static ObservableList<AppointmentMonth> getAppointmentsByMonth(){
        ObservableList<AppointmentMonth> appointmentsByMonth = FXCollections.observableArrayList();
        List<String> months = new ArrayList<>();
        for (Appointment a : getAllAppointmentsFromDb()){
            String monthNumber = a.getStartDate().substring(0, 2);
            //String year = a.getStartDate().substring(7, 11);
            String monthName = String.valueOf(Month.of(Integer.parseInt(monthNumber)));
            months.add(monthName);

        }
        List<String> uniqueMonths = new ArrayList<>();
        for (int i = 0; i < months.size(); i++){
            if (!uniqueMonths.contains(months.get(i))){
                uniqueMonths.add(months.get(i));
            }
        }
        List<Integer> frequencies = new ArrayList<>();
        for (int i = 0; i < uniqueMonths.size(); i++){
            frequencies.add(Collections.frequency(months, uniqueMonths.get(i)));
        }
        for (int i = 0; i < uniqueMonths.size(); i++){
            AppointmentMonth am = new AppointmentMonth(uniqueMonths.get(i), frequencies.get(i));
            appointmentsByMonth.add(am);
        }

        return appointmentsByMonth;
    }

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
