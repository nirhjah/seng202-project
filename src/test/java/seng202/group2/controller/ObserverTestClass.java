package seng202.group2.controller;
import seng202.group2.model.CrimeRecord;
import java.util.ArrayList;

/**
 * Assisting class for Observer test classes
 */
public class ObserverTestClass extends DataObserver{
    /** Records are stored here so that test methods can access at any time */
    private ArrayList<CrimeRecord> testRecords;

    /**
     * Get the records
     * @param records
     */
    @Override
    public void updateModel(ArrayList<CrimeRecord> records) {
        testRecords = records;
    }

    /**
     * Get testRecords
     */
    public ArrayList<CrimeRecord> getTestRecords() {
        return  testRecords;
    }
}