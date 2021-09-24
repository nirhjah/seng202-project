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

public class TimeSeriesGraph extends LineGraph {

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

    protected Label intervalTypeLabel = new Label("Interval Type");
    protected ComboBox<IntervalType> intervalTypeSelector = new ComboBox<>();
    protected IntervalType intervalType;

    /*************************************************************************************************************
     *                                   Graph and Option Initialization.                                        *
     *************************************************************************************************************/

    @Override
    public void initialize(BorderPane graphPane, VBox optionList) {
        lineChart.setTitle("Line Chart");
        graphPane.setCenter(lineChart);

        intervalTypeSelector.getItems().addAll(IntervalType.values());

        yAxisSelector.getItems().addAll(DataCategory.getCategories(Numerical.class));
        yAxisSelector.getItems().sort((i, j) -> {
            return i.toString().compareTo(j.toString());
        });

        populateOptions(optionList);
    }

    private void populateOptions(VBox optionList) {
        optionList.getChildren().clear();
        optionList.getChildren().addAll(intervalTypeLabel, intervalTypeSelector, ySelectorLabel, yAxisSelector);
    }

    private void retrieveOptions() {
        intervalType = intervalTypeSelector.getSelectionModel().getSelectedItem();
        yCategory = (Numerical) yAxisSelector.getSelectionModel().getSelectedItem();
    }


    /*************************************************************************************************************
     *                                   Graph Plotting.                                                         *
     *************************************************************************************************************/

    @Override
    public void plotGraph() {
        retrieveOptions();

        if (!isReady())
            throw new NullPointerException("One or more required fields have not been set.");

        DataCategory yCat = (DataCategory) yCategory;
        xAxis.setLabel(intervalType.toString());
        yAxis.setLabel(yCat.toString());

        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();

        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        for (CrimeRecord record : records) {
            Calendar date = record.getDate();
            dataSeries.getData().add(new XYChart.Data<>(
                    intervalType.getIntervalValue(date),
                    (Number) yCat.getRecordValue(record)
            ));
        }

        lineChart.getData().clear();
        lineChart.getData().addAll(dataSeries);
    }

    private boolean isReady() {
        if (intervalType != null && yCategory != null)
            return true;
        return false;
    }
}
