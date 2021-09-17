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
		CamsApplication.main(args);
	}
}

