package seng202.group2.controller;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DataImporter class.
 * @author Connor Dunlop
 */
class DataImporterTest {

    /** Tests getting an importer for a valid filepath */
    @Test
    void getImporterTestValid() throws UnsupportedFileTypeException, FileNotFoundException {
        DataImporter importer = DataImporter.getImporter("testfiles/5k.csv");
        assertEquals(CSVImporter.class, importer.getClass());
    }

    /** Tests getting an importer for a file that doesn't exist */
    @Test
    void getImporterTestFileNotFound() {
        assertThrows(FileNotFoundException.class, () -> DataImporter.getImporter("testfiles/file_not_found.csv"));
    }

    /** Tests getting an importer for an unsupported filetype */
    @Test
    void getImporterTestUnsupportedFileType() {
        assertThrows(UnsupportedFileTypeException.class, () -> DataImporter.getImporter("testfiles/testfiles_manual.txt"));
    }
}