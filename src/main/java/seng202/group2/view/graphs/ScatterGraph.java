package seng202.group2.view.graphs;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.DataCategory;
import seng202.group2.model.datacategories.DataClassification;
import seng202.group2.model.datacategories.Numerical;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Connor Dunlop
 */
public class ScatterGraph extends Graph {

    /*************************************************************************************************************
     *                                   Graph Settings and Options.                                             *
     *************************************************************************************************************/

    // X Axis Options
    protected Label xSelectorLabel = new Label("X Axis");
    protected ComboBox<DataCategory> xAxisSelector = new ComboBox<>();
    protected Numerical xCategory = null;

    // Y Axis Options
    protected Label ySelectorLabel = new Label("Y Axis");
    protected ComboBox<DataCategory> yAxisSelector = new ComboBox<>();
    protected Numerical yCategory = null;


    /*************************************************************************************************************
     *                                   Graph and Option Initialization.                                        *
     *************************************************************************************************************/

    @Override
    public void initialize(BorderPane graphPane, VBox optionList) {
        scatterChart.setTitle("Scatter Chart");
        graphPane.setCenter(scatterChart);

        xAxisSelector.getItems().addAll(DataCategory.getCategories(Numerical.class));
        xAxisSelector.getItems().sort((i, j) -> {
            return i.toString().compareTo(j.toString());
        });
        yAxisSelector.getItems().addAll(DataCategory.getCategories(Numerical.class));
        yAxisSelector.getItems().sort((i, j) -> {
            return i.toString().compareTo(j.toString());
        });

        populateOptions(optionList);
    }

    private void populateOptions(VBox optionList) {
        optionList.getChildren().clear();
        optionList.getChildren().addAll(xSelectorLabel, xAxisSelector, ySelectorLabel, yAxisSelector);
    }

    private void retrieveOptions() {
        xCategory = (Numerical) xAxisSelector.getSelectionModel().getSelectedItem();
        yCategory = (Numerical) yAxisSelector.getSelectionModel().getSelectedItem();
    }


    /*************************************************************************************************************
     *                                   Graph and Option Initialization.                                        *
     *************************************************************************************************************/

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);

    @Override
    public void plotGraph() {
        retrieveOptions();

        if (!isReady())
            throw new NullPointerException("One or more required fields have not been set.");

        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();

        DataCategory xCat = (DataCategory) xCategory;
        DataCategory yCat = (DataCategory) yCategory;
        xAxis.setLabel(xCat.toString());
        yAxis.setLabel(yCat.toString());

        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        for (CrimeRecord record : records) {
            dataSeries.getData().add(new XYChart.Data<>(
                    (Number) xCat.getRecordValue(record),
                    (Number) yCat.getRecordValue(record)
            ));
        }

        scatterChart.getData().clear();
        scatterChart.getData().addAll(dataSeries);
    }

    private boolean isReady() {
        if (xCategory != null && yCategory != null)
            return true;
        return false;
    }

}
