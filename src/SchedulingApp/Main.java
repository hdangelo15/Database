package SchedulingApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Main class for the scheduling application.
 * Displays the login form on start up.
 * @author
 * Hayley D'Angelo
 */
public class Main extends Application {

    /**
     * The start method to display the login form upon opening the application.
     * Displays the title of the application in either English or French depending on the user's computer's default language.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginForm.fxml"));
        primaryStage.setScene(new Scene(root, 350, 250));
        primaryStage.show();

        ResourceBundle rb = ResourceBundle.getBundle("Utilities/Languages_fr");
        if(Locale.getDefault().getLanguage().equals("fr")) {
            primaryStage.setTitle(rb.getString("Title"));
        }
        else {
            primaryStage.setTitle("Scheduling Application");
        }

    }

    /**
     * Launches the application.
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

        launch(args);

    }

}
