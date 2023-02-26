package Model;

import java.time.LocalDateTime;

/**
 * A class that models the User objects.
 * @author Hayley D'Angelo
 */
public class User {

    private int userID;
    private String username;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * The constructor for the User class.
     * @param userID
     * @param username
     * @param password
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     */
    public User (int userID, String username, String password, LocalDateTime createDate, String createdBy,
                 LocalDateTime lastUpdate, String lastUpdatedBy){

        this.userID = userID;
        this.username = username;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;

    }

    /**
     * Gets the user ID for a user.
     * @return userID
     */
    public int getUserID() { return userID; }

    /**
     * Overrides the toString method for User objects to display the user ID and name.
     * @return A string displaying the user ID and name.
     */
    @Override
    public String toString(){
        return (userID + " " + username);
    }
}
