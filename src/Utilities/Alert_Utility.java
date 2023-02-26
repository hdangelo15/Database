package Utilities;

import javafx.scene.control.Alert;

/**
 * A class with methods that display various alert messages throughout the application.
 * @author Hayley D'Angelo
 */
public class Alert_Utility {

    /**
     * Displays various alerts throughout the application.
     * @param alertType The alert to be displayed.
     */
    public static void displayAlert(int alertType) {

        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);

        switch (alertType) {
            case 1:
                alertError.setTitle("Error");
                alertError.setHeaderText("Blank Field");
                alertError.setContentText("One or more fields in the form are blank. Please fill out the form completely and try again.");
                alertError.showAndWait();
                break;
            case 2:
                alertError.setTitle("Error");
                alertError.setHeaderText("No Item Selected");
                alertError.setContentText("Please make a selection to continue.");
                alertError.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("No Country Selected");
                alertError.setContentText("Please select a country first and try again.");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Overlapping Appointments");
                alertError.setContentText("The selected customer has another appointment during the selected appointment time.  Please choose a different time and try again.");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Appointment is Outside of Business Hours");
                alertError.setContentText("Please schedule appointments between 8:00am EST and 10:00pm EST.");
                alertError.showAndWait();
                break;
            case 6:
                alertError.setTitle("Error");
                alertError.setHeaderText("Save Failed");
                alertError.setContentText("Please try again.");
                alertError.showAndWait();
                break;
            case 7:
                alertError.setTitle("Error");
                alertError.setHeaderText("Invalid Appointment Time");
                alertError.setContentText("End time must be after start time.  Please try again.");
                alertError.showAndWait();
                break;
            case 8:
                alertConfirm.setTitle("Confirm Cancel");
                alertConfirm.setHeaderText("Are you sure you want to cancel?");
                alertConfirm.setContentText("Inputs will not be saved and you will return to the main menu.");
                alertConfirm.showAndWait();
                break;

        }

    }

}
