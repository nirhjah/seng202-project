package seng202.group2.view.addEditControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.datacategories.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class AddEditController {

    @FXML protected Label titleLabel;

    @FXML protected Button addEditButton;

    @FXML private TextField caseNumTextField;

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


    public Boolean checkAndSetInputFields(CrimeRecord record) {

        ArrayList<Boolean> valids = new ArrayList<>();
        // Parse Case Number
        valids.add(checkInputValue(caseNumTextField, new CaseNumber(), record));
        valids.add(checkInputValue(fbiCodeTextField, new FBICode(), record));
        valids.add(checkDateInput(new Date(), record));

        // Implement date check.
        valids.add(checkInputValue(iucrTextField, new IUCRCode(), record));
        valids.add(checkInputValue(primaryTextField, new PrimaryDescription(), record));
        valids.add(checkInputValue(secondaryTextField, new SecondaryDescription(), record));
        // add autofill of primary and secondary description?

        record.setArrest(arrestCheckBox.isSelected());
        record.setDomestic(domesticCheckBox.isSelected());

        valids.add(checkInputValue(locationTextField, new LocationDescription(), record));
        valids.add(checkInputValue(blockTextField, new Block(), record));
        valids.add(checkInputValue(beatTextField, new Beat(), record));
        valids.add(checkInputValue(wardTextField, new Ward(), record));

        return !valids.contains(false);
    }

    private Boolean checkInputValue(TextField textField, DataCategory category, CrimeRecord record) {
        try {
            String string = textField.getText();
            Object value = category.parseString(string);
            System.out.println(category.toString() + ": " + value);
            category.setRecordValue(record, value);
            return true;
        } catch (IllegalArgumentException exception) {
            textField.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
            return false;
        }
    }

    private Boolean checkDateInput(Date date, CrimeRecord record) {
        try {
            LocalDate localDate = datePicker.getValue();
            Calendar calendar = date.parseString(localDate == null ? null : localDate.toString());
            date.setRecordValue(record, calendar);
            return true;
        } catch (IllegalArgumentException exception) {
            datePicker.setStyle("-fx-border-color: #ff0000; -fx-focus-color: #ff0000;");
            return false;
        }
    }

    protected void fillFieldsWithValues(CrimeRecord record) {
        caseNumTextField.setText(record.getCaseNum());
        fbiCodeTextField.setText(record.getFbiCode());

        // Add date field
        iucrTextField.setText(record.getIucr());
        primaryTextField.setText(record.getPrimaryDescription());
        secondaryTextField.setText(record.getSecondaryDescription());

        arrestCheckBox.setSelected(record.getArrest());
        domesticCheckBox.setSelected(record.getDomestic());

        locationTextField.setText(record.getLocationDescription());
        blockTextField.setText(record.getBlock());
        beatTextField.setText(record.getBeat().toString());
        wardTextField.setText(record.getWard().toString());

    }

    protected void closeWindow() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
}
