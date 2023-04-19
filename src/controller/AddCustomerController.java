package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    public TextField customerIdTxt;
    public TextField nameTxt;
    public TextField postalCodeTxt;
    public TextField phoneNumberTxt;
    public ComboBox countryCb;
    public ComboBox divisionCb;
    public TextField addressTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddCustomer Initialized.");
    }

    public void displayCustomerTab(ActionEvent actionEvent) {
    }

    public void saveNewCustomer(ActionEvent actionEvent) {
    }
}
