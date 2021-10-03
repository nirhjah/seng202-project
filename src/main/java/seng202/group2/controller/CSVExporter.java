package seng202.group2.controller;

import com.opencsv.CSVWriter;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.datacategories.*;

import java.io.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * A crime data exporter used to export crime data to CSV file format.
 * Uses OpenCSV for writing to CSV files.
 *
 * IMPORTANT: This class currently makes the assumption that the given file path to export to, points to an
 * empty or non-existent file.
 *
 * @author Connor Dunlop
 */
public class CSVExporter extends DataExporter {

    /** A CSV file writer from OpenCSV used to write exported crime data to a csv file. */
    private CSVWriter fileWriter;
    /** A flag indicating whether the category headers have been written to the file yet. */
    private Boolean headerExists = false;
    /**
     * A map of the indices of the different DataCategory's of a crime record in each line of data.
     * Used to define which DataCategory values to write to which columns in the output file, i.e. the order
     * of categories in the output file.
     */
    private Map<Class<? extends DataCategory>, Integer> categoryMap = Map.ofEntries(
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(CaseNumber.class, 0),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(Date.class, 1),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(Block.class, 2),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(IUCRCode.class, 3),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(PrimaryDescription.class, 4),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(SecondaryDescription.class, 5),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(LocationDescription.class, 6),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(Arrest.class, 7),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(Domestic.class, 8),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(Beat.class, 9),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(Ward.class, 10),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(FBICode.class, 11),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(Latitude.class, 12),
            new AbstractMap.SimpleEntry<Class<? extends DataCategory>, Integer>(Longitude.class, 13)
    );

    /**
     * Constructs a CSVImporter, setting the file path and instantiating the file reader.
     *
     * @param file A File instance constructed from the path of the file to be imported from.
     * @throws IOException If the given file could not be opened for reading.
     */
    public CSVExporter(File file) throws UnsupportedFileTypeException, IOException {
        super(file);

        // Get suffix
        String fileName = file.getAbsolutePath();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!suffix.equals("csv"))
            throw new UnsupportedFileTypeException("Cannot export to file type \"" + suffix + "\" with CSVExporter.");

        // Make a new CSV file writer from OpenCSV
        fileWriter = new CSVWriter(new FileWriter(file));
    }

    @Override
    public void close() throws IOException {
        fileWriter.close();
    }

    /**
     * Writes the category headers to the file.
     * IMPORTANT: This method currently assumes the provided file is empty.
     */
    private void writeHeader() {
        ArrayList<String> headerNames = new ArrayList<>();
        for (DataCategory category : DataCategory.getCategories(Exportable.class)) {
            headerNames.add(category.toString().toUpperCase());
        }
        fileWriter.writeNext((String[]) headerNames.toArray());

        headerExists = true;
    }

    @Override
    public void exportRecords(ArrayList<CrimeRecord> records) {
        // If the file header hasn't yet been written, write the header.
        if (!headerExists)
            writeHeader();

        Set<DataCategory> exportableCategories = DataCategory.getCategories(Exportable.class);
        for (CrimeRecord record : records) {
            ArrayList<String> recordValues = new ArrayList<>();

            // Get record's values for each exportable category of data
            for (DataCategory exportableCategory : exportableCategories) {
                // Get the record's value for this category of data
                // If value null, use empty string
                String valueString = "";
                try {
                    valueString = exportableCategory.getRecordCategory(record).getValueString();
                } catch (NullPointerException e) {}
                recordValues.add(categoryMap.get(exportableCategory.getClass()), valueString);
            }

            // Write record's values to file
            fileWriter.writeNext((String[]) recordValues.toArray());
        }
    }

}
