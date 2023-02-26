package DBAccess;

import Model.Customer;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

/**
 * A class that holds all the methods for adding, editing, and retrieving customers from the database.
 * @author Hayley D'Angelo
 */
public class DBCustomer {

    /**
     * Gets a list of all the customers in the database.
     * @return customers A list of all the customers in the database.
     */
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            // Access the database to retrieve customers
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, " +
                    "customers.Phone, customers.Create_Date, customers.Created_By, customers.Last_Update, customers.Last_Updated_By, " +
                    "customers.Division_ID, first_level_divisions.Division, countries.Country FROM customers " +
                    "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                    "INNER JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet rs = ps.executeQuery();
            // Scroll the results set to add customers to the list
            while(rs.next()){
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                String country = rs.getString("Country");

                Customer nextCustomer = new Customer(id, name, address, postal, phone, createDate, createdBy, lastUpdate,
                        lastUpdatedBy, divisionID, division, country);

                customers.add(nextCustomer);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return customers;

    }

    /**
     * Gets a customer from the database.
     * @param customerID The ID of the customer to retrieve from the database.
     * @return selectedCustomer The customer retrieved from the database.
     */
    public static Customer getCustomerByID(int customerID){

        Customer selectedCustomer = null;

        try {
            // Access the database to retrieve customer
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, " +
                    "customers.Phone, customers.Create_Date, customers.Created_By, customers.Last_Update, customers.Last_Updated_By, " +
                    "customers.Division_ID, first_level_divisions.Division, countries.Country FROM customers " +
                    "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                    "INNER JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID WHERE customers.Customer_ID = ?";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, Integer.toString(customerID));

            ResultSet rs = ps.executeQuery();
            // Get the selected customer
            rs.next();
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postal = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            String country = rs.getString("Country");

            selectedCustomer = new Customer(id, name, address, postal, phone, createDate, createdBy, lastUpdate,
                    lastUpdatedBy, divisionID, division, country);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return selectedCustomer;

    }

    /**
     * Adds a new customer to the database.
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param createdBy
     * @param lastUpdatedBy
     * @param divisionID
     */
    public static void addNewCustomer(String name, String address, String postal, String phone,
                                       String createdBy, String lastUpdatedBy, int divisionID) {
        try {
            // Access the database to add a new customer
            Connection conn = DBConnection.startConnection();
            String insertStatement = "INSERT into customers (Customer_Name, Address, Postal_Code, Phone, " +
                    "Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                    "VALUES (?,?,?,?,current_timestamp,?,current_timestamp,?,?)";

            DBQuery.setPreparedStatement(conn, insertStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setString(5, createdBy);
            ps.setString(6, lastUpdatedBy);
            ps.setString(7, Integer.toString(divisionID));

            ps.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Updates an existing customer in the database.
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param lastUpdatedBy
     * @param divisionID
     * @param customerID The ID of the customer to be updated.
     */
    public static void updateCustomer(String name, String address, String postal, String phone,
                                      String lastUpdatedBy, int divisionID, String customerID) {

        try {
            // Access the database to update customer record
            Connection conn = DBConnection.startConnection();
            String updateStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                    "Last_Update = current_timestamp, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

            DBQuery.setPreparedStatement(conn, updateStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setString(5, lastUpdatedBy);
            ps.setString(6, Integer.toString(divisionID));
            ps.setString(7, customerID);

            ps.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Deletes a customer from the database.
     * @param customerID The ID of the customer to delete.
     */
    public static void deleteCustomer(int customerID) {

        try {
            // Access the database to delete customer
            Connection conn = DBConnection.startConnection();
            String deleteStatement = "DELETE from customers WHERE Customer_ID = ?";

            DBQuery.setPreparedStatement(conn, deleteStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, Integer.toString(customerID));

            ps.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
