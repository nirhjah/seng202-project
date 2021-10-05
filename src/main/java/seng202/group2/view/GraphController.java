package seng202.group2.view;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.robot.Robot;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group2.controller.DataObserver;
import seng202.group2.view.graphs.Graph;
import seng202.group2.view.graphs.Plottable;


/**
 * Graph Controller class which controls the data to be displayed on the graph, which type of graph to use and the categories used for the axis.
 * @author nse41
 * @author Connor Dunlop
 * @author George Hampton
 * @author Yiyang Yu
 */

public class GraphController extends DataObserver {

	@FXML private BorderPane graphPane;
	@FXML private BorderPane controlPane;
	@FXML private VBox optionList = new VBox();

	private Map<String, Graph> graphTypes = new HashMap<>();
	@FXML private ComboBox<String> graphTypeSelector;

	private Graph graph;
	/** The stage the graph window exists in*/
	private Stage stage;

	/**
	 * Initialize method
	 */
	public void initialize() {
		for (Graph graphType : Graph.getGraphTypes()) {
			if (!(graphType instanceof Plottable))
				continue;
			graphTypes.put(graphType.getName(), graphType);
		}

		ArrayList<String> sortedGraphTypes = new ArrayList<String>(graphTypes.keySet());
		sortedGraphTypes.sort((i, j) -> {
			return i.toString().compareTo(j.toString());
		});

		//Default text on the graph window instructing to please select a graph to plot first
		if (graphTypeSelector.getValue() == null) {
      String centerText = "No graph to generate. Please select a graph to plot in the control panel on the left.";
      graphPane.setCenter(new Text(centerText));
    }

		graphTypeSelector.getItems().addAll(sortedGraphTypes);
	}

	@FXML public void selectGraphType() {
		try {
			graph = Graph.newGraph(graphTypes.get(graphTypeSelector.getSelectionModel().getSelectedItem()));
		} catch (InstantiationException e) {
			e.printStackTrace();
			return;
		}

		graphPane.setCenter(null);

		optionList.getChildren().clear();
		graph.initialize(graphPane, optionList);
	}

	@FXML public void hideControlMenu() {
		controlPane.setVisible(false);
	}

	@FXML public void showControlMenu() {
		controlPane.setVisible(true);
	}

	@FXML public void toggleControlMenu() {
		if (controlPane.isVisible())
			hideControlMenu();
		else
			showControlMenu();
	}

	@FXML public void plotGraph() {
		if (graph != null)
			graph.plotGraph();
	}

	public void showExportErrorDialogue() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(CamsApplication.class.getClassLoader().getResource("export-unsupported-dialog.fxml"));
			Parent root = fxmlLoader.load();

			Stage stage = new Stage();

			// Prevent use of the application while dialogue is open.
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);

			stage.setTitle("Exporting not Supported in Tabbed Views.");
			stage.setScene(new Scene(root, 400, 300));

			stage.show();
		} catch (IOException e) {
			// This is where you would enter the error handling code, for now just print the stacktrace
			e.printStackTrace();
		}
	};

	// TODO Hide graph options pane when taking a screencap
	@FXML public void showExportWindow() {

		if (stage == null) {
			showExportErrorDialogue();
			return;
		}

		/*
		 * Currently this section assumes that the stage exists (i.e. to click the export button
		 * the window must be opened.
		 * Find the edges of the window. These are rounded to give the equivalent of integer values.
		 */
		double x = Math.floor(stage.getX());
		double y = Math.floor(stage.getY());
		double width = Math.floor(stage.getWidth());
		double height = Math.floor(stage.getHeight());

		//Set the bounds of the area to select.
		Rectangle2D bounds = new Rectangle2D(x + 10, y + 31, width - 20, height - 33);

		//Select the given area and create an image
		javafx.scene.robot.Robot robot = new Robot();
		WritableImage exportVisual = robot.getScreenCapture(null, bounds);

		//Save the image
		//Create a save dialog
		FileChooser saveChooser = new FileChooser();
		saveChooser.setTitle("Save Image");
		FileChooser.ExtensionFilter saveTypes = new FileChooser.ExtensionFilter("image files (*.png)", "*.png");
		saveChooser.getExtensionFilters().add(saveTypes);

		Stage saveStage = new Stage();
		saveStage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/CAMS_logo.png")));
		File save = saveChooser.showSaveDialog(saveStage);

		//Check filename is not null and save file
		if (save != null) {
			String saveName = save.getName();
			//check whether user put ".png" on the filename
			if (!saveName.toUpperCase().endsWith(".PNG")) {
				save = new File(save.getAbsolutePath() + ".png");
			}

			//Write to the file
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(exportVisual, null), "png", save);
			} catch (IOException e) {
				// This is where you would enter the error handling code, for now just print the stacktrace
				e.printStackTrace();
			}
		}
	}

	@Override
	public void activeDataUpdate() {
		plotGraph();
	}

	@Override
	public void selectedRecordsUpdate() {
		return;
	}

	@Override
	public void frameUpdate() {
		activeDataUpdate();
	}

	/**
	 * Used to define for this class the window in which it exists
	 * @param stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
