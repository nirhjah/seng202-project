package seng202.group2.view.addEditControllers;

import javafx.fxml.Initializable;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The JavaFX controller for adding crime records in the CAMS application.
 *
 * Most of the functionality and checking of this class is held within the superclass {@link AddEditController}.
 * This class is an extension of the AddEditController superclass, to specify that the record fields are for adding
 * a crime record rather than editing one.
 *
 * @author Sam Clark
 */
public class AddRecordController extends AddEditController implements Initializable {

    /**
     * This method is called when the add record button is clicked by the user in the add_edit.fxml screen.
     * If the user has entered valid information in all the fields, the crime record is added, otherwise
     * the record is not added.
     */
    public void addRecordFromFields() {
        CrimeRecord newRecord = new CrimeRecord();

        if (checkAndSetInputFields(newRecord)) {
            DBMS.addRecord(newRecord, true);
            closeWindow();
        }
    }


    /**
     *  This method is called when an add record window is opened. It sets the title and button on the add_edit.fxml
     *  file to say add rather than edit.
     *
     * @param url - Not used parameter, required by the implemented interface {@link javafx.fxml.Initializable}
     * @param resourceBundle - Not used parameter, required by the implemented interface {@link javafx.fxml.Initializable}
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText("Add Record Details");
        addEditButton.setText("Add Record");
    }
}
