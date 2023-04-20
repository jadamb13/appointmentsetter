package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** A Controller class for the UpdateCustomer view. */
public class UpdateCustomerController implements Initializable {

    @FXML
    private TextField customerIdTxt;
    @FXML
    private TextField nameTxt;

    @FXML
    private TextField postalCodeCb;
    @FXML
    private TextField phoneNumberTxt;
    @FXML
    private ComboBox<?> countryCb;
    @FXML
    private ComboBox<?> divisionCb;
    @FXML
    private TextField addressTxt;


    /**
     Initializes UpdateCustomer view.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UpdateCustomer Initialized.");
    }

    /**
     Checks the user input for validity, creates a new Customer object, and updates the Customer in DB before returning
     the user to the MainView.

     @param actionEvent object containing information about the page where user clicks Save button
     //@exception IOException thrown if FXMLLoader.load() resource is Null
     //@exception SQLException thrown in case of invalid SQL statement
     */
    public void saveUpdatedCustomer(ActionEvent actionEvent) { }

    /**
     Returns user to the MainView.

     @param actionEvent ActionEvent object holding information about page where user clicks Cancel button
     @exception IOException thrown if FXMLLoader.load() resource is Null
     */
    public void displayCustomerTab(ActionEvent actionEvent) {
        MainViewController.getMainViewStage().close();
    }



}
