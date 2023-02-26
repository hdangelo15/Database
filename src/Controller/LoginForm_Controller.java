package Controller;

import DBAccess.DBAppointment;
import DBAccess.DBUser;
import Model.Appointment;
import Utilities.General_Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * The controller class for the Login Form.
 * Allows the user to login to the scheduling program.
 * @author Hayley D'Angelo
 */
public class LoginForm_Controller implements Initializable {

    @FXML
    private Label titleLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField userIDText;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordText;

    @FXML
    private Button loginButton;

    @FXML
    private Label locationLabel;

    @FXML
    private Label countryLabel;

    ResourceBundle rb = ResourceBundle.getBundle("Utilities/Languages_fr");

    /**
     * Logs the user into the scheduling program.
     * Checks the user name and password against the database to make sure entry is valid and displays a message if not.
     * Automatically detects the user's location and displays it on the form.
     * Automatically detects the user's language and changes accordingly.
     * Appends login attempt information to a text file in the main program folder.
     * Displays a message to let the user know if they have any appointments within 15 minutes of login or not.
     * @param event The login button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException {

        DateTimeFormatter fileDateFormatter = DateTimeFormatter.ofPattern("hh:mm a z MM/dd/YYYY [VV]" );

        String username = userIDText.getText();
        String password = passwordText.getText();

        // Sets up the text file to append login attempts to
        String fileName = "login_activity.txt";
        FileWriter loginResult = new FileWriter(fileName, true);
        PrintWriter writeLoginResult = new PrintWriter(loginResult);

        // Valid Login Attempt
        if (DBUser.validateLogin(username, password) == true){

            // Set the name of the current user
            DBUser.setCurrentUsername(username);

            // Writes login attempt to text file
            writeLoginResult.println("ID: " + DBUser.getCurrentUser().getUserID() + " " + "User_Name: " + DBUser.getCurrentUsername() +
                    " logged in successfully at " + fileDateFormatter.format(ZonedDateTime.now(ZoneId.systemDefault())));
            writeLoginResult.close();

            // Loads the main menu screen
            General_Utility.returnToMainMenu(event);

            // Display message alerted user whether or not they have an appointment within 15 minutes
            LocalDateTime loginTime = LocalDateTime.now();
            ObservableList<Appointment> appointmentsSoon = FXCollections.observableArrayList();

            for (Appointment appointment : DBAppointment.getUserAppointments()){
                if (appointment.getStart().isAfter(loginTime) && appointment.getStart().isBefore(loginTime.plusMinutes(15))){
                    appointmentsSoon.add(appointment);
                }
            }

            if (!appointmentsSoon.isEmpty()){
                for (Appointment appointment : appointmentsSoon) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Soon");
                    alert.setContentText("Appointment " + appointment.getAppointment_ID() + " " + appointment.getTitle() + " starts at " +
                            formatter.format(appointment.getStart()) + ".");
                    alert.showAndWait();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Appointments");
                alert.setContentText("You have no appointments starting soon.");
                alert.showAndWait();
            }
        }

        // Invalid Login Attempt
        else {

            // Writes login attempt to text file
            writeLoginResult.println("User_Name: " + username +
                    " failed to login at " + fileDateFormatter.format(ZonedDateTime.now(ZoneId.systemDefault())));
            writeLoginResult.close();

            // Displays invalid login message in French or English
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if(Locale.getDefault().getLanguage().equals("fr")) {
                alert.setTitle(rb.getString("Alert"));
                alert.setContentText(rb.getString("Context"));
            }
            else {
                alert.setTitle("Login Failed");
                alert.setContentText("Username or Password is incorrect.");
            }
            alert.showAndWait();
        }

    }

    /**
     * Initializes the login form.
     * Displays the user's timezone on the form based on their computer's location.
     * Displays the form in English as default and French if the user's computer's default language is French.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Gets the user's timezone and displays it on the form
        TimeZone timezone = TimeZone.getDefault();
        String displayTimezone = timezone.getID();
        countryLabel.setText(displayTimezone);

        // Changes the text of the login form to French if the user's computer's default language is French
        if(Locale.getDefault().getLanguage().equals("fr")) {

            titleLabel.setText(rb.getString("Header"));
            usernameLabel.setText(rb.getString("Username") + ":");
            passwordLabel.setText(rb.getString("Password") + ":");
            loginButton.setText(rb.getString("Login"));
            locationLabel.setText(rb.getString("Location") + ": ");

        }

    }

}
