package seng202.group2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Tests for CrimeData class.
 *
 * TODO Implement more thorough tests.
 *
 * @author Moses Wescombe
 */
class CrimeDataTest {
    private CrimeData crimeData;

    /** Helper function for tests, adding multiple CrimeRecords */
    private void addMultipleRecords(Integer numberRecords) {
        for (int i=0; i < numberRecords; i++) {
            crimeData.addRecord(new CrimeRecord());
        }
    }

    @BeforeEach
    void initializeCrimeData() {
        crimeData = new CrimeData();
    }

    /**
     * Check that a CrimeRecord can be added to the HashMap and that the IDs are properly aligned.
     */
    @Test
    void testAddRecord() {
        CrimeRecord record = new CrimeRecord();
        record.setCaseNum("This is a test");

        int initialID = crimeData.getIdCounter();

        crimeData.addRecord(record);

        CrimeRecord result = crimeData.getCrimeRecord(initialID);

        assertEquals(result.getPrimaryDescription(), "This is a test");
        assertEquals(initialID, result.getID());
    }

    /**
     * Test that deleteRecord to see if the correct record is deleted and the HashMap is updated
     */
    @Test
    void testDeleteRecord() {
        //Add multiple records
        addMultipleRecords(10);

        int originalSize = crimeData.getCrimeRecordCount();
        crimeData.deleteRecord(8);

        assertEquals(originalSize, crimeData.getCrimeRecordCount() + 1);
        assertNull(crimeData.getCrimeRecord(8));
    }
}
