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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
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
    private TableColumn<Appointment, String> apptStartDateCol;
    @FXML
    private TableColumn<Appointment, String> apptStartTimeCol;
    @FXML
    private TableColumn<Appointment, String> apptEndDateCol;
    @FXML
    private TableColumn<Appointment, String> apptEndTimeCol;
    @FXML
    private TableColumn<Appointment, Integer>  apptCustomerIdCol;
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

    private static Appointment selectedAppointment;

    // Getters and Setters
    public static Stage getMainViewStage() {
        return mainViewStage;
    }

    public static Appointment getSelectedAppointment(){
        return selectedAppointment;
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
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        apptStartDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        apptEndDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        apptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointmentsTable.setItems(DBAppointment.getAllAppointmentsFromDb());

        // Sort Appointments Table by Appointment ID
        appointmentsTable.getSortOrder().add(apptIdCol);

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
            appointmentsTable.setItems(DBAppointment.getAllAppointmentsFromDb());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     Displays overlay window/stage of the UpdateAppointment view.

     @param actionEvent represents user click Update button under Appointments Table

     */
    public void displayUpdateAppointmentWindow(ActionEvent actionEvent) {
        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if(!Objects.isNull(selectedAppointment)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateAppointment.fxml"));
                Parent root = loader.load();
                mainViewStage = new Stage();
                mainViewStage.setTitle("Update Appointment");
                mainViewStage.setScene(new Scene(root));
                mainViewStage.showAndWait();
                appointmentsTable.setItems(DBAppointment.getAllAppointmentsFromDb());
            } catch (IOException e) {
                System.out.println("Caught you: " + e.getMessage());
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must select an Appointment to Update.");
            alert.show();
        }
    }

    /**
     Deletes Appointment object selected from the DB and tableView

     @param actionEvent represents user clicking Delete button under Appointments table

     */
    public void deleteSelectedAppointment(ActionEvent actionEvent) {
        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        DBAppointment.deleteAppointmentInDb(selected.getAppointmentId());
        appointmentsTable.setItems(DBAppointment.getAllAppointmentsFromDb());
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
    public void displayAllAppointments(ActionEvent actionEvent) {
        appointmentsTable.setItems(DBAppointment.getAllAppointmentsFromDb());
    }

    /**
     Updates Appointments tableView to show only appointments this week (filter)

     @param actionEvent represents user clicking "Current Week" Radio Button

     */
    public void displayCurrentWeekAppointments(ActionEvent actionEvent) {

    }

    /**
     Updates Appointments tableView to show appointments for the current month (filter)

     @param actionEvent represents user clicking "Current Month" Radio Button

     */
    public void displayCurrentMonthAppointments(ActionEvent actionEvent) {

    }


}
