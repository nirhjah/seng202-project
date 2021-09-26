package seng202.group2.controller;
import seng202.group2.model.ActiveData;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Assisting class for Observer test classes
 */
public class ObserverTestClass extends DataObserver {
    /** Records are stored here so that test methods can access at any time */
    private ArrayList<CrimeRecord> testRecords;
    private HashSet<Integer> selectedRecords;
    public int min;
    public int max;
    public int size;
    public int total;

    /**
     * Get the records
     */
    @Override
    public void activeDataUpdate() {
        testRecords = DBMS.getActiveData().getAllActiveRecords();
    }

    @Override
    public void selectedRecordsUpdate() {
        selectedRecords = DBMS.getActiveData().getSelectedRecords();
    }

    @Override
    public void frameUpdate() {
        ActiveData activeData = DBMS.getActiveData();
        this.min = activeData.getCurrentMin();
        this.max = activeData.getCurrentMax();
        this.size = activeData.getFrameSize();
        this.total = activeData.getRecordCount();
    }

    /**
     * Get testRecords
     */
    public ArrayList<CrimeRecord> getTestRecords() {
        return  testRecords;
    }

    /**
     * Get testRecords
     */
    public HashSet<Integer> getSelectedRecords() {
        return  selectedRecords;
    }
}
