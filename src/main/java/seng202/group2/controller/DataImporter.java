package seng202.group2.controller;

import seng202.group2.model.CrimeRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * An abstract class providing an interface for importing crime data from a supported file type.
 * This class has one static method used to get a DataImporter for a file, that is compatible with the 
 * supplied file type.
 * 
 * IMPORTANT: After using a DataImporter the close() method must be called to free up any resources
 * reserved by opening files, etc.
 * 
 * @author Connor Dunlop
 *
 */
public abstract class DataImporter {
	
	/** An abstract representation of the path to the file to import crime data from. */
	protected File file;
	
	/**
	 * Sets the path to the file to import crime data from.
	 * 
	 * @param file The path of the file to import crime data from.
	 */
	public DataImporter(File file) {
		this.file = file;
	}
	
	/**
	 * Releases any resources reserved by opening files, etc. back to the operating system.
	 * @throws IOException If an error occurs trying to close the reserved resources.
	 */
	public abstract void close() throws IOException;
	
	/**
	 * Gets a DataImporter instance that can parse the supplied file.
	 * Uses the suffix of the file path to determine the type of file supplied, and
	 * consequently which DataImporter to use to parse the file.
	 * 
	 * @param fileName The path of the file from which to import crime data. 
	 * @return An instance of a DataImporter subclass which can parse the file type determined by the suffix of the file path.
	 * 
	 * @throws UnsupportedFileTypeException If the file suffix does not match a supported file type
	 * @throws FileNotFoundException If no file could be found with the supplied file path
	 */
	public static DataImporter getImporter(String fileName) throws UnsupportedFileTypeException, FileNotFoundException {
		// Check if file has a suffix
		if (!fileName.contains("."))
			throw new UnsupportedFileTypeException("");
		
		// Get suffix
		File file = new File(fileName);
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		// Return an importer for the file type given by the suffix
		switch (suffix) {
			case "csv":
				return new CSVImporter(file);
				
			default:
				// If there is no importer for this file type throw an error
				throw new UnsupportedFileTypeException(suffix);
		}
	}

	/** An array list of the data fields which exist in the imported file, but are unsupported, and ignored. */
	private ArrayList<String> discardedFields = new ArrayList<>();
	/** An array list of all the records for which an error occurred trying to parse. */
	private ArrayList<CrimeRecord> invalidRecords = new ArrayList<>();

	/**
	 * Adds an ignored field to the array of ignored data fields.
	 * @param field An ignored data field.
	 */
	protected void addDiscardedField(String field) {
		discardedFields.add(field);
	}

	/**
	 * Returns the list of ignored data fields from the file.
	 * @return The list of ignored data fields.
	 */
	public ArrayList<String> getDiscardedFields() {
		return discardedFields;
	}

	/**
	 * Adds an invalid record to the array of invalid records.
	 * @param record An invalid record.
	 */
	protected void addInvalidRecord(CrimeRecord record) {
		invalidRecords.add(record);
	}

	/**
	 * Returns the list of invalid records from the file.
	 * @return The list of invalid records.
	 */
	public ArrayList<CrimeRecord> getInvalidRecords() {
		return invalidRecords;
	}

	/**
	 * Counts the number of crime records stored in the file this importer is bound to.
	 * Does not check if the crime record is valid.
	 *
	 * @return The number of crime records stored in the file.
	 * @throws IOException If an error occurs when trying to read.
	 */
	public abstract Integer countRecords() throws IOException;
	
	/**
	 * Imports all crime records from the file to which this DataImporter is bound to.
	 *
	 * @return An ArrayList of crime records with all (valid) crime records from the file.
	 * @throws IOException If an error occurs when trying to parse the file.
	 */
	public abstract ArrayList<CrimeRecord> importAllRecords() throws IOException;


	/**
	 * Imports the next [recordCount] valid crime records from the file which this DataImporter is bound to.
	 * If there are fewer than [recordCount] valid crime records remaining in the file, returns the remaining
	 * valid crime records.
	 * If there are no crime records left to read from the file, returns an empty ArrayList.
	 *
	 * @param recordCount The number of (valid) crime records to import.
	 * @return An ArrayList of the imported crime records.
	 * @throws IOException If an error occurs when trying to parse the file.
	 */
	public abstract  ArrayList<CrimeRecord> importRecords(Integer recordCount) throws IOException;
}
