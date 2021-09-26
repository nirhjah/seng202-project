package seng202.group2.view.graphs;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.Categorical;
import seng202.group2.model.datacategories.DataCategory;

import java.util.*;

/**
 * A TimeSeriesGraph type used to plot a graph of number of crime records over time.
 * The time domain is partitioned into a set of intervals, and a count of crime records which occur within each
 * interval is produced. These counts are then plotted against each time interval.
 *
 * An option is provided to plot a graph of number of crime records per [DataCategory] over time.
 * When a (Categorical) DataCategory is selected to plot a line per distinct category value, separate counts for each
 * category value are kept for the number of crime records that occur within each time interval.
 *
 * When plotting graphs of crime records per [DataCategory] over time, the system is unable to cope with large numbers
 * of distinct category values. To prevent the user's system from being overwhelmed by trying to plot such a graph,
 * the number lines for distinct category values which may be plotted at once is limited.
 * The limit is determined by an option presented to the user, which is hard-capped at the value of  MAX_CATEGORY_VALUES.
 * TODO Provide the user an option to choose which category values are plotted.
 *
 * @author Connor Dunlop
 */
public class CrimesOverTimeGraph extends TimeSeriesGraph {

    private static final String NAME = "Crimes Over Time";

    @Override
    public String getName() {
        return this.NAME;
    }

    /*************************************************************************************************************
     *                                   Graph Settings and Options.                                             *
     *************************************************************************************************************/

    protected SelectionGraphOption<DataCategory> perCategoryValueSelector = new SelectionGraphOption<>("Plot Line per Category Value");


    private final int MAX_CATEGORY_VALUES = 50;
    protected NumericGraphOption maxCategoryValueSelector = new NumericGraphOption("Maximum Category Value Lines \nto Plot");

    /*************************************************************************************************************
     *                                   Graph and Option Initialization.                                        *
     *************************************************************************************************************/

    @Override
    public void initialize(BorderPane graphPane, VBox optionList) {
        super.initialize(graphPane, optionList);

        lineChart.setTitle("Number of Crimes over Time");

        ArrayList<DataCategory> categories = new ArrayList<>(DataCategory.getCategories(Categorical.class));
        categories.sort((i, j) -> {
            return i.toString().compareTo(j.toString());
        });
        categories.add(0, null);

        perCategoryValueSelector.addItems(categories);
        perCategoryValueSelector.addChangeListener(() -> {
            populateOptions();
        });

        maxCategoryValueSelector.setBounds(1, MAX_CATEGORY_VALUES);

        populateOptions();
    }

    private void populateOptions() {
        clearOptions();
        addOptions(intervalTypeSelector, perCategoryValueSelector);
        if (perCategoryValueSelector.getSelectedItem() != null) {
            maxCategoryValueSelector.setRequired(true);
            addOptions(maxCategoryValueSelector);
        } else {
            maxCategoryValueSelector.setRequired(false);
        }
    }


    /*************************************************************************************************************
     *                                   Graph Plotting.                                                         *
     *************************************************************************************************************/

    @Override
    public void plotGraph() {
        IntervalType intervalType = intervalTypeSelector.getSelectedItem();

        // Check all required fields have been set
        if (!optionStatesValid()) {
            displayInvalidOptionsDialogue();
            return;
        }

        // Set Axis properties
        xAxis.setLabel(intervalType.toString());
        xAxis.setForceZeroInRange(false);

        // Plot data set
        lineChart.getData().clear();
        if (perCategoryValueSelector.getSelectedItem() == null) {
            lineChart.getData().addAll(generateTimeSeries());
        } else {
            DataCategory perValueCategory = perCategoryValueSelector.getSelectedItem();
            Map<String, XYChart.Series<Number, Number>> perCategoryValueSeries = generateTimeSeriesPerCategory(perValueCategory);
            for (String categoryValue : perCategoryValueSeries.keySet()) {
                lineChart.getData().add(perCategoryValueSeries.get(categoryValue));
            }
        }
    }

    /**
     * Generates a data series containing the counts of crime records in ActiveData which occur within each partition
     * of the time domain.
     * The partitions are determined by the IntervalType selected by the user.
     *
     * @return A data series for the number of crimes over time, according to the user selected time interval.
     */
    private XYChart.Series<Number, Number> generateTimeSeries() {
        IntervalType intervalType = intervalTypeSelector.getSelectedItem();

        // For each value the interval may take on, count the number of crime records which have that value
        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();
        HashMap<Number, Integer> valueCounts = new HashMap<>();
        for (CrimeRecord record : records) {
            Calendar date = record.getDate();
            Number recordValue = intervalType.getIntervalValue(date);
            if (valueCounts.containsKey(recordValue))
                valueCounts.put(recordValue, valueCounts.get(recordValue) + 1);
            else
                valueCounts.put(recordValue, 1);
        }

        // Construct a series from the record counts for each value
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        for (Number value : valueCounts.keySet()) {
            dataSeries.getData().add(new XYChart.Data<>(
                    value,
                    valueCounts.get(value)
            ));
        }

        return dataSeries;
    }

    /**
     * Generates a data series consisting of number of crimes per time interval from the CrimeRecords in ActiveData,
     * for each distinct value a record takes on for a given DataCategory.
     *
     * TODO Decompose this method, if sensible/possible
     * @param category A Categorical DataCategory from which to plot a graph for each category value.
     * @return A map of category values to a series of number of crime records over time, whose crime records have that category value.
     */
    private Map<String, XYChart.Series<Number, Number>> generateTimeSeriesPerCategory(DataCategory category) {
        IntervalType intervalType = intervalTypeSelector.getSelectedItem();

        int maxCategoryValues = maxCategoryValueSelector.getValue();
        int numCategoryValues = 0;

        // For each value the interval may take on, count the number of crime records which have that value
        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();
        HashMap<String, HashMap<Number, Integer>> perCategoryValueCounts = new HashMap<>();
        for (CrimeRecord record : records) {
            // Get the category value of the record
            String categoryValue = category.getRecordCategory(record).getValueString();

            // Get the time interval value of the record
            Calendar date = record.getDate();
            Number recordIntervalValue = intervalType.getIntervalValue(date);

            // If the category value has already been encountered
            if (perCategoryValueCounts.containsKey(categoryValue)) {
                // Get the value count map for that category value
                HashMap<Number, Integer> valueCounts = perCategoryValueCounts.get(categoryValue);
                // Increment the count for the interval value of the record
                if (valueCounts.containsKey(recordIntervalValue))
                    valueCounts.put(recordIntervalValue, valueCounts.get(recordIntervalValue) + 1);
                else
                    valueCounts.put(recordIntervalValue, 1);

            // If the category value has not been encountered yet
            } else {
                // If the maximum number of category values have already been discovered, skip this record
                if (maxCategoryValues <= numCategoryValues)
                    continue;
                else
                    numCategoryValues++;

                // Create a new interval value count map for this category value
                HashMap<Number, Integer> valueCounts = new HashMap<>();
                valueCounts.put(recordIntervalValue, 1);
                perCategoryValueCounts.put(categoryValue, valueCounts);
            }
        }

        HashMap<String, XYChart.Series<Number, Number>> categoryValueSeries = new HashMap<>();

        // Construct a data series for each category value
        for (String categoryValue : perCategoryValueCounts.keySet()) {
            HashMap<Number, Integer> valueCounts = perCategoryValueCounts.get(categoryValue);
            // Construct a series from the record counts for each value
            XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
            for (Number value : valueCounts.keySet()) {
                dataSeries.getData().add(new XYChart.Data<>(
                        value,
                        valueCounts.get(value)
                ));
            }

            categoryValueSeries.put(categoryValue, dataSeries);
        }

        return categoryValueSeries;
    }

}
