package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** A Controller class for the UpdateAppointment view. */
public class UpdateAppointmentController implements Initializable {

    @FXML
    private DatePicker startDateDp;
    @FXML
    private ComboBox<?> contactCb;
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
    private ComboBox<?> startTimeCb;
    @FXML
    private ComboBox<?> endTimeCb;
    @FXML
    private ComboBox<?> typeCb;
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
    }

    /**
     Checks the user input for validity, creates a new Appointment object, and updates the Appointment in DB before returning
     the user to the MainView.

     @param actionEvent object containing information about the page where user clicks Save button
     //@exception IOException thrown if FXMLLoader.load() resource is Null
     //@exception SQLException thrown in case of invalid SQL statement
     */
    public void saveUpdatedAppointment(ActionEvent actionEvent) { }

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
