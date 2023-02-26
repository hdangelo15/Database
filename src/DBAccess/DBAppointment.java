package DBAccess;

import Model.Appointment;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * A class that holds all of the methods for accessing the database to add and retrieve Appointment objects and lists.
 * @author Hayley D'Angelo
 */
public class DBAppointment {

    /**
     * Formatter for storing dates in the database.
     */
    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC));

    /**
     * Accesses the database to return all of the appointments.
     * @return appointments An observable list of all the appointments in the database.
     */
    public static ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            // Access database and get result set
            Connection conn = DBConnection.startConnection();

            String selectStatement = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, " +
                    "appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Create_Date, " +
                    "appointments.Created_By, appointments.Last_Update, appointments.Last_Updated_By, appointments.Customer_ID, " +
                    "appointments.User_ID, appointments.Contact_ID, contacts.Contact_Name FROM appointments INNER JOIN contacts " +
                    "ON appointments.Contact_ID = contacts.Contact_ID;";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();

            // Scroll result set to add appointments to the list
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");

                Appointment nextAppointment = new Appointment(id, title, description, location, type, start, end, createDate, createdBy,
                        lastUpdate, lastUpdatedBy, customerID, userID, contactID, contact);

                appointments.add(nextAppointment);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return appointments;

    }

    /**
     * Adds a new appointment to the database.
     * Converts local times to instants and formats them for storage in the database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     */
    public static void addNewAppointment(String title, String description, String location, String type,
                                      LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {

        // Convert LocalDateTimes to Instants
        ZonedDateTime zstart = ZonedDateTime.of(start,ZoneId.systemDefault());
        ZonedDateTime zend = ZonedDateTime.of(end,ZoneId.systemDefault());
        Instant istart = zstart.toInstant();
        Instant iend = zend.toInstant();


        try {
            // Access the database to add appointment
            Connection conn = DBConnection.startConnection();
            String insertStatement = "INSERT into appointments (Title, Description, Location, Type, " +
                    "Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (?,?,?,?,?,?,current_timestamp,?,current_timestamp,?,?,?,?)";

            DBQuery.setPreparedStatement(conn, insertStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setString(5, formatter.format(istart));
            ps.setString(6, formatter.format(iend));
            ps.setString(7, DBUser.getCurrentUsername());
            ps.setString(8, DBUser.getCurrentUsername());
            ps.setString(9, Integer.toString(customerID));
            ps.setString(10, Integer.toString(userID));
            ps.setString(11, Integer.toString(contactID));

            ps.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates an existing appointment record in the database.
     * Converts local times to instants and formats them for storage in the database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     * @param appointmentID The appointment to be updated
     */
    public static void updateAppointment (String title, String description, String location, String type, LocalDateTime start,
                                          LocalDateTime end, int customerID, int userID, int contactID, int appointmentID) {

        // Convert Local Times to Instants
        ZonedDateTime zstart = ZonedDateTime.of(start,ZoneId.systemDefault());
        ZonedDateTime zend = ZonedDateTime.of(end,ZoneId.systemDefault());
        Instant istart = zstart.toInstant();
        Instant iend = zend.toInstant();

        try {
            // Access the database to update appointment
            Connection conn = DBConnection.startConnection();
            String updateStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, " +
                    "Start = ?, End = ?, Last_Update = current_timestamp, Last_Updated_By = ?, Customer_ID = ?, " +
                    "User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

            DBQuery.setPreparedStatement(conn, updateStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setString(5, formatter.format(istart));
            ps.setString(6, formatter.format(iend));
            ps.setString(7, DBUser.getCurrentUsername());
            ps.setString(8, Integer.toString(customerID));
            ps.setString(9, Integer.toString(userID));
            ps.setString(10, Integer.toString(contactID));
            ps.setString(11, Integer.toString(appointmentID));

            ps.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes an appointment from the database.
     * @param appointmentID The appointment to be deleted.
     */
    public static void deleteAppointment(int appointmentID) {
        try {
            Connection conn = DBConnection.startConnection();
            String deleteStatement = "DELETE from appointments WHERE Appointment_ID = ?";
            DBQuery.setPreparedStatement(conn, deleteStatement); // Create Prepared Statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, Integer.toString(appointmentID));

            ps.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets the associated appointments for a customer.
     * @param customer_ID The customer to pull appointments for.
     * @return customerAppointments A list of associated appointments for the customer.
     */
    public static ObservableList<Appointment> getCustomerAppointments(int customer_ID){

            ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

            try {
                // Access the database to retrieve appointments
                Connection conn = DBConnection.startConnection();
                String selectStatement = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, " +
                        "appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Create_Date, " +
                        "appointments.Created_By, appointments.Last_Update, appointments.Last_Updated_By, appointments.Customer_ID, " +
                        "appointments.User_ID, appointments.Contact_ID, contacts.Contact_Name FROM appointments INNER JOIN contacts " +
                        "ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Customer_ID = ?;";

                DBQuery.setPreparedStatement(conn, selectStatement);
                PreparedStatement ps = DBQuery.getPreparedStatement();

                ps.setString(1, Integer.toString(customer_ID));

                ResultSet rs = ps.executeQuery();
                // Scroll result set to add appointments to the list
                while(rs.next()){
                    int id = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                    LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                    String createdBy = rs.getString("Created_By");
                    LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                    String lastUpdatedBy = rs.getString("Last_Updated_By");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    String contact = rs.getString("Contact_Name");

                    Appointment nextAppointment = new Appointment(id, title, description, location, type, start, end, createDate, createdBy,
                            lastUpdate, lastUpdatedBy, customerID, userID, contactID, contact);

                    customerAppointments.add(nextAppointment);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return customerAppointments;

    }

    /**
     * Gets a list of appointments from the database by weeks.
      * @param filterDate The start of the week to filter.
     * @return appointmentsByWeek A list of the selected week's appointments.
     */
    public static ObservableList<Appointment> getAppointmentsByWeek(LocalDateTime filterDate){

        ObservableList<Appointment> appointmentsByWeek = FXCollections.observableArrayList();

        try {
            // Access the database to retrieve appointments
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, " +
                    "appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Create_Date, " +
                    "appointments.Created_By, appointments.Last_Update, appointments.Last_Updated_By, appointments.Customer_ID, " +
                    "appointments.User_ID, appointments.Contact_ID, contacts.Contact_Name FROM appointments INNER JOIN contacts " +
                    "ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Start BETWEEN ? and ?;";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, formatter.format(filterDate));
            ps.setString(2, formatter.format(filterDate.plusWeeks(1)));

            ResultSet rs = ps.executeQuery();
            // Scroll result set to add appointments to the list
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");

                Appointment nextAppointment = new Appointment(id, title, description, location, type, start, end, createDate, createdBy,
                        lastUpdate, lastUpdatedBy, customerID, userID, contactID, contact);

                appointmentsByWeek.add(nextAppointment);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return appointmentsByWeek;

    }

    /**
     * Get appointments for the current user.
     * @return userAppointments A list of the current user's appointments.
     */
    public static ObservableList<Appointment> getUserAppointments(){

        ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();

        try {
            // Access the database to retrieve appointments
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, " +
                    "appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Create_Date, " +
                    "appointments.Created_By, appointments.Last_Update, appointments.Last_Updated_By, appointments.Customer_ID, " +
                    "appointments.User_ID, appointments.Contact_ID, contacts.Contact_Name FROM appointments INNER JOIN contacts " +
                    "ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.User_ID = ?;";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            int currentUserID = DBUser.getCurrentUser().getUserID();

            ps.setString(1, Integer.toString(currentUserID));

            ResultSet rs = ps.executeQuery();
            // Scroll the result set to add appointments to the list
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");

                Appointment nextAppointment = new Appointment(id, title, description, location, type, start, end, createDate, createdBy,
                        lastUpdate, lastUpdatedBy, customerID, userID, contactID, contact);

                userAppointments.add(nextAppointment);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return userAppointments;

    }

    /**
     * Gets a list of appointments of the selected type.
     * @param selectedType The type of appointments to retrieve.
     * @return appointmentsByType A list of appointments of the selected type.
     */
    public static ObservableList<Appointment> getAppointmentsByType(String selectedType){

        ObservableList<Appointment> appointmentsByType = FXCollections.observableArrayList();

        try {
            // Access the database to retrieve appointments
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, " +
                    "appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Create_Date, " +
                    "appointments.Created_By, appointments.Last_Update, appointments.Last_Updated_By, appointments.Customer_ID, " +
                    "appointments.User_ID, appointments.Contact_ID, contacts.Contact_Name FROM appointments INNER JOIN contacts " +
                    "ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Type = ?;";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, selectedType);

            ResultSet rs = ps.executeQuery();
            // Scroll the result set to add appointments to the list
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");

                Appointment nextAppointment = new Appointment(id, title, description, location, type, start, end, createDate, createdBy,
                        lastUpdate, lastUpdatedBy, customerID, userID, contactID, contact);

                appointmentsByType.add(nextAppointment);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return appointmentsByType;

    }

    /**
     * Gets a list of appointments for the selected contact.
     * @param selectedContactID The contact to retrieve appointments for.
     * @return contactAppointments A list of appointments for the selected contact.
     */
    public static ObservableList<Appointment> getContactAppointments(Integer selectedContactID){

        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

        try {
            // Access the database to retrieve appointments
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, " +
                    "appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Create_Date, " +
                    "appointments.Created_By, appointments.Last_Update, appointments.Last_Updated_By, appointments.Customer_ID, " +
                    "appointments.User_ID, appointments.Contact_ID, contacts.Contact_Name FROM appointments INNER JOIN contacts " +
                    "ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Contact_ID = ?;";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, Integer.toString(selectedContactID));

            ResultSet rs = ps.executeQuery();
            // Scroll result set to add appointments to the list
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");

                Appointment nextAppointment = new Appointment(id, title, description, location, type, start, end, createDate, createdBy,
                        lastUpdate, lastUpdatedBy, customerID, userID, contactID, contact);

                contactAppointments.add(nextAppointment);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return contactAppointments;

    }

}
