package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {


    public TextField titleTxt;
    public TextField appointmentIdTxt;
    public ComboBox contactCb;
    public TextField locationTxt;
    public TextArea descriptionTxt;
    public DatePicker startDateDp;
    public DatePicker endDateDp;
    public ComboBox startTimeCb;
    public ComboBox endTimeCb;
    public ComboBox typeCb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddAppointment Initialized.");
    }

    public void saveNewAppointment(ActionEvent actionEvent) {
    }

    public void displayMainView(ActionEvent actionEvent) {
    }
}
