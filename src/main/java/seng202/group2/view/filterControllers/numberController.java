package seng202.group2.view.filterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import seng202.group2.model.Filter;
import seng202.group2.model.FilterType;
import seng202.group2.model.datacategories.DataCategory;

import java.util.ArrayList;
import java.util.List;

public class numberController extends OptionsController {
    @FXML
    private TextField value;

    @FXML
    private ComboBox<FilterType> comparators;

    @Override
    public List<Filter> generateFilter(DataCategory category) {
        List<Filter> results = new ArrayList<>();

        FilterType type = comparators.getSelectionModel().getSelectedItem();
        String filterValue = value.getText();

        results.add(type.createFilter(category, filterValue));
        return results;
    }

    @Override
    public void initialize() {
        // Set Combobox for comparators to show the toString() representation.
        comparators.setCellFactory(new Callback<ListView<FilterType>, ListCell<FilterType>>() {
            @Override
            public ListCell<FilterType> call(ListView<FilterType> param) {
                return new ListCell<FilterType>() {
                    @Override
                    protected void updateItem(FilterType item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item == null ? "Error: null" : item.toString());
                    }
                };
            }
        });

        // Add all filter types to comparator combo box and select the first
        comparators.getItems().setAll(FilterType.values());
        comparators.getItems().remove(FilterType.SORT);
        comparators.getSelectionModel().select(0);
    }
}
