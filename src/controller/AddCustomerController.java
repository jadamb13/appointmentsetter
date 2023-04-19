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
import java.util.ResourceBundle;

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
    private ComboBox<?> countryCb;
    @FXML
    private ComboBox<?> divisionCb;
    @FXML
    private TextField addressTxt;

    Stage stage;
    Parent scene;

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

    public TextField getPostalCodeTxt() {
        return postalCodeTxt;
    }

    public void setPostalCodeTxt(TextField postalCodeTxt) {
        this.postalCodeTxt = postalCodeTxt;
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
        System.out.println("AddCustomer Initialized.");
    }

    public void displayCustomerTab(ActionEvent actionEvent) throws IOException {
        MainViewController.getMainViewStage().close();
    }

    public void saveNewCustomer(ActionEvent actionEvent) {
    }
}
