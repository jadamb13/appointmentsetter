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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddCustomer Initialized.");
    }

    public void displayCustomerTab(ActionEvent actionEvent) throws IOException {
        MainViewController.mainViewStage.close();
    }

    public void saveNewCustomer(ActionEvent actionEvent) {
    }
}
