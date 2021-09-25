package seng202.group2.view.graphs;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.Categorical;
import seng202.group2.model.datacategories.DataCategory;
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

    /*************************************************************************************************************
     *                                   Graph Settings and Options.                                             *
     *************************************************************************************************************/

    /**
     * Used to select a mode for the BarGraph to use.
     * DEFAULT - plot one DataCategory against another.
     * RECORD_COUNT - plot one DataCategory against the count of records which take each value of that DataCategory.
     */
    public enum Mode {
        DEFAULT,
        RECORD_COUNT
    }

    /** Used to specify whether to plot DataCategory types against each other, or a DataCategory against record count. */
    private Mode mode = Mode.RECORD_COUNT;

    // Mode option
    protected BooleanGraphOption recordCountToggle = new BooleanGraphOption("Record Count");

    // X Axis Options
    protected SelectionGraphOption<DataCategory> xAxisSelector = new SelectionGraphOption<>("X Axis");

    // Y Axis Options
    protected SelectionGraphOption<DataCategory> yAxisSelector = new SelectionGraphOption<>("Y Axis");


    /*************************************************************************************************************
     *                                   Graph and Option Initialization.                                        *
     *************************************************************************************************************/

    @Override
    public void initialize(BorderPane graphPane, VBox optionList) {
        super.initialize(graphPane, optionList);

        barChart.setTitle("Bar Graph");
        graphPane.setCenter(barChart);

        ArrayList<DataCategory> xCategories = new ArrayList<>(DataCategory.getCategories(Categorical.class));
        xCategories.sort((i, j) -> {
            return i.toString().compareTo(j.toString());
        });
        xAxisSelector.addItems(xCategories);

        ArrayList<DataCategory> yCategories = new ArrayList<>(DataCategory.getCategories(Numerical.class));
        yCategories.sort((i, j) -> {
            return i.toString().compareTo(j.toString());
        });
        yAxisSelector.addItems(yCategories);

        recordCountToggle.setState(true);
        recordCountToggle.addChangeListener(() -> {
            if (recordCountToggle.getState())
                mode = Mode.RECORD_COUNT;
            else
                mode = Mode.DEFAULT;
            populateOptions();
        });

        populateOptions();
    }

    private void populateOptions() {
        clearOptions();
        addOption(xAxisSelector);
        if (mode == Mode.DEFAULT)
            addOption(yAxisSelector);
        addOption(recordCountToggle);
    }


    /*************************************************************************************************************
     *                                              Graph Plotting.                                              *
     *************************************************************************************************************/

    protected CategoryAxis xAxis = new CategoryAxis();
    protected NumberAxis yAxis = new NumberAxis();
    protected BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    @Override
    public void plotGraph() {
        // Check mode
        if (recordCountToggle.getState())
            mode = Mode.RECORD_COUNT;
        else
            mode = Mode.DEFAULT;

        // Get selected categories
        DataCategory xCategory = xAxisSelector.getSelectedItem();
        DataCategory yCategory = null;
        if (mode == Mode.DEFAULT)
            yCategory = yAxisSelector.getSelectedItem();

        if (xCategory == null || (mode == Mode.DEFAULT && yCategory == null))
            throw new NullPointerException("One or more required fields have not been set.");

        // Set Axis labels as required
        xAxis.setLabel(xCategory.toString());
        if (mode != Mode.RECORD_COUNT)
            yAxis.setLabel(yCategory.toString());
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
     * Constructs an XYChart.Series populated with values taken from the active crime records, using the values from
     * the categories the x and y fields are bound to.
     * @return An XYChart.Series populated with values from two DataCategory subtypes, taken from the active crime records.
     */
    private XYChart.Series<String, Number> serializeRecordValues() {
        DataCategory xCat = xAxisSelector.getSelectedItem();
        DataCategory yCat = yAxisSelector.getSelectedItem();

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
        DataCategory xCat = xAxisSelector.getSelectedItem();

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
