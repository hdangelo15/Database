package Controller;

import DBAccess.DBAppointment;
import DBAccess.DBContact;
import Model.Appointment;
import Model.Contact;
import Utilities.ColumnInterface;
import Utilities.General_Utility;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller class for the Contact Schedules page.
 * Displays a schedule for each contact in the database.
 * @author Hayley D'Angelo
 */
public class ContactSchedules_Controller implements Initializable {

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private Label contactLabel;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> descCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, Integer> custIDCol;

    /**
     * Takes the user back to the Main Menu.
     * @param event The Back button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        General_Utility.returnToMainMenu(event);

    }

    /**
     * LAMBDA EXPRESSION USAGE to display the schedule for the selected Contact.
     * The lambda expression is used here to set the values for the columns in the table.
     * Instead of having to write the setCellValueFactory and PropertyValueFactory functions 7 times,
     * it is just written once in the lambda expression using the column names and values as inputs.
     * @param event The Display Schedule button is clicked.
     */
    @FXML
    void onActionDisplaySchedule(ActionEvent event) {

        Contact selectedContact = contactCombo.getValue();
        String contactName = selectedContact.getContact_Name();
        Integer contactID = selectedContact.getContact_ID();
        ObservableList<Appointment> contactAppointments = DBAppointment.getContactAppointments(contactID);

        contactLabel.setText(contactName);

        appointmentTableView.setItems(contactAppointments);
        ColumnInterface columns = (column, label) -> column.setCellValueFactory(new PropertyValueFactory<>(label));
        columns.setColumns(apptIDCol, "Appointment_ID");
        columns.setColumns(titleCol, "Title");
        columns.setColumns(descCol, "Description");
        columns.setColumns(typeCol, "Type");
        columns.setColumns(startCol, "Start");
        columns.setColumns(endCol, "End");
        columns.setColumns(custIDCol, "Customer_ID");

    }

    /**
     * Initializes the Contact Schedule page.
     * Sets the items in the contact combo box.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactCombo.setItems(DBContact.getAllContacts());
        contactCombo.setPromptText("Choose a contact...");

    }
}
