package seng202.group2.view.graphs;

import javafx.beans.value.ObservableValue;
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

    BooleanGraphOption(String name, boolean isRequired) {
        super(name, isRequired);

        root.getChildren().addAll(nameLabel, checkBox);
    }

    /**
     * Sets the state of the option to enabled if state true, disabled if state false.
     * @param state True to set the option state to enabled, false to set the option state to disabled.
     */
    public void setState(Boolean state) {
        checkBox.selectedProperty().setValue(state);
    }

    /**
     * Checks whether the option is enabled or not.
     * @return True if the option is enabled, false otherwise.
     */
    public Boolean getState() {
        return checkBox.isSelected();
    }

    /** A simple functional interface stub to facilitate use of lambda expressions for method addChangeListener. */
    @FunctionalInterface
    public interface StateChangeListener {
        void onChange();
    }

    /**
     * Adds a function to be called when a change in the state of the option occurs.
     * @param onChangeFunction A lambda function which takes no arguments, and returns no parameters.
     */
    public void addChangeListener(StateChangeListener onChangeFunction) {
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            onChangeFunction.onChange();
        });
    }

    @Override
    public Node getRoot() {
        return root;
    }

    /**
     * A BooleanGraphOption has no invalid state for plotting, so always return true.
     * @return true
     */
    @Override
    public boolean requirementsMet() {
        return true;
    }

    @Override
    public String getValidityMessage() {
        return "Configuration valid";
    }
}