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

    public DatePicker getStartDateDp() {
        return startDateDp;
    }

    public void setStartDateDp(DatePicker startDateDp) {
        this.startDateDp = startDateDp;
    }

    public ComboBox<?> getContactCb() {
        return contactCb;
    }

    public void setContactCb(ComboBox<?> contactCb) {
        this.contactCb = contactCb;
    }

    public TextField getAppointmentIdTxt() {
        return appointmentIdTxt;
    }

    public void setAppointmentIdTxt(TextField appointmentIdTxt) {
        this.appointmentIdTxt = appointmentIdTxt;
    }

    public TextField getTitleTxt() {
        return titleTxt;
    }

    public void setTitleTxt(TextField titleTxt) {
        this.titleTxt = titleTxt;
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

    public DatePicker getEndDateDp() {
        return endDateDp;
    }

    public void setEndDateDp(DatePicker endDateDp) {
        this.endDateDp = endDateDp;
    }

    public ComboBox<?> getStartTimeCb() {
        return startTimeCb;
    }

    public void setStartTimeCb(ComboBox<?> startTimeCb) {
        this.startTimeCb = startTimeCb;
    }

    public ComboBox<?> getEndTimeCb() {
        return endTimeCb;
    }

    public void setEndTimeCb(ComboBox<?> endTimeCb) {
        this.endTimeCb = endTimeCb;
    }

    public ComboBox<?> getTypeCb() {
        return typeCb;
    }

    public void setTypeCb(ComboBox<?> typeCb) {
        this.typeCb = typeCb;
    }

    public Button getUpdateAppointmentBtn() {
        return updateAppointmentBtn;
    }

    public void setUpdateAppointmentBtn(Button updateAppointmentBtn) {
        this.updateAppointmentBtn = updateAppointmentBtn;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UpdateAppointment Initialized.");
    }

    public void saveUpdatedAppointment(ActionEvent actionEvent) {
    }

    public void displayMainView(ActionEvent actionEvent) throws IOException {

        // On "Cancel" button being clicked
        MainViewController.getMainViewStage().close();
    }
}
