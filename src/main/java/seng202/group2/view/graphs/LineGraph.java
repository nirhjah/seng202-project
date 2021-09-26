package seng202.group2.view.graphs;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.DataCategory;
import seng202.group2.model.datacategories.Numerical;

import java.util.ArrayList;

/**
 *
 * @author Connor Dunlop
 */
public class LineGraph extends Graph {

    private static final String NAME = "Line";

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

        lineChart.setTitle("Line Chart");
        setChart(lineChart);

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
    protected LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

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
            dataSeries.getData().add(new XYChart.Data<>(
                    (Number) xCat.getRecordValue(record),
                    (Number) yCat.getRecordValue(record)
            ));
        }

        lineChart.getData().clear();
        lineChart.getData().addAll(dataSeries);
    }

}
