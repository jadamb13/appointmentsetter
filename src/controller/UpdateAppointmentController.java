package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {

    public AnchorPane startDateDp;
    public ComboBox contactCb;
    public TextField appointmentIdTxt;
    public TextField titleTxt;
    public TextField locationTxt;
    public TextArea descriptionTxt;
    public DatePicker endDateDp;
    public ComboBox startTimeCb;
    public ComboBox endTimeCb;
    public ComboBox typeCb;
    @FXML
    private Button updateAppointmentBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UpdateAppointment Initialized.");
    }

    public void saveUpdatedAppointment(ActionEvent actionEvent) {
    }

    public void displayMainView(ActionEvent actionEvent) {
    }
}
