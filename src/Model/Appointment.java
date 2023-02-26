package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/**
 * A class that models the Appointment objects.
 * @author Hayley D'Angelo
 */
public class Appointment {

    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private LocalDateTime Start;
    private LocalDateTime End;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;
    private int Customer_ID;
    private int User_ID;
    private int Contact_ID;
    private String Contact;

    /**
     * Constructor for the Appointment class.
     * @param Appointment_ID
     * @param Title
     * @param Description
     * @param Location
     * @param Type
     * @param Start
     * @param End
     * @param Create_Date
     * @param Created_By
     * @param Last_Update
     * @param Last_Updated_By
     * @param Customer_ID
     * @param User_ID
     * @param Contact_ID
     * @param Contact
     */
    public Appointment(int Appointment_ID, String Title, String Description, String Location, String Type, LocalDateTime Start,
                       LocalDateTime End, LocalDateTime Create_Date, String Created_By, LocalDateTime Last_Update, String Last_Updated_By,
                       int Customer_ID, int User_ID, int Contact_ID, String Contact){

        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;
        this.Contact = Contact;

    }

    /**
     * Gets the appointment ID of an appointment.
     * @return Appointment_ID
     */
    public int getAppointment_ID() {
        return Appointment_ID;
    }

    /**
     * Gets the title of an appointment.
     * @return Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Gets the description of an appointment.
     * @return Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Gets the location of an appointment.
     * @return Location
     */
    public String getLocation() {
        return Location;
    }

    /**
     * Gets the type of an appointment.
     * @return Type
     */
    public String getType() {
        return Type;
    }

    /**
     * Gets the start time of an appointment.
     * @return Start
     */
    public LocalDateTime getStart() {
        return Start;
    }

    /**
     * Gets the end time of an appointment.
     * @return End
     */
    public LocalDateTime getEnd() {
        return End;
    }

    /**
     * Gets the customer ID of an appointment.
     * @return Customer_ID
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }

    /**
     * Gets the user ID of an appointment.
     * @return User_ID
     */
    public int getUser_ID() {
        return User_ID;
    }

    /**
     * Gets the contact ID of an appointment.
     * @return Contact_ID
     */
    public int getContact_ID() {
        return Contact_ID;
    }

    /**
     * Gets the contact name of an appointment.
     * @return Contact
     */
    public String getContact() { return Contact; }

}
