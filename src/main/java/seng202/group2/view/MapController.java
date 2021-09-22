package seng202.group2.view;


import com.sun.javafx.webkit.WebConsoleListener;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import seng202.group2.controller.DataObserver;
import seng202.group2.model.ActiveData;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
 *  @author George Hampton
 */

public class MapController extends DataObserver implements Initializable {

    /** WebView hold the visualization of a map.html. */
    @FXML private WebView webView;
    private WebEngine webEngine;

    public void initialize(URL location, ResourceBundle resources) {
        DBMS.getActiveData().addObserver(this);

        webEngine = webView.getEngine();
        webEngine.load(CamsApplication.class.getClassLoader().getResource("map.html").toExternalForm());

        // Forwards console.log() output from any javascript to System.out
        WebConsoleListener.setDefaultListener((webView, message, lineNumber, sourceId) -> {
            System.out.println(message + "[at " + lineNumber + "]");
        });

        // Wait until javascript in map.html has loaded before trying to call functions from there
        webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                updateModel();
            }
        });
    }

    @Override
    public void updateModel() {
        ActiveData activeData = DBMS.getActiveData();
        ArrayList<CrimeRecord> activeRecords = activeData.getActiveRecords();

        // Remove all markers from the map, then add markers for all active records
        clearMarkers();
        for (CrimeRecord record : activeRecords) {
            if (record.getLatitude() != null && record.getLongitude() != null) {
                addMarker(record);
            }
        }
    }

    /**
     * Adds a marker to the map for a particular CrimeRecord.
     * The marker is placed at the latitude and longitude stored by the CrimeRecord, where the crime occurred.
     * @param record The record for which to add a map marker.
     */
    public void addMarker(CrimeRecord record) {
        webEngine.executeScript(
                "addMarker(" + record.getID() + "," + record.getLatitude() + "," + record.getLongitude() + ");"
        );
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
	 * This showExportWindow method opens the export window and brings it to the front.
	 *
	 * TODO Find a way to make selection area based on the window
	 */
	public void showExportWindow() {
		//Find the edges of the window
		double x = 350;
		double y = 100;
		double width = 900;
		double height = 600;
		
		//Set the bounds of the area to select
		Rectangle2D bounds = new Rectangle2D(x, y, width, height);
		
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
}
