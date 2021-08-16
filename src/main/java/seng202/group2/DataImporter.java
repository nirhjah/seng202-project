package seng202.group2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class DataImporter {
	
	protected File file;
	protected Scanner fileScanner;
	
	public DataImporter(String fileName) throws FileNotFoundException {
		this.file = new File(fileName);
		fileScanner = new Scanner(file);
	}
	
	public void close() {
		fileScanner.close();
	}
	
	public static DataImporter getImporter(String fileName) throws UnsupportedFileTypeException, FileNotFoundException {
		
		// Get file name suffix
		String[] splitName = fileName.split("\\.");
		String suffix = splitName[splitName.length - 1];
		
		// Return an importer for the file type given by the suffix
		switch (suffix) {
			case "csv":
				return new CSVImporter(fileName);
				
			default:
				// If there is no importer for this file type throw an error
				throw new UnsupportedFileTypeException(suffix);
		}
	}
	
	public abstract CrimeData importAllRecords();
	
}
