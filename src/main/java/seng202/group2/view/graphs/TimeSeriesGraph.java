package seng202.group2.view.graphs;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.DataCategory;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A LineGraph type which is used to plot a graph over time.
 * Defines an enumeration of intervals which the user may choose between.
 * When plotting records using a TimeSeriesGraph, the time domain is partitioned into intervals.
 *
 * TODO Add more time intervals - continuous, custom, more primitives such as day of week, etc.
 *
 * @author Connor Dunlop
 */
public class TimeSeriesGraph extends LineGraph {

    private static final String NAME = "Time Series";

    @Override
    public String getName() {
        return this.NAME;
    }

    /*************************************************************************************************************
     *                                   Graph Settings and Options.                                             *
     *************************************************************************************************************/

    protected enum IntervalType {

        HOUR_OF_DAY {
            @Override
            public String toString() {
                return "Hour of Day";
            }

            @Override
            public Number getIntervalValue(Calendar date) {
                if (date == null)
                    return null;
                return date.get(Calendar.HOUR_OF_DAY);
            }
        },

        DAY_OF_MONTH {
            @Override
            public String toString() {
                return "Day of Month";
            }

            @Override
            public Number getIntervalValue(Calendar date) {
                if (date == null)
                    return null;
                return date.get(Calendar.DAY_OF_MONTH);
            }
        },

        MONTH_OF_YEAR {
            @Override
            public String toString() {
                return "Month of Year";
            }

            @Override
            public Number getIntervalValue(Calendar date) {
                if (date == null)
                    return null;
                return date.get(Calendar.MONTH) + 1;
            }
        },

        YEAR {
            @Override
            public String toString() {
                return "Year";
            }

            @Override
            public Number getIntervalValue(Calendar date) {
                if (date == null)
                    return null;
                return date.get(Calendar.YEAR);
            }
        };

        public abstract String toString();
        public abstract Number getIntervalValue(Calendar date);
    }

    protected SelectionGraphOption<IntervalType> intervalTypeSelector = new SelectionGraphOption<>("Interval Type", true);


    /*************************************************************************************************************
     *                                   Graph and Option Initialization.                                        *
     *************************************************************************************************************/

    @Override
    public void initialize(BorderPane graphPane, VBox optionList) {
        super.initialize(graphPane, optionList);

        lineChart.setTitle("Time Series Chart");

        intervalTypeSelector.setItems(IntervalType.values());

        clearOptions();
        addOptions(intervalTypeSelector, yAxisSelector);
    }


    /*************************************************************************************************************
     *                                   Graph Plotting.                                                         *
     *************************************************************************************************************/

    @Override
    public void plotGraph() {
        IntervalType intervalType = intervalTypeSelector.getSelectedItem();
        DataCategory yCategory = yAxisSelector.getSelectedItem();

        // Check all required fields have been set
        if (!optionStatesValid()) {
            displayInvalidOptionsDialogue();
            return;
        }

        // Set Axis labels
        xAxis.setLabel(intervalType.toString());
        yAxis.setLabel(yCategory.toString());

        // Generate data set from crime records
        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        for (CrimeRecord record : records) {
            Calendar date = record.getDate();

            Number xValue = intervalType.getIntervalValue(date);
            Number yValue = (Number) yCategory.getRecordValue(record);

            if (xValue == null || yValue == null)
                continue;

            dataSeries.getData().add(new XYChart.Data<>(
                    xValue,
                    yValue
            ));
        }

        // Plot data on chart
        lineChart.getData().clear();
        lineChart.getData().addAll(dataSeries);
    }

}