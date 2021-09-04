package seng202.group2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group2.controller.IUCRCodeDictionary;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test the DBMS class, and its methods
 */
public class DBMSTest {
    //Date format for creating calander
    private static final String DATE_FORMAT = "yyyy/MM/dd hh:mm:ss a";

    /**
     * Add a given number of crime records to the database, each crime records data is labeled by its ID, starting at 1
     *
     * @param max Number of records to add
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ParseException
     */
    void addRecords(int max) throws SQLException, ClassNotFoundException, ParseException {
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
            record.setIucr(IUCRCodeDictionary.getCode("110"));
            record.setLocationDescription("lDesc" + num);
            record.setArrest(false);
            record.setDomestic(false);
            record.setWard((short) (int) Integer.valueOf(num));
            record.setBeat((short) (int) Integer.valueOf(num));
            record.setFbiCode("fbiCode" + num);
            record.setLongitude((float)(int) Integer.valueOf(num));
            record.setLatitude((float)(int) Integer.valueOf(num));
            DBMS.addRecord(record);
        }
    }


    @BeforeEach
    void resetDataBase() throws SQLException, ClassNotFoundException {
        DBMS.clearDB();
    }

    /**
     * Test that a record is correctly added to the DB
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    void addRecordTest() throws SQLException, ClassNotFoundException, ParseException {
        int oldSize = DBMS.getDBSize();

        addRecords(1);

        assertEquals((oldSize + 1), DBMS.getDBSize());
    }

    /**
     * Check that the correct record is returned and converted into CrimeRecord
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    void getRecordTest() throws SQLException, ClassNotFoundException, ParseException {
        DBMS.clearDB();
        addRecords(5);

        CrimeRecord record = DBMS.getRecord(2);
        assertEquals("TEST2", record.getCaseNum());
    }

    /**
     * Test that the correct records are retrieved from the DB and correctly converted to CrimeRecords
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    void getRecordsTest() throws SQLException, ClassNotFoundException, ParseException {
        addRecords(5);

        ArrayList<Integer> ids = new ArrayList<>(Arrays.asList(1, 2));
        ArrayList<CrimeRecord> records = DBMS.getRecords(ids);
        assertEquals("TEST1", records.get(0).getCaseNum());
        assertEquals("TEST2", records.get(1).getCaseNum());
    }

    /**
     * Test that all the records are converted to an ArrayList correctly
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    void getAllRecordsTest() throws SQLException, ClassNotFoundException, ParseException {
        addRecords(5);

        ResultSet results = DBMS.getAllRecords();
        results.next();
        assertEquals("TEST1", results.getString("caseNum"));
        results.next();
        assertEquals("TEST2", results.getString("caseNum"));
        results.next();
        assertEquals("TEST3", results.getString("caseNum"));
    }

    /**
     * Test that deleteRecord to see if the correct record is deleted
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    void deleteRecordTest() throws SQLException, ClassNotFoundException, ParseException {
        addRecords(5);

        int oldSize = DBMS.getDBSize();

        DBMS.deleteRecord(2);
        CrimeRecord res = DBMS.getRecord(2);

        assertEquals((oldSize - 1), DBMS.getDBSize());
        assertNull(res.getCaseNum());
    }
}
