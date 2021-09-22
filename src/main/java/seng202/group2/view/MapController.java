package seng202.group2.view;


import com.sun.javafx.webkit.WebConsoleListener;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
}
