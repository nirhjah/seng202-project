package seng202.group2;

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
	 * @param fileName The path of the file to import crime data from.
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
	
	/**
	 * Imports all crime records from the file to which this DataImporter is bound to.
	 * @return A CrimeData instance with all (valid) crime records from the file.
	 * @throws IOException If an error occurs when trying to parse the file.
	 */
	public abstract ArrayList<CrimeRecord> importAllRecords() throws IOException;
	
}
