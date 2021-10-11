package seng202.group2.view.filterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import seng202.group2.model.Filter;
import seng202.group2.model.FilterType;
import seng202.group2.model.datacategories.DataCategory;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls the filter inputs when sorting a category
 * @author Moses Wescombe
 */
public class sortController extends OptionsController{
    //Inputs
    @FXML
    private ToggleButton ascDescButton;

    public void toggleButton() {
        if (ascDescButton.isSelected()) {
            ascDescButton.setText("Descending");
        } else {
            ascDescButton.setText("Ascending");
        }
    }

    @Override
    public List<Filter> generateFilter(DataCategory category) {
        List<Filter> results = new ArrayList<>();

        String filterValue = ascDescButton.isSelected() ? "DESC" : "ASC";

        results.add(FilterType.SORT.createFilter(category, filterValue));
        return results;
    }

    @Override
    public void initialize() {
        toggleButton();
    }
}
