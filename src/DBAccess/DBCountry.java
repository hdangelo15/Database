package DBAccess;

import Model.Country;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

/**
 * A class that holds all of the methods for retrieving countries from the database.
 * @author Hayley D'Angelo
 */
public class DBCountry {

    /**
     * Gets a list of all the countries in the database.
     * @return countries A list of all the countries in the database.
     */
    public static ObservableList<Country> getAllCountries() {

        ObservableList<Country> countries = FXCollections.observableArrayList();

        try {
            // Access the database to retrieve countries
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT * FROM countries";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();
            // Scroll results set to add countries to the list
            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                Country nextCountry = new Country(countryID, country, createDate, createdBy, lastUpdate, lastUpdatedBy);
                countries.add(nextCountry);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return countries;

    }

    /**
     * Get a selected country from the database.
     * @param selectedCountryID The ID of the country to retrieve.
     * @return selectedCounty The country that was selected.
     */
    public static Country getSelectedCountry(int selectedCountryID) {

        Country selectedCountry = null;

        try {
            // Access the database to retrieve country
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT * FROM countries WHERE Country_ID = ?";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, Integer.toString(selectedCountryID));

            ps.execute();

            ResultSet rs = ps.getResultSet();
            // Get the selected country
            rs.next();
            int countryID = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            selectedCountry = new Country(countryID, country, createDate, createdBy, lastUpdate, lastUpdatedBy);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return selectedCountry;

    }

}
