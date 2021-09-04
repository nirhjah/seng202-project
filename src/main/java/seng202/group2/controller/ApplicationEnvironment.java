package seng202.group2.controller;

import seng202.group2.controller.CSVImporter;
import seng202.group2.controller.DataImporter;
import seng202.group2.model.CrimeRecord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// TODO: implement class
public class ApplicationEnvironment {

	public static void main(String[] args) throws IOException {
		File file = new File("testfiles/5_extra_arrest_field.csv");
		DataImporter importer = new CSVImporter(file);

		ArrayList<CrimeRecord> records = importer.importAllRecords();
		for (CrimeRecord record : records)
			System.out.println(record.toString());

		importer.close();
	}

}

