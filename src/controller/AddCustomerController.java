package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** A Controller class for the AddCustomer view. */
public class AddCustomerController implements Initializable {

    @FXML
    private TextField customerIdTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField postalCodeTxt;
    @FXML
    private TextField phoneNumberTxt;
    @FXML
    private ComboBox<String> countryCb;
    @FXML
    private ComboBox<Integer> divisionCb;
    @FXML
    private TextField addressTxt;

    Stage stage;
    Parent scene;


    /**
     Initializes AddCustomer view.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddCustomer Initialized.");
    }

    /**
     Returns user to the MainView.

     @param actionEvent ActionEvent object holding information about page where user clicks Cancel button
     @exception IOException thrown if FXMLLoader.load() resource is Null
     */
    public void displayCustomerTab(ActionEvent actionEvent) throws IOException {
        MainViewController.getMainViewStage().close();
    }

    /**
     Checks the user input for validity, creates a new Customer object, and adds the Customer to DB before returning
     the user to the MainView.

     @param actionEvent object containing information about the page where user clicks Save button
     //@exception IOException thrown if FXMLLoader.load() resource is Null
     //@exception SQLException thrown in case of invalid SQL statement during createAppointment()
     */
    public void saveNewCustomer(ActionEvent actionEvent) { }
}
