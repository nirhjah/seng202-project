package seng202.group2.view.graphs;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.Categorical;
import seng202.group2.model.datacategories.DataCategory;
import seng202.group2.model.datacategories.DataClassification;
import seng202.group2.model.datacategories.Numerical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Connor Dunlop
 */
public class BarGraph extends Graph {

    public enum Mode {
        DEFAULT,
        RECORD_COUNT
    }

    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();

    private Field<Categorical> xField = Field.newField("X Axis", Categorical.class);
    private Field<Numerical> yField = Field.newField("Y Axis", Numerical.class);
    private Mode mode = Mode.RECORD_COUNT;

    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    @Override
    public void initialize(BorderPane borderPane) {
        barChart.setTitle("Bar Graph");
        borderPane.setCenter(barChart);
    }

    @Override
    public Set<Field<? extends DataClassification>> getFields() {
        HashSet<Field<? extends DataClassification>> fields = new HashSet<>();
        fields.add(xField);

        if (mode == Mode.DEFAULT)
            fields.add(yField);

        return fields;
    }

    @Override
    public void plotGraph() {
        if (!isReady())
            throw new NullPointerException("One or more required fields have not been set.");

        xAxis.setLabel(xField.getDataCategory().toString());
        if (mode != Mode.RECORD_COUNT)
            yAxis.setLabel(xField.getDataCategory().toString());
        else
            yAxis.setLabel("Number of Records");

        XYChart.Series<String, Number> dataSeries;
        switch (mode) {
            case RECORD_COUNT:
                dataSeries = getValueCounts();
                break;

            case DEFAULT:
            default:
                dataSeries = serializeRecordValues();
        }

        barChart.getData().clear();
        barChart.getData().addAll(dataSeries);
    }

    private boolean isReady() {
        switch (mode) {
            case RECORD_COUNT:
                if (xField.getDataCategory() != null)
                    return true;
                return false;

            case DEFAULT:
            default:
                if (xField.getDataCategory() != null && yField.getDataCategory() != null)
                    return true;
                return false;
        }
    }

    private XYChart.Series<String, Number> serializeRecordValues() {
        DataCategory xCat = (DataCategory) xField.getDataCategory();
        DataCategory yCat = (DataCategory) yField.getDataCategory();

        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (CrimeRecord record : records) {
            dataSeries.getData().add(new XYChart.Data<String, Number>(
                    xCat.getRecordValue(record).toString(),
                    (Number) yCat.getRecordValue(record)
            ));
        }

        return dataSeries;
    }

    private XYChart.Series<String, Number> getValueCounts() {
        DataCategory xCat = (DataCategory) xField.getDataCategory();

        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();
        HashMap<String, Integer> valueCounts = new HashMap<>();
        for (CrimeRecord record : records) {
            String recordValue = xCat.getRecordValue(record).toString();
            if (valueCounts.containsKey(recordValue))
                valueCounts.put(recordValue, valueCounts.get(recordValue) + 1);
            else
                valueCounts.put(recordValue, 1);
        }

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (String value : valueCounts.keySet()) {
            dataSeries.getData().add(new XYChart.Data<String, Number>(
                    value,
                    valueCounts.get(value)
            ));
        }

        return dataSeries;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

}
