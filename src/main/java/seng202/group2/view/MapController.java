package seng202.group2.view;

import com.sun.javafx.webkit.WebConsoleListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.WindowEvent;
import netscape.javascript.JSObject;
import seng202.group2.controller.DataObserver;
import seng202.group2.model.ActiveData;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

    /**
     * Initialize the map window
     *
     * This method prepares the Map Window in the UI. It does the following preparations:
     *  - Add an observer to the activeData, which will be displayed as markers on the map.
     *  - Use webEngine to provide access to the document object model of the web page map.html
     */
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        webEngine.load(CamsApplication.class.getClassLoader().getResource("map.html").toExternalForm());

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
                "setBounds();"
        );
    }

    /**
     * Update active data
     */
    @Override
    public void activeDataUpdate() {
        ActiveData activeData = DBMS.getActiveData();

        //Get active data from frame
        ArrayList<CrimeRecord> activeRecords = activeData.getActiveRecords(activeData.getCurrentMin(), activeData.getFrameSize());

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
    public void frameUpdate(int min, int max, int size, int total) {
        activeDataUpdate();
    }
}
