package seng202.group2.controller.viewcontollers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
 * TODO populate the Table View
 * TODO connect each button to their associated method.
 * TODO Implement data searching.
 */
public class MainController extends DataObserver implements Initializable {
	/** The variable used to retrieve user input into the search text field. */
	@FXML private TextField searchTextField;


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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DBMS.getActiveData().addObserver(this);

		idColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Integer>("ID"));
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

	@Override
	public void updateModel(ArrayList<CrimeRecord> activeRecords)
	{
		System.out.println("Attempted update");

		tableView.getItems().removeAll();
		for (CrimeRecord record: activeRecords)
			tableView.getItems().add(record);
	}
}
