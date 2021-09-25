package seng202.group2.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.controller.CSVImporter;
import seng202.group2.controller.DataImporter;
import javafx.util.Callback;
import seng202.group2.model.ActiveData;
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
	private int windowSizeInt = DBMS.getActiveData().windowSizeInt;
	private int recordCount = DBMS.getActiveData().recordCount;
	private int currentMin = DBMS.getActiveData().currentMin;
	private int currentMax = DBMS.getActiveData().currentMax;
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
	 * Update window size when a new size is entered into windowSize textField.
	 */
	public void updateWindowSize() {
		windowSizeInt = Integer.parseInt(windowSize.getText());
		currentMax = currentMin + windowSizeInt;
		DBMS.getActiveData().updateFrame(currentMin, currentMax, windowSizeInt);
	}

	/**
	 * Cycle to the next set of data
	 */
	public void recordsScrollNext() {
		currentMax = Math.min(currentMax + windowSizeInt, recordCount);
		currentMin = Math.min(currentMin + windowSizeInt, recordCount - windowSizeInt);
		DBMS.getActiveData().updateFrame(currentMin, currentMax, windowSizeInt);
	}

	/**
	 * Cycle to the previous set of data
	 */
	public void recordsScrollPrev() {
		currentMin = Math.max(currentMin - windowSizeInt, 0);
		currentMax = Math.max(currentMax - windowSizeInt, Math.min(recordCount, windowSizeInt));
		DBMS.getActiveData().updateFrame(currentMin, currentMax, windowSizeInt);
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
		try {
			Parent root = FXMLLoader.load(CamsApplication.class.getClassLoader().getResource("map.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Map Window");
			stage.setScene(new Scene(root, 900, 600));
			stage.show();
		} catch (IOException e) {
			// This is where you would enter the error handling code, for now just print the stacktrace
			e.printStackTrace();
		}

		//DBMS.getActiveData().addFilter(FilterType.EQ.createFilter("id", "10"));
	}

	/**
	 * This showGraphWindow method opens the graph window and brings it to the front.
	 *
	 * The graph window uses the 'graph.fxml' FXML file and the GraphController Class
	 * @see GraphController
	 */
	public void showGraphWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();

			Parent root = fxmlLoader.load(CamsApplication.class.getClassLoader().getResource("graph.fxml"));
			GraphController graphController = (GraphController) fxmlLoader.getController();

			Stage stage = new Stage();
			stage.setTitle("Graph Window");
			stage.setScene(new Scene(root, 1280, 720));

			DBMS.getActiveData().addObserver(graphController);
			stage.setOnCloseRequest(event -> {
				DBMS.getActiveData().removeObserver(graphController);
			});

			stage.show();
		} catch (IOException e) {
			// This is where you would enter the error handling code, for now just print the stacktrace
			e.printStackTrace();
		}
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
	 * showFilterWindow method opens the filter window and brings it to the front.
	 *
	 * The filter window uses the 'filter.fxml' FXML file and the FilterController Class
	 * @see FilterController
	 */
	public void showFilterWindow() {
		try {
			Parent root = FXMLLoader.load(CamsApplication.class.getClassLoader().getResource("filter.fxml"));
			Stage stage = new Stage();
			// This will cause the login window to always be in front of the main window
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setTitle("Filters");
			stage.setScene(new Scene(root, 600, 400));
			stage.show();
		} catch (IOException e) {
			// This is where you would enter the error handling code, for now just print the stacktrace
			e.printStackTrace();
		}
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
	 * Called when a click event occurs on the tableView.
	 * Updates the set of selected records in ActiveData using the selection out of currently tabulated records.
	 */
	public void updateSelection() {
		ActiveData activeData = DBMS.getActiveData();

		// Deselect all currently tabulated records
		for (CrimeRecord item : tableView.getItems()) {
			activeData.deselectRecord(item.getID());
		}

		// Select all records currently selected in the table.
		for (CrimeRecord selectedItem : tableView.getSelectionModel().getSelectedItems()) {
			activeData.selectRecord(selectedItem.getID());
		}

		// Tell all observers of ActiveData that the selection has been updated.
		activeData.updateSelectionObservers();
	}

	/**
	 * Initialize the window.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DBMS.getActiveData().addObserver(this);

		recordsShown.setText(currentMin + "-" + currentMax + "/" + recordCount);
		windowSize.setText(Integer.toString(windowSizeInt));

		idColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Integer>("ID"));
		caseNumColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("caseNum"));
		dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CrimeRecord, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<CrimeRecord, String> param) {
				return new ReadOnlyObjectWrapper<>(param.getValue().getDateCategory().getValueString());
			}
		});
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
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		//Import test files
		File file = new File("testfiles/5k.csv");

		try {
			DataImporter importer = new CSVImporter(file);
			DBMS.addRecords(importer.importAllRecords());
			importer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update the model when the observer is called. This will reset the window to show rows 0 - limit
	 */
	@Override
	public void activeDataUpdate() {
		ActiveData activeData = DBMS.getActiveData();
		ArrayList<CrimeRecord> activeRecords = activeData.getActiveRecords(0, windowSizeInt);

		//Change the number of records
		recordCount = DBMS.getActiveRecordsSize();
		recordsShown.setText(0 + "-" + Math.min(windowSizeInt, recordCount) + "/" + recordCount);


		//Update table
		tableView.getItems().clear();
		for (CrimeRecord record: activeRecords)
			tableView.getItems().add(record);

		selectedRecordsUpdate();
	}

	@Override
	public void selectedRecordsUpdate() {
		tableView.getSelectionModel().clearSelection();

		ActiveData activeData = DBMS.getActiveData();
		for (CrimeRecord record: tableView.getItems()) {
			if (activeData.isSelected(record.getID()))
				tableView.getSelectionModel().select(record);
		}
	}

	/**
	 * Update the window frame size
	 */
	public void frameUpdate() {
		//Get records in frame range
		ArrayList<CrimeRecord> activeRecords = new ArrayList<>(DBMS.getActiveData().getActiveRecords(currentMin, windowSizeInt));

		//Change the text
		recordsShown.setText(Math.max(currentMin, 0) + "-" + Math.min(currentMax, recordCount) + "/" + recordCount);

		//Update table
		tableView.getItems().clear();
		for (CrimeRecord record: activeRecords) {
			tableView.getItems().add(record);

			//Select record if required
			if (DBMS.getActiveData().isSelected(record.getID())) {
				tableView.getSelectionModel().select(record);
			}
		}
	}
}
