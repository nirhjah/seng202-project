package seng202.group2.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This Class creates a new Application window, initializing the CAMS GUI.
 *
 * CamsApplication extends JavaFX's Application class to create a new application. The application
 * starts with the main window using main.fxml and the {@link MainController} class.
 *
 * @see javafx.application.Application
 * @author Sam Clark
 */
public class CamsApplication extends Application {

    /**
     * Initialises the main window for the CAMS application.
     *
     * After initialising the control is passed to the MainController to handle the GUI controls.
     *
     * @param primaryStage - The stage for the main.fxml layout to display on.
     * @throws IOException - An exception thrown if an error occurs initialising the window.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
      Parent root = loader.load();

        primaryStage.setTitle("CAMS - Crime Advertising Management System");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.getScene().getStylesheets().add(CamsApplication.class.getClassLoader().getResource("main.css").toExternalForm());
        //The following line should be used for each new stage to set the taskbar and window icons
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/CAMS_logo.png")));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
