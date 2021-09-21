package seng202.group2.view;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seng202.group2.controller.DataObserver;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

import java.lang.reflect.Array;
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
 *
 */

public class MapController implements Initializable {

    /** WebView hold the visualization of a map.html. */
    @FXML private WebView webView;
    //private WebEngine webEngine = webView.getEngine();

    private ArrayList<CrimeRecord> activeRecords = DBMS.getActiveData().getActiveRecords(0, 5);

    public void initialize(URL location, ResourceBundle resources) {
        webView.getEngine().load(CamsApplication.class.getClassLoader().getResource("map.html").toExternalForm());

        WebEngine webEngine = webView.getEngine();

        webEngine.executeScript(
                "    let map;\n" +
                        "\n" +
                        "    function initMap() {\n" +
                        "      const myLatLng = { lat: -25.363, lng: 131.044 };\n" +
                        "      const map = new google.maps.Map(document.getElementById(\"map\"), {\n" +
                        "        zoom: 4,\n" +
                        "        center: myLatLng,\n" +
                        "      });\n" +
                        "\n" +
                        "        const image = {\n" +
                        "    url: \"https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png\",\n" +
                        "    // This marker is 20 pixels wide by 32 pixels high.\n" +
                        "    size: new google.maps.Size(20, 32),\n" +
                        "    // The origin for this image is (0, 0).\n" +
                        "    origin: new google.maps.Point(0, 0),\n" +
                        "    // The anchor for this image is the base of the flagpole at (0, 32).\n" +
                        "    anchor: new google.maps.Point(0, 32),\n" +
                        "  };\n" +
                        "\n" +
                        "      new google.maps.Marker({\n" +
                        "        position: myLatLng,\n" +
                        "        map,\n" +
                        "        title: \"Hello World!\",\n" +
                        "        icon: image,\n" +
                        "        crimeID: 1,\n" +
                        "      });\n" +
                        "    }\n");

    }



}
