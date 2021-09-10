package seng202.group2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActiveDataTest {
    //Date format for creating calendar
    private static final String DATE_FORMAT = "yyyy/MM/dd hh:mm:ss a";
    private static final ActiveData activeData = DBMS.getActiveData();

    /**
     * Add a given number of crime records to the database, each crime records data is labeled by its ID, starting at 1
     *
     * @param max Number of records to add
     * @throws ParseException
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

        //DBMS.getActiveData().updateActiveRecords();
    }

    @BeforeEach
    void clearFilters() {
        activeData.clearFilters(false);
    }

    @Test
    void clearFilterTest() {
        //Create filters
        Filter filter = FilterType.GT.createFilter("id", "1");
        Filter filter2 = FilterType.EQ.createFilter("id", "5");
        Filter filter3 = FilterType.LT.createFilter("id", "10");
        Filter filter4 = FilterType.SORT.createFilter("id", "ASC");

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
        Filter filter = FilterType.GT.createFilter("id", "1");
        Filter filter2 = FilterType.EQ.createFilter("id", "5");
        Filter filter3 = FilterType.LT.createFilter("id", "10");
        Filter filter4 = FilterType.SORT.createFilter("id", "ASC");

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
        Filter filter = FilterType.GT.createFilter("id", "1");
        Filter filter2 = FilterType.EQ.createFilter("id", "5");
        Filter filter3 = FilterType.LT.createFilter("id", "10");
        Filter filter4 = FilterType.SORT.createFilter("id", "ASC");

        //Add filters
        activeData.addFilter(filter, false);
        activeData.addFilter(filter2, false);
        activeData.addFilter(filter3, false);
        activeData.addFilter(filter4, false);

        System.out.println(activeData.getFilters());
        //remove filters
        System.out.println(filter);
        activeData.removeFilter(filter, false);
        System.out.println(activeData.getFilters());


        //Check
        assertEquals(filter2, activeData.getFilters().get(0));
        assertEquals(filter3, activeData.getFilters().get(1));
        assertEquals(filter4, activeData.getFilters().get(2));
    }

    @Test
    void getActiveRecordsTest() {
        //Add records
        try {
            addRecords(10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        Filter filter = FilterType.GT.createFilter("id", "1");
        Filter filter2 = FilterType.EQ.createFilter("id", "5");
        Filter filter3 = FilterType.LT.createFilter("id", "10");
        Filter filter4 = FilterType.SORT.createFilter("id", "ASC");

        //Add filters
        activeData.addFilter(filter, false);
        activeData.addFilter(filter2, false);
        activeData.addFilter(filter3, false);
        activeData.addFilter(filter4, false);

        assertEquals(4, activeData.getFilters().size());
    }
}
