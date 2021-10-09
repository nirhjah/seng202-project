package seng202.group2.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.model.ActiveData;
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
 */
public class MainController extends DataObserver implements Initializable {
	/** The variable used to retrieve user input into the search text field. */
	@FXML private TextField searchTextField;

	//FXML fields
	@FXML private TextField windowSize;
	@FXML private Text recordsShown;
	@FXML private SplitPane splitPane;
	@FXML private TabPane tabPane;
	@FXML private Tab mapTab;
	@FXML private Tab tableTab;
	@FXML private Tab graphTab;

	//View controllers
	@FXML private MapController mapController;
	@FXML private TableController tableController;
	@FXML private GraphController graphController;

	/**
	 * Update window size when a new size is entered into windowSize textField.
	 */
	public void updateWindowSize() {
		try {
			DBMS.getActiveData().updateFrameSize(Integer.parseInt(windowSize.getText()));
		} catch (NumberFormatException e) {
			System.out.println("Value " + windowSize.getText() + " is not an integer.");
			windowSize.setText(Integer.toString(DBMS.getActiveData().getFrameSize()));
		}
	}

	/**
	 * Cycle to the next set of data
	 */
	public void recordsScrollNext() {
		DBMS.getActiveData().incrementFrame();
	}

	/**
	 * Cycle to the previous set of data
	 */
	public void recordsScrollPrev() {
		DBMS.getActiveData().decrementFrame();
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
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/CAMS_logo.png")));
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
	 * Toggle map tab visible, called when tabs are changed
	 */
	public void toggleMapTab() {
		if (mapTab.isSelected()) {
			//Update map window
			ActiveData activeData =  DBMS.getActiveData();
			activeData.addObserver(mapController);
			mapController.frameUpdate();
		} else {
			DBMS.getActiveData().removeObserver(mapController);
		}
	}

	/**
	 * Toggle map tab visible, called when tabs are changed
	 */
	public void toggleTableTab() {
		if (tableTab.isSelected()) {
			//Update table window
			ActiveData activeData =  DBMS.getActiveData();
			activeData.addObserver(tableController);
			tableController.frameUpdate();
		} else {
			DBMS.getActiveData().removeObserver(tableController);
		}
	}

	/**
	 * Toggle graph tab visible, called when tabs are changed
	 */
	public void toggleGraphTab() {
		if (graphTab.isSelected()) {
			//Update graph window
			ActiveData activeData =  DBMS.getActiveData();
			activeData.addObserver(graphController);
			graphController.frameUpdate();
		} else {
			DBMS.getActiveData().removeObserver(graphController);
		}
	}


	/**
	 * This showMapWindow method opens the map window and brings it to the front.
	 */
	public void showMapWindow() {
		try {
			//Get Javafx
			FXMLLoader loader = new FXMLLoader(CamsApplication.class.getClassLoader().getResource("map.fxml"));
			Parent root = (Parent) loader.load();
			MapController mapWindowController = (MapController)loader.getController();
			Stage stage = new Stage();
			stage.setTitle("Map Window");
			stage.setScene(new Scene(root, 900, 600));
			stage.show();

			mapWindowController.setStage(stage);

			DBMS.getActiveData().addObserver(mapWindowController);
			//Reopen map tab on close
			stage.setOnCloseRequest(event -> {
				DBMS.getActiveData().removeObserver(mapWindowController);
				mapTab.setDisable(false);
				toggleMapTab();
			});

			//Disable map tab
			tabPane.getSelectionModel().select(tableTab);
			mapTab.setDisable(true);
			toggleMapTab();

		} catch (IOException e) {
			// This is where you would enter the error handling code, for now just print the stacktrace
			mapTab.setDisable(false);
			e.printStackTrace();
		}
	}

	/**
	 * This showGraphWindow method opens the graph window and brings it to the front.
	 *
	 * The graph window uses the 'graph.fxml' FXML file and the GraphController Class
	 * @see GraphController
	 */
	public void showGraphWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(CamsApplication.class.getClassLoader().getResource("graph.fxml"));

			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Graph Window");
			stage.setScene(new Scene(root, 1280, 720));

			GraphController graphWindowController = (GraphController) loader.getController();
			graphWindowController.setStage(stage);

			DBMS.getActiveData().addObserver(graphWindowController);
			stage.setOnCloseRequest(event -> {
				DBMS.getActiveData().removeObserver(graphWindowController);
			});

			stage.show();

			//Reopen map tab on close
			stage.setOnCloseRequest(event -> {
				DBMS.getActiveData().removeObserver(graphWindowController);
				graphTab.setDisable(false);
				toggleGraphTab();
			});

			//Disable map tab
			tabPane.getSelectionModel().select(tableTab);
			graphTab.setDisable(true);
			toggleMapTab();
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
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(CamsApplication.class.getClassLoader().getResource("add_edit.fxml"));
			fxmlLoader.setController(new AddRecordController());
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			// This will cause the login window to always be in front of the main window
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setTitle("Add record Window");
			stage.setScene(new Scene(root, 400, 600));
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/CAMS_logo.png")));
			stage.show();
		} catch (IOException e) {
			// This is where you would enter the error handling code, for now just print the stacktrace
			e.printStackTrace();
		}
	}

	/**
	 * showFilterWindow method opens the filter window and brings it to the front.
	 *
	 * The filter window uses the 'filter.fxml' FXML file and the FilterController Class
	 * @see FilterController
	 */
	public void showFilterWindow() {
		double[] current = splitPane.getDividerPositions();
		splitPane.setDividerPositions(current[0] > 0.9 ? 0.5 : 1);
	}

	/**
	 * This method is called when the delete button is clicked on the CAMS main window.
	 *
	 * The 'delete records' window uses the 'deleteRecords.fxml' FXML file and the DeleteRecordsController Class
	 * The 'delete records' window simply gets the user to confirm deletion and tells them how many records are selected.
	 *
	 * @see DeleteRecordsController
	 */
	public void openDeleteRecordsWindow() {
		try {
			Parent root = FXMLLoader.load(CamsApplication.class.getClassLoader().getResource("deleteRecords.fxml"));
			Stage stage = new Stage();
			// This will cause the login window to always be in front of the main window
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setTitle("Delete Records");
			stage.setScene(new Scene(root, 400, 200));
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/CAMS_logo.png")));

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
	public void showNotImplementedYet() {
		try {
			Parent root = FXMLLoader.load(CamsApplication.class.getClassLoader().getResource("unimplemented.fxml"));
			Stage stage = new Stage();
			// This will cause the login window to always be in front of the main window
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setTitle("Unimplemented Window");
			stage.setScene(new Scene(root, 400, 200));
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/CAMS_logo.png")));

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

		updateText();
	}

	private void updateText() {
		ActiveData activeData =  DBMS.getActiveData();
		int min = activeData.getCurrentMin();
		int max = activeData.getCurrentMax();
		int total = activeData.getRecordCount();
		int size = activeData.getFrameSize();

		recordsShown.setText(min + "-" + max + "/" + total);
		windowSize.setText(Integer.toString(size));
	}

	@Override
	public void activeDataUpdate() {
		DBMS.getActiveData().updateFrameSize(Integer.parseInt(windowSize.getText()));
		updateText();
	}

	@Override
	public void selectedRecordsUpdate() {
		updateText();
	}

	@Override
	public void frameUpdate() {
		updateText();
	}

	public SplitPane getSplitPane() {
		return splitPane;
	}
}
