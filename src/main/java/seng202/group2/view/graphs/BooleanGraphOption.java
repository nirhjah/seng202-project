package seng202.group2.view.graphs;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A graph option which can either be enabled, or disabled.
 *
 * @author Connor Dunlop
 */
public class BooleanGraphOption extends GraphOption {

    private HBox root = new HBox();
    private Label nameLabel = new Label(NAME);
    private CheckBox checkBox = new CheckBox();

    BooleanGraphOption(String name) {
        super(name);

        root.getChildren().addAll(nameLabel, checkBox);
    }

    /**
     * Checks whether the option is enabled or not.
     * @return True if the option is enabled, false otherwise.
     */
    public Boolean getState() {
        return checkBox.isSelected();
    }

    @Override
    public Node getRoot() {
        return root;
    }
}
