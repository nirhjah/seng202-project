package seng202.group2.controller;

import seng202.group2.controller.CSVImporter;
import seng202.group2.controller.DataImporter;
import seng202.group2.controller.viewcontollers.CamsApplication;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

// TODO: implement class
public class ApplicationEnvironment {

	public static void main(String[] args) throws IOException {
		try {
			DBMS.clearDB();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		CamsApplication.main(args);
	}

}

