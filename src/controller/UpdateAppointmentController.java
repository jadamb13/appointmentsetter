package controller;

import DBAccess.DBAppointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/** A Controller class for the UpdateAppointment view. */
public class UpdateAppointmentController implements Initializable {

    @FXML
    private DatePicker startDateDp;
    @FXML
    private ComboBox<String> contactCb;
    @FXML
    private TextField appointmentIdTxt;
    @FXML
    private TextField titleTxt;
    @FXML
    private TextField locationTxt;
    @FXML
    private TextArea descriptionTxt;
    @FXML
    private DatePicker endDateDp;
    @FXML
    private ComboBox<String> startTimeCb;
    @FXML
    private ComboBox<String> endTimeCb;
    @FXML
    private ComboBox<String> typeCb;
    @FXML
    private Button updateAppointmentBtn;

    Stage stage;
    Parent scene;

    /**
     Initializes UpdateAppointment view.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UpdateAppointment Initialized.");
        Appointment selected = MainViewController.getSelectedAppointment();

        // Convert timestamp to LDT and add formatting for easier readability
        LocalDateTime startLdt = selected.getStart().toLocalDateTime();
        LocalDateTime endLdt = selected.getEnd().toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String start = startLdt.format(formatter);
        String end = endLdt.format(formatter);

        // Populate form data from selected Appointment
        appointmentIdTxt.setText(String.valueOf(selected.getAppointmentId()));
        titleTxt.setText(selected.getTitle());
        descriptionTxt.setText(selected.getDescription());
        locationTxt.setText(selected.getLocation());
        contactCb.setItems(Contact.getAllContactNames());
        contactCb.setValue(selected.getContact());
        typeCb.setItems(Appointment.getAppointmentTypes());
        typeCb.setValue(selected.getType());
        startDateDp.setValue(selected.getStart().toLocalDateTime().toLocalDate());
        endDateDp.setValue(selected.getEnd().toLocalDateTime().toLocalDate());
        startTimeCb.setValue(start);
        endTimeCb.setValue(end);


    }

    /**
     Checks the user input for validity, creates a new Appointment object, and updates the Appointment in DB before returning
     the user to the MainView.

     @param actionEvent object containing information about the page where user clicks Save button
     //@exception IOException thrown if FXMLLoader.load() resource is Null
     //@exception SQLException thrown in case of invalid SQL statement
     */
    public void saveUpdatedAppointment(ActionEvent actionEvent) {
        // Form data
        int appointmentId = Integer.parseInt(appointmentIdTxt.getText());
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        Timestamp start = new Timestamp(System.currentTimeMillis());
        Timestamp end = new Timestamp(System.currentTimeMillis() + (15*60*1000));
        String contactName = contactCb.getSelectionModel().getSelectedItem();
        String type = typeCb.getSelectionModel().getSelectedItem();
        int customerId = 10;
        int contactId = Contact.getContactIdByName(contactName);
        int userId = 10; // need to get user ID of currently logged in user

        // Use insertAppointment() and data from form to insert new Appointment into DB
        DBAppointment.updateAppointmentInDb(appointmentId, customerId, contactId, userId, title, description, location, type, start, end);

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
