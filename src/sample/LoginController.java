package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/** A Controller class for the LoginView. */
public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     Initializes LoginView.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("LoginView Initialized.");
    }

    /**
     * Displays the MainView after receiving valid login credentials.
     *
     * @param actionEvent represents user clicking on "Login" button
     */
    public void onActionDisplayMainView(ActionEvent actionEvent) throws IOException {
        System.out.println("Clicked Login.");
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        stage.setScene(new Scene(scene));
    }
}