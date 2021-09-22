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

import java.util.*;

/**
 * A Graph class that generates a Bar chart, plotting either two DataCategory subtypes against each other,
 * or a single DataCategory subtype against the count of crime records which take on each value for that DataCategory.
 * Selecting between these two behaviours is done using the internal enumeration Mode.
 *
 * @author Connor Dunlop
 */
public class BarGraph extends Graph {

    /**
     * Used to select a mode for the BarGraph to use.
     * DEFAULT - plot one DataCategory against another.
     * RECORD_COUNT - plot one DataCategory against the count of records which take each value of that DataCategory.
     */
    public enum Mode {
        DEFAULT,
        RECORD_COUNT
    }

    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();

    private Field<Categorical> xField = Field.newField("X Axis", Categorical.class);
    private Field<Numerical> yField = Field.newField("Y Axis", Numerical.class);
    /** Used to specify whether to plot DataCategory types against each other, or a DataCategory against record count. */
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

        // Set Axis labels as required
        xAxis.setLabel(xField.getDataCategory().toString());
        if (mode != Mode.RECORD_COUNT)
            yAxis.setLabel(xField.getDataCategory().toString());
        else
            yAxis.setLabel("Number of Records");

        // Get data to plot
        XYChart.Series<String, Number> dataSeries;
        switch (mode) {
            case RECORD_COUNT:
                dataSeries = getValueCounts();
                break;

            case DEFAULT:
            default:
                dataSeries = serializeRecordValues();
        }

        // Plot data
        barChart.getData().clear();
        barChart.getData().addAll(dataSeries);
    }

    /**
     * Checks if each required field has been set, and the graph is ready to plot data.
     * @return True if all required fields are set, false otherwise.
     */
    private boolean isReady() {
        switch (mode) {
            // If counting records, only the x field is required
            case RECORD_COUNT:
                if (xField.getDataCategory() != null)
                    return true;
                return false;

            // If plotting two DataCategory subtypes against each other, then both x and y fields are required
            case DEFAULT:
            default:
                if (xField.getDataCategory() != null && yField.getDataCategory() != null)
                    return true;
                return false;
        }
    }

    /**
     * Constructs an XYChart.Series populated with values taken from the active crime records, using the values from
     * the categories the x and y fields are bound to.
     * @return An XYChart.Series populated with values from two DataCategory subtypes, taken from the active crime records.
     */
    private XYChart.Series<String, Number> serializeRecordValues() {
        DataCategory xCat = (DataCategory) xField.getDataCategory();
        DataCategory yCat = (DataCategory) yField.getDataCategory();

        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();

        // Construct a series with the data from each category
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (CrimeRecord record : records) {
            dataSeries.getData().add(new XYChart.Data<String, Number>(
                    xCat.getRecordValue(record).toString(),
                    (Number) yCat.getRecordValue(record)
            ));
        }

        // Sort series into ascending order of category values (this is a string sort)
        dataSeries.getData().sort((i, j) -> {
            return i.getXValue().compareTo(j.getXValue());
        });

        return dataSeries;
    }

    /**
     * Constructs an XYChart.Series populated with values taken from the category xField is bound to, and the counts of
     * how many crime records in ActiveData currently take on each value.
     * @return An XYChart.Series populated with the counts of crime records which take on each value from a DataCategory subtype.
     */
    private XYChart.Series<String, Number> getValueCounts() {
        DataCategory xCat = (DataCategory) xField.getDataCategory();

        // For each value of xCat crime records take on, count the number of crime records which have that value
        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();
        HashMap<String, Integer> valueCounts = new HashMap<>();
        for (CrimeRecord record : records) {
            String recordValue = xCat.getRecordValue(record).toString();
            if (valueCounts.containsKey(recordValue))
                valueCounts.put(recordValue, valueCounts.get(recordValue) + 1);
            else
                valueCounts.put(recordValue, 1);
        }

        // Construct a series from the record counts for each value
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (String value : valueCounts.keySet()) {
            dataSeries.getData().add(new XYChart.Data<String, Number>(
                    value,
                    valueCounts.get(value)
            ));
        }

        // Sort series into ascending order of category values (this is a string sort)
        dataSeries.getData().sort((i, j) -> {
            return i.getXValue().compareTo(j.getXValue());
        });

        return dataSeries;
    }

    /**
     * Sets the graph's mode.
     * @param mode The mode of the graph.
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

}
