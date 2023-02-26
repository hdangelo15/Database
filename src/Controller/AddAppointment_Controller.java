package Controller;


import DBAccess.DBAppointment;
import DBAccess.DBContact;
import DBAccess.DBCustomer;
import DBAccess.DBUser;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import Utilities.Alert_Utility;
import Utilities.ComboInterface;
import Utilities.General_Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * A controller class for the Add Appointment form.
 * Allows a user to add a new appointment to the database.
 * @author Hayley D'Angelo
 */

public class AddAppointment_Controller implements Initializable {

    @FXML
    private TextField apptTitleText;

    @FXML
    private TextField apptDescText;

    @FXML
    private TextField apptLocText;

    @FXML
    private TextField apptTypeText;

    @FXML
    private ComboBox<Customer> comboCustomer;

    @FXML
    private ComboBox<User> comboUser;

    @FXML
    private ComboBox<Contact> comboContact;

    @FXML
    private DatePicker datePickerStart;

    @FXML
    private ComboBox<String> startCombo;

    @FXML
    private ComboBox<String> endCombo;

    /**
     * Clears all of the data from the form so the user can start over.
     * @param event The CLEAR ALL button is clicked.
     */
    @FXML
    void onActionClearForm(ActionEvent event) {

        apptTitleText.clear();
        apptDescText.clear();
        apptLocText.clear();
        apptTypeText.clear();
        startCombo.getSelectionModel().clearSelection();
        datePickerStart.setValue(null);
        endCombo.getSelectionModel().clearSelection();
        comboCustomer.getSelectionModel().clearSelection();
        comboUser.getSelectionModel().clearSelection();
        comboContact.getSelectionModel().clearSelection();

        comboCustomer.setPromptText("Choose a Customer...");
        comboUser.setPromptText("Choose a User...");
        comboContact.setPromptText("Choose a Contact...");
        startCombo.setPromptText("Choose a Start Time...");
        endCombo.setPromptText("Choose an End Time...");

    }

    /**
     * Adds a new appointment to the database.
     * Checks to make sure fields are not blank, the appointment is scheduled within business hours,
     * the customer doesn't have overlapping appointments, and that the end time is after the start time.
     * Displays an error message for each condition that needs to be fixed.
     * @param event The Save button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        try{
            String title = apptTitleText.getText();
            String desc = apptDescText.getText();
            String location = apptLocText.getText();
            String type = apptTypeText.getText();
            LocalTime startTime = LocalTime.parse(startCombo.getSelectionModel().getSelectedItem(), formatter);
            LocalDate date = datePickerStart.getValue();
            LocalDateTime start = LocalDateTime.of(date, startTime);
            LocalTime endTime = LocalTime.parse(endCombo.getSelectionModel().getSelectedItem(), formatter);
            LocalDateTime end = LocalDateTime.of(date, endTime);
            int customerID = comboCustomer.getSelectionModel().getSelectedItem().getCustomer_ID();
            int userID = comboUser.getSelectionModel().getSelectedItem().getUserID();
            int contactID = comboContact.getSelectionModel().getSelectedItem().getContact_ID();
            boolean blankField = false;
            boolean overlapAppt = false;
            boolean outsideHours = false;
            boolean invalidEndTime = false;

            for (Appointment appointment : DBAppointment.getCustomerAppointments(customerID)){
                if (appointment.getEnd().isAfter(start) && appointment.getStart().isBefore(end)){
                    overlapAppt = true;
                    Alert_Utility.displayAlert(4);
                    break;
                }
            }

            if (startTime.isBefore(General_Utility.getBusinessOpen()) || startTime.isAfter(General_Utility.getBusinessClose()) ||
                    endTime.isBefore(General_Utility.getBusinessOpen()) || endTime.isAfter(General_Utility.getBusinessClose())){
                outsideHours = true;
                Alert_Utility.displayAlert(5);
            }

            if (endTime.isBefore(startTime)){
                invalidEndTime = true;
                Alert_Utility.displayAlert(7);
            }

            if (title.isBlank() || desc.isBlank() || location.isBlank() || type.isBlank() || startTime == null ||
                    date == null || endTime == null || comboCustomer.getSelectionModel().isEmpty() ||
                    comboUser.getSelectionModel().isEmpty() || comboContact.getSelectionModel().isEmpty()){
                blankField = true;
                Alert_Utility.displayAlert(1);
            }

            if (!overlapAppt && !outsideHours && !blankField && !invalidEndTime) {
                    DBAppointment.addNewAppointment(title, desc, location, type, start, end, customerID, userID, contactID);
                    General_Utility.returnToMainMenu(event);
                }
        }
        catch (Exception e) {
            Alert_Utility.displayAlert(6);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Cancels the addition of an appointment to the database.
     * Displays an alert to confirm cancellation so that data is not accidentally lost.
     * @param event The Cancel button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Do you want to cancel and return to the main menu?");
        alert.setContentText("Data will not be saved.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            General_Utility.returnToMainMenu(event);
        }

    }

    /**
     * LAMBDA EXPRESSION  USAGE to initialize the Add Appointment form.
     * The lambda expression is used to set the items in the five combo boxes on the Add Appointment form.
     * The lambda expression simplifies the redundant lines of code.
     * Instead of writing the setItems and setPromptText functions 5 different times, you only need to write
     * them one time and then use the lambda to put the combos, lists to fill them, and prompts all on one line.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ComboInterface comboSetup = (combo, list, prompt) -> {
            combo.setItems(list);
            combo.setPromptText(prompt);
        };

        comboSetup.setCombo(comboCustomer, DBCustomer.getAllCustomers(), "Choose a Customer...");
        comboSetup.setCombo(comboUser, DBUser.getAllUsers(), "Choose a User...");
        comboSetup.setCombo(comboContact, DBContact.getAllContacts(), "Choose a Contact...");
        comboSetup.setCombo(startCombo, General_Utility.fillTimeSelection(), "Choose a Start Time...");
        comboSetup.setCombo(endCombo, General_Utility.fillTimeSelection(), "Choose an End Time...");


    }
}
