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

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Map Controller
 * @author Yiyang Yu
 *
 */

public class MapController implements Initializable {

    /** The center panel that hold the visualization of a map. */
    @FXML private AnchorPane mapPane;
    @FXML private WebView webView;
    //private webEngine webEngine;

   // webView.getEngine().load("http://google.com");

    public void initialize(URL location, ResourceBundle resources) {
        webView.getEngine().load("http://google.com");

    }



//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("JavaFX WebView Example");
//
//        WebView webView = new WebView();
//
//        webView.getEngine().load("http://google.com");
//
//        VBox vBox = new VBox(webView);
//        Scene scene = new Scene(vBox, 960, 600);
//
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//    }


}
