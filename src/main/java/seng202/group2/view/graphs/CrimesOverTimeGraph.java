package seng202.group2.view.graphs;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.Categorical;
import seng202.group2.model.datacategories.DataCategory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CrimesOverTimeGraph extends TimeSeriesGraph {

    /*************************************************************************************************************
     *                                   Graph Settings and Options.                                             *
     *************************************************************************************************************/

    protected SelectionGraphOption<DataCategory> perCategoryValueSelector = new SelectionGraphOption<>("Plot Line per Category Value");

    /*************************************************************************************************************
     *                                   Graph and Option Initialization.                                        *
     *************************************************************************************************************/

    @Override
    public void initialize(BorderPane graphPane, VBox optionList) {
        super.initialize(graphPane, optionList);

        lineChart.setTitle("Number of Crimes over Time");
        graphPane.setCenter(lineChart);

        ArrayList<DataCategory> categories = new ArrayList<>(DataCategory.getCategories(Categorical.class));
        categories.sort((i, j) -> {
            return i.toString().compareTo(j.toString());
        });
        categories.add(0, null);

        perCategoryValueSelector.addItems(categories);

        clearOptions();
        addOptions(intervalTypeSelector, perCategoryValueSelector);
    }


    /*************************************************************************************************************
     *                                   Graph Plotting.                                                         *
     *************************************************************************************************************/

    @Override
    public void plotGraph() {
        IntervalType intervalType = intervalTypeSelector.getSelectedItem();

        if (intervalType == null)
            throw new NullPointerException("One or more required fields have not been set.");

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
                System.out.println("Adding line for category value: " + categoryValue);
                lineChart.getData().add(perCategoryValueSeries.get(categoryValue));
            }
        }
    }

    /**
     *
     * @return
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
     *
     * @param category
     * @return
     */
    private Map<String, XYChart.Series<Number, Number>> generateTimeSeriesPerCategory(DataCategory category) {
        IntervalType intervalType = intervalTypeSelector.getSelectedItem();

        // For each value the interval may take on, count the number of crime records which have that value
        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();
        HashMap<Object, HashMap<Number, Integer>> perCategoryValueCounts = new HashMap<>();
        for (CrimeRecord record : records) {
            // Get the category value of the record
            Object categoryValue = category.getRecordValue(record);

            // Get the time interval value of the record
            Calendar date = record.getDate();
            Number recordIntervalValue = intervalType.getIntervalValue(date);

            // If the category value has already been encountered
            if (perCategoryValueCounts.containsKey(category)) {
                // Get the value count map for that category value
                HashMap<Number, Integer> valueCounts = perCategoryValueCounts.get(categoryValue);
                // Increment the count for the interval value of the record
                if (valueCounts.containsKey(recordIntervalValue))
                    valueCounts.put(recordIntervalValue, valueCounts.get(recordIntervalValue) + 1);
                else
                    valueCounts.put(recordIntervalValue, 1);

            // If the category value has not been encountered yet
            } else {
                System.out.println("Found new category value: " + categoryValue.toString());
                // Create a new interval value count map for this category value
                HashMap<Number, Integer> valueCounts = new HashMap<>();
                valueCounts.put(recordIntervalValue, 1);
                perCategoryValueCounts.put(categoryValue, valueCounts);
            }
        }

        HashMap<String, XYChart.Series<Number, Number>> categoryValueSeries = new HashMap<>();

        // Construct a data series for each category value
        for (Object categoryValue : perCategoryValueCounts.keySet()) {
            System.out.println("Creating dataSeries for category value: " + categoryValue.toString());
            HashMap<Number, Integer> valueCounts = perCategoryValueCounts.get(categoryValue);

            // Construct a series from the record counts for each value
            XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
            for (Number value : valueCounts.keySet()) {
                dataSeries.getData().add(new XYChart.Data<>(
                        value,
                        valueCounts.get(value)
                ));
            }
            categoryValueSeries.put(categoryValue.toString(), dataSeries);
        }

        return categoryValueSeries;
    }

}
