package seng202.group2.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterTest {
    private static final String DATE_FORMAT = "yyyy/MM/dd hh:mm:ss a";
    private static final ActiveData activeData = DBMS.getActiveData();

    /**
     * Add a 50 records to the database
     */
    @BeforeAll
    static void addRecords() throws ParseException {
        DBMS.clearDB();
        for (int i=1; i <= 50; i++) {
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

    /**
     * Clear the filters before each test
     */
    @BeforeEach
    void clearFilters() {
        activeData.clearFilters(true);
    }

    /**
     * Test equality filter
     */
    @Test
    void equalitySingleTest() {
        activeData.addFilter(FilterType.EQ.createFilter("beat", "5"));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();

        assertEquals((short) 5, records.get(0).getBeat());
        assertEquals(1, records.size());
    }

    /**
     * Test having multiple equality filters
     */
    @Test
    void equalityMultipleTest() {
        activeData.addFilter(FilterType.EQ.createFilter("beat", "5"));
        activeData.addFilter(FilterType.EQ.createFilter("beat", "6"));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();

        assertEquals((short) 5, records.get(0).getBeat());
        assertEquals((short) 6, records.get(1).getBeat());
        assertEquals(2, records.size());
    }

    /**
     * Test greater than filter
     */
    @Test
    void gtSingleTest() {
        activeData.addFilter(FilterType.GT.createFilter("beat", "4"));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();

        assertEquals((short) 5, records.get(0).getBeat());
        assertEquals((short) 6, records.get(1).getBeat());
        assertEquals(46, records.size());
    }

    /**
     * Test having multiple greater than filters
     */
    @Test
    void gtMultipleTest() {
        activeData.addFilter(FilterType.GT.createFilter("beat", "5"));
        activeData.addFilter(FilterType.GT.createFilter("beat", "4"));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();

        assertEquals((short) 6, records.get(0).getBeat());
        assertEquals((short) 7, records.get(1).getBeat());
        assertEquals(45, records.size());
    }

    /**
     * Test less than filter
     */
    @Test
    void ltSingleTest() {
        activeData.addFilter(FilterType.LT.createFilter("beat", "5"));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();

        assertEquals((short) 1, records.get(0).getBeat());
        assertEquals((short) 4, records.get(3).getBeat());
        assertEquals(4, records.size());
    }

    /**
     * Test having multiple less than filters
     */
    @Test
    void ltMultipleTest() {
        activeData.addFilter(FilterType.LT.createFilter("beat", "5"));
        activeData.addFilter(FilterType.LT.createFilter("beat", "4"));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();

        assertEquals((short) 1, records.get(0).getBeat());
        assertEquals((short) 2, records.get(1).getBeat());
        assertEquals(3, records.size());
    }

    /**
     * Test sorting filter
     */
    @Test
    void sortSingleTest() {
        activeData.addFilter(FilterType.SORT.createFilter("beat", "DESC"));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();

        assertEquals((short) 50, records.get(0).getBeat());
        assertEquals((short) 1, records.get(49).getBeat());
    }

    /**
     * Test having multiple sorting filter
     */
    @Test
    void sortMultipleTest() {
        //Should prioritise the first one
        activeData.addFilter(FilterType.SORT.createFilter("beat", "DESC"));
        activeData.addFilter(FilterType.SORT.createFilter("ward", "ASC"));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();

        assertEquals((short) 50, records.get(0).getBeat());
        assertEquals((short) 1, records.get(49).getBeat());
    }

    /**
     * Equality + GT combo test
     */
    @Test
    void eqGTComboTest() {
        //Should prioritise the first one
        Filter gt = FilterType.GT.createFilter("ward", "6");

        activeData.addFilter(gt);
        activeData.addFilter(FilterType.EQ.createFilter("beat", "5"));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();

        assertEquals(0, records.size());

        activeData.removeFilter(gt);
        records = activeData.getActiveRecords();

        assertEquals((short) 5, records.get(0).getBeat());
        assertEquals(1, records.size());
    }

    /**
     * EQ + GT + SORT test
     */
    @Test
    void fullComboTest() {
        activeData.addFilter(FilterType.GT.createFilter("beat", "5"));
        activeData.addFilter(FilterType.EQ.createFilter("beat", "5"));
        activeData.addFilter(FilterType.EQ.createFilter("beat", "6"));
        activeData.addFilter(FilterType.EQ.createFilter("beat", "7"));
        activeData.addFilter(FilterType.EQ.createFilter("beat", "8"));
        activeData.addFilter(FilterType.SORT.createFilter("beat", "DESC"));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();

        assertEquals((short) 8, records.get(0).getBeat());
        assertEquals((short) 6, records.get(2).getBeat());
        assertEquals(3, records.size());
    }
}
