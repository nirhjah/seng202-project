package seng202.group2.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import seng202.group2.model.DBMS;
import seng202.group2.model.Filter;
import seng202.group2.model.FilterType;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FilterController implements Initializable {

    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> comparatorsComboBox;
    @FXML private TextField filterValueTextField;
    @FXML private ListView filterListView;

    private HashMap<String, String> categories;
    private HashMap<String, FilterType> comparators;
    private HashMap<String, Filter> listedFilters = new HashMap<>();

    /**
     * Ideally this method lives somewhere else, perhaps in the DataCategories class or something similar.
     */
    private void buildCategories()
    {
        categories = new HashMap<>();
        categories.put("Case Number", "caseNum");
        categories.put("Date", "date");
        categories.put("Block", "block");
        categories.put("IUCR", "IUCR");
        categories.put("Primary Description", "primaryDescription");
        categories.put("Secondary Description", "secondaryDescription");
        categories.put("Location Description", "locationDescription");
        categories.put("Arrest", "arrest");
        categories.put("Domestic", "domestic");
        categories.put("Beat", "beat");
        categories.put("Ward", "ward");
        categories.put("Fbi Code", "fbiCode");
        categories.put("Latitude", "latitude");
        categories.put("Longitude", "longitude");
    }

    /**
     * I believe this list is accessible by the enum, but they would need a 'visual' method
     * this method would need to get the ">" string from the FilterType.EQ type etc.
     */
    private void buildComparators()
    {
        comparators = new HashMap<String, FilterType>();
        comparators.put(">", FilterType.GT);
        comparators.put("=", FilterType.EQ);
    }

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
        listedFilters.put(filterString, newFilter);
        filterListView.getItems().add(filterString);
    }

    public void removeSelectedFilter()
    {
        String toRemove = (String) filterListView.getSelectionModel().getSelectedItem();
        DBMS.getActiveData().removeFilter(listedFilters.get(toRemove));
        listedFilters.remove(toRemove); // This is only required to access the filter object and remove it.
        filterListView.getItems().remove(toRemove);
    }

    /**
     * TODO This should be changed to a method in Active data.
     */
    public void removeAllFilters()
    {
        for (Filter filter: DBMS.getActiveData().getFilters())
        {
            DBMS.getActiveData().removeFilter(filter);
        }
        filterListView.getItems().clear();
        filterListView.getItems().setAll(listedFilters.keySet());
        listedFilters.clear();
    }

    /**
     * Initialize method which automatically runs on stage startup - populates combobox with values
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildCategories();
        buildComparators();
        categoryComboBox.getItems().setAll(getCategories().keySet());
        comparatorsComboBox.getItems().setAll(getComparators().keySet());
        categoryComboBox.getSelectionModel().select(0);
        comparatorsComboBox.getSelectionModel().select(0);
    }

    /**
     * Ideally this method would live in another class to have a more universal access.
     * @return
     */
    private HashMap<String, String> getCategories() {
        return categories;
    }

    /**
     * Ideally this method would live in another class to have a more universal access.
     */
    private HashMap<String, FilterType> getComparators() {
        return comparators;
    }
}
