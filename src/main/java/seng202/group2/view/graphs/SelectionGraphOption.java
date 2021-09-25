package seng202.group2.view.graphs;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

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
     * Adds a collection of choices from which the user may choose from.
     * @param items The objects between which the user may choose.
     */
    public void addItems(T... items) {
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
                            setText(factoryFunction.apply(item));
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
}
