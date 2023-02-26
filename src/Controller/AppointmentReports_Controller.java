package Controller;

import DBAccess.DBAppointment;
import Model.Appointment;
import Utilities.General_Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

/**
 * Controller class for the Appointment Reports page.
 * Displays a report of the number of appointments based on type and month.
 * @author Hayley D'Angelo
 */
public class AppointmentReports_Controller implements Initializable {

    @FXML
    private ComboBox<String> comboType;

    @FXML
    private DatePicker datePickerMonth;

    @FXML
    private Label appointmentReportLabel;

    /**
     * Takes the user back to the Main Menu.
     * @param event The back button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        General_Utility.returnToMainMenu(event);

    }

    /**
     * Generates a report that tells the user the number of appointments scheduled by type and month.
     * @param event The Generate Report button is clicked.
     */
    @FXML
    void onActionGenerateReport(ActionEvent event) {

        int year = datePickerMonth.getValue().getYear();
        String type = comboType.getValue();
        Month month = datePickerMonth.getValue().getMonth();
        int count = 0;

        for (Appointment appointment : DBAppointment.getAppointmentsByType(type)){
            ObservableList<Appointment> reportAppointments = FXCollections.observableArrayList();
            if (appointment.getStart().getMonth().toString() == month.toString() ){
                reportAppointments.add(appointment);
            }
            count = reportAppointments.size();
        }

        appointmentReportLabel.setText("There are " + count + " " + type + " appointments in " + month + " " + year + ".");

    }

    /**
     * Initializes the Appointment Reports page.
     * Sets the list of types to display in the type combo box.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointment> appointments = DBAppointment.getAllAppointments();
        ObservableList<String> types = FXCollections.observableArrayList();
        for (Appointment appointment : appointments){
            if (!types.contains(appointment.getType())){
                types.add(appointment.getType());
            }
        }
        comboType.setItems(types);
        comboType.setPromptText("Choose a type...");
    }

}
