package seng202.group2.view.addEditControllers;

import javafx.fxml.Initializable;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

import java.net.URL;
import java.util.ResourceBundle;

public class EditRecordController extends AddEditController implements Initializable {

    public void addRecordFromFields() {
        CrimeRecord newRecord = new CrimeRecord();

        if (checkAndSetInputFields(newRecord)) {
            System.out.println("Now add this update to the dbms");
            closeWindow();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText("Edit Record Details");
        addEditButton.setText("Update Record");

        Integer selectedID = (Integer) DBMS.getActiveData().getSelectedRecords().toArray()[0];
        CrimeRecord record = DBMS.getRecord(selectedID);
        fillFieldsWithValues(record);
    }
}
