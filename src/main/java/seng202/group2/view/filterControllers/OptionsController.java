package seng202.group2.view.filterControllers;

import seng202.group2.model.Filter;
import seng202.group2.model.datacategories.DataCategory;

import java.util.List;

public abstract class OptionsController {
    abstract public List<Filter> generateFilter(DataCategory category);

    abstract public void initialize();
}
