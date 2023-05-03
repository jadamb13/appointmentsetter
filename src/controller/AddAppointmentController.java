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
import java.time.ZoneId;
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
        try{
            // Assign values to disabled fields and pre-populated lists (Combo Boxes)
            appointmentIdTxt.setText(String.valueOf(DBAppointment.getNextAppointmentId()));
            userIdTxt.setText(String.valueOf(LoginController.getProgramUserId()));
            contactCb.setItems(Contact.getAllContactNames());
            typeCb.setItems(Appointment.getAppointmentTypes());
            customerCb.setItems(Customer.getAllCustomerNames());


            // Get user's LocalDate to perform logic check against date selected
            ZoneId userZoneId = ZoneId.systemDefault();
            LocalDate userDate = LocalDate.now(userZoneId);

            startDateDp.setValue(userDate);
            endDateDp.setValue(userDate);
            startTimeCb.setItems(Appointment.getAppointmentTimes(userDate));
            endTimeCb.setItems(Appointment.getAppointmentTimes(userDate));
            setStartDateListener();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     Checks the user input for validity, creates a new Appointment object, and adds the Appointment to DB before returning
     the user to the MainView.

     @param actionEvent object containing information about the page where user clicks Save button
     @exception IOException thrown if FXMLLoader.load() resource is Null
     @exception SQLException thrown in case of invalid SQL statement during insertAppointment()
     */
    public void saveNewAppointment(ActionEvent actionEvent) throws SQLException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        try {
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
            LocalTime ltStartTime = LocalTime.parse(startTime, formatter);
            LocalTime ltEndTime = LocalTime.parse(endTime, formatter);
            LocalDateTime ldtStart = LocalDateTime.of(startDate, ltStartTime);
            LocalDateTime ldtEnd = LocalDateTime.of(endDate, ltEndTime);

            // Turn LDTs into Timestamps for entry into the DB
            Timestamp start = Timestamp.valueOf(ldtStart);
            Timestamp end = Timestamp.valueOf(ldtEnd);

            // Logic check to make sure end date and time are after start date and time
            boolean logicFlag = false;
            if(ldtEnd.compareTo(ldtStart) < 0){
                logicFlag = true;
                Alert timeAlert = new Alert(Alert.AlertType.WARNING);
                timeAlert.setTitle("");
                timeAlert.setHeaderText("Incorrect time entered");
                timeAlert.setContentText("The end date and time for the appointment must be after the start date and time.");
                timeAlert.show();
            }

            if(!logicFlag) {
                // If Appointment information input is not blank
                if (MainViewController.validateAppointmentInput(title, description, location, type, customerId, contactId)) {
                    // If the insert is successful (no conflicting/overlapping customer appointments)
                    if (!DBAppointment.insertAppointment(customerId, contactId, userId, title, description, location, type, start, end)) {
                        MainViewController.getMainViewStage().close();
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("No fields can be empty. Please enter text for all fields and try again.");
                    alert.show();
                }
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No fields can be empty. Please enter text for all fields and try again.");
            alert.show();
            System.out.println(e.getMessage() + " " + e.getCause());
        }

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

    /**
     Sets startTime and endTime combo boxes based on selection made for startDate DatePicker.

     @param actionEvent ActionEvent object holding information about page where user chooses a Start Date
     from the startDate DatePicker
     */
    public void setAppointmentTimes(ActionEvent actionEvent) {
        LocalDate selectedDate = startDateDp.getValue();
        endDateDp.setValue(selectedDate);
        startTimeCb.setItems(Appointment.getAppointmentTimes(selectedDate));
        endTimeCb.setItems(Appointment.getAppointmentTimes(selectedDate));
    }

    /**
     * Sets listener on startDate DatePicker to update startTime and endTime combo boxes based on new date selected.
     *
     */
    private void setStartDateListener() {
        startDateDp.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                startTimeCb.setItems(Appointment.getAppointmentTimes(newValue));
                endTimeCb.setItems(Appointment.getAppointmentTimes(newValue));
            }

        });
    }


}
