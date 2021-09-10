package seng202.group2.view;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import seng202.group2.controller.DataObserver;
import seng202.group2.model.CrimeRecord;


/**
 * Graph Controller class which controls the data to be displayed on the graph, which type of graph to use and the categories used for the axis.
 * @author nse41
 *
 */

public class GraphController {
	
	private GraphType type;
	
	@FXML 
	private ComboBox<GraphType> selectGraph;
	
	@FXML
	private BorderPane borderPane;
	

	

	/**
	 * Creates the graph based on the graph type, categories and records
	 * TODO need to add records and categories (setCategories method)  
	 * @param type the type of the graph to be used
	 */
	public void createGraph(GraphType type) { 
		
		switch(type) {
		case BAR:
			//create bar
			CategoryAxis xAxis = new CategoryAxis();
	        NumberAxis yAxis = new NumberAxis();
	        BarChart<String,Number> barChart = new BarChart<String,Number>(xAxis,yAxis);
	        barChart.setTitle("Bar Graph");
	        xAxis.setLabel("X axis");       
	        yAxis.setLabel("Y axis");
	        borderPane.setCenter(barChart);
			break;
				
		case SCATTER:
			//create scatter
			NumberAxis xAxis2 = new NumberAxis();
	        NumberAxis yAxis2 = new NumberAxis();        
	        ScatterChart<Number,Number> scatterGraph = new ScatterChart<Number,Number>(xAxis2,yAxis2);
	        xAxis2.setLabel("X axis");                
	        yAxis2.setLabel("Y axis");
	        scatterGraph.setTitle("Scatter Graph");
	        borderPane.setCenter(scatterGraph);
			break;
			
		case LINE:
			NumberAxis xAxis3 = new NumberAxis();
		    NumberAxis yAxis3 = new NumberAxis();
		    xAxis3.setLabel("X Axis");
		    LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis3,yAxis3);
		    lineChart.setTitle("Line Chart");
		    borderPane.setCenter(lineChart);
			break;
		}
		
		
		
	}
	
	
	
	
	/**
	 * Sets the type of graph to use to display data
	 * @param type - the type of graph
	 * TODO need to fix this as it is returning null
	 */
	public void setGraphType(GraphType type) {
		this.type = selectGraph.getSelectionModel().getSelectedItem();

	}
	
	
	
	public GraphType getGraphType() {
		return type;
	}
	
	
	
	
	/**
	 * Listener for when selected graph type is changed
	 */
	public void comboBoxListener() {
		selectGraph.getSelectionModel().selectedItemProperty().addListener(
		         (ObservableValue<? extends GraphType> observable_value, GraphType old_type, GraphType new_type) -> {
		 			borderPane.setCenter(null); //clears whatever graph is in the pane in order to add a new one

		            setGraphType(new_type);
		            
		    		System.out.println("Selected graph type: " + getGraphType()); 
		    		createGraph(new_type);
		    		
		    		

		      });
	}
	
	/**
	 * Initialize method which automatically runs on stage startup - populates combobox with Enum values
	 */
	public void initialize() {
		
	
		
		selectGraph.getItems().setAll(GraphType.values());
		selectGraph.getSelectionModel().select(0);
		setGraphType(type);
		createGraph(type);
		
		comboBoxListener();


		
	}
	

}