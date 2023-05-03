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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/** A Controller class for the UpdateAppointment view. */
public class UpdateAppointmentController implements Initializable {

    @FXML
    private ComboBox<String> contactCb;
    @FXML
    private ComboBox<String> customerCb;
    @FXML
    private TextField appointmentIdTxt;
    @FXML
    private TextField userIdTxt;
    @FXML
    private TextField titleTxt;
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
     Initializes UpdateAppointment view.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Appointment selected = MainViewController.getSelectedAppointment();

            // Convert String Dates into LocalDate to use with DatePicker boxes
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDate startDate = LocalDate.parse(selected.getStartDate(), dateFormatter);
            LocalDate endDate = LocalDate.parse(selected.getEndDate(), dateFormatter);

            // Populate form data from selected Appointment
            appointmentIdTxt.setText(String.valueOf(selected.getAppointmentId()));
            userIdTxt.setText(String.valueOf(selected.getUserId()));
            customerCb.setItems(Customer.getAllCustomerNames());
            customerCb.setValue(Customer.getCustomerNameById(selected.getCustomerId()));
            titleTxt.setText(selected.getTitle());
            descriptionTxt.setText(selected.getDescription());
            locationTxt.setText(selected.getLocation());
            contactCb.setItems(Contact.getAllContactNames());
            contactCb.setValue(selected.getContact());
            typeCb.setItems(Appointment.getAppointmentTypes());
            typeCb.setValue(selected.getType());

            startTimeCb.setValue(selected.getStartTime());
            endTimeCb.setValue(selected.getEndTime());
            startDateDp.setValue(startDate);
            endDateDp.setValue(endDate);
            startTimeCb.setItems(Appointment.getAppointmentTimes(startDate));
            endTimeCb.setItems(Appointment.getAppointmentTimes(startDate));

            setStartDateListener();

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     Checks the user input for validity, creates a new Appointment object, and updates the Appointment in DB before returning
     the user to the MainView.

     @param actionEvent object containing information about the page where user clicks Save button
     //@exception IOException thrown if FXMLLoader.load() resource is Null
     //@exception SQLException thrown in case of invalid SQL statement
     */
    public void saveUpdatedAppointment(ActionEvent actionEvent) {
        try {
            // Form data
            int appointmentId = Integer.parseInt(appointmentIdTxt.getText());
            int userId = Integer.parseInt(userIdTxt.getText());
            String contactName = contactCb.getSelectionModel().getSelectedItem();
            int contactId = Contact.getContactIdByName(contactName);
            String customer = customerCb.getSelectionModel().getSelectedItem();
            int customerId = Customer.getCustomerIdByName(customer);
            String title = titleTxt.getText();
            String description = descriptionTxt.getText();
            String location = locationTxt.getText();
            String type = typeCb.getSelectionModel().getSelectedItem();

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
                if (MainViewController.validateAppointmentInput(title, description, location, type, customerId, contactId)) {
                    DBAppointment.updateAppointmentInDb(appointmentId, customerId, contactId, userId, title, description, location, type, start, end);
                    MainViewController.getMainViewStage().close();
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
            // This method will be called when the value of the date picker changes
            if(newValue != null){
                startTimeCb.setItems(Appointment.getAppointmentTimes(newValue));
                endTimeCb.setItems(Appointment.getAppointmentTimes(newValue));
            }
        });
    }

}
