package seng202.group2.view.graphs;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * A GraphOption used for displaying a list of choices to the user, out of which the user chooses one.
 *
 * @param <T> The type of the objects between which the user may select.
 * @author Connor Dunlop
 */
public class SelectionGraphOption<T> extends GraphOption {

    private VBox root = new VBox();
    private Label nameLabel = new Label(NAME);
    private ComboBox<T> optionSelector = new ComboBox<>();

    SelectionGraphOption(String name) {
        super(name);

        root.getChildren().addAll(nameLabel, optionSelector);
        setItemFactory((item) -> item.toString());
    }

    SelectionGraphOption(String name, boolean isRequired) {
        super(name, isRequired);

        root.getChildren().addAll(nameLabel, optionSelector);
        setItemFactory((item) -> item.toString());
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
    public void addChangeListener(BooleanGraphOption.StateChangeListener onChangeFunction) {
        optionSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onChangeFunction.onChange();
        });
    }

    /**
     * Sets the choices from which the user may choose from.
     * @param items The objects between which the user may choose.
     */
    public void setItems(T... items) {
        clearItems();
        addItems(items);
    }

    /**
     * Sets the choices from which the user may choose from.
     * @param items The objects between which the user may choose.
     */
    public void setItems(Collection<T> items) {
        clearItems();
        addItems(items);
    }

    /**
     * Adds a collection of choices from which the user may choose from.
     * @param items The objects between which the user may choose.
     */
    public void addItems(T... items) {
        optionSelector.getItems().addAll(items);
    }

    /**
     * Adds a collection of choices from which the user may choose from.
     * @param items The objects between which the user may choose.
     */
    public void addItems(Collection<T> items) {
        optionSelector.getItems().addAll(items);
    }

    /**
     * Clears all choices presented to the user.
     */
    public void clearItems() {
        optionSelector.getItems().clear();
    }

    /**
     * Gets the choice which has been chosen by the user.
     * @return The object selected by the user.
     */
    public T getSelectedItem() {
        return optionSelector.getSelectionModel().getSelectedItem();
    }

    /**
     * Sets a function used to generate a string to represent each choice.
     * @param factoryFunction A function which takes the choice object as an argument, and produces a string used to represent that choice.
     */
    public void setItemFactory(Function<T, String> factoryFunction) {
        optionSelector.setCellFactory(new Callback<ListView<T>, ListCell<T>>() {
            @Override
            public ListCell<T> call(ListView<T> param) {
                return new ComboBoxListCell<>() {
                    @Override
                    public void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);
                        try {
                            if (item != null)
                                setText(factoryFunction.apply(item));
                            else
                                setText("None");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
            }
        });
    }

    @Override
    public Node getRoot() {
        return root;
    }

    /**
     * A SelectionGraphOption is in a valid state for plotting if either it is not required to be in a valid state for
     * plotting, or if an item has been selected.
     * @return True if in a valid state for plotting, false if not.
     */
    @Override
    public boolean requirementsMet() {
        if (isRequired)
            return optionSelector.getSelectionModel().getSelectedItem() != null;
        else
            return true;
    }

    @Override
    public String getValidityMessage() {
        if (requirementsMet())
            return "Configuration valid";
        else
            return "Must select an option";
    }
}
