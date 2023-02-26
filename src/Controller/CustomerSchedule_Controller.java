package Controller;

import DBAccess.DBAppointment;
import Model.Appointment;
import Model.Customer;
import Utilities.ColumnInterface;
import Utilities.General_Utility;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller class for the Customer Schedule page that is the THIRD REPORT FOR THIS PROJECT.
 * Displays a schedule for each customer in the database.
 * @author Hayley D'Angelo
 */
public class CustomerSchedule_Controller implements Initializable {

    @FXML
    private Label customerLabel;

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
    private TableColumn<Appointment, Integer> contactIDCol;

    /**
     * Takes the user back to the main menu.
     * @param event The Back button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        General_Utility.returnToMainMenu(event);

    }

    /**
     * LAMBDA EXPRESSION USAGE to initialize the Customer Schedule page.
     * The lambda is used here to simplify setting the values of the columns in the appointments table.
     * Instead of typing the setCellValueFactor and PropertyValueFactory functions 7 different times,
     * they are just written once in the lambda expression and then the column name and value are
     * entered as parameters.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Customer selectedCustomer = MainMenu_Controller.getCustomerToUpdate();
        ObservableList<Appointment> customerAppointments = DBAppointment.getCustomerAppointments(selectedCustomer.getCustomer_ID());

        customerLabel.setText(selectedCustomer.getCustomer_Name());

        appointmentTableView.setItems(customerAppointments);
        ColumnInterface columns = (column, label) -> column.setCellValueFactory(new PropertyValueFactory<>(label));
        columns.setColumns(apptIDCol, "Appointment_ID");
        columns.setColumns(titleCol, "Title");
        columns.setColumns(descCol, "Description");
        columns.setColumns(typeCol, "Type");
        columns.setColumns(startCol, "Start");
        columns.setColumns(endCol, "End");
        columns.setColumns(contactIDCol, "Contact_ID");

    }

}
