package seng202.group2.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
	@FXML private Button browseButton;
	@FXML private Button importButton;
	@FXML private Button summaryButton;

	/** The number of crime records imported so far. */
	private int numImported = 0;
	/** The total number of crime records stored in the file to be imported. */
	private int totalToImport = 0;

	private int numSuccessful = 0;
	private ArrayList<CrimeRecord> invalidRecords = new ArrayList<>();
	private ArrayList<CrimeRecord> duplicateRecords = new ArrayList<>();
	private ArrayList<String> discardedFields = new ArrayList<>();

	/** Sets initial focus to browse button. */
	public void initialise() {
		Platform.runLater(() -> {
			browseButton.requestFocus();
		});
	}

	/**
	 * Displays a message indicating which importing state/status the controller is in, and updates the user interface
	 * appropriately to the importing status.
	 */
	public void updateProgress() {
		switch (status) {
			case IDLE:
				Platform.runLater(() -> {
					importMessage.setText("Select a file to import.");

					progressBar.setVisible(false);
					browseButton.setDisable(false);
					importButton.setDisable(false);
					importProgressMessageBox.setVisible(false);
					progressBar.setProgress(0);
				});
				break;

			case IMPORTING:
				Platform.runLater(() -> {
					importMessage.setText("Importing Records:");

					importProgressMessageBox.setVisible(true);
					browseButton.setDisable(true);
					importButton.setDisable(true);
					progressBar.setVisible(true);

					recordsSoFar.setText(Integer.toString(numImported));
					recordsToImport.setText(Integer.toString(totalToImport));
					progressBar.setProgress(((double) numImported) / ((double) totalToImport));
				});
				break;

			case POPULATING_DATABASE:
				Platform.runLater(() -> {
					importMessage.setText("Adding to Database:");

					recordsSoFar.setText(Integer.toString(numImported));
					recordsToImport.setText(Integer.toString(totalToImport));
					progressBar.setProgress(((double) numImported) / ((double) totalToImport));
				});
				break;

			case DONE:
				Platform.runLater(() -> {
					importMessage.setText("Import finished.");

					importProgressMessageBox.setVisible(false);
					summaryButton.setVisible(true);

					Stage stage = (Stage) importPathTextField.getScene().getWindow();
					stage.getScene().setCursor(Cursor.DEFAULT);
				});
				break;
		}
	}

	/**
	 * Shows a new window summarising number of records imported successfully, duplicate records, invalid records,
	 * discarded data fields, etc.
	 */
	@FXML private void displayImportSummary() {

		Parent root = null;
		ImportSummaryController summaryController = null;
		try {
			// Try to load the import summary window.
			FXMLLoader fxmlLoader = new FXMLLoader(CamsApplication.class.getClassLoader().getResource("import-summary.fxml"));
			root = fxmlLoader.load();
			summaryController = fxmlLoader.getController();
		} catch (IOException e) {
			// If failed, abort
			importMessage.setText("Unable to open summary.");
			summaryButton.setDisable(true);
			return;
		}

		// Close the import window
		Stage stage = (Stage) summaryButton.getScene().getWindow();
		stage.close();

		// Initialise summary controller with summary information
		summaryController.setNumSuccessful(numSuccessful);
		summaryController.setTotalRecords(totalToImport);
		summaryController.setDiscardedFields(discardedFields);
		summaryController.setInvalidRecords(invalidRecords);
		summaryController.setDuplicateRecords(duplicateRecords);

		// Open import summary window
		stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("Import Summary");
		stage.setScene(new Scene(root, 400, 300));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/CAMS_logo.png")));
		stage.show();

	}

	/**
	 * Adds an ArrayList of crime records to the database.
	 * @param records An ArrayList of crime records to add to the database.
	 */
	private void addToDatabase(ArrayList<CrimeRecord> records) {
		//Change loading bar
		status = ImportingStatus.POPULATING_DATABASE;
		numImported = 0;
		totalToImport = records.size();
		updateProgress();

		//Loop through records in blocks
		int start = 0;
		int end;
		while(start < records.size()) {
			end = Math.min(records.size(), start + RECORDS_PER_IMPORT);

			//Add records to database
			DBMS.addRecords(records.subList(start, end));
			numImported = end;

			//Update progress
			updateProgress();

			start = end;
		}

		DBMS.getActiveData().updateActiveRecords();
		DBMS.getActiveData().updateActiveData();
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

			// Get summary info from importer
			discardedFields = importer.getDiscardedFields();
			invalidRecords = importer.getInvalidRecords();
			numSuccessful = numImported;

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
