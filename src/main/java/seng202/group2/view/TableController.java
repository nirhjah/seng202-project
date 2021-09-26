package seng202.group2.view;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import seng202.group2.controller.DataObserver;
import seng202.group2.model.ActiveData;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TableController extends DataObserver implements Initializable {
    //Table
    @FXML private TableView<CrimeRecord> tableView;
    @FXML private TableColumn<CrimeRecord, Integer> idColumn;
    @FXML private TableColumn<CrimeRecord, String> caseNumColumn;
    @FXML private TableColumn<CrimeRecord, String> dateColumn;
    @FXML private TableColumn<CrimeRecord, String> blockColumn;
    @FXML private TableColumn<CrimeRecord, String> iucrColumn;
    @FXML private TableColumn<CrimeRecord, String> primaryDescriptionColumn;
    @FXML private TableColumn<CrimeRecord, String> secondaryDescriptionColumn;
    @FXML private TableColumn<CrimeRecord, String> locationDescriptionColumn;
    @FXML private TableColumn<CrimeRecord, Boolean> arrestColumn;
    @FXML private TableColumn<CrimeRecord, Boolean> domesticColumn;
    @FXML private TableColumn<CrimeRecord, Short> beatColumn;
    @FXML private TableColumn<CrimeRecord, Short> wardColumn;
    @FXML private TableColumn<CrimeRecord, Short> fbiCodeColumn;
    @FXML private TableColumn<CrimeRecord, Short> latitudeColumn;
    @FXML private TableColumn<CrimeRecord, Short> longitudeColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DBMS.getActiveData().addObserver(this);

        //Build table
        idColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Integer>("ID"));
        caseNumColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("caseNum"));
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CrimeRecord, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CrimeRecord, String> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getDateCategory().getValueString());
            }
        });
        blockColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("block"));
        iucrColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("iucr"));
        primaryDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("primaryDescription"));
        secondaryDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("secondaryDescription"));
        locationDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, String>("locationDescription"));
        arrestColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Boolean>("arrest"));
        domesticColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Boolean>("domestic"));
        beatColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Short>("beat"));
        wardColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Short>("ward"));
        fbiCodeColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Short>("fbiCode"));
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Short>("latitude"));
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<CrimeRecord, Short>("longitude"));
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Called when a click event occurs on the tableView.
     * Updates the set of selected records in ActiveData using the selection out of currently tabulated records.
     */
    public void updateSelection() {
        ActiveData activeData = DBMS.getActiveData();

        // Deselect all currently tabulated records
        for (CrimeRecord item : tableView.getItems()) {
            activeData.deselectRecord(item.getID());
        }

        // Select all records currently selected in the table.
        for (CrimeRecord selectedItem : tableView.getSelectionModel().getSelectedItems()) {
            activeData.selectRecord(selectedItem.getID());
        }

        // Tell all observers of ActiveData that the selection has been updated.
        activeData.updateSelectionObservers();
    }

    @Override
    public void activeDataUpdate() {
        ActiveData activeData = DBMS.getActiveData();
        ArrayList<CrimeRecord> activeRecords = activeData.getActiveRecords(0, DBMS.getActiveData().getFrameSize());

        //Update table
        tableView.getItems().clear();
        for (CrimeRecord record: activeRecords)
            tableView.getItems().add(record);

        selectedRecordsUpdate();
    }

    @Override
    public void selectedRecordsUpdate() {
        ActiveData activeData = DBMS.getActiveData();

        //Clear selections
        tableView.getSelectionModel().clearSelection();

        //Select all records that are seleceted in active records
        for (CrimeRecord record: tableView.getItems()) {
            if (activeData.isSelected(record.getID()))
                tableView.getSelectionModel().select(record);
        }
    }

    @Override
    public void frameUpdate() {
        //Get records in frame range
        ArrayList<CrimeRecord> activeRecords = new ArrayList<>(DBMS.getActiveData().getActiveRecords());

        //Update table
        tableView.getItems().clear();
        for (CrimeRecord record: activeRecords) {
            tableView.getItems().add(record);

            //Select record if required
            if (DBMS.getActiveData().isSelected(record.getID())) {
                tableView.getSelectionModel().select(record);
            }
        }
    }
}
