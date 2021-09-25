package seng202.group2.view.graphs;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

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
        super.initialize(graphPane, optionList);

        lineChart.setTitle("Number of Crimes over Time");
        graphPane.setCenter(lineChart);

        clearOptions();
        addOptions(intervalTypeSelector);
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

        // Plot data set
        lineChart.getData().clear();
        lineChart.getData().addAll(dataSeries);
    }

}
