package seng202.group2.controller;

import org.junit.jupiter.api.Test;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.datacategories.Date;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CSVImporter
 * @author Connor Dunlop
 */
class CSVImporterTest {

    /** Tests instantiation of a CSVImporter using a valid file. */
    @Test
    void instantiationTestValid() throws FileNotFoundException, UnsupportedFileTypeException {
        CSVImporter importer = new CSVImporter(new File("testfiles/5k.csv"));
    }

    /** Tests instantiation of a CSVImporter using file that does not exist. */
    @Test
    void instantiationTestFileNotFound() {
        assertThrows(FileNotFoundException.class, () -> new CSVImporter(new File("testfiles/file_not_found.csv")));
    }

    /** Tests instantiation of a CSVImporter using file that is not of the correct format. */
    @Test
    void instantiationTestUnsupportedFileType() {
        assertThrows(UnsupportedFileTypeException.class, () -> new CSVImporter(new File("testfiles/testfiles_manual.txt")));
    }

    /** Tests importing of a valid crime data file. */
    @Test
    void importTestValid() throws IOException, UnsupportedFileTypeException {
        CSVImporter importer = new CSVImporter(new File("testfiles/5k.csv"));
        ArrayList<CrimeRecord> records = importer.importAllRecords();
        importer.close();

        assertEquals(5000, records.size());

        CrimeRecord record1 = records.get(0);
        assertEquals("JE163990", record1.getCaseNum());
        assertEquals(Date.getInstance().parseString("11/23/2020 03:05:00 pm"), record1.getDate());
        assertEquals("073XX S SOUTH SHORE DR", record1.getBlock());
        assertEquals("820", record1.getIucr());
        assertEquals("THEFT", record1.getPrimaryDescription());
        assertEquals("$500 AND UNDER", record1.getSecondaryDescription());
        assertEquals("APARTMENT", record1.getLocationDescription());
        assertEquals(false, record1.getArrest());
        assertEquals(false, record1.getDomestic());
        assertEquals((short) 334, record1.getBeat());
        assertEquals((short) 7, record1.getWard());
        assertEquals("6", record1.getFbiCode());
        assertEquals(null, record1.getLatitude());
        assertEquals(null, record1.getLongitude());

        CrimeRecord record2 = records.get(4999);
        assertEquals(41.93888, record2.getLatitude(), 0.00001);
        assertEquals(-87.74302, record2.getLongitude(), 0.00001);
    }
}