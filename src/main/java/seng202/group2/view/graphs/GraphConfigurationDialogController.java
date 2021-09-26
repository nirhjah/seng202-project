package seng202.group2.view.graphs;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Collection;

/**
 * A controller for a dialogue box which is shown when graph options are not in a valid configuration for plotting.
 * The dialogue box shows a list of all options incorrectly set, with a message indicating why they are invalid.
 *
 * @author Connor Dunlop
 */
public class GraphConfigurationDialogController {

    @FXML private Accordion invalidOptionsAccordion;

    /**
     * Adds a collection of GraphOptions to the accordion displaying invalid options
     * @param invalidOptions A collection of the invalid GraphOptions.
     */
    public void addInvalidOptions(GraphOption... invalidOptions) {
        addInvalidOptions(invalidOptions);
    }

    /**
     * Adds a collection of GraphOptions to the accordion displaying invalid options
     * @param invalidOptions A collection of the invalid GraphOptions.
     */
    public void addInvalidOptions(Collection<GraphOption> invalidOptions) {
        for (GraphOption invalidOption : invalidOptions) {
            addInvalidOption(invalidOption);
        }
    }

    /**
     * Adds a single GraphOption to the accordion displaying invalid options.
     * @param invalidOption A GraphOption which is not in a valid state for plotting.
     */
    private void addInvalidOption(GraphOption invalidOption) {
        String optionName = invalidOption.getName();
        String validityMessage = invalidOption.getValidityMessage();

        TextFlow descriptionPane = new TextFlow();
        Text description = new Text(validityMessage);
        descriptionPane.getChildren().add(description);

        TitledPane optionPane = new TitledPane();
        optionPane.setText(optionName);
        optionPane.setContent(descriptionPane);

        invalidOptionsAccordion.getPanes().add(optionPane);
    }

}
