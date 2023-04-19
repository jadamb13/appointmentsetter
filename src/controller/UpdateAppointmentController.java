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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UpdateAppointment Initialized.");
    }

    public void saveUpdatedAppointment(ActionEvent actionEvent) {
    }

    public void displayMainView(ActionEvent actionEvent) throws IOException {

        // On "Cancel" button being clicked
        MainViewController.mainViewStage.close();
    }
}
