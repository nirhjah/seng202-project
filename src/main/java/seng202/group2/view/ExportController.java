package seng202.group2.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group2.controller.DataExporter;
import seng202.group2.controller.UnsupportedFileTypeException;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ExportController is the controller class for the crime export GUI.
 *
 * This class uses the 'export.fxml' FXML file for laying it out.
 *
 * @author Connor Dunlop
 */
public class ExportController {

    /** The number of records to save per export step. */
    private final int RECORDS_PER_EXPORT = 100;

    /** The textField used for entering a filepath. */
    @FXML private TextField exportPathTextField;

    /** Used to define the current exporting status of the controller. */
    private enum ExportingStatus {
        /** The initial status, before the import button has been clicked. */
        IDLE,
        EXPORTING,
        DONE
    }

    /** The current exporting status of the controller */
    private ExportingStatus status = ExportingStatus.IDLE;

    @FXML private ProgressBar progressBar;
    @FXML private Label exportMessage;
    @FXML private HBox exportProgressMessageBox;
    @FXML private Label recordsSoFar;
    @FXML private Label recordsToExport;
    @FXML private Button exportButton;

    /** The number of crime records exported so far. */
    private int numExported = 0;
    /** The total number of crime records stored in the database, to be exported. */
    private int totalToExport = 0;

    /**
     * Displays a message indicating which exporting state/status the controller is in, and updates the user interface
     * appropriately to the exporting status.
     */
    public void updateProgress() {
        switch (status) {
            case IDLE:
                Platform.runLater(() -> {
                    exportMessage.setText("Select an output path.");
                    progressBar.setVisible(false);
                    exportButton.setDisable(false);
                    exportProgressMessageBox.setVisible(false);
                    progressBar.setProgress(0);
                });
                break;

            case EXPORTING:
                Platform.runLater(() -> {
                    exportMessage.setText("Exporting Records:");
                    exportProgressMessageBox.setVisible(true);
                    exportButton.setDisable(true);
                    progressBar.setVisible(true);
                    recordsSoFar.setText(Integer.toString(numExported));
                    recordsToExport.setText(Integer.toString(totalToExport));
                    progressBar.setProgress(((double) numExported) / ((double) totalToExport));
                });
                break;

            case DONE:
                Platform.runLater(() -> {
                    exportMessage.setText("Done.");
                    Stage stage = (Stage) exportPathTextField.getScene().getWindow();
                    stage.getScene().setCursor(Cursor.DEFAULT);
                    stage.close();
                });
                break;
        }
    }

    /**
     * Exports all crime records stored in the database to the provided file.
     * This method exports the crime records in chunks of [RECORDS_PER_EXPORT] records.
     *
     * @param file The file to which crime data is to be written to.
     */
    private void exportToFile(File file) {
        status = ExportingStatus.EXPORTING;
        ArrayList<CrimeRecord> records = DBMS.getAllRecords();

        // Try to get an exporter for the provided file
        DataExporter exporter;
        try {
            exporter = DataExporter.getExporter(file.getPath());
        } catch (UnsupportedFileTypeException e) {
            e.printStackTrace();
            System.out.println("File type not supported.");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not open the file for exporting.");
            return;
        }

        // Export all crime records to the file.
        try {
            totalToExport = records.size();

            // Export the crime records in chunks
            int chunkStart = 0;
            int chunkEnd;
            while (chunkStart < records.size()) {

                // Get next sublist of records to export
                chunkEnd = Math.min(records.size(), chunkStart + RECORDS_PER_EXPORT);
                List<CrimeRecord> nextRecords = records.subList(chunkStart, chunkEnd);
                chunkStart = chunkEnd;

                // Export sublist of records to file
                exporter.exportRecords(nextRecords);
                numExported += nextRecords.size();
                updateProgress();
            }

        // Always try to close the exporter
        } finally {
            try {
                exporter.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error trying to close file.");
            }
        }
    }

    /**
     * exportFileFromField takes the filepath enterend by the user and passes it the importer.
     *
     * This method is called when the 'export' button is clicked. It takes the filepath from
     * {@link ExportController#exportPathTextField} and exports data to that file using {@link DataExporter}.
     */
    public void exportFileFromField()
    {
        String filePath = exportPathTextField.getText();
        if (filePath.equals(""))
            return;

        File file = new File(filePath);
        System.out.println("Retrieving file : " + exportPathTextField.getText());

        new Thread(() -> {
            exportToFile(file);
            status = ExportingStatus.DONE;
            updateProgress();
        }).start();
    }

    /**
     * Opens a FileChooser to browse for a file to import.
     *
     * This method uses a {@link FileChooser} to select a file to import. When a file is selected
     * it sets the text of {@link ExportController#exportPathTextField} to the selected file path,
     * ready to be imported.
     */
    public void browseImportDirectory()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Comma Separated Value","*.csv"));
        File chosenFile = fileChooser.showOpenDialog(null);


        if (chosenFile != null) {
            exportPathTextField.setText(chosenFile.getAbsolutePath());
        }
    }
}
