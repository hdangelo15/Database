package Controller;


import DBAccess.DBCountry;
import DBAccess.DBCustomer;
import DBAccess.DBDivision;
import DBAccess.DBUser;
import Model.Country;
import Model.Division;
import Utilities.Alert_Utility;
import Utilities.General_Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for the Add Customer Form.
 * Allows a user to add a new customer to the database.
 * @author Hayley D'Angelo
 */
public class AddCustomer_Controller implements Initializable {

    @FXML
    private TextField customerNameText;

    @FXML
    private TextField customerAddressText;

    @FXML
    private TextField customerPostalText;

    @FXML
    private TextField customerPhoneText;

    @FXML
    private ComboBox<Country> countryCombo;

    @FXML
    private ComboBox<Division> divisionCombo;

    /**
     * Clears the division combo box when the country box is clicked.
     * This ensures that the division box can refresh with valid divisions for the selected country.
     * @param event The country combo box is clicked.
     */
    @FXML
    void countrySelect(MouseEvent event) {

            divisionCombo.getSelectionModel().clearSelection();

    }

    /**
     * Sets the items of the division combo box based on which country is selected in the country combo box.
     * Displays an error message if no country is selected.
     * @param event The division combo box is clicked.
     */
    @FXML
    void comboDivisionDisplay(MouseEvent event) {

        if (!countryCombo.getSelectionModel().isEmpty()) {
            Country selectedCountry = countryCombo.getSelectionModel().getSelectedItem();
            int selectedCountryID = selectedCountry.getCountry_ID();
            divisionCombo.setItems(DBDivision.getCountryDivisions(selectedCountryID));
            divisionCombo.setVisibleRowCount(5);
        } else {
            Alert_Utility.displayAlert(3);
        }

    }

    /**
     * Clears all the data from the form so that the user can start over.
     * @param event The CLEAR ALL button is clicked.
     */
    @FXML
    void onActionClearForm(ActionEvent event) {

        customerNameText.clear();
        customerAddressText.clear();
        customerPostalText.clear();
        customerPhoneText.clear();
        countryCombo.getSelectionModel().clearSelection();
        divisionCombo.getSelectionModel().clearSelection();

    }

    /**
     * Saves a new customer to the database.
     * Checks to make sure fields are not blank before adding and displays an error message if they are.
     * @param event The Save button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws IOException {

        try {
            String name = customerNameText.getText();
            String address = customerAddressText.getText();
            String postal = customerPostalText.getText();
            String phone = customerPhoneText.getText();
            String createdBy = DBUser.getCurrentUsername();
            String lastUpdatedBy = DBUser.getCurrentUsername();
            Division savedDivision = divisionCombo.getSelectionModel().getSelectedItem();
            int divisionID = savedDivision.getDivision_ID();
            String division = savedDivision.getDivision();
            String country = DBCountry.getSelectedCountry(savedDivision.getCountry_ID()).getCountry();

            if (name.isEmpty() || address.isEmpty() || postal.isEmpty() || phone.isEmpty() || division.isEmpty() || country.isEmpty()) {
                Alert_Utility.displayAlert(1);
            } else {
                DBCustomer.addNewCustomer(name, address, postal, phone, createdBy, lastUpdatedBy, divisionID);
                General_Utility.returnToMainMenu(event);
            }
        }catch (Exception e){
            Alert_Utility.displayAlert(6);
        }

    }

    /**
     * Cancels the addition of a customer to the database.
     * Displays a confirmation message before canceling to ensure data is not lost.
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
     * Initializes the Add Customer form.
     * Set the countries in the country combo box.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryCombo.setItems(DBCountry.getAllCountries());
        countryCombo.setVisibleRowCount(3);

    }
}