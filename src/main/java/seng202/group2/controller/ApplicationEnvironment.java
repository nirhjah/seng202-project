package seng202.group2.controller;

import javafx.stage.Stage;
import seng202.group2.view.CamsApplication;
import seng202.group2.model.DBMS;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

// TODO: implement class
public class ApplicationEnvironment {

	public static void main(String[] args) throws IOException {
		DBMS.clearDB();
//		File file = new File("testfiles/10k.csv");
//
//		try {
//			DataImporter importer = new CSVImporter(file);
//			DBMS.addRecords(importer.importAllRecords());
//			importer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		CamsApplication.main(args);
	}
}

