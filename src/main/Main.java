package main;

import DBAccess.DBAppointment;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        primaryStage.setTitle("Appointment Management System");
        primaryStage.setScene(new Scene(root, 452, 212));
        primaryStage.show();
    }

    public static void main(String[] args) {
        JDBC.makeConnection();

        /* Set Language/Locale */
        //Locale.setDefault(new Locale("fr", "FR"));
        Locale.setDefault(new Locale("en", "US"));

        launch(args);
        JDBC.closeConnection();
    }
}
