package controller;

import DBAccess.DBAppointment;
import DBAccess.DBContact;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/** A Controller class for the AddAppointment view. */
public class AddAppointmentController implements Initializable {

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
    private ComboBox<Timestamp> startTimeCb;
    @FXML
    private ComboBox<Timestamp> endTimeCb;
    @FXML
    private ComboBox<String> typeCb;

    Stage stage;
    Parent scene;

    /**
     Initializes AddAppointment view.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddAppointment Initialized.");
        contactCb.setItems(DBContact.getContactNames());
        typeCb.setItems(Appointment.getAppointmentTypes());
    }

    /**
     Checks the user input for validity, creates a new Appointment object, and adds the Appointment to DB before returning
     the user to the MainView.

     @param actionEvent object containing information about the page where user clicks Save button
     @exception IOException thrown if FXMLLoader.load() resource is Null
     @exception SQLException thrown in case of invalid SQL statement during createAppointment()
     */
    public void saveNewAppointment(ActionEvent actionEvent) throws SQLException, IOException {

        // Form data
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        Timestamp start = new Timestamp(System.currentTimeMillis()); // Figure out how to parse datepicker data
        Timestamp end = new Timestamp(System.currentTimeMillis() + 900);  // Figure out how to parse datepicker data
        String contactName = contactCb.getSelectionModel().getSelectedItem();
        String type = typeCb.getSelectionModel().getSelectedItem();
        int customerId = 1; //Customer.getCustomerIdByName(customerName);
        int contactId = Contact.getContactIdByName(contactName);
        int userId = 1; // need to get user ID of currently logged in user

        // Use createAppointment() and data from form to insert new Appointment into DB
        DBAppointment.createAppointment(customerId, contactId, userId, title, description, location, type, start, end);

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
