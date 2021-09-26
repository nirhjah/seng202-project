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

    private static final String NAME = "Bar";

    @Override
    public String getName() {
        return this.NAME;
    }

    /*************************************************************************************************************
     *                                   Graph Settings and Options.                                             *
     *************************************************************************************************************/

    // Record Count enabled? option
    // True if in record counting mode, false otherwise
    protected BooleanGraphOption recordCountToggle = new BooleanGraphOption("Record Count");

    // X Axis Options
    protected SelectionGraphOption<DataCategory> xAxisSelector = new SelectionGraphOption<>("X Axis", true);

    // Y Axis Options
    protected SelectionGraphOption<DataCategory> yAxisSelector = new SelectionGraphOption<>("Y Axis", true);


    /*************************************************************************************************************
     *                                   Graph and Option Initialization.                                        *
     *************************************************************************************************************/

    @Override
    public void initialize(BorderPane graphPane, VBox optionList) {
        super.initialize(graphPane, optionList);

        barChart.setTitle("Bar Graph");
        setChart(barChart);

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
            populateOptions();
        });

        populateOptions();
    }

    private void populateOptions() {
        clearOptions();
        addOption(xAxisSelector);
        if (!recordCountToggle.getState())
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
        // Get selected categories
        DataCategory xCategory = xAxisSelector.getSelectedItem();
        DataCategory yCategory = null;
        if (!recordCountToggle.getState())
            yCategory = yAxisSelector.getSelectedItem();

        // Check all required fields have been set
        if (!optionStatesValid()) {
            displayInvalidOptionsDialogue();
            return;
        }

        // Set Axis labels as required
        xAxis.setLabel(xCategory.toString());
        if (!recordCountToggle.getState())
            yAxis.setLabel(yCategory.toString());
        else
            yAxis.setLabel("Number of Records");

        // Get data to plot
        XYChart.Series<String, Number> dataSeries;
        if (recordCountToggle.getState())
            dataSeries = getValueCounts();
        else
            dataSeries = serializeRecordValues();

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

}
