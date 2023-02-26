package DBAccess;

import Model.Division;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

/**
 * A class that hold all of the methods to retrieve divisions from the database.
 * @author Hayley D'Angelo
 */
public class DBDivision {

    /**
     * Gets a list of all of the divisions for a selected country.
     * @param countryID The ID of the country to retrieve divisions for.
     * @return divisions A list of all of the divisions for the selected country.
     */
    public static ObservableList<Division> getCountryDivisions(int countryID) {

        ObservableList<Division> divisions = FXCollections.observableArrayList();

        try {
            // Access the database to retrieve divisions
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ?";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, Integer.toString(countryID));

            ps.execute();

            ResultSet rs = ps.getResultSet();
            // Scroll result set to add divisions to list
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int country_id = rs.getInt("COUNTRY_ID");

                Division nextDivision = new Division(divisionID, division, createDate, createdBy, lastUpdate, lastUpdatedBy, country_id);
                divisions.add(nextDivision);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return divisions;

    }

    /**
     * Gets a division from the database.
     * @param selectedDivisionID The ID of the division to retrieve.
     * @return selectedDivision The division the was retrieved from the database.
     */
    public static Division getSelectedDivision(int selectedDivisionID) {

        Division selectedDivision = null;

        try {
            // Access the database to retrieve division
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, Integer.toString(selectedDivisionID));

            ps.execute();

            ResultSet rs = ps.getResultSet();
            // Get the selected division
            rs.next();
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int country_id = rs.getInt("COUNTRY_ID");

            selectedDivision = new Division(divisionID, division, createDate, createdBy, lastUpdate, lastUpdatedBy, country_id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return selectedDivision;

    }

}
