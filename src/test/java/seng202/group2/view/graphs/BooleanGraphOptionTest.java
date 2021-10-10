package seng202.group2.view.graphs;

import de.saxsys.javafx.test.JfxRunner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BooleanGraphOption.
 *
 * @author Connor Dunlop
 */
@RunWith(JfxRunner.class)
class BooleanGraphOptionTest {

    /** Tests setting and getting of boolean state. */
    @Test
    void setGetStateTest() {
        BooleanGraphOption option = new BooleanGraphOption("Option 1");

        option.setState(true);
        assertEquals(true, option.getState());

        option.setState(false);
        assertEquals(false, option.getState());
    }

    /**
     * Tests requirementsCheck.
     * BooleanGraphOption cannot be in an invalid state, so this method should always return true.
     */
    @Test
    void requirementsCheckTest() {
        BooleanGraphOption option = new BooleanGraphOption("Option 1", true);

        option.setState(false);
        assertTrue(option.requirementsMet());

        option.setState(true);
        assertTrue(option.requirementsMet());
    }
}