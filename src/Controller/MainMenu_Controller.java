package Controller;


import DBAccess.DBAppointment;
import DBAccess.DBCustomer;
import Model.Appointment;
import Model.Customer;
import Utilities.Alert_Utility;
import Utilities.ColumnInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for the Main Menu.
 * Displays both a table of customers and a table of appointments.
 * Takes the user to the add/edit appointment and customer pages, allows the user to delete
 * customers and appointments, filters appointments by week and month, takes the user to
 * the contact and customer schedule pages, takes the user to the appointment reports page.
 * Allows the user to sign out of the application.
 * @author Hayley D'Angelo
 */
public class MainMenu_Controller implements Initializable {

    @FXML
    private TableView<Customer> customersTableView;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerPostalCodeCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerDivisionCol;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private TableView<Appointment> appointmentsTableView;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointment, String> appointmentDescCol;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentStartCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentEndCol;

    @FXML
    private TableColumn<Appointment, String> appointmentContactCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerCol;

    @FXML
    private DatePicker datePickerFilter;

    @FXML
    private RadioButton weekViewRadio;

    @FXML
    private RadioButton monthViewRadio;


    private static Customer customerToUpdate;

    /**
     * Gets the customer from the table on the main screen that the user has selected.
     * @return customerToUpdate
     */
    public static Customer getCustomerToUpdate() {
        return customerToUpdate;
    }

    private static Appointment appointmentToUpdate;

    /**
     * Gets the appointment from the table on the main screen that the user has selected.
     * @return appointmentToUpdate
     */
    public static Appointment getAppointmentToUpdate() {
        return appointmentToUpdate;
    }

    /**
     * Takes the user to the Contact Schedules page.
     * @param event The Contact Schedules button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionViewContactSchedules(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ContactSchedulesView.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    /**
     * Takes the user to the Customer Schedule page.
     * Uses the customer that the user has selected from the main menu to display that customer's schedule.
     * @param event The Customer Schedule button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionViewCustomerAppointments(ActionEvent event) throws IOException {

        if (customersTableView.getSelectionModel().isEmpty()){
            Alert_Utility.displayAlert(2);
        }
        else {
            customerToUpdate = customersTableView.getSelectionModel().getSelectedItem();

            Parent parent = FXMLLoader.load(getClass().getResource("../view/CustomerScheduleView.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }

    }

    /**
     * Takes the user to the Appointment Reports page.
     * @param event The Appointment Reports button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionAppointmentReports(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../view/AppointmentReports.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }

    /**
     * Takes the user to the Add Customer form.
     * @param event The Add Customer button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionAddNewCustomer(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddCustomerForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }

    /**
     * Takes the customer to the Edit Customer form.
     * Uses the selected customer to load the Edit Customer form.
     * Displays an error message if no customer is selected.
     * @param event The Edit Customer button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException {

        if (customersTableView.getSelectionModel().isEmpty()){
            Alert_Utility.displayAlert(2);
        }

        else {
            customerToUpdate = customersTableView.getSelectionModel().getSelectedItem();

            Parent parent = FXMLLoader.load(getClass().getResource("../view/EditCustomerForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }

    }

    /**
     * Takes the user to the Add Appointment form.
     * @param event The Add Appointment button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionAddNewAppointment(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddAppointmentForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }

    /**
     * Takes the customer to the Edit Appointment form.
     * Uses the selected appointment to load the Edit Appointment form.
     * Displays an error message if no appointment is selected.
     * @param event The Edit Appointment button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException {

        if (appointmentsTableView.getSelectionModel().isEmpty()){
            Alert_Utility.displayAlert(2);
        }

        else {
            appointmentToUpdate = appointmentsTableView.getSelectionModel().getSelectedItem();

            Parent parent = FXMLLoader.load(getClass().getResource("../view/EditAppointmentForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }

    }

    /**
     * Deletes the selected appointment.
     * Displays an error message if no appointment is selected.
     * Displays a custom confirmation message to confirm delete.
     * Displays a custom message after delete to let the user know the appointment has been deleted.
     * @param event The Delete Appointment button is clicked.
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

        if (appointmentsTableView.getSelectionModel().isEmpty()){
            Alert_Utility.displayAlert(2);
        }
        else {
            // Confirm delete
            appointmentToUpdate = appointmentsTableView.getSelectionModel().getSelectedItem();
            int appointmentID = appointmentToUpdate.getAppointment_ID();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to cancel appointment " + appointmentToUpdate.getAppointment_ID() +
                    " " + appointmentToUpdate.getType() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            // Display custom message that the appointment has been deleted
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBAppointment.deleteAppointment(appointmentID);
                appointmentsTableView.setItems(DBAppointment.getAllAppointments());
                Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
                deleteAlert.setTitle("Appointment Canceled");
                deleteAlert.setContentText("The selected appointment has been canceled.");
                deleteAlert.showAndWait();
            }

        }

    }

    /**
     * Deletes a customer from the database.
     * Displays an error message if no customer is selected.
     * Checks to see if a customer has any associated appointments before deleting and displays an error message if so.
     * Displays a confirmation message for the user before deleting.
     * @param event The Delete Customer button is clicked.
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

        if (customersTableView.getSelectionModel().isEmpty()){
            Alert_Utility.displayAlert(2);
        }
        else {
            // Get customer to delete and their associated appointments
            customerToUpdate = customersTableView.getSelectionModel().getSelectedItem();
            int customerID = customerToUpdate.getCustomer_ID();
            ObservableList<Appointment> customerAppointments = DBAppointment.getCustomerAppointments(customerID);

            // Customer has no associated appointments
            if (customerAppointments.isEmpty()) {

                // Display custom confirmation message
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Are you sure you want to delete customer " + customerToUpdate.getCustomer_ID() +
                        " " + customerToUpdate.getCustomer_Name() + "?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {

                    DBCustomer.deleteCustomer(customerID);
                    customersTableView.setItems(DBCustomer.getAllCustomers());

                    // Display message announcing customer deletion.
                    Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
                    deleteAlert.setTitle("Customer Deleted");
                    deleteAlert.setContentText("The selected customer has been deleted.");
                    deleteAlert.showAndWait();

                }

            }

            // Customer has associated appointments
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Failed");
                alert.setContentText("This customer has associated appointments, please cancel associated appointments and try again.");
                alert.showAndWait();
            }
        }

    }

    /**
     * Filters the appointment table by month or week.
     * Radio buttons select either month or week to filter by and the date picker selects the starting date for the filter.
     * @param event The Filter appointments button is clicked.
     */
    @FXML
    void onActionFilterAppointments(ActionEvent event) {

        LocalDateTime filterDate = datePickerFilter.getValue().atStartOfDay();

        if (weekViewRadio.isSelected()){
            appointmentsTableView.setItems(DBAppointment.getAppointmentsByWeek(filterDate));
        }

        if (monthViewRadio.isSelected()){
            ObservableList<Appointment> appointmentsByMonth = FXCollections.observableArrayList();
            for (Appointment appointment : DBAppointment.getAllAppointments()) {
                if (appointment.getStart().getMonth() == filterDate.getMonth()){
                    appointmentsByMonth.add(appointment);
                }
            }
            appointmentsTableView.setItems(appointmentsByMonth);

        }

    }

    /**
     * Resets the appointment table view after filtering.
     * @param event The Reset button is clicked.
     */
    @FXML
    void onActionResetTable(ActionEvent event) {

        appointmentsTableView.setItems(DBAppointment.getAllAppointments());

    }

    /**
     * Returns the user to the login page.
     * @param event The Exit button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionExitProgram(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }

    /**
     * LAMBDA EXPRESSION USAGE to initialize the Main Menu.
     * A lambda expression is used to set the columns for both the customer and appointment tables.
     * This simplifies the code by only typing the setCellValueFactor and PropertyValueFactory functions 1 time
     * as opposed to typing them a total of 16 times between both tables.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Lambda to set customer table columns
        ColumnInterface columns = (column, label) -> column.setCellValueFactory(new PropertyValueFactory<>(label));

        // Customer Table
        customersTableView.setItems(DBCustomer.getAllCustomers());
        columns.setColumns(customerIDCol, "Customer_ID");
        columns.setColumns(customerNameCol, "Customer_Name");
        columns.setColumns(customerAddressCol, "Address");
        columns.setColumns(customerPostalCodeCol, "Postal_Code");
        columns.setColumns(customerPhoneCol, "Phone");
        columns.setColumns(customerDivisionCol, "Division");
        columns.setColumns(customerCountryCol, "Country");

        // Appointments Table
        appointmentsTableView.setItems(DBAppointment.getAllAppointments());
        columns.setColumns(appointmentIDCol, "Appointment_ID");
        columns.setColumns(appointmentTitleCol, "Title");
        columns.setColumns(appointmentDescCol, "Description");
        columns.setColumns(appointmentLocationCol, "Location");
        columns.setColumns(appointmentTypeCol, "Type");
        columns.setColumns(appointmentContactCol, "Contact");
        columns.setColumns(appointmentStartCol, "Start");
        columns.setColumns(appointmentEndCol, "End");
        columns.setColumns(appointmentCustomerCol, "Customer_ID");

    }

}
