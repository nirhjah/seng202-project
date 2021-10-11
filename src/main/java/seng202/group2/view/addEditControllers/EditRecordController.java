package seng202.group2.view.addEditControllers;

import javafx.fxml.Initializable;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The JavaFX controller for editing crime records in the CAMS application.
 *
 * Most of the functionality and checking of this class is held within the superclass {@link AddEditController}.
 * This class is an extension of the AddEditController superclass, to specify that the record fields are for editing
 * a crime record rather than adding a new one.
 *
 * @author Sam Clark
 */
public class EditRecordController extends AddEditController implements Initializable {

    /** The ID of the crime record chosen to be edited. */
    private Integer selectedID;

    /**
     * This method is called when the add record button is clicked by the user in the add_edit.fxml screen.
     * If the user has entered valid information in all the fields, the crime record with the selectedID is
     * updated, otherwise the update is not made.
     *
     * This method creates a new Crime record to replace the original, rather
     * that updating the original. This way the update is not made unless it is valid in all fields.
     */
    public void addRecordFromFields() {
        CrimeRecord record = new CrimeRecord();

        if (checkAndSetInputFields(record)) {
            record.setID(selectedID);
            DBMS.updateRecord(record);
            closeWindow();
        }
    }

    /**
     * This method is called when an edit record window is opened. It sets the title and button on the add_edit.fxml
     * file to say edit rather than add. The initialise also sets the input fields initial value to those of the
     * selected record, so that records are easier to edit rather than starting from scratch.
     *
     * @param url - Not used parameter, required by the implemented interface {@link javafx.fxml.Initializable}
     * @param resourceBundle - Not used parameter, required by the implemented interface {@link javafx.fxml.Initializable}
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText("Edit Record Details");
        addEditButton.setText("Update Record");

        selectedID = (Integer) DBMS.getActiveData().getSelectedRecords().toArray()[0];
        CrimeRecord record = DBMS.getRecord(selectedID);
        fillFieldsWithValues(record);
    }
}
