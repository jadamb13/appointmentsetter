package controller;

import DBAccess.DBCustomer;
import DBAccess.DBDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.Division;

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
     Initializes UpdateCustomer view.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Customer selected = MainViewController.getSelectedCustomer();
            customerIdTxt.setText(String.valueOf(selected.getCustomerId()));
            nameTxt.setText(selected.getCustomerName());
            addressTxt.setText(selected.getCustomerAddress());
            postalCodeTxt.setText(selected.getCustomerPostalCode());
            phoneNumberTxt.setText(selected.getCustomerPhoneNumber());
            countryCb.setItems(Country.getAllCountryNames());
            String countryName = DBDivision.getCountryNameById(selected.getDivisionId());
            countryCb.setValue(countryName);

            // Populate divisionCb based on the selected country
            switch (countryName) {
                case "U.S" -> {
                    divisionCb.setItems(Division.getAllUnitedStatesDivisionNames());
                    divisionCb.setValue(Division.getDivisionNameById(selected.getDivisionId()));
                }
                case "UK" -> {
                    divisionCb.setItems(Division.getAllUnitedKingdomDivisionNames());
                    divisionCb.setValue(Division.getDivisionNameById(selected.getDivisionId()));
                }
                case "Canada" -> {
                    divisionCb.setItems(Division.getAllCanadaDivisionNames());
                    divisionCb.setValue(Division.getDivisionNameById(selected.getDivisionId()));
                }
            }

            // Set up listener for countryCb
            countryCb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Populate divisionCb based on the selected country
                    String selectedCountry = newValue.toString();
                    switch (selectedCountry) {
                        case "U.S" -> divisionCb.setItems(Division.getAllUnitedStatesDivisionNames());
                        case "UK" -> divisionCb.setItems(Division.getAllUnitedKingdomDivisionNames());
                        case "Canada" -> divisionCb.setItems(Division.getAllCanadaDivisionNames());
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Caught ye AddCustomerController: " + e.getMessage());
        }
    }

    /**
     Checks the user input for validity, creates a new Customer object, and updates the Customer in DB before returning
     the user to the MainView.

     @param actionEvent object containing information about the page where user clicks Save button

     */
    public void saveUpdatedCustomer(ActionEvent actionEvent) {
        try {
            // Form data
            int customerId = Integer.parseInt(customerIdTxt.getText());
            String customerName = nameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalCodeTxt.getText();
            String phone = phoneNumberTxt.getText();
            String divisionName = divisionCb.getSelectionModel().getSelectedItem();
            int divisionId = Division.getDivisionIdByName(divisionName);


            if(MainViewController.validateCustomerInput(customerName, address, postalCode, phone, divisionName, divisionId)){
                DBCustomer.updateCustomerInDb(customerId, customerName, address, postalCode, phone, divisionId);

                // Close AddCustomer view and show MainView
                MainViewController.getMainViewStage().close();
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No fields can be empty. Please enter text for all fields and try again.");
                alert.show();
            }


        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     Returns user to the MainView.

     @param actionEvent ActionEvent object holding information about page where user clicks Cancel button

     */
    public void displayCustomerTab(ActionEvent actionEvent) {
        MainViewController.getMainViewStage().close();
    }



}
