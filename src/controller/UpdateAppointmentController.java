package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {

    @FXML
    private Button updateAppointmentBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UpdateAppointment Initialized.");
    }
}
