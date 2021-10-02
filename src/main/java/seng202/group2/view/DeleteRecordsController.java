package seng202.group2.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seng202.group2.controller.DataObserver;
import seng202.group2.model.DBMS;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteRecordsController implements Initializable {

    @FXML private TextArea deleteRecordsTextArea;
    @FXML private Button deleteRecordsButton;

    public void cancelDelete() {
        Stage stage = (Stage) deleteRecordsTextArea.getScene().getWindow();
        stage.close();
    }

    public void deleteSelectedRecords() {
        Stage stage = (Stage) deleteRecordsTextArea.getScene().getWindow();
        DBMS.deleteRecords(DBMS.getActiveData().getSelectedRecords());
        stage.close();
    }


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
