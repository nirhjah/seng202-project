package seng202.group2.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group2.controller.DataImporter;
import seng202.group2.controller.UnsupportedFileTypeException;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ImportController is the controller class for the crime import GUI.
 *
 * This class uses the 'import.fxml' FXML file for laying it out.
 * The GUI for this class allows a user-friendly way to get a filepath to a csv file to be imported,
 * either by browsing a file explorer for it or entering it manually in a textField.
 * This class uses a {@link FileChooser} to select a file using a file explorer.
 *
 * @author Sam Clark
 * @author Connor Dunlop
 *
 */
public class ImportController {

	/** The number of records to import per import step. */
	private final int RECORDS_PER_IMPORT = 100;

	/** The textField used for entering a filepath. */
	@FXML private TextField importPathTextField;

	/** Used to define the current importing status of the controller. */
	private enum ImportingStatus {
		/** The initial status, before the import button has been clicked. */
		IDLE,
		IMPORTING,
		POPULATING_DATABASE,
		DONE
	}

	/** The current importing status of the controller */
	private ImportingStatus status = ImportingStatus.IDLE;

	@FXML private ProgressBar progressBar;
	@FXML private Label importMessage;
	@FXML private HBox importProgressMessageBox;
	@FXML private Label recordsSoFar;
	@FXML private Label recordsToImport;

	/** The number of crime records imported so far. */
	private int numImported = 0;
	/** The total number of crime records stored in the file to be imported. */
	private int totalToImport = 0;

	/**
	 * Displays a message indicating which importing state/status the controller is in, and updates the user interface
	 * appropriately to the importing status.
	 */
	public void updateProgress() {
		switch (status) {
			case IDLE:
				Platform.runLater(() -> {
					importMessage.setText("Select a file to import.");
					importProgressMessageBox.setVisible(false);
					progressBar.setProgress(0);
				});
				break;

			case IMPORTING:
				Platform.runLater(() -> {
					importMessage.setText("Importing Records:");
					importProgressMessageBox.setVisible(true);
					recordsSoFar.setText(Integer.toString(numImported));
					recordsToImport.setText(Integer.toString(totalToImport));
					progressBar.setProgress(((double) numImported) / ((double) totalToImport));
				});
				break;

			case POPULATING_DATABASE:
				Platform.runLater(() -> {
					importMessage.setText("Adding to Database...");
					importProgressMessageBox.setVisible(false);
					Stage stage = (Stage) importPathTextField.getScene().getWindow();
					stage.getScene().setCursor(Cursor.WAIT);
				});
				break;

			case DONE:
				Platform.runLater(() -> {
					importMessage.setText("Done.");
					Stage stage = (Stage) importPathTextField.getScene().getWindow();
					stage.getScene().setCursor(Cursor.DEFAULT);
					stage.close();
				});
				break;
		}
	}

	/**
	 * Adds an ArrayList of crime records to the database.
	 * @param records An ArrayList of crime records to add to the database.
	 */
	private void addToDatabase(ArrayList<CrimeRecord> records) {
		status = ImportingStatus.POPULATING_DATABASE;
		updateProgress();

		DBMS.addRecords(records);
	}

	/**
	 * Imports an ArrayList of crime records from the specified file.
	 * This method imports the crime records in chunks of [RECORDS_PER_IMPORT] records.
	 *
	 * @param file The file from which to import crime records.
	 * @return An ArrayList of crime records imported from the specified file.
	 */
	private ArrayList<CrimeRecord> importFromFile(File file) {
		status = ImportingStatus.IMPORTING;
		ArrayList<CrimeRecord> records = new ArrayList<>();

		// Try to get an importer for the provided file
		DataImporter importer;
		try {
			importer = DataImporter.getImporter(file.getPath());
		} catch (UnsupportedFileTypeException e) {
			e.printStackTrace();
			System.out.println("File type not supported.");
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File does not exist.");
			return null;
		}

		// Import all CrimeRecords in the file.
		try {
			totalToImport = importer.countRecords();

			// Import the crime records in chunks
			while (true) {
				ArrayList<CrimeRecord> nextRecords = importer.importRecords(RECORDS_PER_IMPORT);
				if (nextRecords.size() == 0)
					break;

				records.addAll(nextRecords);
				numImported += nextRecords.size();
				updateProgress();
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error parsing file.");

		// Always try to close the importer
		} finally {
			try {
				importer.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error trying to close file.");
			}
		}

		return records;
	}

	/**
	 * importFileFromField takes the giving filepath and passes it the importer.
	 *
	 * This method is called when the 'import' button is clicked. It takes the filepath from
	 * {@link ImportController#importPathTextField} and imports it with {@link DataImporter}
	 * into the database.
	 *
	 */
	public void importFileFromField()
	{
		String filePath = importPathTextField.getText();
		if (filePath.equals(""))
			return;

		File file = new File(filePath);
		System.out.println("Retrieving file : " + importPathTextField.getText());

		new Thread(() -> {
			ArrayList<CrimeRecord> records = importFromFile(file);
			addToDatabase(records);
			status = ImportingStatus.DONE;
			updateProgress();
		}).start();
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
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Comma Separated Value","*.csv"));
		File chosenFile = fileChooser.showOpenDialog(null);
		
		
		if (chosenFile != null) {
			importPathTextField.setText(chosenFile.getAbsolutePath());
		}
	}
}
