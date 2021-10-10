package seng202.group2.view.addEditControllers;


import javafx.fxml.Initializable;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRecordController extends AddEditController implements Initializable {


    public void addRecordFromFields() {
        CrimeRecord newRecord = new CrimeRecord();

        if (checkAndSetInputFields(newRecord)) {
            DBMS.addRecord(newRecord, true);
            closeWindow();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText("Add Record Details");
        addEditButton.setText("Add Record");
    }
}
