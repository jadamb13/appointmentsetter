package controller;

import DBAccess.DBAppointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/** A Controller class for the MainView. */
public class MainViewController implements Initializable {

    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;
    @FXML
    private TableColumn<Appointment, String> apptTitleCol;
    @FXML
    private TableColumn<Appointment, String> apptDescriptionCol;
    @FXML
    private TableColumn<Appointment, String> apptLocationCol;
    @FXML
    private TableColumn<Appointment, String> apptContactCol;
    @FXML
    private TableColumn<Appointment, String> apptTypeCol;
    @FXML
    private TableColumn<Appointment, Timestamp> apptStartCol;
    @FXML
    private TableColumn<Appointment, Timestamp> apptEndCol;
    @FXML
    private TableColumn<Appointment, Integer>  apptCustomerIdCol;
    @FXML
    private TableColumn<Appointment, Integer>  apptContactIdCol;
    @FXML
    private TableColumn<Appointment, Integer>  apptUserIdCol;
    @FXML
    private RadioButton currentMonthRBtn;
    @FXML
    private RadioButton currentWeekRBtn;
    @FXML
    private RadioButton allAppointmentsRBtn;
    @FXML
    private Button apptsDelBtn;
    @FXML
    private Tab customerTab;

    @FXML
    private static Stage mainViewStage;
    //Parent scene;
    @FXML
    private Button btn;

    // Getters and Setters
    public RadioButton getCurrentMonthRBtn() {
        return currentMonthRBtn;
    }

    public void setCurrentMonthRBtn(RadioButton currentMonthRBtn) {
        this.currentMonthRBtn = currentMonthRBtn;
    }

    public RadioButton getCurrentWeekRBtn() {
        return currentWeekRBtn;
    }

    public void setCurrentWeekRBtn(RadioButton currentWeekRBtn) {
        this.currentWeekRBtn = currentWeekRBtn;
    }

    public RadioButton getAllAppointmentsRBtn() {
        return allAppointmentsRBtn;
    }

    public void setAllAppointmentsRBtn(RadioButton allAppointmentsRBtn) {
        this.allAppointmentsRBtn = allAppointmentsRBtn;
    }

    public Button getApptsDelBtn() {
        return apptsDelBtn;
    }

    public void setApptsDelBtn(Button apptsDelBtn) {
        this.apptsDelBtn = apptsDelBtn;
    }

    public Tab getCustomerTab() {
        return customerTab;
    }

    public void setCustomerTab(Tab customerTab) {
        this.customerTab = customerTab;
    }

    public static Stage getMainViewStage() {
        return mainViewStage;
    }

    public void setMainViewStage(Stage mainViewStage) {
        MainViewController.mainViewStage = mainViewStage;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    /**
     Initializes MainView.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allAppointmentsRBtn.setSelected(true);
        System.out.println("MainView Initialized.");

        appointmentsTable.setItems(DBAppointment.getAllAppointments());
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        //populateAppointmentFields();

    }

    private void populateAppointmentFields() {
    }

    public void displayAddAppointmentWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddAppointment.fxml"));
            Parent root = loader.load();
            mainViewStage = new Stage();
            mainViewStage.setTitle("Add Appointment");
            mainViewStage.setScene(new Scene(root));
            mainViewStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void displayUpdateAppointmentWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateAppointment.fxml"));
            Parent root = loader.load();
            mainViewStage = new Stage();
            mainViewStage.setTitle("Update Appointment");
            mainViewStage.setScene(new Scene(root));
            mainViewStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void displayAddCustomerWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddCustomer.fxml"));
            Parent root = loader.load();
            mainViewStage = new Stage();
            mainViewStage.setTitle("Update Appointment");
            mainViewStage.setScene(new Scene(root));
            mainViewStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void displayUpdateCustomerWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateCustomer.fxml"));
            Parent root = loader.load();
            mainViewStage = new Stage();
            mainViewStage.setTitle("Update Appointment");
            mainViewStage.setScene(new Scene(root));
            mainViewStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }



    public void displayAllAppointments(ActionEvent actionEvent) {
    }

    public void displayCurrentWeekAppointments(ActionEvent actionEvent) {
    }

    public void displayCurrentMonthAppointments(ActionEvent actionEvent) {
    }

    public void deleteSelectedAppointment(ActionEvent actionEvent) {
    }
}
