package seng202.group2.controller;

import seng202.group2.model.CrimeRecord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * An abstract class providing an interface for exporting crime data into a supported file type.
 * This class has one static method used to get a DataExporter for a file, that is compatible with the
 * desired file format.
 *
 * IMPORTANT: After using a DataExporter the close() method must be called to free up any resources
 * reserved by opening files for writing, etc.
 *
 * @author Connor Dunlop
 */
public abstract class DataExporter {

    /** An abstract representation of the path to the file to save exported crime data to. */
    protected File file;

    /**
     * Sets the path to the file to export crime data to.
     *
     * @param file The path to the file to export crime data to.
     */
    public DataExporter(File file) {
        this.file = file;
    }

    /**
     * Releases any resources reserved by opening files, etc. back to the operating system.
     * @throws IOException If an error occurs trying to close the reserved resources.
     */
    public abstract void close() throws IOException;

    /**
     * Gets a DataExporter instance that can parse data into the desired file type.
     * Uses the suffix of the file path to determine the output file type, and
     * consequently which DataExporter to use to export the data.
     *
     * @param fileName The path of the file to write exported crime data to.
     * @return An instance of a DataExporter subclass which can export crime data to the desired file format.
     *
     * @throws UnsupportedFileTypeException If the file suffix does not match a supported file type
     * @throws IOException If the provided file could not be opened for writing.
     */
    public static DataExporter getExporter(String fileName) throws UnsupportedFileTypeException, IOException {
        // Check if file has a suffix
        if (!fileName.contains("."))
            throw new UnsupportedFileTypeException("");

        // Get suffix
        File file = new File(fileName);
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        // Return an importer for the file type given by the suffix
        switch (suffix) {
            case "csv":
                return new CSVExporter(file);

            default:
                // If there is no importer for this file type throw an error
                throw new UnsupportedFileTypeException(suffix);
        }
    }

    /**
     * Writes the supplied records to the file which this exporter is bound to.
     * @param records An ArrayList of the crime records to write to the file.
     */
    public abstract void exportRecords(ArrayList<CrimeRecord> records);
}
