package seng202.group2.view.graphs;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BorderPane;
import seng202.group2.model.datacategories.Categorical;
import seng202.group2.model.datacategories.DataClassification;
import seng202.group2.model.datacategories.Numerical;

import java.util.Arrays;
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

    BarGraph(BorderPane borderPane) {
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
        xAxis.setLabel(xField.getDataCategory().toString());
        yAxis.setLabel(yField.getDataCategory().toString());
    }




/*
    //  plotRecords();
	        dataseries.setName("Data");
	        barChart.getData().addAll(dataseries);
	        */
}
