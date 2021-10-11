package seng202.group2.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import seng202.group2.model.CrimeRecord;

import java.util.ArrayList;

public class ImportSummaryController {

    @FXML private Label numSuccessfulLabel;
    @FXML private Label totalRecordsLabel;

    @FXML private ListView<String> discardedFieldList;
    @FXML private ListView<CrimeRecord> invalidRecordList;
    @FXML private ListView<CrimeRecord> duplicateRecordList;

    public void setNumSuccessful(Integer numSuccessful) {
        numSuccessfulLabel.setText(numSuccessful.toString());
    }

    public void setTotalRecords(Integer totalRecords) {
        totalRecordsLabel.setText(totalRecords.toString());
    }

    public void setDiscardedFields(ArrayList<String> discardedFields) {
        discardedFieldList.getItems().setAll(discardedFields);
    }

    public void setInvalidRecords(ArrayList<CrimeRecord> invalidRecords) {
        invalidRecordList.getItems().setAll(invalidRecords);
    }

    public void setDuplicateRecords(ArrayList<CrimeRecord> duplicateRecords) {
        duplicateRecordList.getItems().setAll(duplicateRecords);
    }

}
