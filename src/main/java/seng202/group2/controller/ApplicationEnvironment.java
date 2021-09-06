package seng202.group2.controller;

import seng202.group2.view.CamsApplication;
import seng202.group2.model.DBMS;

import java.io.IOException;
import java.sql.SQLException;

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

