package seng202.group2.controller;

/**
 * Provides updateModel method for child classes.
 */
public abstract class DataObserver {
    /**
     * Update the views data model. This may be a list if records presented in a table, map or graph.
     * You should not need to manually call this function, aside from testing.
     */
    public abstract void activeDataUpdate();

    /**
     * Update the views selected data model
     */
    public abstract void selectedRecordsUpdate();

    /**
     * Update the view frame
     *
     * @param min Minimum index to display
     * @param max  Maximum index to display
     * @param size Frame size
     * @param total Total size of activeRecords database
     */
    public abstract void frameUpdate(int min, int max, int size, int total);
}
