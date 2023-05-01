package controller;

import DBAccess.DBAppointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointment;
import model.Contact;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/** A Controller class for the AddAppointment view. */
public class AddAppointmentController implements Initializable {

    @FXML
    private ComboBox<String> customerCb;
    @FXML
    private TextField userIdTxt;
    @FXML
    private TextField titleTxt;
    @FXML
    private TextField appointmentIdTxt;
    @FXML
    private ComboBox<String> contactCb = new ComboBox<>();
    @FXML
    private TextField locationTxt;
    @FXML
    private TextArea descriptionTxt;
    @FXML
    private DatePicker startDateDp;
    @FXML
    private DatePicker endDateDp;
    @FXML
    private ComboBox<String> startTimeCb;
    @FXML
    private ComboBox<String> endTimeCb;
    @FXML
    private ComboBox<String> typeCb;

    /**
     Initializes AddAppointment view.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddAppointment Initialized.");

        // Assign values to disabled fields and pre-populated lists (Combo Boxes)
        appointmentIdTxt.setText(String.valueOf(DBAppointment.getNextAppointmentId()));
        userIdTxt.setText(String.valueOf(LoginController.getProgramUserId()));
        contactCb.setItems(Contact.getAllContactNames());
        typeCb.setItems(Appointment.getAppointmentTypes());
        customerCb.setItems(Customer.getAllCustomerNames());
        Appointment.getAppointmentTimes();
        //startTimeCb.setItems(Appointment.getAppointmentTimes());
        //endTimeCb.setItems(Appointment.getAppointmentTimes());
    }

    /**
     Checks the user input for validity, creates a new Appointment object, and adds the Appointment to DB before returning
     the user to the MainView.

     @param actionEvent object containing information about the page where user clicks Save button
     @exception IOException thrown if FXMLLoader.load() resource is Null
     @exception SQLException thrown in case of invalid SQL statement during insertAppointment()
     */
    public void saveNewAppointment(ActionEvent actionEvent) throws SQLException, IOException {

        // Form data (not including times and dates)
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String contactName = contactCb.getSelectionModel().getSelectedItem();
        String type = typeCb.getSelectionModel().getSelectedItem();
        String customerName = customerCb.getSelectionModel().getSelectedItem();
        int customerId = Customer.getCustomerIdByName(customerName);
        int contactId = Contact.getContactIdByName(contactName);
        int userId = LoginController.getProgramUserId();

        // Get selected/entered times and dates from Add Appointment Form
        String startTime = startTimeCb.getValue();
        String endTime = endTimeCb.getValue();
        LocalDate startDate = startDateDp.getValue();
        LocalDate endDate = endDateDp.getValue();

        // Create LocalDate and LocalTime objects to create LocalDateTimes
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime ltStartTime = LocalTime.parse(startTime, formatter);
        LocalTime ltEndTime = LocalTime.parse(endTime, formatter);
        LocalDateTime ldtStart = LocalDateTime.of(startDate, ltStartTime);
        LocalDateTime ldtEnd = LocalDateTime.of(endDate, ltEndTime);

        // Turn LDTs into Timestamps for entry into the DB
        Timestamp start = Timestamp.valueOf(ldtStart);
        Timestamp end = Timestamp.valueOf(ldtEnd);

        // Use insertAppointment() and data from form to insert new Appointment into DB
        DBAppointment.insertAppointment(customerId, contactId, userId, title, description, location, type, start, end);

        // Close AddAppointment view and show MainView
        MainViewController.getMainViewStage().close();
    }

    /**
     Returns user to the MainView.

     @param actionEvent ActionEvent object holding information about page where user clicks Cancel button
     @exception IOException thrown if FXMLLoader.load() resource is Null
     */
    public void displayMainView(ActionEvent actionEvent) throws IOException {

        // On "Cancel" button being clicked
        MainViewController.getMainViewStage().close();
    }
}
