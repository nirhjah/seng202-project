package seng202.group2.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class AddRecordController {

    private CrimeRecord newRecord;

    @FXML  private TextField caseNumTextField;

    @FXML private TextField fbiCodeTextField;

    @FXML private DatePicker datePicker;

    @FXML private TextField iucrTextField;

    @FXML private TextField primaryTextField;

    @FXML private TextField secondaryTextField;

    @FXML private CheckBox arrestCheckBox;

    @FXML private CheckBox domesticCheckBox;

    @FXML private TextField locationTextField;

    @FXML private TextField blockTextField;

    @FXML private TextField beatTextField;

    @FXML private TextField wardTextField;

    public void addRecordFromFields() {
        newRecord = new CrimeRecord();

        CrimeRecord newRecord = new CrimeRecord();
        ArrayList<Boolean> valids = new ArrayList<>();
        // Parse Case Number
        valids.add(checkInputValue(caseNumTextField, new CaseNumber()));
        valids.add(checkInputValue(fbiCodeTextField, new FBICode()));
        valids.add(checkDateInput(new Date()));

        System.out.println("Getting date: " + newRecord.getDate());;

        // Implement date check.
        valids.add(checkInputValue(iucrTextField, new IUCRCode()));
        valids.add(checkInputValue(primaryTextField, new PrimaryDescription()));
        valids.add(checkInputValue(secondaryTextField, new SecondaryDescription()));
        // add autofill of primary and secondary description?

        newRecord.setArrest(arrestCheckBox.isSelected());
        newRecord.setDomestic(domesticCheckBox.isSelected());

        valids.add(checkInputValue(locationTextField, new LocationDescription()));
        valids.add(checkInputValue(blockTextField, new Block()));
        valids.add(checkInputValue(beatTextField, new Block()));
        valids.add(checkInputValue(wardTextField, new Ward()));


        if (!valids.contains(false)) {
            DBMS.addRecord(newRecord, true);
            Stage stage = (Stage) caseNumTextField.getScene().getWindow();
            stage.close();
        }
    }

    private Boolean checkInputValue(TextField textField, DataCategory category) {
        try {
            String string = textField.getText();
            Object value = category.parseString(string);
            category.setRecordValue(newRecord, value);
            return true;
        } catch (IllegalArgumentException exception) {
            textField.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
            return false;
        }
    }

    private Boolean checkDateInput(Date date) {
        try {
            LocalDate localDate = datePicker.getValue();
            Calendar calendar = date.parseString(localDate.toString());
            System.out.println(calendar + " Calendar: " + calendar.toString());
            date.setRecordValue(newRecord, calendar);
            return true;
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
            return false;
        }
    }

}
