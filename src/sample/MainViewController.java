package sample;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/** A Controller class for the MainView. */
public class MainViewController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     Initializes MainView.

     @param url location information
     @param resourceBundle resource information
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("MainView Initialized.");
    }
}
