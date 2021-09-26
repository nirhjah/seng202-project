package seng202.group2.view;


import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group2.controller.DataObserver;
import seng202.group2.model.DBMS;
import seng202.group2.view.graphs.Graph;


/**
 * Graph Controller class which controls the data to be displayed on the graph, which type of graph to use and the categories used for the axis.
 * @author nse41
 * @author Connor Dunlop
 */

public class GraphController extends DataObserver {

	@FXML private BorderPane graphPane;
	@FXML private BorderPane controlPane;
	@FXML private VBox optionList = new VBox();

	private Map<String, Graph> graphTypes = new HashMap<>();
	@FXML private ComboBox<String> graphTypeSelector;

	private Graph graph;

	/**
	 * Initialize method
	 */
	public void initialize() {
		for (Graph graphType : Graph.getGraphTypes()) {
			graphTypes.put(graphType.NAME, graphType);
		}
		graphTypeSelector.getItems().addAll(graphTypes.keySet());
	}

	@FXML public void selectGraphType() {
		try {
			graph = Graph.newGraph(graphTypes.get(graphTypeSelector.getSelectionModel().getSelectedItem()));
		} catch (InstantiationException e) {
			e.printStackTrace();
			return;
		}

		graphPane.setCenter(null);

		optionList.getChildren().clear();
		graph.initialize(graphPane, optionList);
	}

	@FXML public void hideControlMenu() {
		controlPane.setVisible(false);
	}

	@FXML public void showControlMenu() {
		controlPane.setVisible(true);
	}

	@FXML public void plotGraph() {
		graph.plotGraph();
	}

	@Override
	public void activeDataUpdate() {
		plotGraph();
	}

	@Override
	public void selectedRecordsUpdate() {
		return;
	}

	@Override
	public void frameUpdate() {
		return;
	}
}