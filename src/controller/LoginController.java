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

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;


/** A Controller class for the LoginView. */
public class LoginController implements Initializable {

    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label appNameLabel;
    @FXML
    private Button loginBtn;
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private Label timezoneTxt;

    Stage stage;
    Parent scene;

    // Find timezone information of user's system
    ZoneId zone = ZoneId.systemDefault();

    private static int programUserId = 0;

    /* Getters and Setters */
    public static int getProgramUserId() {
        return programUserId;
    }

    public void setProgramUserId(int programUserId) {
        LoginController.programUserId = programUserId;
    }

    /**
     * Initializes LoginView.
     *
     * @param url            location information
     * @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timezoneTxt.setText(zone.getId());

        if(Locale.getDefault().equals(new Locale("fr", "FR"))) {
            ResourceBundle resourceBundle1 = ResourceBundle.getBundle("resources/LanguageSupport_fr");
            String login = resourceBundle1.getString("loginBtn");
            String username = resourceBundle1.getString("usernameLabel");
            String password = resourceBundle1.getString("passwordLabel");
            String appName = resourceBundle1.getString("appNameLabel");
            loginBtn.setText(login);
            usernameLabel.setText(username);
            passwordLabel.setText(password);
            appNameLabel.setText(appName);
        }

    }

    /**
     * Displays the MainView after receiving valid login credentials.
     *
     * @param actionEvent represents user clicking on "Login" button
     */
    public void onActionDisplayMainView(ActionEvent actionEvent) throws IOException {
        ObservableList<User> userList =  DBUser.getAllUsers();
        boolean userFound = false;
        boolean passwordMatches = false;
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        ZoneId userZoneId = ZoneId.systemDefault();
        String successOrFail = "";

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
                passwordMatches = true;
                successOrFail = "SUCCESS";
                setProgramUserId(u.getUserId());
                trackLoginAttempt(getProgramUserId(), localDate, localTime, userZoneId,successOrFail);
                Locale.setDefault(new Locale("en", "US"));
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
                stage.setScene(new Scene(scene));
                stage.centerOnScreen();
            }
            if(un.equals(inputUn) && !pw.equals(inputPw)){
                userFound = true;
                successOrFail = "FAILURE";
                setProgramUserId(u.getUserId());
                trackLoginAttempt(getProgramUserId(), localDate, localTime, userZoneId, successOrFail);
                Alert loginAlert = new Alert(Alert.AlertType.WARNING);
                if(Locale.getDefault().equals(new Locale("fr", "FR"))){
                    try{
                        ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/LanguageSupport_fr");
                        String error = resourceBundle.getString("error");
                        loginAlert.setTitle("");
                        loginAlert.setContentText(error);
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                        System.out.println(e.getCause());
                    }

                }else{
                    loginAlert.setContentText("Invalid username and password combination. Please check your information and try again.");
                }
                loginAlert.show();
            }
        }
        if (!userFound){
            successOrFail = "FAILURE";
            trackLoginAttempt(getProgramUserId(), localDate, localTime, userZoneId, successOrFail);
            Alert loginAlert = new Alert(Alert.AlertType.WARNING);
            if(Locale.getDefault().equals(new Locale("fr", "FR"))){
                try{
                    ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/LanguageSupport_fr");
                    String error = resourceBundle.getString("error");
                    loginAlert.setTitle("");
                    loginAlert.setContentText(error);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    System.out.println(e.getCause());
                }

            }else{
                loginAlert.setContentText("Invalid username and password combination. Please check your information and try again.");
            }
            loginAlert.show();


        }

    }

    /**
     * Writes user login attempt information to login_activity.txt.
     *
     * @param userId userId of user attempting to login
     * @param date LocalDate when user attempts to login
     * @param time LocalTime of user login attempt
     * @param successOrFail outcome of login attempt
     */
    public void trackLoginAttempt(int userId, LocalDate date, LocalTime time, ZoneId userZoneId, String successOrFail){
        try{
            FileWriter writer = new FileWriter("src/login_activity.txt", true);
            if(successOrFail.equals("SUCCESS")){
                writer.write("User " + User.getUserNameById(userId) + " successfully logged in at " +
                        date + " " + time.truncatedTo(ChronoUnit.SECONDS) + " (" + userZoneId + ")\n");
            }else{
                if(User.getUserNameById(userId).equals("")){
                    writer.write("Unknown user " + "gave invalid log-in credentials at " +
                            date + " " + time.truncatedTo(ChronoUnit.SECONDS) + " (" + userZoneId + ")\n");
                }else {
                    writer.write("User " + User.getUserNameById(userId) + " gave invalid log-in credentials at " +
                            date + " " + time.truncatedTo(ChronoUnit.SECONDS) + " (" + userZoneId + ")\n");
                }
            }
            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
