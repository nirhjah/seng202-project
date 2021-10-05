package seng202.group2.view.filterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import seng202.group2.model.Filter;
import seng202.group2.model.FilterType;
import seng202.group2.model.datacategories.DataCategory;

import java.util.ArrayList;
import java.util.List;

public class stringController extends OptionsController {
    @FXML private TextField value;

    @Override
    public List<Filter> generateFilter(DataCategory category) {
        List<Filter> results = new ArrayList<>();

        FilterType type = FilterType.EQ;
        String filterValue = value.getText();

        results.add(type.createFilter(category, filterValue));
        return results;
    }

    @Override
    public void initialize() {

    }
}
