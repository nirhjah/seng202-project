package seng202.group2.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seng202.group2.model.DBMS;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * DeleteRecordsController is the JavaFX controller class for the deleteRecords.fxml file.
 * Together these classes prompt the user that they have chosen to delete selected data, telling them how many
 * records are selected to delete, and allowing them to confirm the deletion or cancel it.
 * If no records are selected the deletion confirmation button is hidden and the user is told how to delete records.
 *
 * @author Sam Clark
 */
public class DeleteRecordsController implements Initializable {

    /** The JavaFX text area which displays either how many records are selected, asking the user to confirm the deletion,
     * or it tells the user how to delete records if none are selected.*/
    @FXML private TextArea deleteRecordsTextArea;

    /** The JavaFX button which controls the deletion confirmation, This is hidden when no records are selected.*/
    @FXML private Button deleteRecordsButton;

    /**
     * cancelDelete is called when the user clicks the cancel button on the 'delete records' window.
     * This method simply closes the window without doing anything, cancelling the deletion.
     */
    public void cancelDelete() {
        Stage stage = (Stage) deleteRecordsTextArea.getScene().getWindow();
        stage.close();
    }

    /**
     * deleteSelectedRecords is called when the delete button is clicked on the 'delete records' window.
     * This method deletes the records through the DBMS by calling {@link DBMS#deleteSelectedRecords}
     * which simply deletes all the selected records from the Database and active data, and updates the observers.
     */
    public void deleteSelectedRecords() {
        Stage stage = (Stage) deleteRecordsTextArea.getScene().getWindow();
        DBMS.deleteSelectedRecords();
        stage.close();
    }

    /**
     * Initialises this delete records window when it is opened.
     *
     * This method sets the prompt message depending on how many records are selected. It also sets the visibility of
     * the delete button to false if there are no selected records.
     *
     * @param location - Not used parameter required by the Initializable interface implemented.
     * @param resources - Not used parameter required by the Initializable interface implemented.
     *
     * @see Initializable
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Integer selectedRecordsSize = DBMS.getActiveData().getSelectedRecords().size();
        String promptText;

        if (selectedRecordsSize == 0) {
            promptText = "To delete records select them before clicking delete.";
            deleteRecordsButton.setVisible(false);
        } else if (selectedRecordsSize == 1) {
            promptText = "One record is selected. \n" +
                    "Are you sure you want to delete it?";
            deleteRecordsButton.setText("Delete");
            deleteRecordsButton.setVisible(true);
        } else {
            promptText = selectedRecordsSize + " records are selected. \n" +
                    "Are you sure you want to delete them?";
            deleteRecordsButton.setText("Delete All");
            deleteRecordsButton.setVisible(true);
        }

        deleteRecordsTextArea.setText(promptText);
    }
}
