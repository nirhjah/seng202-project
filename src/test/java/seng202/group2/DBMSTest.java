package seng202.group2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test the DBMS class, and its methods
 */
public class DBMSTest {
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
    void addRecordTest() throws SQLException, ClassNotFoundException {
        int oldSize = DBMS.getDBSize();

        DBMS.addRecord("TEST1", "2001/12/12 03:40:12 PM", "Block1", "IUCR1", "pDesc1",  "SDec1",
                "lDesc1", false, false, (short)1, (short)1, "fbiCode1", 1.0f, 1.0f);

        assertEquals((oldSize + 1), DBMS.getDBSize());
    }

    /**
     * Check that the correct record is returned and converted into CrimeRecord
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    void getRecordTest() throws SQLException, ClassNotFoundException {
        DBMS.clearDB();
        //Add some data
        DBMS.addRecord("TEST1", "2001/12/12 03:40:12 PM", "Block1", "IUCR1", "pDesc1",  "SDec1",
                "lDesc1", false, false, (short)1, (short)1, "fbiCode1", 1.0f, 1.0f);
        DBMS.addRecord("TEST2", "2001/12/12 03:40:12 PM", "Block2", "IUCR2", "pDesc2",  "SDec2",
                "lDesc2", false, false, (short)2, (short)2, "fbiCode2", 2.0f, 2.0f);
        DBMS.addRecord("TEST3", "2001/12/12 03:40:12 PM", "Block3", "IUCR3", "pDesc3",  "SDec3",
                "lDesc3", false, false, (short)3, (short)3, "fbiCode3", 3.0f, 3.0f);

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
    void getRecordsTest() throws SQLException, ClassNotFoundException {
        //Add some data
        DBMS.addRecord("TEST1", "2001/12/12 03:40:12 PM", "Block1", "IUCR1", "pDesc1",  "SDec1",
                "lDesc1", false, false, (short)1, (short)1, "fbiCode1", 1.0f, 1.0f);
        DBMS.addRecord("TEST2", "2001/12/12 03:40:12 PM", "Block2", "IUCR2", "pDesc2",  "SDec2",
                "lDesc2", false, false, (short)2, (short)2, "fbiCode2", 2.0f, 2.0f);
        DBMS.addRecord("TEST3", "2001/12/12 03:40:12 PM", "Block3", "IUCR3", "pDesc3",  "SDec3",
                "lDesc3", false, false, (short)3, (short)3, "fbiCode3", 3.0f, 3.0f);

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
    void getAllRecordsTest() throws SQLException, ClassNotFoundException {
        //Add some data
        DBMS.addRecord("TEST1", "2001/12/12 03:40:12 PM", "Block1", "IUCR1", "pDesc1",  "SDec1",
                "lDesc1", false, false, (short)1, (short)1, "fbiCode1", 1.0f, 1.0f);
        DBMS.addRecord("TEST2", "2001/12/12 03:40:12 PM", "Block2", "IUCR2", "pDesc2",  "SDec2",
                "lDesc2", false, false, (short)2, (short)2, "fbiCode2", 2.0f, 2.0f);
        DBMS.addRecord("TEST3", "2001/12/12 03:40:12 PM", "Block3", "IUCR3", "pDesc3",  "SDec3",
                "lDesc3", false, false, (short)3, (short)3, "fbiCode3", 3.0f, 3.0f);

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
    void deleteRecordTest() throws SQLException, ClassNotFoundException {
        //Add some data
        DBMS.addRecord("TEST1", "2001/12/12 03:40:12 PM", "Block1", "IUCR1", "pDesc1",  "SDec1",
                "lDesc1", false, false, (short)1, (short)1, "fbiCode1", 1.0f, 1.0f);
        DBMS.addRecord("TEST2", "2001/12/12 03:40:12 PM", "Block2", "IUCR2", "pDesc2",  "SDec2",
                "lDesc2", false, false, (short)2, (short)2, "fbiCode2", 2.0f, 2.0f);
        DBMS.addRecord("TEST3", "2001/12/12 03:40:12 PM", "Block3", "IUCR3", "pDesc3",  "SDec3",
                "lDesc3", false, false, (short)3, (short)3, "fbiCode3", 3.0f, 3.0f);

        int oldSize = DBMS.getDBSize();

        DBMS.deleteRecord(2);
        CrimeRecord res = DBMS.getRecord(2);

        assertEquals((oldSize - 1), DBMS.getDBSize());
        assertNull(res.getCaseNum());
    }
}
