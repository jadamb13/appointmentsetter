package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** A Controller class for the MainView. */
public class MainViewController implements Initializable {

    //Stage stage;
    //Parent scene;
    @FXML
    private Button btn;

    /**
     Initializes MainView.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("MainView Initialized.");
    }

    public void displayAddAppointmentWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddAppointment.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Appointment");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void displayUpdateAppointmentWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateAppointment.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Update Appointment");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void displayAddCustomerWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddCustomer.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Update Appointment");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void displayUpdateCustomerWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateCustomer.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Update Appointment");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
