package seng202.group2.view.graphs;

import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.DataCategory;
import seng202.group2.model.datacategories.Numerical;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CrimesOverTimeGraph extends TimeSeriesGraph {

    /*************************************************************************************************************
     *                                   Graph Settings and Options.                                             *
     *************************************************************************************************************/



    /*************************************************************************************************************
     *                                   Graph and Option Initialization.                                        *
     *************************************************************************************************************/

    @Override
    public void initialize(BorderPane graphPane, VBox optionList) {
        lineChart.setTitle("Line Chart");
        graphPane.setCenter(lineChart);

        intervalTypeSelector.getItems().addAll(TimeSeriesGraph.IntervalType.values());

        yAxisSelector.getItems().addAll(DataCategory.getCategories(Numerical.class));
        yAxisSelector.getItems().sort((i, j) -> {
            return i.toString().compareTo(j.toString());
        });

        populateOptions(optionList);
    }

    private void populateOptions(VBox optionList) {
        optionList.getChildren().clear();
        optionList.getChildren().addAll(intervalTypeLabel, intervalTypeSelector);
    }

    private void retrieveOptions() {
        intervalType = intervalTypeSelector.getSelectionModel().getSelectedItem();
    }


    /*************************************************************************************************************
     *                                   Graph Plotting.                                                         *
     *************************************************************************************************************/

    @Override
    public void plotGraph() {
        retrieveOptions();

        if (!isReady())
            throw new NullPointerException("One or more required fields have not been set.");

        xAxis.setLabel(intervalType.toString());

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

        lineChart.getData().clear();
        lineChart.getData().addAll(dataSeries);
    }

    private boolean isReady() {
        if (intervalType != null)
            return true;
        return false;
    }

}
