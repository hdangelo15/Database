package Utilities;

import javafx.scene.control.TableColumn;

/**
 * An interface that holds the set columns method for use in a lambda expression.
 * @author Hayley D'Angelo
 */
public interface ColumnInterface {

    /**
     * Sets the column values in a table view.
     * @param column
     * @param value
     */
    void setColumns (TableColumn column, String value);

}
