package DBAccess;

import Model.User;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

/**
 * A class that holds all of the methods to retrieve users from the database and validate login attempts.
 * @author Hayley D'Angelo
 */
public class DBUser {

    /**
     * The username of the user who is currently logged into the application.
     */
    private static String currentUsername;

    /**
     * Gets a list of all of the users in the database.
     * @return users A list of all of the users in the database.
     */
    public static ObservableList<User> getAllUsers() {

        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            // Access the database to retrieve users
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT * FROM users";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet rs = ps.executeQuery();
            // Scroll the result set to add users to the list
            while(rs.next()){
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String password = rs.getString("Password");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                User nextUser = new User(id, name, password, createDate, createdBy, lastUpdate, lastUpdatedBy);

                users.add(nextUser);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return users;

    }

    /**
     * Gets the selected user from the database.
     * @param userID The ID of the user to retrieve.
     * @return selectedUser The user retrieved from the database.
     */
    public static User getUserByID(int userID){

        User selectedUser = null;

        try {
            // Access the database to retrieve user
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT * FROM users WHERE User_ID = ?";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, Integer.toString(userID));

            ResultSet rs = ps.executeQuery();
            // Get the values for the selected user from the database
            rs.next();
            int id = rs.getInt("User_ID");
            String name = rs.getString("User_Name");
            String address = rs.getString("Password");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            selectedUser = new User(id, name, address, createDate, createdBy, lastUpdate, lastUpdatedBy);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return selectedUser;

    }

    /**
     * Confirms that a matching username and password exist in the database before allowing the user to login.
     * @param usernameInput
     * @param passwordInput
     * @return boolean True if there is a matching username and password, false if there is not.
     */
    public static boolean validateLogin(String usernameInput, String passwordInput) {

        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            // Access the database to retrieve users
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, usernameInput);
            ps.setString(2, passwordInput);

            ps.execute();

            ResultSet rs = ps.getResultSet();
            // Scroll the result set to add users to the list
            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                User validUser = new User(userID, username, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
                users.add(validUser);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return !users.isEmpty();

    }

    /**
     * Gets the current user from the database.
     * @return currentUser The user that is currently logged into the application.
     */
    public static User getCurrentUser(){

        User currentUser = null;

        try {
            // Access the database to retrieve user
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT * FROM users WHERE User_Name = ?";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, currentUsername);

            ResultSet rs = ps.executeQuery();
            // Get the values for the user from the database
            rs.next();
            int id = rs.getInt("User_ID");
            String name = rs.getString("User_Name");
            String address = rs.getString("Password");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            currentUser = new User(id, name, address, createDate, createdBy, lastUpdate, lastUpdatedBy);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return currentUser;

    }

    /**
     * Sets the username of the current user.
     * @param inputUser The username of the user that is currently logged in.
     */
    public static void setCurrentUsername(String inputUser) {
        currentUsername = inputUser;
    }

    /**
     * Gets the username of the current user.
     * @return currentUsername
     */
    public static String getCurrentUsername () {
        return currentUsername;
    }

}
