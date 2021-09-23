package seng202.group2.view.graphs;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
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

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();

    protected Field<Numerical> xField = Field.newField("X Axis", Numerical.class);
    protected Field<Numerical> yField = Field.newField("Y Axis", Numerical.class);

    ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);

    @Override
    public void initialize(BorderPane borderPane) {
        scatterChart.setTitle("Scatter Chart");
        borderPane.setCenter(scatterChart);
    }

    @Override
    public Set<Field<? extends DataClassification>> getFields() {
        HashSet<Field<? extends DataClassification>> fields = new HashSet<>();
        fields.add(xField);
        fields.add(yField);
        return fields;
    }

    @Override
    public void plotGraph() {
        if (!isReady())
            throw new NullPointerException("One or more required fields have not been set.");

        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();

        DataCategory xCat = xField.getDataCategory();
        DataCategory yCat = yField.getDataCategory();
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
        if (xField.getDataCategory() != null && yField.getDataCategory() != null)
            return true;
        return false;
    }

}
