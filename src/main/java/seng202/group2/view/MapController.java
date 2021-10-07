package seng202.group2.view;

import com.sun.javafx.webkit.WebConsoleListener;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.robot.Robot;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import netscape.javascript.JSObject;
import seng202.group2.controller.DataObserver;
import seng202.group2.model.ActiveData;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.view.graphs.GraphConfigurationDialogController;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

/**
 * Map Controller is the controller class for the Map GUI
 *
 * This class uses the 'map.fxml' FXML file for laying it out in JavaFX
 * and map.html for displaying the Google Map API in JavaFX's WebView Element.
 * This GUI provides a mapping visualisation of the current ActiveData.
 *
 *  @author Yiyang Yu
 *  @author Connor Dunlop
 *  @author Moses Wescombe
 *  @author George Hampton
 */

public class MapController extends DataObserver implements Initializable {
    /** JavaFX's WebView Element hold the visualization of a map.html. */
    @FXML private WebView webView;
    @FXML private Label radiusSliderLabel;
    @FXML private Slider radiusSlider;

    /** WebEngine is a non-visual object to support web page managements
     *  and enable two-way communication between a Java application and JavaScript
     *  */
    private WebEngine webEngine;

    /** The stage that the map window is created on*/
    private Stage stage;

    /**
     * Initialize the map window
     *
     * This method prepares the Map Window in the UI. It does the following preparations:
     *  - Add an observer to the activeData, which will be displayed as markers on the map.
     *  - Use webEngine to provide access to the document object model of the web page map.html
     */
//    public void initialize(URL location, ResourceBundle resources) {
//        webEngine = webView.getEngine();
//
//        try {
//          File map = new File(CamsApplication.class.getClassLoader().getResource("map.html").getFile());
//          String content = new String(Files.readAllBytes(map.toPath()));
//          Dotenv dotenv = Dotenv.load();
//          content = content.replaceFirst("API_KEY_MATCHER", dotenv.get("API_KEY"));
//          webEngine.loadContent(content);
//          // Forwards console.log() output from any javascript to System.out
//          WebConsoleListener.setDefaultListener((webView, message, lineNumber, sourceId) -> {
//            System.out.println(message + "[at " + lineNumber + "]");
//          });
//        } catch (IOException e) {
//          System.out.println(e.getMessage());
//        }
//
//        // Wait until javascript in map.html has loaded before trying to call functions from there
//        webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
//            if (newState == Worker.State.SUCCEEDED) {
//                Dotenv dotenv = Dotenv.load();
//                //setApiKey(dotenv.get("API_KEY"));
//                updateRadius();
//                activeDataUpdate();
//            }
//        });
//
//        radiusSlider.valueChangingProperty().addListener((obs, oldVal, newVal) -> {
//            updateRadius();
//        });
//
//        //Connect javascript to this Java class so that it can call methods
//        JSObject win = (JSObject) webEngine.executeScript("window");
//        win.setMember("app", this);
//    }

//    /**
//     * Set API key from local .env file
//     * @param key - Google Map API Key used for SENG202 Team 2 Project
//     */
//    private void setApiKey(String key){
//        webEngine.executeScript("setApiKey('"+ key +"');");
//    }

    public void initialize(URL location, ResourceBundle resources) {
      webEngine = webView.getEngine();

      InputStream is = getClass().getResourceAsStream("/map.html");
      String content = new BufferedReader(
        new InputStreamReader(is, StandardCharsets.UTF_8))
        .lines()
        .collect(Collectors.joining("\n"));
      Dotenv dotenv = Dotenv.load();
      content = content.replaceFirst("API_KEY_MATCHER", dotenv.get("API_KEY"));
      webEngine.loadContent(content);
      // Forwards console.log() output from any javascript to System.out
      WebConsoleListener.setDefaultListener((webView, message, lineNumber, sourceId) -> {
        System.out.println(message + "[at " + lineNumber + "]");
      });

      // Wait until javascript in map.html has loaded before trying to call functions from there
      webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
        if (newState == Worker.State.SUCCEEDED) {
          updateRadius();
          activeDataUpdate();
        }
      });

      radiusSlider.valueChangingProperty().addListener((obs, oldVal, newVal) -> {
        updateRadius();
      });

      //Connect javascript to this Java class so that it can call methods
      JSObject win = (JSObject) webEngine.executeScript("window");
      win.setMember("app", this);
    }


  /**
     * Toggle the markers on and off
     */
    public void toggleMarkers() {
        webEngine.executeScript("toggleMarkers();");
    }

    /**
     * Toggle the heatmap on and off
     */
    public void toggleHeatMap() {
        webEngine.executeScript("toggleHeatmap();");
    }

    /**
     * Toggle the street names on and off
     */
    public void toggleStreetNames() {
        webEngine.executeScript("toggleStreetNames();");
    }

    /**
     * Toggle the animations on and off
     */
    public void toggleAnimations() {
        webEngine.executeScript("toggleAnimations();");
    }

    /**
     * Update heatmap radius
     */
    public void updateRadius() {
        int radius = (int) radiusSlider.getValue();
        radiusSliderLabel.setText("Heatmap Radius: " + radius);
        webEngine.executeScript("changeRadius(" + radius +");");
    }

    /**
     * Adds a marker to the map for a particular CrimeRecord.
     * The marker is placed at the latitude and longitude stored by the CrimeRecord, where the crime occurred.
     * @param record The record for which to add a map marker.
     */
    public void addMarker(CrimeRecord record, String color) {
        webEngine.executeScript(
                "addMarker(" + record.getID() + "," + record.getLatitude() + "," + record.getLongitude() + ",'" + color + "');"
        );
    }

    /**
     * Select a record by ID
     *
     * @param id - ID of record to select
     */
    public void selectRecord(int id) {
        DBMS.getActiveData().selectRecord(id);
        DBMS.getActiveData().updateSelectionObservers();
    }

    /**
     * Deselect a record by ID
     *
     * @param id - ID of record to deselect
     */
    public void deselectRecord(int id) {
        DBMS.getActiveData().deselectRecord(id);
        DBMS.getActiveData().updateSelectionObservers();
    }

    /**
     * Removes a marker corresponding to a particular CrimeRecord from the map.
     * @param record The record whose map marker is to be removed.
     */
    public void removeMarker(CrimeRecord record) {
        webEngine.executeScript(
                "removeMarker(" + record.getID() + ");"
        );
    }

    /**
     * Removes all markers from the map.
     */
    public void clearMarkers() {
        webEngine.executeScript(
                "clearMarkers();"
        );
    }

    /**
     * Sets the boundary of the map to display all markers in view
     */
    public void setBounds() {
        webEngine.executeScript(
                "setBoundsJS();"
        );
    }

    /**
     * Update active data
     */
    @Override
    public void activeDataUpdate() {
        ActiveData activeData = DBMS.getActiveData();

        //Get active data from frame
        ArrayList<CrimeRecord> activeRecords = activeData.getActiveRecords();

        // Remove all markers from the map, then add markers for all currently not selected active records
        clearMarkers();
        for (CrimeRecord record : activeRecords) {
            //Prevent null location records
            if (record.getLatitude() != null && record.getLongitude() != null) {
                if (!DBMS.getActiveData().isSelected(record.getID()))
                    addMarker(record, "red");
            }
        }

        //Add selected markers
        for (Integer selectedRecord : activeData.getSelectedRecords()) {
            CrimeRecord record = DBMS.getRecord(selectedRecord);
            if (record != null)
                //Prevent null location records
                if (record.getLatitude() != null && record.getLongitude() != null) {
                    addMarker(record, "blue");
                }
        }

        // Adjust the boundary of the map based on the activeData and their marker positions
        setBounds();
    }

    /**
     * Update selected markers
     */
    @Override
    public void selectedRecordsUpdate() {
        //Deselect all markers
        webEngine.executeScript(
                "deselectAllMarkers();"
        );

        //Select markers
        for (Integer selectedRecord : DBMS.getActiveData().getSelectedRecords()) {
            webEngine.executeScript(
                    "selectMarker(" + selectedRecord + ");"
            );
        }
    }

    /**
     * Update markers when the frame changes
     */
    @Override
    public void frameUpdate() {
        activeDataUpdate();
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

    /**
	 * This showExportWindow method opens the export window and brings it to the front.
	 * It allows the user to export the current map window as a visual.
	 */
	public void showExportWindow() {

        if (stage == null) {
            showExportErrorDialogue();
            return;
        }

		/*Currently this section assumes that the stage exists (i.e. to click the export button
		 * the window must be opened.
		 *Find the edges of the window. These are rounded to give the equivalent of integer values.
		 */
		double x = Math.floor(stage.getX());
		double y = Math.floor(stage.getY());
		double width = Math.floor(stage.getWidth());
		double height = Math.floor(stage.getHeight());

		//Set the bounds of the area to select.
		Rectangle2D bounds = new Rectangle2D(x + 5, y + 90, width - 70, height - 120);

		//Select the given area and create an image
		javafx.scene.robot.Robot robot = new Robot();
		WritableImage exportVisual = robot.getScreenCapture(null, bounds);

		//Save the image
		//Create a save dialog
		FileChooser saveChooser = new FileChooser();
		saveChooser.setTitle("Save Image");
		FileChooser.ExtensionFilter saveTypes = new FileChooser.ExtensionFilter("image files (*.png)", "*.png");
		saveChooser.getExtensionFilters().add(saveTypes);

		Stage stage = new Stage();
		File save = saveChooser.showSaveDialog(stage);

		//Check filename is not null and save file
		if(save != null) {
			String saveName = save.getName();
			//check whether user put ".png" on the filename
			if(!saveName.toUpperCase().endsWith(".PNG")) {
				save = new File(save.getAbsolutePath() + ".png");
			}

			//Write to the file
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(exportVisual, null), "png", save);
			} catch(IOException e) {
				// This is where you would enter the error handling code, for now just print the stacktrace
				e.printStackTrace();
			}
		}
	}

	/**
	 * Stores the stage that the map is drawn on.
	 * @param stage The stage this map is drawn on.
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
