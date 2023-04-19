package controller;

import DBAccess.DBAppointment;
import DBAccess.DBContact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML
    private TextField titleTxt;
    @FXML
    private TextField appointmentIdTxt;
    @FXML
    private ComboBox<String> contactCb = new ComboBox<>();
    @FXML
    private TextField locationTxt;
    @FXML
    private TextArea descriptionTxt;
    @FXML
    private DatePicker startDateDp;
    @FXML
    private DatePicker endDateDp;
    @FXML
    private ComboBox<Timestamp> startTimeCb;
    @FXML
    private ComboBox<Timestamp> endTimeCb;
    @FXML
    private ComboBox<String> typeCb;

    Stage stage;
    Parent scene;

    // Getters and Setters
    public TextField getTitleTxt() {
        return titleTxt;
    }

    public void setTitleTxt(TextField titleTxt) {
        this.titleTxt = titleTxt;
    }

    public TextField getAppointmentIdTxt() {
        return appointmentIdTxt;
    }

    public void setAppointmentIdTxt(TextField appointmentIdTxt) {
        this.appointmentIdTxt = appointmentIdTxt;
    }

    public ComboBox<?> getContactCb() {
        return contactCb;
    }

    public void setContactCb(ComboBox<String> contactCb) {
        this.contactCb = contactCb;
    }

    public TextField getLocationTxt() {
        return locationTxt;
    }

    public void setLocationTxt(TextField locationTxt) {
        this.locationTxt = locationTxt;
    }

    public TextArea getDescriptionTxt() {
        return descriptionTxt;
    }

    public void setDescriptionTxt(TextArea descriptionTxt) {
        this.descriptionTxt = descriptionTxt;
    }

    public DatePicker getStartDateDp() {
        return startDateDp;
    }

    public void setStartDateDp(DatePicker startDateDp) {
        this.startDateDp = startDateDp;
    }

    public DatePicker getEndDateDp() {
        return endDateDp;
    }

    public void setEndDateDp(DatePicker endDateDp) {
        this.endDateDp = endDateDp;
    }

    public ComboBox<?> getStartTimeCb() {
        return startTimeCb;
    }

    public void setStartTimeCb(ComboBox<Timestamp> startTimeCb) {
        this.startTimeCb = startTimeCb;
    }

    public ComboBox<Timestamp> getEndTimeCb() {
        return endTimeCb;
    }

    public void setEndTimeCb(ComboBox<Timestamp> endTimeCb) {
        this.endTimeCb = endTimeCb;
    }

    public ComboBox<String> getTypeCb() {
        return typeCb;
    }

    public void setTypeCb(ComboBox<String> typeCb) {
        this.typeCb = typeCb;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddAppointment Initialized.");
        contactCb.setItems(DBContact.getContactNames());
        typeCb.setItems(DBAppointment.getAppointmentTypes());
    }

    public void saveNewAppointment(ActionEvent actionEvent) throws SQLException, IOException {

        // Form data

        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        Timestamp start = new Timestamp(System.currentTimeMillis()); // Figure out how to parse datepicker data
        Timestamp end = new Timestamp(System.currentTimeMillis() + 900);  // Figure out how to parse datepicker data
        String contactName = contactCb.getSelectionModel().getSelectedItem();
        String type = typeCb.getSelectionModel().getSelectedItem();
        int customerId = 1; //Customer.getCustomerIdByName(customerName);
        int contactId = Contact.getContactIdByName(contactName);
        int userId = 1; // need to get user ID of currently logged in user

        DBAppointment.createAppointment(customerId, contactId, userId, title, description, location, type, start, end);

        MainViewController.getMainViewStage().close();
        /*
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen(); */

    }


    public void displayMainView(ActionEvent actionEvent) throws IOException {

        // On "Cancel" button being clicked
        MainViewController.getMainViewStage().close();
    }
}
