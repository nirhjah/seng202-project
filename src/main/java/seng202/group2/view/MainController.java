package seng202.group2.view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.controller.CSVImporter;
import seng202.group2.controller.DataImporter;
import seng202.group2.model.CrimeRecord;
import seng202.group2.controller.DataObserver;
import seng202.group2.model.DBMS;

/**
 * MainController is the GUI controller for the main Cams window.
 *
 * This controller controls the main.fxml file.
 * The window consists of a TableView of the active crime data, a search bar, and buttons for
 * importing data, exporting, mapping, graphing filtering and adding data.
 *
 * MainController implements {@link Initializable} for setting up TableColumn values
 *
 * @author Sam Clark
 * TODO connect each button to their associated method.
 * TODO Implement data searching.
 */
public class MainController extends DataObserver implements Initializable {
	/** The variable used to retrieve user input into the search text field. */
	@FXML private TextField searchTextField;

	//Variables used to control page(window) size
	private int windowSizeInt = 200;
	private int recordCount = 0;
	private int currentMin = 0;
	private int currentMax = windowSizeInt;
	@FXML private TextField windowSize;
	@FXML private Text recordsShown;

	//Table
	@FXML private TableView<CrimeRecord> tableView;
	@FXML private TableColumn<CrimeRecord, Integer> idColumn;
	@FXML private TableColumn<CrimeRecord, String> caseNumColumn;
	@FXML private TableColumn<CrimeRecord, String> dateColumn;
	@FXML private TableColumn<CrimeRecord, String> blockColumn;
	@FXML private TableColumn<CrimeRecord, String> iucrColumn;
	@FXML private TableColumn<CrimeRecord, String> primaryDescriptionColumn;
	@FXML private TableColumn<CrimeRecord, String> secondaryDescriptionColumn;
	@FXML private TableColumn<CrimeRecord, String> locationDescriptionColumn;
	@FXML private TableColumn<CrimeRecord, Boolean> arrestColumn;
	@FXML private TableColumn<CrimeRecord, Boolean> domesticColumn;
	@FXML private TableColumn<CrimeRecord, Short> beatColumn;
	@FXML private TableColumn<CrimeRecord, Short> wardColumn;
	@FXML private TableColumn<CrimeRecord, Short> fbiCodeColumn;
	@FXML private TableColumn<CrimeRecord, Short> latitudeColumn;
	@FXML private TableColumn<CrimeRecord, Short> longitudeColumn;

	/**
	 * Update the current page of records. This displays a subset of the active data.
	 */
	private void recordsUpdate() {
		ArrayList<CrimeRecord> activeRecords = new ArrayList<>(DBMS.getActiveData().getActiveRecords(currentMin, currentMax));

		//Change the text
		recordsShown.setText(currentMin + "-" + currentMax + "/" + recordCount);


		//Update table
		tableView.getItems().clear();
		for (CrimeRecord record: activeRecords)
			tableView.getItems().add(record);
	}

	/**
	 * Update window size when a new size is entered into windowSize textField.
	 */
	public void updateWindowSize() {
		windowSizeInt = Integer.parseInt(windowSize.getText());
		currentMax = currentMin + windowSizeInt;
		recordsUpdate();
	}

	/**
	 * Cycle to the next set of data
	 */
	public void recordsScrollNext() {
		currentMax = Math.min(currentMax + windowSizeInt, recordCount);
		currentMin = Math.min(currentMin + windowSizeInt, recordCount - windowSizeInt);
		recordsUpdate();
	}

	/**
	 * Cycle to the previous set of data
	 */
	public void recordsScrollPrev() {
		currentMin = Math.max(currentMin - windowSizeInt, 0);
		currentMax = Math.max(currentMax - windowSizeInt, Math.min(recordCount, windowSizeInt));
		recordsUpdate();
	}


	/**
	 * showImportWindow method opens the import window and brings it to the front.
	 *
	 * The import window uses the 'import.fxml' FXML file and the ImportController Class
	 * @see ImportController
	 */
	public void showImportWindow() {
		try {
			Parent root = FXMLLoader.load(CamsApplication.class.getClassLoader().getResource("import.fxml"));
			Stage stage = new Stage();
			// This will cause the login window to always be in front of the main window
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setTitle("Import Window");
			stage.setScene(new Scene(root, 400, 200));
			stage.show();
		} catch (IOException e) {
			// This is where you would enter the error handling code, for now just print the stacktrace
			e.printStackTrace();
		}
	}

	/**
	 * This showExportWindow method opens the export window and brings it to the front.
	 *
	 * TODO This method is not yet implemented. Temporarily it is calling {@link MainController#showNotImplementedYet()}
	 */
	public void showExportWindow() {
		showNotImplementedYet();
	}

	/**
	 * This showMapWindow method opens the map window and brings it to the front.
	 *
	 * TODO This method is not yet implemented. Temporarily it is calling {@link MainController#showNotImplementedYet()}
	 */
	public void showMapWindow() {
		showNotImplementedYet();
	}

	/**
	 * This showGraphWindow method opens the graph window and brings it to the front.
	 *
	 * TODO This method is not yet implemented. Temporarily it is calling {@link MainController#showNotImplementedYet()}
	 */
	public void showGraphWindow() {
		showNotImplementedYet();
	}

	/**
	 * This showAddRecordWindow method opens the addRecord window and brings it to the front.
	 *
	 * TODO This method is not yet implemented. Temporarily it is calling {@link MainController#showNotImplementedYet()}
	 */
	public void showAddRecordWindow() {
		showNotImplementedYet();
	}

	/**
	 * This showNotImplementedYet method opens the unimplemented window and brings it to the front.
	 *
	 * The unimplemented window tells the user graphically that a feature they have interacted with is not implemented yet.
	 * Features that not yet implemented call this method to avoid bugs. No complete feature should call this method.
	 * The unimplemented window uses the 'unimplemented.fxml' FXML file and the UnimplementedController Class
	 * @see UnimplementedController
	 */
	public void showNotImplementedYet()
	{
		try {
			Parent root = FXMLLoader.load(CamsApplication.class.getClassLoader().getResource("unimplemented.fxml"));
			Stage stage = new Stage();
			// This will cause the login window to always be in front of the main window
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setTitle("Unimplemented Window");
			stage.setScene(new Scene(root, 400, 200));
			stage.show();
		} catch (IOException e) {
			// This is where you would enter the error handling code, for now just print the stacktrace
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the window.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DBMS.getActiveData().addObserver(this);

		recordsShown.setText(currentMin + "-" + currentMax + "/" + recordCount);
		windowSize.setText(Integer.toString(windowSizeInt));

		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		idColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Integer>("ID"));
		// Format a row in the table based on the value in its ID cell
		idColumn.setCellFactory(column -> {
			TableCell<CrimeRecord, Integer> cell = new TableCell<>() {
				@Override
				public void updateItem(Integer id, boolean empty) {
					super.updateItem(id, empty);

					// If the id is not null set the cell text to the id
					setText(id == null ? "" : id.toString());

					// If the record in this row has been selected
					if (DBMS.getActiveData().getSelectedRecords().contains(id))
						// Set the row as selected
						tableView.getSelectionModel().select(getTableRow().getIndex());

					// Add a listener to the table row which (de)selects the record when clicked
					getTableRow().setOnMouseReleased(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							if (id != null)
								DBMS.getActiveData().toggleSelectRecord(id);
						}
					});
				}
			};
			return cell;
		});

		caseNumColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("caseNum"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("dateString"));
		blockColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("block"));
		iucrColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("iucr"));
		primaryDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("primaryDescription"));
		secondaryDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("secondaryDescription"));
		locationDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("locationDescription"));
		arrestColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Boolean>("arrest"));
		domesticColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Boolean>("domestic"));
		beatColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Short>("beat"));
		wardColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Short>("ward"));
		fbiCodeColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Short>("fbiCode"));
		latitudeColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Short>("latitude"));
		longitudeColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Short>("longitude"));
	}

	/**
	 * Update the model when the observer is called.
	 *
	 * @param activeRecords -- ActiveData.getActiveRecords() - List of records to update the model with
	 */
	@Override
	public void updateModel(ArrayList<CrimeRecord> activeRecords, HashSet<Integer> selectedRecords)
	{
		activeRecords = new ArrayList<>(activeRecords.subList(0, Math.min(windowSizeInt, activeRecords.size())));

		//Change the number of records
		recordCount = DBMS.getActiveData().getActiveRecords().size();
		recordsShown.setText(currentMin + "-" + currentMax + "/" + recordCount);

		//Update table
		tableView.getItems().clear();
		for (CrimeRecord record: activeRecords)
			tableView.getItems().add(record);
	}
}
