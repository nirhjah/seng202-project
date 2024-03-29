package seng202.group2.model;

import seng202.group2.controller.DataObserver;
import java.util.ArrayList;

/**
 * DataSource abstract class. Provides updates to observers when data is updated in ActiveData
 */
public abstract class DataSource {
    /** List of current data observers */
    private ArrayList<DataObserver> observers = new ArrayList<>();

    /**
     * Add an observer to the list
     *
     * @param observer -- Observer object to add
     */
    public void addObserver(DataObserver observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    /**
     * Remove observers from the list
     *
     * @param observer -- Observer object to remove
     */
    public void removeObserver(DataObserver observer) {
        observers.remove(observer);
    }

    /**
     * Update all observers that the data has changed
     */
    public void updateActiveData() {
        ActiveData activeData = DBMS.getActiveData();

        //Update active data
        activeData.updateActiveRecords();

        //Update all observers
        for (DataObserver observer: observers) {
            observer.activeDataUpdate();
        }
    }

    /**
     * Update all observers that the selection data has changed
     */
    public void updateSelectionObservers() {
        //Update all observers
        for (DataObserver observer: observers) {
            observer.selectedRecordsUpdate();
        }
    }

    /**
     * Update active data frame variables and update all observers of the change
     */
    public void updateFrame() {

        //Update all observers
        for (DataObserver observer: observers) {
            observer.frameUpdate();
        }
    }
}
