package seng202.group2.controller;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

import java.util.ArrayList;

/**
 * Assisting class for Observer test classes
 */
public class ObserverTestClass extends DataObserver {
    /** Records are stored here so that test methods can access at any time */
    private ArrayList<CrimeRecord> testRecords;

    /**
     * Get the records
     */
    @Override
    public void activeDataUpdate() {
        testRecords = DBMS.getActiveData().getActiveRecords();
    }

    /**
     * Get testRecords
     */
    public ArrayList<CrimeRecord> getTestRecords() {
        return  testRecords;
    }
}
