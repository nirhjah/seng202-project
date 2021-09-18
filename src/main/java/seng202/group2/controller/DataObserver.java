package seng202.group2.controller;

import seng202.group2.model.CrimeRecord;
import java.util.ArrayList;

/**
 * Provides updateModel method for child classes.
 *
 * HOW TO USE:
 *      1. Extend your class with DataObserver
 *      2. Implement an updateModel method. This takes in an ArrayList of crime records, but you should never have to supply
 *          this, unless you are manually testing.
 *      3. The implementation should use the ArrayList to update the view or view data. (I.E) Update the table data, map points or graph points
 *
 * WHEN IT IS CALLED:
 *      - When a filter is added or removed, all updateModel methods will be called
 *      - When update is manually called via: DBMS.getActiveData().updateObservers(DBMS.getActiveData().getActiveRecords())
 */
public abstract class DataObserver {
    /**
     * Update the views data model. This may be a list if records presented in a table, map or graph.
     * You should not need to manually call this function, aside from testing.
     *
     */
    public abstract void activeDataUpdate();

    public abstract void selectedRecordsUpdate();
}
