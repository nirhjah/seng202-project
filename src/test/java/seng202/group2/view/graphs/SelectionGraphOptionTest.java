package seng202.group2.view.graphs;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SelectionGraphOption class.
 * @author Connor Dunlop
 */
class SelectionGraphOptionTest {

    /** JFX must be initialised to construct JFX components. */
    @BeforeAll
    static void initializeJavaFXEnvironment() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {}
    }

    /** Tests setting and getting of choices */
    @Test
    void setGetItemsTest() {
        List<Integer> choices = new ArrayList<Integer>(List.of(1, 2, 3));

        SelectionGraphOption<Integer> option = new SelectionGraphOption<>("Option 1");
        option.setItems(choices);

        Integer choice4 = 4;
        Integer choice5 = 5;
        choices.add(choice4);
        choices.add(choice5);

        option.addItems(choice4, choice5);

        List<Integer> listedChoices = option.getItems();
        for (int i = 0; i < listedChoices.size(); i++) {
            assertSame(choices.get(i), listedChoices.get(i));
        }
    }

    /** Tests setting and getting the selected item */
    @Test
    void setGetSelectionTest() {
        SelectionGraphOption<String> option = new SelectionGraphOption<>("Option 1");

        String choice1 = "Choice 1";
        String choice2 = "Choice 2";
        option.addItems(choice1, choice2);

        assertNull(option.getSelectedItem());

        option.setSelectedItem(choice2);
        assertEquals(choice2, option.getSelectedItem());
    }

    /** Tests state validity for plotting checking. */
    @Test
    void requirementsMetTest() {
        SelectionGraphOption<Float> option = new SelectionGraphOption<>("Option 1");

        Float choice1 = 0.01f;
        Float choice2 = 0.02f;
        Float choice3 = 0.03f;
        option.setItems(choice1, choice2, choice3);

        assertTrue(option.requirementsMet());
        option.setSelectedItem(choice1);
        assertTrue(option.requirementsMet());

        option.setRequired(true);
        option.setSelectedItem(null);
        assertFalse(option.requirementsMet());
        option.setSelectedItem(choice1);
        assertTrue(option.requirementsMet());
    }


}