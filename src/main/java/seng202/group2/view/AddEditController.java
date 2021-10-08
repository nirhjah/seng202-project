package seng202.group2.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.*;

import java.util.ArrayList;

public class AddEditController {

    private CrimeRecord newRecord;

    @FXML private TextField caseNumTextField;

    @FXML private Text caseNumText;

    @FXML private DatePicker datePicker;

    @FXML private Text dateText;

    @FXML private TextField iucrTextField;

    @FXML private Text iucrText;

    @FXML private TextField blockTextField;

    @FXML private Text blockText;

    @FXML private TextField primaryTextField;

    @FXML private TextField secondaryTextField;

    @FXML private TextField locationTextField;

    @FXML private CheckBox arrestCheckBox;

    @FXML private CheckBox domesticCheckBox;

    public void addRecordFromFields() {
        clearWarningTexts();
        newRecord = new CrimeRecord();

        CrimeRecord newRecord = new CrimeRecord();
        ArrayList<Boolean> valids = new ArrayList<>();
        // Parse Case Number
        valids.add(checkInputValue(caseNumTextField.getText(), new CaseNumber(), caseNumText));
        String dateString = datePicker.getValue() == null ? "invalid" : datePicker.getValue().toString();
        System.out.println(dateString);
        valids.add(checkInputValue(dateString, new Date(), dateText));
        valids.add(checkInputValue(iucrTextField.getText(), new IUCRCode(), iucrText));
        // add autofill of primary and secondary description?
        valids.add(checkInputValue(blockTextField.getText(), new Block(), blockText));

        newRecord.setPrimaryDescription(PrimaryDescription.getInstance().parseString(primaryTextField.getText())); // All values are valid
        newRecord.setSecondaryDescription(SecondaryDescription.getInstance().parseString(secondaryTextField.getText())); // All values are valid
        newRecord.setLocationDescription(LocationDescription.getInstance().parseString(locationTextField.getText()));   // All values are valid

        newRecord.setArrest(arrestCheckBox.isSelected());
        newRecord.setDomestic(domesticCheckBox.isSelected());


        if (!valids.contains(false)) {
            DBMS.addRecord(newRecord, true);
            Stage stage = (Stage) caseNumText.getScene().getWindow();
            stage.close();
        }
    }

    private Boolean checkInputValue(String input, DataCategory category, Text textBox) {
        try {
            category.setRecordValue(newRecord, category.parseString(input));
            return true;
        } catch (IllegalArgumentException exception) {
            textBox.setText(exception.getMessage());
            textBox.setVisible(true);
            return false;
        }
    }

    private void clearWarningTexts() {
        caseNumText.setVisible(false);
        dateText.setVisible(false);
        iucrText.setVisible(false);
    }
}
