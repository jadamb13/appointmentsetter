package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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

    public TextField getCustomerIdTxt() {
        return customerIdTxt;
    }

    public void setCustomerIdTxt(TextField customerIdTxt) {
        this.customerIdTxt = customerIdTxt;
    }

    public TextField getNameTxt() {
        return nameTxt;
    }

    public void setNameTxt(TextField nameTxt) {
        this.nameTxt = nameTxt;
    }

    public TextField getPostalCodeCb() {
        return postalCodeCb;
    }

    public void setPostalCodeCb(TextField postalCodeCb) {
        this.postalCodeCb = postalCodeCb;
    }

    public TextField getPhoneNumberTxt() {
        return phoneNumberTxt;
    }

    public void setPhoneNumberTxt(TextField phoneNumberTxt) {
        this.phoneNumberTxt = phoneNumberTxt;
    }

    public ComboBox<?> getCountryCb() {
        return countryCb;
    }

    public void setCountryCb(ComboBox<?> countryCb) {
        this.countryCb = countryCb;
    }

    public ComboBox<?> getDivisionCb() {
        return divisionCb;
    }

    public void setDivisionCb(ComboBox<?> divisionCb) {
        this.divisionCb = divisionCb;
    }

    public TextField getAddressTxt() {
        return addressTxt;
    }

    public void setAddressTxt(TextField addressTxt) {
        this.addressTxt = addressTxt;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UpdateCustomer Initialized.");
    }

    public void displayCustomerTab(ActionEvent actionEvent) {
        MainViewController.getMainViewStage().close();
    }

    public void saveUpdatedCustomer(ActionEvent actionEvent) {
    }

}
