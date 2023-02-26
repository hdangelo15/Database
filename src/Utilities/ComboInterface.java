package Utilities;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * An interface that holds a method for setting up combo boxes with a lambda expression.
 * @author Hayley D'Angelo
 */
public interface ComboInterface {

    /**
     * Sets up the values and prompt for a combo box.
     * @param combo
     * @param list
     * @param prompt
     */
    void setCombo (ComboBox combo, ObservableList list, String prompt);

}
