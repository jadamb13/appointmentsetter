package controller;

import DBAccess.DBAppointment;
import DBAccess.DBContact;
import DBAccess.DBCustomer;
import DBAccess.DBDivision;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** A Controller class for the MainView. */
public class MainViewController implements Initializable {


    @FXML
    private ComboBox byMonthCb;
    @FXML
    private ComboBox byWeekCb;
    @FXML
    private TableColumn<Appointment, String> contactApptEndDateCol;
    @FXML
    private TableColumn<Appointment, Integer> contactApptCustomerIdCol;
    @FXML
    private TableView<Appointment> appointmentScheduleTable;
    @FXML
    private TableColumn<Appointment, Integer> contactApptIdCol;
    @FXML
    private TableColumn<Appointment, String> contactApptTitleCol;
    @FXML
    private TableColumn<Appointment, String> contactApptDescCol;
    @FXML
    private TableColumn<Appointment, String> contactApptTypeCol;
    @FXML
    private TableColumn<Appointment, String> contactApptStartTimeCol;
    @FXML
    private TableColumn<Appointment, String> contactApptStartDateCol;
    @FXML
    private TableColumn<Appointment, String> contactApptEndTimeCol;
    @FXML
    private ComboBox<String> contactCb;
    @FXML
    private TableView<AppointmentType> appointmentsByTypeTable;
    @FXML
    private TableColumn<AppointmentType, String> byTypeCol;
    @FXML
    private TableColumn<AppointmentType, Integer> numberByTypeCol;
    @FXML
    private TableView<AppointmentMonth> appointmentsByMonthTable;
    @FXML
    private TableColumn<AppointmentMonth, String> byMonthCol;
    @FXML
    private TableColumn<AppointmentMonth, Integer> numberByMonthCol;
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
    private RadioButton allAppointmentsRBtn;
    @FXML
    private Button apptsDelBtn;

    @FXML
    private static Stage mainViewStage;
    //Parent scene;
    @FXML
    private Button btn;

    private static Appointment selectedAppointment;
    private static Customer selectedCustomer;

    // Getters and Setters
    public static Stage getMainViewStage() {
        return mainViewStage;
    }

    public static Appointment getSelectedAppointment(){
        return selectedAppointment;
    }

    public static Customer getSelectedCustomer(){
        return selectedCustomer;
    }

    /**
     Initializes MainView.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //allAppointmentsRBtn.setSelected(true);
        System.out.println("MainView Initialized.");

        /* Appointments Tab */
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
        //appointmentsTable.setItems(DBAppointment.getAllAppointmentsFromDb());

        // Sort Appointments Table by Appointment ID
        appointmentsTable.getSortOrder().add(apptIdCol);

        /* Customers Tab */
        // Set values in Customers Table
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        //customerTable.setItems(DBCustomer.getAllCustomersFromDb());

        // Sort Appointments Table by Appointment ID
        customerTable.getSortOrder().add(customerIdCol);

        /* Reports Tab */
        contactApptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        contactApptTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        contactApptDescCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        contactApptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        contactApptStartDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        contactApptEndDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        contactApptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        contactApptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        contactApptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        contactCb.setItems(Contact.getAllContactNames());
        populateTables();

        // Set up listener for Contact Combo Box on Reports Tab
        contactCb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Contact contact = null;

                // Find which Contact object is responsible for appointment
                for (Contact c : DBContact.getAllContacts()) {
                    if (c.getContactName().equals(newValue)) {
                        contact = c;

                        // Add appointment to Contact's contactAppointment list
                        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
                        for (Appointment a : DBAppointment.getAllAppointmentsFromDb()) {
                            if (a.getContactId() == contact.getContactId()) {
                                contactAppointments.add(a);
                            }

                            // Populate Appointment Schedule Table based on contact selected
                            appointmentScheduleTable.setItems(contactAppointments);
                        }
                    }
                }
            }
        }); // End of listener


        // Initialize Appointments By Type table
        byTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        numberByTypeCol.setCellValueFactory(new PropertyValueFactory<>("countOfType"));


        byMonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        numberByMonthCol.setCellValueFactory(new PropertyValueFactory<>("CountByMonth"));
        populateTables();
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
            populateTables();
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
                populateTables();
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
        populateTables();
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
            mainViewStage.showAndWait();
            populateTables();
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
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateCustomer.fxml"));
            Parent root = loader.load();
            mainViewStage = new Stage();
            mainViewStage.setTitle("Update Appointment");
            mainViewStage.setScene(new Scene(root));
            mainViewStage.showAndWait();
            populateTables();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     Deletes Customer object selected from the DB and tableView

     @param actionEvent represents user clicking Delete button under Customers table

     */
    public void deleteSelectedCustomer(ActionEvent actionEvent) {

        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        for(Appointment a : DBAppointment.getAllAppointmentsFromDb()){
            if(a.getCustomerId() == selected.getCustomerId()){
                DBAppointment.deleteAppointmentInDb(a.getAppointmentId());
                populateTables();
            }
        }
        DBCustomer.deleteCustomerInDb(selected.getCustomerId());
        populateTables();
    }

    /**
     Updates Appointments tableView to show all appointments

     @param actionEvent represents user clicking "All Appointments" Radio Button

     */
    public void displayAllAppointments(ActionEvent actionEvent) {
        populateTables();
    }

    /**
     Updates Appointments tableView to show only appointments this week (filter)

     @param actionEvent represents user clicking "Current Week" Radio Button

     */
    public void displayAppointmentsByWeek(ActionEvent actionEvent) {

    }

    /**
     Updates Appointments tableView to show appointments for the current month (filter)

     @param actionEvent represents user clicking "Current Month" Radio Button

     */
    public void displayAppointmentsByMonth(ActionEvent actionEvent) {
        ObservableList<Appointment> appointments = DBAppointment.getAllAppointmentsFromDb();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        List<List<Appointment>> appointmentsByMonth = Stream.generate(ArrayList<Appointment>::new)
                .limit(12)
                .collect(Collectors.toList());

        appointments.forEach(a -> {
            int monthIndex = LocalDate.parse(a.getStartDate(),formatter).getMonthValue() - 1;
            appointmentsByMonth.get(monthIndex).add(a);
        });
        ObservableList<Appointment> appointmentsByMonthList = FXCollections.observableArrayList();
        for (List<Appointment> appts : appointmentsByMonth) {
            appointmentsByMonthList.addAll(appts);
        }
        appointmentsTable.setItems(appointmentsByMonthList);
    }


    public void populateTables() {
        appointmentsTable.setItems(DBAppointment.getAllAppointmentsFromDb());
        appointmentsByTypeTable.setItems(DBAppointment.getAppointmentsByType());
        appointmentsByMonthTable.setItems(DBAppointment.getAppointmentsByMonth());
        customerTable.setItems(DBCustomer.getAllCustomersFromDb());
        contactCb.setValue(null);
        appointmentScheduleTable.setItems(null);
    }


}
