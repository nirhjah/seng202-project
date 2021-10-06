package seng202.group2.view.filterControllers;

import seng202.group2.model.Filter;
import seng202.group2.model.datacategories.DataCategory;

import java.util.List;

/**
 * Parent class for filter option controllers
 */
public abstract class OptionsController {
    /**
     * Generate a filter for given category. Using given values from fxml file.
     * @param category - Category to create filter
     * @return List of filters to add
     */
    abstract public List<Filter> generateFilter(DataCategory category);

    /**
     * Initialize the required data in dropdowns and other inputs
     */
    abstract public void initialize();
}
