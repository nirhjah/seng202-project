package seng202.group2;

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
	
	/** The dictionary used to store the IUCR codes */
	private static HashMap<String, IUCRCode> IUCRCodes = new HashMap<String, IUCRCode>();
	
	/** Empties the IUCRCode dictionary totally. */
	public static void clear() {
		IUCRCodes = new HashMap<String, IUCRCode>();
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
				
				Boolean index = null;
				if (indexCode == "I")
					index = true;
				else if (indexCode == "N")
					index = false;
				
				if (!IUCRCodes.containsKey(iucr)) {
					IUCRCode code = new IUCRCode(iucr, primaryDescription, secondaryDescription, index);
					IUCRCodes.put(iucr, code);
				} else {
					throw new IOException("Duplicate IUCR code listing");
				}
			}
		} finally {
			fileReader.close();
		}
	}
	
	/**
	 * Returns the IUCRCode object from its IUCR code.
	 * 
	 * @param iucr The IUCR code as a string, used as a key to find the IUCRCode object.
	 * @return The IUCRCode object for the IUCR code.
	 * @throws IllegalArgumentException If the IUCR does not exist within the dictionary.
	 */
	public static IUCRCode getCode(String iucr) {
		if (IUCRCodes.containsKey(iucr))
			return IUCRCodes.get(iucr);
		else
			throw new IllegalArgumentException("Non-existent IUCR: " + iucr);
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
}
