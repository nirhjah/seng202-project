package seng202.group2.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group2.model.ActiveData;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test that child classes of DataObserver are correctly notified of updates
 */
public class DataObserverTest {
    private ObserverTestClass observer;

    /**
     * Add records to the database to test other methods.
     *
     * @param max
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ParseException
     */
    void addRecords(int max) throws SQLException, ClassNotFoundException, ParseException {
        for (int i=1; i <= max; i++) {
            CrimeRecord record = new CrimeRecord();
            record.setCaseNum("TEST" + i);

            //Convert String from db to Calendar
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse("2001/12/12 03:40:12 PM"));
            record.setDate(cal);

            record.setBlock("Block" + i);
            record.setIucr("430");
            record.setPrimaryDescription("pDesc" + i);
            record.setSecondaryDescription("sDesc" + i);
            record.setLocationDescription("lDesc" + i);
            record.setArrest(false);
            record.setDomestic(false);
            record.setWard((short) i);
            record.setBeat((short) i);
            record.setFbiCode("fbiCode" + i);
            record.setLongitude((float) i);
            record.setLatitude((float) i);
            DBMS.addRecord(record,false);
        }
    }

    /**
     * Add observers
     */
    @BeforeEach
    void addObserver() {
        DBMS.clearDB();
        DBMS.getActiveData().clearFilters(false);
        DBMS.getActiveData().clearSelection();

        observer = new ObserverTestClass();
        DBMS.getActiveData().addObserver(observer);
    }

    /**
     * Test the updateModel and getTestRecords method by forcing an update. This does not test if adding filters will
     * correctly update.
     */
    @Test
    void updateModelTest() {
        try {
            addRecords(7);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        //Update data
        observer.activeDataUpdate();

        ArrayList<CrimeRecord> results = observer.getTestRecords();
        int num = 1;

        for (CrimeRecord result : results) {
            assertEquals(num, result.getID());
            num++;
        }
    }

    /**
     * Test the data selection update
     */
    @Test
    void selectedRecordsUpdateTest() {
        try {
            addRecords(10);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> recordsToSelect = new ArrayList<>();
        recordsToSelect.add(1);
        recordsToSelect.add(6);
        recordsToSelect.add(4);

        //Update data
        observer.selectedRecordsUpdate();

        HashSet<Integer> results = observer.getSelectedRecords();

        for (Integer result : results) {
            assertTrue(recordsToSelect.contains(result));
        }
    }

    @Test
    void frameUpdateTest() {
        try {
            addRecords(50);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        ActiveData activeData = DBMS.getActiveData();
        activeData.updateFrameSize(10);

        assertEquals(observer.min, activeData.getCurrentMin());
        assertEquals(observer.max, activeData.getCurrentMax());
        assertEquals(observer.size, activeData.getFrameSize());
        assertEquals(observer.total, activeData.getRecordCount());
    }
}
