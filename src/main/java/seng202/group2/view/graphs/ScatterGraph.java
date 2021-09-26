package seng202.group2.view.graphs;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.DataCategory;
import seng202.group2.model.datacategories.Numerical;

import java.util.ArrayList;

/**
 * A simple Graph type used to plot a scatter chart.
 * Populates the graph options pane with two selection options, one for selecting a DataCategory for the X Axis,
 * another for selecting a DataCategory for the Y Axis.
 * The DataCategorys for each axis are Numerical.
 *
 * @author Connor Dunlop
 */
public class ScatterGraph extends Graph {

    private static final String NAME = "Scatter";

    @Override
    public String getName() {
        return this.NAME;
    }

    /*************************************************************************************************************
     *                                   Graph Settings and Options.                                             *
     *************************************************************************************************************/

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

        scatterChart.setTitle("Scatter Chart");
        setChart(scatterChart);

        xAxis.setForceZeroInRange(false);
        yAxis.setForceZeroInRange(false);

        // Sort categories alphanumerically
        ArrayList<DataCategory> categories = new ArrayList<>(DataCategory.getCategories(Numerical.class));
        categories.sort((i, j) -> {
            return i.toString().compareTo(j.toString());
        });

        // Add categories to option lists
        xAxisSelector.setItems(categories);
        yAxisSelector.setItems(categories);

        clearOptions();
        addOptions(xAxisSelector, yAxisSelector);
    }


    /*************************************************************************************************************
     *                                   Graph and Option Initialization.                                        *
     *************************************************************************************************************/

    protected NumberAxis xAxis = new NumberAxis();
    protected NumberAxis yAxis = new NumberAxis();
    protected ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);

    @Override
    public void plotGraph() {
        DataCategory xCat = xAxisSelector.getSelectedItem();
        DataCategory yCat = yAxisSelector.getSelectedItem();

        // Check all required fields have been set
        if (!optionStatesValid()) {
            displayInvalidOptionsDialogue();
            return;
        }

        // Set axis labels
        xAxis.setLabel(xCat.toString());
        yAxis.setLabel(yCat.toString());

        // Generate data set from records
        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        for (CrimeRecord record : records) {
            Number xValue = (Number) xCat.getRecordValue(record);
            Number yValue = (Number) yCat.getRecordValue(record);

            if (xValue == null || yValue == null)
                continue;

            dataSeries.getData().add(new XYChart.Data<>(
                    xValue,
                    yValue
            ));
        }

        // Plot data set
        scatterChart.getData().clear();
        scatterChart.getData().addAll(dataSeries);
    }
}
