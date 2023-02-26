package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * A class to hold various utility methods that are used multiple times in the application.
 * @author Hayley D'Angelo
 */
public class General_Utility {

    private static LocalTime businessOpen;
    private static LocalTime businessClose;
    private static ZoneId businessZoneID = ZoneId.of("America/New_York");
    private static ZoneId localZoneID = ZoneId.systemDefault();
    private static LocalDate businessOpenDate = LocalDate.now();

    /**
     * A method to generate a list of times to fill a combo box.
     * @return timesList A list of start or end times for appointments.
     */
    public static ObservableList<String> fillTimeSelection() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 40, 1);
        LocalTime last = LocalTime.of(23, 50);

        ObservableList<String> timesList = FXCollections.observableArrayList();

        while(start.isBefore(end)){
            timesList.add(start.format(formatter));
            start = start.plusMinutes(10);
        }

        timesList.add(last.format(formatter));

        return timesList;

    }

    /**
     * Get the opening time for the business in local time.
     * @return businessOpen The opening time for the business in local time.
     */
    public static LocalTime getBusinessOpen(){

        LocalTime businessOpenTime = LocalTime.of(8, 0);
        ZonedDateTime businessOpenZDT = ZonedDateTime.of(businessOpenDate, businessOpenTime, businessZoneID);
        Instant businessOpenUTC = businessOpenZDT.toInstant();
        ZonedDateTime businessOpenLocal = businessOpenUTC.atZone(localZoneID);
        businessOpen = businessOpenLocal.toLocalTime();

        return businessOpen;

    }

    /**
     * Get the closing time for the business in local time.
     * @return businessClose The closing time for the business in local time.
     */
    public static LocalTime getBusinessClose(){

        LocalTime businessCloseTime = LocalTime.of(22, 0);
        ZonedDateTime businessCloseZDT = ZonedDateTime.of(businessOpenDate, businessCloseTime, businessZoneID);
        Instant businessCloseUTC = businessCloseZDT.toInstant();
        ZonedDateTime businessCloseLocal = businessCloseUTC.atZone(localZoneID);
        businessClose = businessCloseLocal.toLocalTime();

        return businessClose;

    }

    /**
     * Takes the user to the Main Menu.
     * @param event An action triggers the return to the main menu.
     * @throws IOException
     */
    public static void returnToMainMenu(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(General_Utility.class.getResource("../view/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }

}
