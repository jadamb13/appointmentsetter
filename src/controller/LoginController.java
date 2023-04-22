package controller;

import DBAccess.DBUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.TimeZone;


/** A Controller class for the LoginView. */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private Label timezoneTxt;
    Stage stage;
    Parent scene;
    ZoneId zone = ZoneId.systemDefault();


    /**
     * Initializes LoginView.
     *
     * @param url            location information
     * @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("LoginView Initialized.");
        timezoneTxt.setText(zone.getId());

    }

    /**
     * Displays the MainView after receiving valid login credentials.
     *
     * @param actionEvent represents user clicking on "Login" button
     */
    public void onActionDisplayMainView(ActionEvent actionEvent) throws IOException {
        /*System.out.println("Clicked Login.");
        ObservableList<User> userList = FXCollections.observableArrayList();
        userList = DBUser.getAllUsers();
        boolean userFound = false;

        for (User u : userList){
            if (userFound){
                break;
            }
            String un = u.getUserName();
            String pw = u.getUserPassword();
            String inputUn = usernameTxt.getText();
            String inputPw = passwordTxt.getText();

            if (un.equals(inputUn) && pw.equals(inputPw)){
                userFound = true;
                setUserId(u.getUserId());

        */
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        /*
            }
        }
        if (!userFound){
            Alert loginAlert = new Alert(Alert.AlertType.WARNING);
            loginAlert.setTitle("Invalid Login Credentials");
            loginAlert.setContentText("Invalid username/password combination. Please check your information and try again.");
            loginAlert.show();

        }

         */
    }

    }
