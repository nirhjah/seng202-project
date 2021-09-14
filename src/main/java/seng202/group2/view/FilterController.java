package seng202.group2.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import seng202.group2.model.DBMS;
import seng202.group2.model.Filter;
import seng202.group2.model.FilterType;
import seng202.group2.model.datacategories.DataCategory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FilterController implements Initializable {

    /** The JavaFX ComboBox that allows the user to select the category for filtering*/
    @FXML private ComboBox<DataCategory> categoryComboBox;
    @FXML private ComboBox<String> comparatorsComboBox;
    @FXML private TextField filterValueTextField;
    @FXML private ListView<Filter> filterListView;

    private HashMap<String, FilterType> comparators;
    private static ObservableList<Filter> listedFilters = FXCollections.observableArrayList();

    /**
     * Adds a filter to the filters list in the ActiveData, this method should also show the filter in the listview
     * of filters.
     */
    @FXML
    private void addFilterFromInputs()
    {
        FilterType type = comparators.get(comparatorsComboBox.getSelectionModel().getSelectedItem());
        DataCategory category = categoryComboBox.getSelectionModel().getSelectedItem();
        String filterValue = filterValueTextField.getText();
        Filter newFilter = type.createFilter(category.getSQL(), filterValue);
        DBMS.getActiveData().addFilter(newFilter);
        listedFilters.add(newFilter);
    }

    public void removeSelectedFilter()
    {
        Filter toRemove = filterListView.getSelectionModel().getSelectedItem();
        DBMS.getActiveData().removeFilter(toRemove);
        listedFilters.remove(toRemove);
    }

    public void removeAllFilters()
    {
        DBMS.getActiveData().clearFilters(true);
        listedFilters.clear();
    }

    /**
     * Initialize method which automatically runs on stage startup - populates combobox with values
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comparators = new HashMap<String, FilterType>();
        comparators.put(">", FilterType.GT);
        comparators.put("=", FilterType.EQ);

        // Set Combobox for categories to show the toString() representation.
        categoryComboBox.setCellFactory(new Callback<ListView<DataCategory>, ListCell<DataCategory>>() {
            @Override
            public ListCell<DataCategory> call(ListView<DataCategory> param) {
                return new ListCell<DataCategory>() {
                    @Override
                    protected void updateItem(DataCategory item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item == null ? "Error: null" : item.toString());
                    }
                };
            }
        });
        categoryComboBox.getItems().setAll(DataCategory.getCategories());
        comparatorsComboBox.getItems().setAll(comparators.keySet());
        categoryComboBox.getSelectionModel().select(0);
        comparatorsComboBox.getSelectionModel().select(0);

        // Makes the cells in the listView show their filter.SQLText string.
        filterListView.setCellFactory(new Callback<ListView<Filter>, ListCell<Filter>>() {
            @Override
            public ListCell<Filter> call(ListView<Filter> param) {
                return new ListCell<Filter>() {
                    @Override
                    protected void updateItem(Filter item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item == null ? "" : item.getSQLText());
                    }
                };
            }
        });
        filterListView.setItems(listedFilters);
    }
}
