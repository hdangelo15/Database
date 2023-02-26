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
import Utilities.General_Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for the Edit Appointment page.
 * Allows the user to edit an existing appointment.
 * @author Hayley D'Angelo
 */
public class EditAppointment_Controller implements Initializable {

    @FXML
    private TextField appointmentIDText;

    @FXML
    private TextField appointmentTitleText;

    @FXML
    private TextField appointmentDescText;

    @FXML
    private TextField appointmentLocText;

    @FXML
    private TextField appointmentTypeText;

    @FXML
    private ComboBox<Customer> comboCustomer;

    @FXML
    private ComboBox<User> comboUser;

    @FXML
    private ComboBox<Contact> comboContact;

    @FXML
    private DatePicker datePickerStart;

    @FXML
    private ComboBox<String> comboStartTime;

    @FXML
    private ComboBox<String> comboEndTime;

    private Appointment selectedAppointment;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

    /**
     * Clears all the data on the form so the user can start over.
     * @param event The CLEAR ALL button is clicked.
     */
    @FXML
    void onActionClearForm(ActionEvent event) {

        appointmentTitleText.clear();
        appointmentDescText.clear();
        appointmentLocText.clear();
        appointmentTypeText.clear();
        comboStartTime.setValue(null);
        datePickerStart.setValue(null);
        comboEndTime.setValue(null);
        comboCustomer.setValue(null);
        comboUser.setValue(null);
        comboContact.setValue(null);

        comboCustomer.setPromptText("Choose a Customer...");
        comboUser.setPromptText("Choose a User...");
        comboContact.setPromptText("Choose a Contact...");
        comboStartTime.setPromptText("Choose a Start Time...");
        comboEndTime.setPromptText("Choose an End Time...");

    }

    /**
     * Saves the updated appointment to the database.
     * Checks to make sure fields are not blank, the appointment is scheduled within business hours,
     * the customer doesn't have overlapping appointments, and that the end time is after the start time.
     * Displays an error message for each condition that needs to be fixed.
     * @param event The Save button is clicked.
     */
    @FXML
    void onActionSaveAppointment(ActionEvent event) {

        try{

            String title = appointmentTitleText.getText();
            String desc = appointmentDescText.getText();
            String location = appointmentLocText.getText();
            String type = appointmentTypeText.getText();
            LocalTime startTime = LocalTime.parse(comboStartTime.getSelectionModel().getSelectedItem(), formatter);
            LocalDate date = datePickerStart.getValue();
            LocalDateTime start = LocalDateTime.of(date, startTime);
            LocalTime endTime = LocalTime.parse(comboEndTime.getSelectionModel().getSelectedItem(), formatter);
            LocalDateTime end = LocalDateTime.of(date, endTime);
            int customerID = comboCustomer.getSelectionModel().getSelectedItem().getCustomer_ID();
            int userID = comboUser.getSelectionModel().getSelectedItem().getUserID();
            int contactID = comboContact.getSelectionModel().getSelectedItem().getContact_ID();
            int apptID = Integer.parseInt(appointmentIDText.getText());
            boolean blankField = false;
            boolean overlapAppt = false;
            boolean outsideHours = false;
            boolean invalidEndTime = false;

            for (Appointment appointment : DBAppointment.getCustomerAppointments(customerID)){
                if (appointment.getEnd().isAfter(start) && appointment.getStart().isBefore(end) && apptID != selectedAppointment.getAppointment_ID()){
                    overlapAppt = true;
                    Alert_Utility.displayAlert(4);
                    break;
                }
            }

            if (title.isEmpty() || desc.isEmpty() || location.isEmpty() || type.isEmpty()) {
                blankField = true;
                Alert_Utility.displayAlert(1);
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

            if (!overlapAppt && !outsideHours && !blankField && !invalidEndTime){
                DBAppointment.updateAppointment(title, desc, location, type, start, end, customerID, userID, contactID, apptID);

                General_Utility.returnToMainMenu(event);

            }
        }
        catch (Exception e) {

            Alert_Utility.displayAlert(6);
            System.out.println(e.getMessage());

        }

    }

    /**
     * Cancels the update to an appointment in the database.
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
     * Initializes the Edit Appointment form.
     * Sets the items in the combo boxes and pulls the initial display values from the appointment
     * the user selected from the main menu.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedAppointment = MainMenu_Controller.getAppointmentToUpdate();

        comboCustomer.setItems(DBCustomer.getAllCustomers());
        comboCustomer.setValue(DBCustomer.getCustomerByID(selectedAppointment.getCustomer_ID()));

        comboUser.setItems(DBUser.getAllUsers());
        comboUser.setValue(DBUser.getUserByID(selectedAppointment.getUser_ID()));

        comboContact.setItems(DBContact.getAllContacts());
        comboContact.setValue(DBContact.getContactByID(selectedAppointment.getContact_ID()));

        comboStartTime.setItems(General_Utility.fillTimeSelection());
        comboStartTime.setValue(selectedAppointment.getStart().format(formatter));

        comboEndTime.setItems(General_Utility.fillTimeSelection());
        comboEndTime.setValue(selectedAppointment.getEnd().format(formatter));

        appointmentIDText.setText(String.valueOf(selectedAppointment.getAppointment_ID()));
        appointmentTitleText.setText(selectedAppointment.getTitle());
        appointmentDescText.setText(selectedAppointment.getDescription());
        appointmentLocText.setText(selectedAppointment.getLocation());
        appointmentTypeText.setText(selectedAppointment.getType());
        datePickerStart.setValue(selectedAppointment.getStart().toLocalDate());

    }

}
