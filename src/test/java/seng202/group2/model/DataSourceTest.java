package seng202.group2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group2.controller.ObserverTestClass;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test that the observer class is correctly updating and connected to ActiveData
 */
public class DataSourceTest {
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

        observer = new ObserverTestClass();
        DBMS.getActiveData().addObserver(observer);
        DBMS.getActiveData().clearFilters(false);
    }

    /**
     * Test if observers are correctly updated when a filter is added
     */
    @Test
    void addFilterUpdateTest() {
        //Add Records
        try {
            addRecords(10);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        Filter filter = new Filter("id = 5", FilterType.EQ);

        //Check if adding updates
        DBMS.getActiveData().addFilter(filter);
        ArrayList<CrimeRecord> records = observer.getTestRecords();
        assertEquals(5, records.get(0).getID());
    }

    /**
     * Test if observers are correctly updated when a filter is removed
     * Relies on addFilter to work
     */
    @Test
    void removeFilterUpdateTest() {
        //Add Records
        try {
            addRecords(10);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        Filter filter = new Filter("id = 5", FilterType.EQ);

        //Add filter
        DBMS.getActiveData().addFilter(filter);
        ArrayList<CrimeRecord> records = observer.getTestRecords();
        assertEquals(5, records.get(0).getID());

        //Check if removing updates
        DBMS.getActiveData().removeFilter(filter);
        records = observer.getTestRecords();
        assertEquals(1, records.get(0).getID());
    }
}
