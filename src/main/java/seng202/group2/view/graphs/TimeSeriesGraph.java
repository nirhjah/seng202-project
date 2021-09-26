package seng202.group2.view.graphs;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.DataCategory;

import java.util.ArrayList;
import java.util.Calendar;

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
        CONTINUOUS {
            @Override
            public String toString() {
                return "Continuous";
            }

            @Override
            public Number getIntervalValue(Calendar date) {
                return date.toInstant().getEpochSecond();
            }
        },

        HOUR_OF_DAY {
            @Override
            public String toString() {
                return "Hour of Day";
            }

            @Override
            public Number getIntervalValue(Calendar date) {
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
                return date.get(Calendar.YEAR);
            }
        };

        public abstract String toString();
        public abstract Number getIntervalValue(Calendar date);
    }

    protected SelectionGraphOption<IntervalType> intervalTypeSelector = new SelectionGraphOption<>("Interval Type");


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

        if (intervalTypeSelector.getSelectedItem() == null || yAxisSelector.getSelectedItem() == null)
            throw new NullPointerException("One or more required fields have not been set.");

        // Set Axis labels
        xAxis.setLabel(intervalType.toString());
        yAxis.setLabel(yCategory.toString());

        // Generate data set from crime records
        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        for (CrimeRecord record : records) {
            Calendar date = record.getDate();
            dataSeries.getData().add(new XYChart.Data<>(
                    intervalType.getIntervalValue(date),
                    (Number) yCategory.getRecordValue(record)
            ));
        }

        // Plot data on chart
        lineChart.getData().clear();
        lineChart.getData().addAll(dataSeries);
    }

}
