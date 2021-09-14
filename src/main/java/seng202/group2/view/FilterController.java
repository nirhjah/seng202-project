package seng202.group2.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> comparatorsComboBox;
    @FXML private TextField filterValueTextField;
    @FXML private ListView filterListView;

    private HashMap<String, String> categories = new HashMap<>();;
    private HashMap<String, FilterType> comparators;
    private static HashMap<String, Filter> filterDict = new HashMap<>();
    private static ObservableList<String> listedFilters = FXCollections.observableArrayList();

    /**
     * Adds a filter to the filters list in the ActiveData, this method should also show the filter in the listview
     * of filters.
     */
    @FXML
    private void addFilterFromInputs()
    {
        String filterString = categoryComboBox.getSelectionModel().getSelectedItem() + " " +
                comparatorsComboBox.getSelectionModel().getSelectedItem() + " " +
                filterValueTextField.getText();
        Filter newFilter = comparators.get(comparatorsComboBox.getSelectionModel().getSelectedItem()).createFilter(
                categories.get(categoryComboBox.getSelectionModel().getSelectedItem()),
                filterValueTextField.getText());
        DBMS.getActiveData().addFilter(newFilter);
        System.out.println(filterString);
        filterDict.put(filterString, newFilter);
        listedFilters.add(filterString);
    }

    public void removeSelectedFilter()
    {
        String toRemove = (String) filterListView.getSelectionModel().getSelectedItem();
        DBMS.getActiveData().removeFilter(filterDict.get(toRemove));
        filterDict.remove(toRemove); // This is only required to access the filter object and remove it.
        listedFilters.remove(toRemove);
    }


    public void removeAllFilters()
    {
        ArrayList<Filter> filters = DBMS.getActiveData().getFilters();
        DBMS.getActiveData().clearFilters(true);
        filterDict.clear();
        listedFilters.clear();
    }

    /**
     * Initialize method which automatically runs on stage startup - populates combobox with values
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (DataCategory category: DataCategory.getCategories())
        {
            categories.put(category.toString(), category.getSQL());
        }

        comparators = new HashMap<String, FilterType>();
        comparators.put(">", FilterType.GT);
        comparators.put("=", FilterType.EQ);


        categoryComboBox.getItems().setAll(categories.keySet());
        comparatorsComboBox.getItems().setAll(comparators.keySet());
        categoryComboBox.getSelectionModel().select(0);
        comparatorsComboBox.getSelectionModel().select(0);
        filterListView.setItems(listedFilters);
    }
}
