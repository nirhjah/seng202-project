package seng202.group2.view.addEditControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;

/**
 * The FXML controller class used when records are added or edited in the CAMS application.
 *
 * This class is not explicitly used, instead it is inherited by the individual add and edit record controllers
 * ({@link AddRecordController} and {@link EditRecordController}. Both of these classes use the add_edit.fxml file
 * for laying out the used JavaFX components.
 *
 * @author Sam Clark, Yiyang Yu, Moses Wescombe
 */
public class AddEditController {

    /** The title label for the edit and add records window for the CAMS application */
    @FXML protected Label titleLabel;

    /** The button used to execute either an add or edit of a crime record in the CAMS UI*/
    @FXML protected Button addEditButton;

    /** This hash map stores as keys the datacategories which take data input from a textfield,
     * and maps them to their corresponding UI TextField to simplify the retrieval of user input. */
    private HashMap<DataCategory, TextField> textFieldsDict;

    /** The JavaFX text field used to get user input for the CaseNumber DataCategory. */
    @FXML private TextField caseNumTextField;

    /** The JavaFX text field used to get user input for the FBICode DataCategory. */
    @FXML private TextField fbiCodeTextField;

    /** The JavaFX date picker used to get user input for the Date DataCategory. */
    @FXML private DatePicker datePicker;

    /** The JavaFX text field used to get user input for the IUCRCode DataCategory. */
    @FXML private TextField iucrTextField;

    /** The JavaFX text field used to get user input for the PrimaryDescription DataCategory. */
    @FXML private TextField primaryTextField;

    /** The JavaFX text field used to get user input for the SecondaryDescription DataCategory. */
    @FXML private TextField secondaryTextField;

    /** The JavaFX check box used to get user input for the Arrest DataCategory. */
    @FXML private CheckBox arrestCheckBox;

    /** The JavaFX check box used to get user input for the Domestic DataCategory. */
    @FXML private CheckBox domesticCheckBox;

    /** The JavaFX text field used to get user input for the LocationDescription DataCategory. */
    @FXML private TextField locationTextField;

    /** The JavaFX text field used to get user input for the Block DataCategory. */
    @FXML private TextField blockTextField;

    /** The JavaFX text field used to get user input for the Beat DataCategory. */
    @FXML private TextField beatTextField;

    /** The JavaFX text field used to get user input for the Ward DataCategory. */
    @FXML private TextField wardTextField;

    @FXML private TextField latitudeTextField;

    @FXML private TextField longitudeTextField;


    /**
     * Assigns the user input values to a given CrimeRecord. If the values are all valid the method returns true otherwise
     * it returns false.
     *
     * @param record - The crime record to assign the new user input value to.
     * @return valid - A boolean value, returning true if all the data values are valid, and hence the record can be added/edited.
     */
    public Boolean checkAndSetInputFields(CrimeRecord record) {
        if (textFieldsDict == null) {
            createInputsDictionary();
        }

        Boolean validRecord = true;
        for (DataCategory category : textFieldsDict.keySet()) {
            Boolean valid = checkInputValue(textFieldsDict.get(category), category, record);
            if (!valid) {
                validRecord = false;
            }
        }
        Boolean valid = checkDateInput(record);
        if (!valid) {
            validRecord = false;
        }

        record.setArrest(arrestCheckBox.isSelected());
        record.setDomestic(domesticCheckBox.isSelected());

        return validRecord;
    }

    /**
     * This method takes a textfield, a datacategory and a crime record and assigns the value of the textfield to the
     * given category of the given record. If the value is invalid this method returns false, to indicate the the corresponding
     * crime record is invalid. Otherwise the method returns true.
     *
     * @param textField - the TextField used to gt the user input for the value.
     * @param category - the datacategory to assign the user input to.
     * @param record - the crime record to give the category attribute value to.
     * @return valid - true if the user input value is valid, and added for the given category. Otherwise false.
     */
    private Boolean checkInputValue(TextField textField, DataCategory category, CrimeRecord record) {
        try {
            String string = textField.getText();
            Object value = category.parseString(string);
            category.setRecordValue(record, value);
            textField.setStyle(null);
            return true;
        } catch (IllegalArgumentException exception) {
            textField.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
            return false;
        }
    }

    /**
     * This method checks and sets the user input for the date attribute of a crime record.
     *
     * @param record - The crime record to add the given date value to.
     * @return - returns true if the date value was valid and successfully added, otherwise returns false.
     */
    private Boolean checkDateInput(CrimeRecord record) {
        try {
            Date date = new Date();
            LocalDate localDate = datePicker.getValue();
            Calendar calendar = date.parseString(localDate == null ? null : localDate.toString());
            date.setRecordValue(record, calendar);
            datePicker.setStyle(null);
            return true;
        } catch (IllegalArgumentException exception) {
            datePicker.setStyle("-fx-border-color: #ff0000; -fx-focus-color: #ff0000;");
            return false;
        }
    }

    /**
     * This methods is called when the user attempts to edit a record. When a record is edited this method intialises
     * the fields with the current values of the crime record, so that these can be edited than creating a new record from
     * scratch.
     *
     * @param record - the crime record from which the data will be taken to add to the input fields.
     */
    protected void fillFieldsWithValues(CrimeRecord record) {
        if (textFieldsDict == null) {
            createInputsDictionary();
        }
       for (DataCategory category : textFieldsDict.keySet()) {
           try {
               textFieldsDict.get(category).setText(category.getRecordValue(record).toString());
           } catch (NullPointerException exception) {} // In the case the value of the category is null the toString will thrown a Null pointer exception
       }

        // Add date field
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DBMS.DATE_FORMAT);
            datePicker.setValue(LocalDate.parse(record.getDateCategory().getValueString(), formatter));
        } catch (NullPointerException exception) {} // In the case the value of the category is null the toString will thrown a Null pointer exception

        // Add arrest field
        if (record.getArrest() == null) {
            arrestCheckBox.setIndeterminate(true);
        } else {
            arrestCheckBox.setSelected(record.getArrest());
        }

        // add domestic field
        if (record.getDomestic() == null) {
            domesticCheckBox.setIndeterminate(true);
        } else {
            domesticCheckBox.setSelected(record.getDomestic());
        }
    }

    /**
     * This method closes the add or edit window.
     */
    protected void closeWindow() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }

    /**
     * Creates a dictionary containing each of the textfields user to get user input. This dictionary is used to
     * iterate through to populate or retrieve the user input fields.
     */
    private void createInputsDictionary() {
        textFieldsDict = new HashMap<>();
        textFieldsDict.put(new CaseNumber(), caseNumTextField);
        textFieldsDict.put(new FBICode(), fbiCodeTextField);
        textFieldsDict.put(new IUCRCode(), iucrTextField);
        textFieldsDict.put(new PrimaryDescription(), primaryTextField);
        textFieldsDict.put(new SecondaryDescription(), secondaryTextField);
        textFieldsDict.put(new LocationDescription(), locationTextField);
        textFieldsDict.put(new Block(), blockTextField);
        textFieldsDict.put(new Beat(), beatTextField);
        textFieldsDict.put(new Ward(), wardTextField);
        textFieldsDict.put(new Latitude(), latitudeTextField);
        textFieldsDict.put(new Longitude(), longitudeTextField);
    }
}
