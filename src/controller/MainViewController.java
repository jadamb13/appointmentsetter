package controller;

import DBAccess.DBAppointment;
import DBAccess.DBCustomer;
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
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/** A Controller class for the MainView. */
public class MainViewController implements Initializable {

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer,Integer> customerIdCol;
    @FXML
    private TableColumn<Customer,String> nameCol;
    @FXML
    private TableColumn<Customer,String> addressCol;
    @FXML
    private TableColumn<Customer,String> postalCodeCol;
    @FXML
    private TableColumn<Customer,String> phoneCol;
    @FXML
    private TableColumn<Customer,Integer> divisionIdCol;
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
    public static Stage getMainViewStage() {
        return mainViewStage;
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

        // Set values in Appointments Table
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


        // Set values in Customers Table
        customerTable.setItems(DBCustomer.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));


    }

    /**
     Displays overlay window/stage of the AddAppointment view.

     @param actionEvent represents user click Add button under Appointments Table
     */
    public void displayAddAppointmentWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddAppointment.fxml"));
            Parent root = loader.load();
            mainViewStage = new Stage();
            mainViewStage.setTitle("Add Appointment");
            mainViewStage.setScene(new Scene(root));
            mainViewStage.showAndWait();
            appointmentsTable.setItems(DBAppointment.getAllAppointments());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     Displays overlay window/stage of the UpdateAppointment view.

     @param actionEvent represents user click Update button under Appointments Table

     */
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

    /**
     Deletes Appointment object selected from the DB and tableView

     @param actionEvent represents user clicking Delete button under Appointments table

     */
    public void deleteSelectedAppointment(ActionEvent actionEvent) {
    }

    /**
     Displays overlay window/stage of the AddCustomer view.

     @param actionEvent represents user click Add button under Customers Table
     */
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

    /**
     Displays overlay window/stage of the UpdateCustomer view.

     @param actionEvent represents user click Update button under Customers Table

     */
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

    /**
     Deletes Customer object selected from the DB and tableView

     @param actionEvent represents user clicking Delete button under Customers table

     */
    public void deleteSelectedCustomer(ActionEvent actionEvent) {
    }

    /**
     Updates Appointments tableView to show all appointments

     @param actionEvent represents user clicking "All Appointments" Radio Button

     */
    public void displayAllAppointments(ActionEvent actionEvent) { }

    /**
     Updates Appointments tableView to show only appointments this week (filter)

     @param actionEvent represents user clicking "Current Week" Radio Button

     */
    public void displayCurrentWeekAppointments(ActionEvent actionEvent) { }

    /**
     Updates Appointments tableView to show appointments for the current month (filter)

     @param actionEvent represents user clicking "Current Month" Radio Button

     */
    public void displayCurrentMonthAppointments(ActionEvent actionEvent) { }


}
