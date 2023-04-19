package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {
    public TextField customerIdTxt;
    public TextField nameTxt;
    public TextField postalCodeCb;
    public TextField phoneNumberTxt;
    public ComboBox countryCb;
    public ComboBox divisionCb;
    public TextField addressTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UpdateCustomer Initialized.");
    }

    public void displayCustomerTab(ActionEvent actionEvent) {
        MainViewController.mainViewStage.close();
    }

    public void saveUpdatedCustomer(ActionEvent actionEvent) {
    }
}
