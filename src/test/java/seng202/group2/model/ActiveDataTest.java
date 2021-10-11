package seng202.group2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group2.model.datacategories.Beat;
import seng202.group2.model.datacategories.ID;
import seng202.group2.model.datacategories.Ward;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActiveDataTest {
    //Date format for creating calendar
    private static final String DATE_FORMAT = "yyyy/MM/dd hh:mm:ss a";
    private static final ActiveData activeData = DBMS.getActiveData();

    /**
     * Add a given number of crime records to the database, each crime records data is labeled by its ID, starting at 1
     *
     * @param max Number of records to add
     */
    void addRecords(int max) throws ParseException {
        for (int i=1; i <= max; i++) {
            String num = Integer.toString(i);
            CrimeRecord record = new CrimeRecord();
            record.setCaseNum("TEST" + num);

            //Convert String from db to Calendar
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse("2001/12/12 03:40:12 PM"));
            record.setDate(cal);

            record.setBlock("Block" + num);
            record.setIucr("110");
            record.setPrimaryDescription("pDesc" + num);
            record.setSecondaryDescription("pDesc" + num);
            record.setLocationDescription("lDesc" + num);
            record.setArrest(false);
            record.setDomestic(false);
            record.setWard((short) Integer.parseInt(num));
            record.setBeat((short) Integer.parseInt(num));
            record.setFbiCode("fbiCode" + num);
            record.setLongitude((float) Integer.parseInt(num));
            record.setLatitude((float) Integer.parseInt(num));
            DBMS.addRecord(record, false);
        }

        DBMS.getActiveData().updateActiveRecords();
    }

    @BeforeEach
    void clearFilters() {
        DBMS.clearDB();
        activeData.clearFilters(false);
        activeData.updateFrameSize(1000);
        activeData.setSearchPattern("");
    }

    @Test
    void clearFilterTest() {
        //Create filters
        Filter filter = FilterType.GT.createFilter( new Ward(), "1");
        Filter filter2 = FilterType.EQ.createFilter(new Ward(), "5");
        Filter filter3 = FilterType.LT.createFilter(new Beat(), "10");
        Filter filter4 = FilterType.SORT.createFilter(new Beat(), "ASC");

        //Add filters
        activeData.addFilter(filter, false);
        activeData.addFilter(filter2, false);
        activeData.addFilter(filter3, false);
        activeData.addFilter(filter4, false);

        //Clear
        activeData.clearFilters(false);

        //Check
        assertEquals(0, activeData.getFilters().size());
    }

    @Test
    void addFilterTest() {
        //Create filters
        Filter filter = FilterType.GT.createFilter( new Ward(), "1");
        Filter filter2 = FilterType.EQ.createFilter(new Ward(), "5");
        Filter filter3 = FilterType.LT.createFilter(new Beat(), "10");
        Filter filter4 = FilterType.SORT.createFilter(new Beat(), "ASC");

        //Add filters
        activeData.addFilter(filter, false);
        activeData.addFilter(filter2, false);
        activeData.addFilter(filter3, false);
        activeData.addFilter(filter4, false);

        //Check
        assertEquals(filter, activeData.getFilters().get(0));
        assertEquals(filter2, activeData.getFilters().get(1));
        assertEquals(filter3, activeData.getFilters().get(2));
        assertEquals(filter4, activeData.getFilters().get(3));
    }

    @Test
    void removeFilterTest() {
        //Create filters
        Filter filter = FilterType.GT.createFilter( new Ward(), "1");
        Filter filter2 = FilterType.EQ.createFilter(new Ward(), "5");
        Filter filter3 = FilterType.LT.createFilter(new Beat(), "10");
        Filter filter4 = FilterType.SORT.createFilter(new Beat(), "ASC");

        //Add filters
        activeData.addFilter(filter, false);
        activeData.addFilter(filter2, false);
        activeData.addFilter(filter3, false);
        activeData.addFilter(filter4, false);

        //remove filter
        activeData.removeFilter(filter, false);

        //Check
        assertEquals(filter2, activeData.getFilters().get(0));
        assertEquals(filter3, activeData.getFilters().get(1));
        assertEquals(filter4, activeData.getFilters().get(2));
    }

    @Test
    void getActiveRecordsTest() {
        //Add records
        DBMS.clearDB();
        activeData.setSearchPattern("");
        try {
            addRecords(10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Filter filter = FilterType.GT.createFilter(new ID(), "0");
        activeData.addFilter(filter, false);

        activeData.updateActiveRecords();
        assertEquals(DBMS.getRecordsSize(), DBMS.getActiveRecordsSize());

    }

    @Test
    void getActiveRecordsWithFilterTest() {
        //Add records
        DBMS.clearDB();
        try {
            addRecords(10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Make sure search pattern is blank
        activeData.setSearchPattern("");

        // Add a sort filter which has no effect on database Size but makes the filter length  more than zero.
        activeData.addFilter(FilterType.SORT.createFilter(new Beat(), "ASC"));
        activeData.updateActiveRecords();
        assertEquals(DBMS.getRecordsSize(), DBMS.getActiveRecordsSize());
    }
    
    @Test
    void getActiveRecordsWithSearchTest() {
    	//Add records
    	DBMS.clearDB();
        try {
            addRecords(10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        //Set search pattern
        activeData.setSearchPattern("pDesc2");
        activeData.updateActiveRecords();

        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();
        assertEquals(1, records.size());
        
        //Reset search pattern to blank
        activeData.setSearchPattern("");
        activeData.updateActiveRecords();
        
        assertEquals(DBMS.getRecordsSize(), DBMS.getActiveRecordsSize());
    }

    @Test
    void getActiveRecordsRangeTest() {
        //Add records
        try {
            addRecords(10);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        activeData.updateActiveRecords();
        ArrayList<CrimeRecord> records = activeData.getActiveRecords(0, 5);
        assertEquals(5, records.size());
    }

    @Test
    void getFiltersTest() {
        //Create filters
        Filter filter = FilterType.GT.createFilter( new Ward(), "1");
        Filter filter2 = FilterType.EQ.createFilter(new Ward(), "5");
        Filter filter3 = FilterType.LT.createFilter(new Beat(), "10");
        Filter filter4 = FilterType.SORT.createFilter(new Beat(), "ASC");

        //Add filters
        activeData.addFilter(filter, false);
        activeData.addFilter(filter2, false);
        activeData.addFilter(filter3, false);
        activeData.addFilter(filter4, false);

        assertEquals(4, activeData.getFilters().size());
    }

    @Test
    void selectRecordTest() {
        try {
            addRecords(30);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        activeData.selectRecord(5);
        assertTrue(activeData.getSelectedRecords().contains(5));

        activeData.selectRecord(1);
        assertTrue(activeData.getSelectedRecords().contains(1));

        activeData.selectRecord(30);
        assertTrue(activeData.getSelectedRecords().contains(30));
    }

    @Test
    void deselectRecordTest() {
        try {
            addRecords(30);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        activeData.selectRecord(5);
        activeData.selectRecord(1);
        activeData.selectRecord(30);

        activeData.deselectRecord(5);
        assertTrue(!activeData.getSelectedRecords().contains(5));
    }

    @Test
    void toggleSelectRecordTest() {
        try {
            addRecords(30);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        activeData.selectRecord(5);
        activeData.selectRecord(1);
        activeData.selectRecord(30);

        activeData.toggleSelectRecord(5);
        assertTrue(!activeData.getSelectedRecords().contains(5));

        activeData.toggleSelectRecord(5);
        assertTrue(activeData.getSelectedRecords().contains(5));
    }

    @Test
    void clearSelectionTest() {
        try {
            addRecords(30);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        activeData.selectRecord(5);
        activeData.selectRecord(1);
        activeData.selectRecord(30);

        activeData.clearSelection();

        assertTrue(!activeData.getSelectedRecords().contains(5));
        assertTrue(!activeData.getSelectedRecords().contains(1));
        assertTrue(!activeData.getSelectedRecords().contains(30));
    }

    @Test
    void isSelectedTest() {
        try {
            addRecords(30);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        activeData.selectRecord(5);
        activeData.selectRecord(1);
        activeData.selectRecord(30);

        assertTrue(activeData.isSelected(5));
        assertTrue(!activeData.isSelected(6));
    }

    @Test
    void updateFrameSizeTest() {
        activeData.updateFrameSize(20);

        assertEquals(20, activeData.getFrameSize());
    }

    @Test
    void incrementFrameNoRecordsTest() {
        activeData.incrementFrame();

        assertEquals(0, activeData.getCurrentMin());
        assertEquals(0, activeData.getCurrentMax());
    }

    @Test
    void incrementFrameTest() {
        try {
            addRecords(50);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        activeData.updateFrameSize(10);
        activeData.incrementFrame();

        assertEquals(10, activeData.getCurrentMin());
        assertEquals(20, activeData.getCurrentMax());
    }

    @Test
    void decrementFrameNoRecordsTest() {
        activeData.decrementFrame();

        assertEquals(0, activeData.getCurrentMin());
        assertEquals(0, activeData.getCurrentMax());
    }

    @Test
    void decrementFrameTest() {
        try {
            addRecords(50);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        activeData.updateFrameSize(10);
        activeData.incrementFrame();
        activeData.incrementFrame();
        activeData.decrementFrame();


        assertEquals(10, activeData.getCurrentMin());
        assertEquals(20, activeData.getCurrentMax());
    }
}
