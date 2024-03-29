package seng202.group2.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;

import seng202.group2.controller.DataImporter;
import seng202.group2.model.datacategories.*;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.datacategories.DataCategory;
import seng202.group2.model.datacategories.Importable;
import seng202.group2.model.datacategories.UnsupportedCategoryException;

/**
 * A crime data importer used to import crime data from CSV files.
 * Uses OpenCSV for parsing CSV files.
 * 
 * @author Connor Dunlop
 *
 */
public class CSVImporter extends DataImporter {
	
	/** A CSV file reader from OpenCSV used to read in crime data from a CSV file. */
	private CSVReader fileReader;
	/**
	 * A map of the indices of the different DataCategory's of a crime record in each line of data.
	 * Used when parsing data into a CrimeRecord to keep track of which column represents which category of data.
	 * */
	private HashMap<Integer, DataCategory> categoryMap;
	
	/**
	 * Constructs a CSVImporter, setting the file path and instantiating the file reader.
	 * 
	 * @param file A File instance constructed from the path of the file to be imported from.
	 * @throws FileNotFoundException If there was no file found at the supplied file path
	 */
	public CSVImporter(File file) throws FileNotFoundException, UnsupportedFileTypeException {
		super(file);

		// Get suffix
		String fileName = file.getAbsolutePath();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (!suffix.equals("csv"))
			throw new UnsupportedFileTypeException("Cannot import file type \"" + suffix + "\" with CSVImporter.");
		
		// Make a new CSV file parser from OpenCSV
		fileReader = new CSVReader(new FileReader(file));
	}
	
	@Override
	public void close() throws IOException {
		fileReader.close();
	}
	
	/**
	 * Parses the first line of the CSV file to determine what DataCategory's are included in the file.
	 * If any additional categories of data are included in the file, they are ignored.
	 * If any DataCategory's are not included in the file, or there is data for some crime records missing
	 * from a category, then when parsing crime data from the file, the CrimeRecord(s) will take on 
	 * the default values for those categories.
	 * 
	 * @throws IOException If an error occurs while parsing the CSV file.
	 */
	private void parseCategories() throws IOException {
		
		categoryMap = new HashMap<Integer, DataCategory>();
		String[] categoryLine = {};
		
		try {
			// Try to parse header line into String[]
			categoryLine = fileReader.readNextSilently();
		} catch (IOException error) {
			throw error;
		}
		
		// Map supported DataCategory's to their index in a row
		for (int i = 0; i < categoryLine.length; i++) {
			DataCategory category;
			try {
				// Try to determine DataCategory of column from header string
				category = DataCategory.getCategoryFromString(categoryLine[i]);
				
				if (category instanceof Importable)
					categoryMap.put(i, category);
				else
					throw new UnsupportedCategoryException("Category exists but is not importable.");
				
			} catch (UnsupportedCategoryException error) {
				// If DataCategory could not be determined from header string
				// It is assumed to be an unsupported category of data
				System.out.println("Did not import data category " + categoryLine[i] + ": " + error.getMessage());
			}
			
		}
		
		System.out.print('\n');
	}
	
	/**
	 * Parses an array of string values collected from a line of the CSV file into a CrimeRecord.
	 * 
	 * @param values The values of attributes of a crime record, in the order corresponding to that of the headers.
	 * @return A CrimeRecord whose attributes have been set to the parsed values supplied.
	 */
	private CrimeRecord parseRecord(String[] values) {
		CrimeRecord record = new CrimeRecord();
		
		// For each DataCategory included in the file
		for (int i : categoryMap.keySet()) {
			DataCategory category = categoryMap.get(i);
			try {
				// Try to parse the value corresponding to that DataCategory into the CrimeRecord
				Object value = category.parseString(values[i]);
				category.setRecordValue(record, value);
			} catch (IllegalArgumentException e) {
				// An error occurred while trying to parse the value
				System.out.println("Error parsing value: " + values[i] + " for category: " + category);
			}
		}
		
		return record;
	}
	
	@Override
	public ArrayList<CrimeRecord> importAllRecords() throws IOException {
		// Parse the header row if it has not been parsed yet.
		if (categoryMap == null)
			parseCategories();
		
		ArrayList<CrimeRecord> crimeData = new ArrayList<CrimeRecord>();

		int count = 0;
		System.out.println("Importing all records.");

		// Read all crime records into crimeData object
		String[] values;
		while ((values = fileReader.readNextSilently()) != null) {
			crimeData.add(parseRecord(values));
			System.out.print("\rImported " + ++count + " records so far.");
		}
		System.out.println();
		
		return crimeData;
	}
}
