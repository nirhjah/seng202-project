package seng202.group2.view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import seng202.group2.controller.DataObserver;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.DataCategory;
import seng202.group2.model.datacategories.DataClassification;
import seng202.group2.model.datacategories.UnsupportedCategoryException;
import seng202.group2.view.graphs.Field;
import seng202.group2.view.graphs.Graph;


/**
 * Graph Controller class which controls the data to be displayed on the graph, which type of graph to use and the categories used for the axis.
 * @author nse41
 * @author Connor Dunlop
 */

public class GraphController extends DataObserver {

	@FXML private BorderPane borderPane;

	private Map<String, Class<? extends Graph>> graphTypes = new HashMap<>();
	@FXML private ComboBox<String> selectGraph;

	@FXML private ComboBox<DataCategory> xCategory;
	@FXML private ComboBox<DataCategory> yCategory;

	/**
	 * Initialize method
	 */
	public void initialize() {
		DBMS.getActiveData().addObserver(this);

		Set<DataCategory> categories = DataCategory.getCategories();
		for (Class<? extends Graph> graphType : Graph.getGraphTypes()) {
			graphTypes.put(graphType.toString(), graphType);
		}

		xCategory.getItems().setAll(categories);
		yCategory.getItems().setAll(categories);
		selectGraph.getItems().setAll(graphTypes.keySet());
	}

	@FXML public void plotGraph() {
		// Make a new graph of the correct type
		Graph graph = null;
		try {
			graph = Graph.newGraph(graphTypes.get(selectGraph.getSelectionModel().getSelectedItem()));
		} catch (InstantiationException e) {
			e.printStackTrace();
			return;
		}

		// Initialize it
		graph.initialize(borderPane);

		// Set the graph's field categories
		Set<Field<? extends DataClassification>> fields = graph.getFields();
		for (Field<? extends DataClassification> field : fields) {
			if (field.name == "X Axis")
				field.setDataCategory(xCategory.getSelectionModel().getSelectedItem());
			else if (field.name == "Y Axis")
				field.setDataCategory(yCategory.getSelectionModel().getSelectedItem());
		}

		// Plot graph
		graph.plotGraph();
	}

	@Override
	public void updateModel() {

	}
}