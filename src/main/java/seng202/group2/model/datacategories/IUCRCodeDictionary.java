package seng202.group2.model.datacategories;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.opencsv.CSVReader;

/**
 * A dictionary of Illinois Crime Reporting Codes and their meanings.
 * 
 * @author Connor Dunlop
 *
 */
public class IUCRCodeDictionary {
	
	private static class IUCRCodeStruct {
		public final String IUCR;
		public final String PRIMARY_DESCRIPTION;
		public final String SECONDARY_DESCRIPTION;
		public final Boolean IS_INDEX;
		
		IUCRCodeStruct(String iucr, String primaryDescription, String secondaryDescription, Boolean is_index) {
			this.IUCR = iucr;
			this.PRIMARY_DESCRIPTION = primaryDescription;
			this.SECONDARY_DESCRIPTION = secondaryDescription;
			this.IS_INDEX = is_index;
		}
	}
	
	/** The dictionary used to store the IUCR codes */
	private static HashMap<String, IUCRCodeStruct> IUCRCodes = new HashMap<String, IUCRCodeStruct>();
	
	/** Empties the IUCRCode dictionary totally. */
	public static void clear() {
		IUCRCodes = new HashMap<String, IUCRCodeStruct>();
	}
	
	/** Adds an iucr code to the dictionary of valid iucr codes */
	public static void addCode(String iucr, String primaryDescription, String secondaryDescription, Boolean is_index) {
		IUCRCodes.put(iucr, new IUCRCodeStruct(iucr, primaryDescription, secondaryDescription, is_index));
	}
	
	/**
	 * Imports the dictionary of IUCR codes from a CSV file.
	 * 
	 * The CSV file is of the same format as provided <a href="https://catalog.data.gov/dataset/chicago-police-department-illinois-uniform-crime-reporting-iucr-codes">here</a>.
	 * That is, it has four columns, iucr, primary description, secondary description, and index code, in that order.
	 * The first line (the header line) is skipped.
	 * 
	 * @param file The CSV file from which to import the dictionary of IUCR codes.
	 * @throws FileNotFoundException If the no file was found at the specified file path.
	 * @throws IOException If an error occurs whilst trying to read the IUCR codes from the file.
	 */
	public static void importFromCSV(File file) throws FileNotFoundException, IOException {
		CSVReader fileReader = new CSVReader(new FileReader(file));
		try {
			fileReader.skip(1);
			
			String[] values;
			while ((values = fileReader.readNextSilently()) != null) {
				String iucr = values[0];
				String primaryDescription = values[1];
				String secondaryDescription = values[2];
				String indexCode = values[3];
				
				while (iucr.startsWith("0"))
					iucr = iucr.replaceFirst("0", "");
				
				Boolean index = null;
				if (indexCode == "I")
					index = true;
				else if (indexCode == "N")
					index = false;
				
				if (!IUCRCodes.containsKey(iucr)) {
					addCode(iucr, primaryDescription, secondaryDescription, index);
				} else {
					throw new IOException("Duplicate IUCR code listing");
				}
			}
		} finally {
			fileReader.close();
		}
	}
	
	/**
	 * Imports IUCR codes from the specified file when the dictionary is first referenced.
	 */
	static {
		File file = new File("config/iucr-codes.csv");
		try {
			importFromCSV(file);
			System.out.println("Imported IUCR codes");
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the IUCR Code dictionary file.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("An error occurred while trying to read the IUCR Code dictionary file.");
			System.exit(1);
		}
	}
	
	/**
	 * Returns the primary description associated with a particular IUCR code.
	 * 
	 * @param iucr The IUCR code as a string, used as a key to find the IUCRCodeStruct object in the dictionary.
	 * @return The primary description associated with the iucr code.
	 * @throws IllegalArgumentException If the IUCR does not exist within the dictionary.
	 */
	public static String getPrimaryDescription(String iucr) {
		if (IUCRCodes.containsKey(iucr))
			return IUCRCodes.get(iucr).PRIMARY_DESCRIPTION;
		else
			throw new IllegalArgumentException("Non-existent IUCR: " + iucr);
	}
	
	/**
	 * Returns the secondary description associated with a particular IUCR code.
	 * 
	 * @param iucr The IUCR code as a string, used as a key to find the IUCRCodeStruct object in the dictionary.
	 * @return The secondary description associated with the iucr code.
	 * @throws IllegalArgumentException If the IUCR does not exist within the dictionary.
	 */
	public static String getSecondaryDescription(String iucr) {
		if (IUCRCodes.containsKey(iucr))
			return IUCRCodes.get(iucr).SECONDARY_DESCRIPTION;
		else
			throw new IllegalArgumentException("Non-existent IUCR: " + iucr);
	}
	
	/**
	 * Returns the index code associated with a particular IUCR code.
	 * 
	 * @param iucr The IUCR code as a string, used as a key to find the IUCRCodeStruct object in the dictionary.
	 * @return True if the IUCRCode is an index code.
	 * @throws IllegalArgumentException If the IUCR does not exist within the dictionary.
	 */
	public static boolean getIndex(String iucr) {
		if (IUCRCodes.containsKey(iucr))
			return IUCRCodes.get(iucr).IS_INDEX;
		else
			throw new IllegalArgumentException("Non-existent IUCR: " + iucr);
	}
	
	/**
	 * Checks if the given IUCR code exists in the dictionary of valid IUCR codes.
	 * 
	 * @param iucr An iucr code to check if exists within the dictionary.
	 * @return True if the iucr exist in the dictionary, false otherwise.
	 */
	public static boolean contains(String iucr) {
		return IUCRCodes.containsKey(iucr);
	}
}
