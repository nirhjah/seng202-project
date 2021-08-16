package seng202.group2;

import java.io.FileNotFoundException;
import java.util.ArrayList;

// TODO: implement class
public class ApplicationEnvironment {
	
	public static void main(String[] args) {
		String fileName = args[0];
		
		DataImporter importer = null;
		
		try {
			importer = DataImporter.getImporter(fileName);
			
		} catch (UnsupportedFileTypeException e) {
			System.out.println("The specified file is of an unsupported file type.");
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the specified file.");
			System.exit(1);
		}
		
		CrimeData crimeData = importer.importAllRecords();
		
		ArrayList<CrimeRecord> records = crimeData.getCrimeRecords();
		
		for (CrimeRecord record : records) {
			System.out.println("");
			System.out.println(record + "\n");
		}
		
		importer.close();
	}

}
