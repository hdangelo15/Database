package Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Sets up the prepared statements to query the database.
 * @author Hayley D'Angelo
 */
public class DBQuery {

    // Statement reference
    private static PreparedStatement statement;

    /**
     * Creates a statement object.
     * @param conn The connection to use.
     * @param sqlStatement The statement to use in the query.
     * @throws SQLException
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException
    {
        statement = conn.prepareStatement(sqlStatement);
    }

    /**
     * Returns the prepared statement.
     * @return statement The PreparedStatement to return.
     */
    public static PreparedStatement getPreparedStatement()
    {
        return statement;
    }

}
