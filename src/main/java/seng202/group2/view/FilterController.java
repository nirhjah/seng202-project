package seng202.group2.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import seng202.group2.model.ActiveData;
import seng202.group2.model.DBMS;
import seng202.group2.model.Filter;
import seng202.group2.model.datacategories.DataCategory;
import seng202.group2.view.filterControllers.OptionsController;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FilterController is the controller class for the crime filters GUI.
 *
 * This class uses the 'filter.fxml' FXML file for laying it out.
 * The GUI for this class allows a user-friendly way to get an SQL query to filter the crime data in
 * ActiveData. This class allows the user to add filters, remove a specific pre-applied filter and
 * remove all pre-applied filters at once.
 *
 * @author Sam Clark, Moses Wescombe
 */
public class FilterController implements Initializable {
    //Filter options
    @FXML private GridPane filterOptions;
    @FXML private OptionsController optionsController;
    @FXML private CheckBox sortCheckBox;

    /** The JavaFX ComboBox that allows the user to select the category for filtering*/
    @FXML private ComboBox<DataCategory> categoryComboBox;

    /**
     * The JavaFX ListView which displays to the user the currently active filters.
     * This ListView is initialised with a CellFactory to show the SQLText attribute of the filters.
     * The items of this list are the {@link FilterController#listedFilters} Observable list.
     */
    @FXML private ListView<Filter> filterListView;

    /** A JavaFX ObservableList that stores the filters that are to be displayed in the {@link FilterController#filterListView}*/
    private static ObservableList<Filter> listedFilters = FXCollections.observableArrayList();

    /**
     * Adds a filter to the filters list in the ActiveData, and displays it in the filters ListView
     *
     * This method takes the user input from the {@link FilterController#categoryComboBox},  {@link FilterController#}
     * and {@link FilterController#} and produces a SQL filter. That filter is then added to the ActiveData's
     * filters; to filter the ActiveData, and the listedFilters; to display in the Filters UI window.
     * 
     * @see ActiveData#addFilter(Filter) Filter(seng202.group2.model.Filter)
     */
    @FXML
    private void addFilterFromInputs()
    {
        try {
            List<Filter> newFilters = optionsController.generateFilter(categoryComboBox.getSelectionModel().getSelectedItem());

            newFilters.forEach((filter -> DBMS.getActiveData().addFilter(filter)));
            listedFilters.addAll(newFilters);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Removes the selected filter from the ActiveData's filter Arraylist and listView showing the current filters.
     * This method is called when the 'remove' button is clicked on the JavaFX UI.
     * If no filter is selected, nothing happens.
     * 
     * @see ActiveData#removeFilter(seng202.group2.model.Filter)
     */
    public void removeSelectedFilter()
    {
        Filter toRemove = filterListView.getSelectionModel().getSelectedItem();
        DBMS.getActiveData().removeFilter(toRemove);
        listedFilters.remove(toRemove);
    }

    /**
     * Removes all filter from the ActiveData's filter Arraylist and listView.
     * This method is called when the 'remove all' button is clicked on the JavaFX UI.
     *
     * @see ActiveData#clearFilters(boolean) Filter(seng202.group2.model.Filter)
     */
    public void removeAllFilters()
    {
        DBMS.getActiveData().clearFilters(true);
        listedFilters.clear();
    }

    public void changeOptions() {
        DataCategory category = categoryComboBox.getSelectionModel().getSelectedItem();
        String resource = "./FilterOptions/";

        if (sortCheckBox.isSelected()) {
            resource += "sortOptions.fxml";
        } else if (category.getValueType() == Calendar.class) {
            resource += "dateOptions.fxml";
        } else if (category.getValueType() == Boolean.class) {
            resource += "boolOptions.fxml";
        } else if (category.getValueType() == String.class) {
            resource += "stringOptions.fxml";
        } else {
            resource += "numberOptions.fxml";
        }


        try {
            FXMLLoader loader = new FXMLLoader(CamsApplication.class.getClassLoader().getResource(resource));
            Parent root = loader.load();

            optionsController = loader.getController();
            optionsController.initialize();
            filterOptions.getChildren().clear();
            filterOptions.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize method to prepare the UI values each time a filter window is opened.
     *
     * This method prepares the Filter Window in the UI. It does the following preparations:
     *  - Sets the cell value of categories in the {@link FilterController#categoryComboBox} to the toString() representation.
     *  - Sets the cell value of categories in the {@link FilterController#categoryComboBox} to the toString() representation.
     *  - Sets the cell value of the filters ListView to the Filter's SQLText Attribute.
     *  - Sets the items of the used ComboBoxes and the ListView, to the relevant datasets.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        // Add all categories to category combo box and select the first
        categoryComboBox.getItems().setAll(DataCategory.getCategories());
        categoryComboBox.getSelectionModel().select(0);


        // Makes the cells in the listView show their filter.SQLText string.
        filterListView.setCellFactory(new Callback<ListView<Filter>, ListCell<Filter>>() {
            @Override
            public ListCell<Filter> call(ListView<Filter> param) {
                return new ListCell<Filter>() {
                    @Override
                    protected void updateItem(Filter item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item == null ? "" : item.getStringRepresentation());
                    }
                };
            }
        });
        // Add all currently applied filters to applied filter list
        filterListView.setItems(listedFilters);

        changeOptions();
    }
}
