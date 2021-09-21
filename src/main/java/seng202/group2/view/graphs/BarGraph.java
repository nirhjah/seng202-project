package seng202.group2.view.graphs;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.Categorical;
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
public class BarGraph extends Graph {

    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();

    private Field<Categorical> xField = Field.newField("X Axis", Categorical.class);
    private Field<Numerical> yField = Field.newField("Y Axis", Numerical.class);

    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    public void initialize(BorderPane borderPane) {
        barChart.setTitle("Bar Graph");
        borderPane.setCenter(barChart);
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
        DataCategory xCat = (DataCategory) xField.getDataCategory();
        DataCategory yCat = (DataCategory) yField.getDataCategory();

        xAxis.setLabel(xCat.toString());
        yAxis.setLabel(yCat.toString());

        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();

        XYChart.Series dataSeries = new XYChart.Series();
        for (CrimeRecord record : records) {
            try {
                dataSeries.getData().add(new XYChart.Data(
                        xCat.getRecordValue(record),
                        yCat.getRecordValue(record)
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        barChart.getData().clear();
        barChart.getData().addAll(dataSeries);
    }




/*
    //  plotRecords();
	        dataseries.setName("Data");
	        barChart.getData().addAll(dataseries);
	        */
}
