package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Sets up the connection to the database.
 * @author Hayley D'Angelo
 */
public class DBConnection {

    // JDBC URL Parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String serverName = "//wgudb.ucertify.com/WJ0746a";

    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + serverName;

    // Driver Interface Reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    // Username
    private static final String username = "U0746a";
    private static final String password = "53688942987";

    /**
     * Makes a connection to the database.
     * @return conn The database connection.
     */
    public static Connection startConnection() {

        try {

            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful!");

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return conn;

    }

    /**
     * Closes the connection to the database.
     */
    public static void closeConnection(){

        try {
            conn.close();
            System.out.println("Connection Closed");
        }
        catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }

    }

}
