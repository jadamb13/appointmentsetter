package controller;

import DBAccess.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    private TextField descriptionTxt;
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

    Stage stage;
    Parent scene;

    public static ObservableList<String> getAppointmentTimes() {
        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

        String[] times = {"8:00 am", "8:15 am", "8:30 am", "8:45 am", "9:00 am", "9:15 am", "9:30 am", "10:00 am",
                "10:15 am", "10:30 am", "10:45 am", "11:00 am", "11:15 am", "11:30 am", "11:45 am", "12:00 pm", "12:15 pm", "12:30 pm", "12:45 pm",
                "1:00 pm", "1:15 pm", "1:30 pm", "1:45 pm", "2:00 pm", "2:15 pm", "2:30 pm", "2:45 pm", "3:00 pm", "3:15 pm", "3:30 pm", "3:45 pm",
                "4:00 pm", "4:15 pm", "4:30 pm", "4:45 pm", "5:00 pm", "5:15 pm", "5:30 pm", "5:45 pm", "6:00 pm", "6:15 pm", "6:30 pm", "6:45 pm,",
                "7:00 pm", "7:15 pm", "7:30 pm", "7:45 pm", "8:00 pm", "8:15 pm", "8:30 pm", "8:45 pm", "9:00 pm", "9:15 pm", "9:30 pm", "9:45 pm", "10:00 pm"};
        for (int i = 0; i < times.length; i++){
            System.out.println("Loop");
            appointmentTimes.add(times[i]);
        }
        return appointmentTimes;
    }

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
        startTimeCb.setItems(getAppointmentTimes());
        endTimeCb.setItems(getAppointmentTimes());
    }



    /**
     Checks the user input for validity, creates a new Appointment object, and adds the Appointment to DB before returning
     the user to the MainView.

     @param actionEvent object containing information about the page where user clicks Save button
     @exception IOException thrown if FXMLLoader.load() resource is Null
     @exception SQLException thrown in case of invalid SQL statement during insertAppointment()
     */
    public void saveNewAppointment(ActionEvent actionEvent) throws SQLException, IOException {

        // Form data
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        Timestamp start = new Timestamp(System.currentTimeMillis()); // Figure out how to parse datepicker data
        Timestamp end = new Timestamp(System.currentTimeMillis() + (15*60*1000));  // Figure out how to parse datepicker data
        String contactName = contactCb.getSelectionModel().getSelectedItem();
        String type = typeCb.getSelectionModel().getSelectedItem();
        int customerId = 1; //Customer.getCustomerIdByName(customerName);
        int contactId = Contact.getContactIdByName(contactName);
        int userId = LoginController.getProgramUserId();

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
