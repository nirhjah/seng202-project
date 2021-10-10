package seng202.group2.view.graphs;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for NumericGraphOption class.
 * @author Connor Dunlop
 */
class NumericGraphOptionTest {

    /** JavaFX environment must be initialised to use JavaFX components. */
    @BeforeAll
    static void initializeJavaFXEnvironment() {
        JFXPanel p = new JFXPanel();
    }

    /** Tests getting and setting of the numerical value. */
    @Test
    void setGetValueTest() {
        NumericGraphOption option = new NumericGraphOption("Option 1");
        option.setBounds(0, 50);

        option.setValue(1);
        assertEquals(1, option.getValue());
    }

    /** Tests setting boundary values by trying to set the value outside the boundaries */
    @Test
    void boundaryValueTest() {
        NumericGraphOption option = new NumericGraphOption("Option 1");
        option.setBounds(1, 5);

        option.setValue(3);
        option.setValue(4);
        option.setValue(5);
        option.setBounds(3, 5);

        assertThrows(IllegalArgumentException.class, () -> option.setValue(2));
        assertThrows(IllegalArgumentException.class, () -> option.setValue(6));
    }

}