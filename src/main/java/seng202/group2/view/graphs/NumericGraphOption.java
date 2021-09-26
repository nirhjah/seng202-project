package seng202.group2.view.graphs;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;

/**
 * A GraphOption used for getting a number from the user.
 */
public class NumericGraphOption extends GraphOption {

    private VBox root = new VBox();
    private Label nameLabel = new Label(NAME);
    private Spinner<Integer> numberSpinner = new Spinner<>(0,0,0,1);

    NumericGraphOption(String name) {
        super(name);

        root.getChildren().addAll(nameLabel, numberSpinner);
    }

    NumericGraphOption(String name, boolean isRequired) {
        super(name, isRequired);

        root.getChildren().addAll(nameLabel, numberSpinner);
    }

    /**
     * Sets the upper and lower boundary numbers for the numeric input.
     * @param min The upper boundary value of numeric input.
     * @param max The lower boundary value of numeric input.
     */
    public void setBounds(Integer min, Integer max) {
        SpinnerValueFactory.IntegerSpinnerValueFactory spinnerFactory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) numberSpinner.getValueFactory();

        spinnerFactory.setMin(min);
        spinnerFactory.setMax(max);
    }

    /**
     * Sets the value in the numeric input.
     * @param value A value to be set in the numeric input.
     */
    public void setValue(Integer value) {
        SpinnerValueFactory.IntegerSpinnerValueFactory spinnerFactory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) numberSpinner.getValueFactory();
        spinnerFactory.setValue(value);
    }

    /**
     * Returns the value of the numeric input.
     * @return The value of the numeric input.
     */
    public Integer getValue() {
        return numberSpinner.getValue();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public boolean requirementsMet() {
        return true;
    }

    @Override
    public String getValidityMessage() {
        return "Configuration valid";
    }
}
