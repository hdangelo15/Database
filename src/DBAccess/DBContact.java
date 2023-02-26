package DBAccess;

import Model.Contact;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * A class the contains all of the methods to retrieve contacts from the database.
 * @author Hayley D'Angelo
 */
public class DBContact {

    /**
     * Gets a list of all the contacts in the database.
     * @return contacts A list of all the contacts in the database
     */
    public static ObservableList<Contact> getAllContacts() {

        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        try {
            // Access the database to retrieve contacts
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT * FROM contacts";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();
            // Scroll the results set to add contacts to the list
            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact nextContact = new Contact(contactID, contactName, email);
                contacts.add(nextContact);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return contacts;

    }

    /**
     * Gets a contact from the database
     * @param contactID The ID of the contact to retrieve.
     * @return selectedContact The contact retrieved from the database.
     */
    public static Contact getContactByID(int contactID){

        Contact selectedContact = null;

        try {
            // Access the database to retrieve contact
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT * FROM contacts WHERE Contact_ID = ?";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, Integer.toString(contactID));

            ResultSet rs = ps.executeQuery();
            rs.next();
            // Gets the contact from the result set
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            selectedContact = new Contact(id, name, email);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return selectedContact;

    }

}
