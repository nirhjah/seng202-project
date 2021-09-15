package seng202.group2.model;

import org.junit.jupiter.api.*;
import seng202.group2.model.datacategories.Beat;
import seng202.group2.model.datacategories.Latitude;
import seng202.group2.model.datacategories.Ward;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterTest {
    private static final String DATE_FORMAT = "yyyy/MM/dd hh:mm:ss a";
    private static final ActiveData activeData = DBMS.getActiveData();

    private static final int numRecords = 50;

    /**
     * Add a 50 records to the database
     */
    @BeforeAll
    static void addRecords() throws ParseException {
        DBMS.clearDB();
        for (int i=1; i <= numRecords; i++) {
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
     * Clean up database after tests.
     */
    @AfterAll
    static void clearRecords() {
        DBMS.clearDB();
        activeData.clearFilters(true);
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
        activeData.addFilter(FilterType.EQ.createFilter(Beat.getInstance(), "5"));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();
        assertEquals(1, records.size());

        assertEquals((short) 5, records.get(0).getBeat());
    }

    /**
     * Test having multiple equality filters
     */
    @Test
    void equalityMultipleTest() {
        HashSet<Short> expectedBeats = new HashSet<Short>(Arrays.asList(
                (short) 5,
                (short) 6
        ));

        for (Short expectedBeat : expectedBeats)
            activeData.addFilter(FilterType.EQ.createFilter(Beat.getInstance(), expectedBeat.toString()));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();
        assertEquals(expectedBeats.size(), records.size());

        for (CrimeRecord record : records)
            assertTrue(expectedBeats.contains(record.getBeat()));
    }

    /**
     * Test greater than filter
     */
    @Test
    void gtSingleTest() {
        Short gtValue = (short) 4;

        HashSet<Short> expectedBeats = new HashSet<Short>();
        for (int i = gtValue+1; i <= numRecords; i++)
            expectedBeats.add((short) i);

        activeData.addFilter(FilterType.GT.createFilter(Beat.getInstance(), gtValue.toString()));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();
        assertEquals(numRecords - gtValue, records.size());

        for (CrimeRecord record : records)
            assertTrue(expectedBeats.contains(record.getBeat()));
    }

    /**
     * Test having multiple greater than filters
     */
    @Test
    void gtMultipleTest() {
        Short gtValueSmall = (short) 4;
        Short gtValue = (short) (gtValueSmall + 1);

        HashSet<Short> expectedBeats = new HashSet<Short>();
        for (int i = gtValue+1; i <= numRecords; i++)
            expectedBeats.add((short) i);

        activeData.addFilter(FilterType.GT.createFilter(Beat.getInstance(), gtValueSmall.toString()));
        activeData.addFilter(FilterType.GT.createFilter(Beat.getInstance(), gtValue.toString()));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();
        assertEquals(numRecords - gtValue, records.size());

        for (CrimeRecord record : records)
            assertTrue(expectedBeats.contains(record.getBeat()));
    }

    /**
     * Test less than filter
     */
    @Test
    void ltSingleTest() {
        Short ltValue = (short) 5;

        HashSet<Short> expectedBeats = new HashSet<Short>();
        for (int i = 1; i <= ltValue && i <= numRecords; i++)
            expectedBeats.add((short) i);

        activeData.addFilter(FilterType.LT.createFilter(Beat.getInstance(), ltValue.toString()));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();
        assertEquals(ltValue - 1, records.size());

        for (CrimeRecord record : records)
            assertTrue(expectedBeats.contains(record.getBeat()));
    }

    /**
     * Test having multiple less than filters
     */
    @Test
    void ltMultipleTest() {
        Short ltValueBig = (short) 5;
        Short ltValue = (short) (ltValueBig - 1);

        HashSet<Short> expectedBeats = new HashSet<Short>();
        for (int i = 1; i <= ltValue && i <= numRecords; i++)
            expectedBeats.add((short) i);

        activeData.addFilter(FilterType.LT.createFilter(Beat.getInstance(), ltValueBig.toString()));
        activeData.addFilter(FilterType.LT.createFilter(Beat.getInstance(), ltValue.toString()));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();
        assertEquals(ltValue - 1, records.size());

        for (CrimeRecord record : records)
            assertTrue(expectedBeats.contains(record.getBeat()));
    }

    /**
     * Test sorting filter
     */
    @Test
    void sortSingleTest() {
        activeData.addFilter(FilterType.SORT.createFilter(Beat.getInstance(), "DESC"));

        ArrayList<Short> expectedBeats = new ArrayList<>();
        for (short i = numRecords; 0 < i; i--)
            expectedBeats.add(i);

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();
        assertEquals(numRecords, records.size());

        for (int i = 0; i < numRecords; i++)
            assertTrue(expectedBeats.get(i) == records.get(i).getBeat());
    }

    /**
     * Test having multiple sorting filter
     */
    @Test
    void sortMultipleTest() {
        //Should prioritise the first one
        activeData.addFilter(FilterType.SORT.createFilter(Beat.getInstance(), "DESC"));
        activeData.addFilter(FilterType.SORT.createFilter(Ward.getInstance(), "ASC"));

        ArrayList<Short> expectedBeats = new ArrayList<>();
        for (short i = numRecords; 0 < i; i--)
            expectedBeats.add(i);

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();
        assertEquals(numRecords, records.size());

        for (int i = 0; i < numRecords; i++)
            assertTrue(expectedBeats.get(i) == records.get(i).getBeat());
    }

    /**
     * Equality + GT combo test
     */
    @Test
    void eqGTComboTest() {
        //Should prioritise the first one
        activeData.addFilter(FilterType.GT.createFilter(Ward.getInstance(), "6"));
        activeData.addFilter(FilterType.EQ.createFilter(Beat.getInstance(), "9"));

        Filter ltFilter = FilterType.LT.createFilter(Latitude.getInstance(), "7.6");
        activeData.addFilter(ltFilter);

        // No records should match these filters
        ArrayList<CrimeRecord> records = activeData.getActiveRecords();
        assertEquals(0, records.size());

        // Remove one filter so some match
        activeData.removeFilter(ltFilter);
        records = activeData.getActiveRecords();
        assertEquals(1, records.size());

        assertEquals((short) 9, records.get(0).getBeat());
    }

    /**
     * EQ + GT + SORT test
     */
    @Test
    void fullComboTest() {
        ArrayList<Short> expectedBeats = new ArrayList<Short>(Arrays.asList(
                (short) 8,
                (short) 7,
                (short) 6
        ));

        activeData.addFilter(FilterType.GT.createFilter(Beat.getInstance(), "5"));
        activeData.addFilter(FilterType.EQ.createFilter(Beat.getInstance(), "5"));
        activeData.addFilter(FilterType.EQ.createFilter(Beat.getInstance(), "6"));
        activeData.addFilter(FilterType.EQ.createFilter(Beat.getInstance(), "7"));
        activeData.addFilter(FilterType.EQ.createFilter(Beat.getInstance(), "8"));
        activeData.addFilter(FilterType.SORT.createFilter(Beat.getInstance(), "DESC"));

        ArrayList<CrimeRecord> records = activeData.getActiveRecords();
        assertEquals(expectedBeats.size(), records.size());

        for (int i = 0; i < expectedBeats.size(); i++)
            assertEquals(expectedBeats.get(i), records.get(i).getBeat());
    }
}
