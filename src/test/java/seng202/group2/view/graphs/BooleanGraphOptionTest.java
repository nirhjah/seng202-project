package seng202.group2.view.graphs;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BooleanGraphOption.
 *
 * @author Connor Dunlop
 */
class BooleanGraphOptionTest {

    /** To create JFX components JFX must be initialized. */
    @BeforeAll
    static void initializeJavaFXEnvironment() {
        Platform.startup(() -> {});
    }

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