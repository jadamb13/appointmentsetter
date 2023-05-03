package controller;


import DBAccess.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.Division;

import java.io.IOException;
import java.net.URL;
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
    private ComboBox<String> divisionCb;
    @FXML
    private TextField addressTxt;


    /**
     Initializes AddCustomer view.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("AddCustomer Initialized.");
            customerIdTxt.setText(String.valueOf(DBCustomer.getNextCustomerId()));
            countryCb.setItems(Country.getAllCountryNames());
            // Set up listener for countryCb
            countryCb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Populate divisionCb based on the selected country
                    String selectedCountry = newValue.toString();
                    // Populate divisionCb
                    if(selectedCountry.equals("U.S")){
                        divisionCb.setItems(Division.getAllUnitedStatesDivisionNames());
                    }
                    if(selectedCountry.equals("UK")){
                        divisionCb.setItems(Division.getAllUnitedKingdomDivisionNames());
                    }
                    if(selectedCountry.equals("Canada")){
                        divisionCb.setItems(Division.getAllCanadaDivisionNames());
                    }
                }
            });
        }catch(Exception e){
            System.out.println("Caught ye AddCustomerController: " + e.getMessage());
        }
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
     //@exception SQLException thrown in case of invalid SQL statement during insertAppointment()
     */
    public void saveNewCustomer(ActionEvent actionEvent) {
        try {
            int customerId = Integer.parseInt(customerIdTxt.getText());
            String customerName = nameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalCodeTxt.getText();
            String phone = phoneNumberTxt.getText();
            String divisionName = divisionCb.getSelectionModel().getSelectedItem();
            int divisionId = Division.getDivisionIdByName(divisionName);
            if(MainViewController.validateCustomerInput(customerName, address, postalCode, phone, divisionName, divisionId)){
                // Use DBCustomer.insertAppointment() and data from form to insert new Appointment into DB
                DBCustomer.insertCustomer(customerId, customerName, address, postalCode, phone, divisionId);

                // Close AddCustomer view and show MainView
                MainViewController.getMainViewStage().close();
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No fields can be empty. Please enter text for all fields and try again.");
                alert.show();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
