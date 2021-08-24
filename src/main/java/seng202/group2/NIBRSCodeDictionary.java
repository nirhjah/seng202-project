package seng202.group2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.opencsv.CSVReader;

/**
 * A dictionary of National Incident-Based Reporting System codes and their meanings.
 * 
 * @author Connor Dunlop
 *
 */
public class NIBRSCodeDictionary {
	
	/** The dictionary used to store the NIBRS codes */
	private static HashMap<String, NIBRSCode> NIBRSCodes = new HashMap<String, NIBRSCode>();
	
	/** Empties the NIBRSCode dictionary totally. */
	public static void clear() {
		NIBRSCodes = new HashMap<String, NIBRSCode>();
	}
	
	/**
	 * Imports the dictionary of NIBRS codes from a CSV file.
	 * 
	 * The format of the CSV file is such that it has four columns,
	 * nibrs code, description, crime against, and group, in that order.
	 * The first line (the header line) is skipped.
	 * 
	 * @param file The CSV file from which to import the dictionary of NIBRS codes.
	 * @throws FileNotFoundException If the no file was found at the specified file path.
	 * @throws IOException If an error occurs whilst trying to read the NIBRS codes from the file.
	 */
	public static void importFromCSV(File file) throws FileNotFoundException, IOException {
		CSVReader fileReader = new CSVReader(new FileReader(file));
		try {
			fileReader.skip(1);
			
			String[] values;
			while ((values = fileReader.readNextSilently()) != null) {
				String nibrs = values[0];
				String description = values[1];
				String against= values[2];
				String group = values[3];
				
				while (nibrs.startsWith("0"))
					nibrs = nibrs.replaceFirst("0", "");
				
				if (!NIBRSCodes.containsKey(nibrs)) {
					NIBRSCode code = new NIBRSCode(nibrs, description, against, group);
					NIBRSCodes.put(nibrs, code);
				} else {
					throw new IOException("Duplicate NIBRS code listing");
				}
			}
		} finally {
			fileReader.close();
		}
	}
	
	/**
	 * Returns the NIBRSCode object from its NIBRS code.
	 * 
	 * @param nibrs The NIBRS code as a string, used as a key to find the NIBRSCode object.
	 * @return The NIBRSCode object for the NIBRS code.
	 * @throws IllegalArgumentException If the NIBRS does not exist within the dictionary.
	 */
	public static NIBRSCode getCode(String nibrs) {
		if (NIBRSCodes.containsKey(nibrs))
			return NIBRSCodes.get(nibrs);
		else
			throw new IllegalArgumentException("Non-existent NIBRS: " + nibrs);
	}
	
	/**
	 * Imports NIBRS codes from the specified file when the dictionary is first referenced.
	 */
	static {
		File file = new File("config/nibrs-codes.csv");
		try {
			importFromCSV(file);
			System.out.println("Imported NIBRS codes");
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the NIBRS Code dictionary file.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("An error occurred while trying to read the NIBRS Code dictionary file.");
			System.exit(1);
		}
	}
}
