package seng202.group2.view;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group2.controller.CSVImporter;
import seng202.group2.controller.DataImporter;
import seng202.group2.model.datacategories.IUCRCode;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

/**
 * ImportController is the controller class for the crime import GUI.
 *
 * This class uses the 'import.fxml' FXML file for laying it out.
 * The GUI for this class allows a user-friendly way to get a filepath to a csv file to be imported,
 * either by browsing a file explorer for it or entering it manually in a textField.
 * This class uses a {@link FileChooser} to select a file using a file explorer.
 *
 * @author Sam Clark
 *
 */
public class ImportController {

	/** The textField used for entering a filepath. */
	@FXML private TextField importPathTextField;

	void addRecords(int max) throws SQLException, ClassNotFoundException, ParseException {
		for (int i=1; i <= max; i++) {
			CrimeRecord record = new CrimeRecord();
			record.setCaseNum("TEST" + i);

			//Convert String from db to Calendar
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse("2001/12/12 03:40:12 PM"));
			record.setDate(cal);

			record.setBlock("Block" + i);
			record.setIucr("110");
            record.setPrimaryDescription("pDesc" + i);
            record.setSecondaryDescription("sDesc" + i);
			record.setLocationDescription("lDesc" + i);
			record.setArrest(false);
			record.setDomestic(false);
			record.setWard((short) i);
			record.setBeat((short) i);
			record.setFbiCode("fbiCode" + i);
			record.setLongitude((float) i);
			record.setLatitude((float) i);
			DBMS.addRecord(record,false);
		}

		DBMS.getActiveData().updateObservers();
	}

	/**
	 * importFileFromField takes the giving filepath and passes it the importer.
	 *
	 * This method is called when the 'import' button is clicked. It takes the filepath from
	 * {@link ImportController#importPathTextField} and passes it to TODO add importer info
	 * to import the crime data into the database.
	 *
	 * TODO use this with connors import method.
	 */
	public void importFileFromField()
	{
		Stage stage = (Stage) importPathTextField.getScene().getWindow();

		File file = new File(importPathTextField.getText());
		try {
			DataImporter importer = new CSVImporter(file);
			DBMS.addRecords(importer.importAllRecords());

			importer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		try {
//			addRecords(10);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

		stage.close();
		System.out.println("Retrieving file : " + importPathTextField.getText());
	}

	/**
	 * Opens a FileChooser to browse for a file to import.
	 *
	 * This method uses a {@link FileChooser} to select a file to import. When a file is selected
	 * it sets the text of {@link ImportController#importPathTextField} to the selected file path,
	 * ready to be imported.
	 */
	public void browseImportDirectory()
	{
		FileChooser fileChooser = new FileChooser();
		File chosenFile = fileChooser.showOpenDialog(null);
		
		if (chosenFile != null) {
			importPathTextField.setText(chosenFile.getAbsolutePath());
		}
	}
}