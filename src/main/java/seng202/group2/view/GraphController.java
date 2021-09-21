package seng202.group2.view;


import java.util.ArrayList;

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
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import seng202.group2.controller.DataObserver;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.DataCategory;
import seng202.group2.model.datacategories.UnsupportedCategoryException;


/**
 * Graph Controller class which controls the data to be displayed on the graph, which type of graph to use and the categories used for the axis.
 * @author nse41
 *
 */

public class GraphController{
	
	ArrayList<CrimeRecord> activeRecords = DBMS.getActiveData().getActiveRecords();

	
	private GraphType type;
	private DataCategory xAxisCategory;
	private DataCategory yAxisCategory;
	@FXML private ComboBox<GraphType> selectGraph;
	@FXML private BorderPane borderPane;
	@FXML private ComboBox<String> xCategory;
	@FXML private ComboBox<String> yCategory;
    XYChart.Series dataseries = new XYChart.Series();

	
	/**
	 * Creates the graph based on the graph type, categories and records
	 * @param type the type of the graph to be used
	 */
	public void createGraph(GraphType type) { 
		switch(type) {
		case BAR:
			CategoryAxis xAxis = new CategoryAxis();
	        NumberAxis yAxis = new NumberAxis();
	        BarChart<String,Number> barChart = new BarChart<String,Number>(xAxis,yAxis);
	        barChart.setTitle("Bar Graph");
	        xAxis.setLabel(getXCategory().getSQL());       
	        yAxis.setLabel(getYCategory().getSQL());
	        
	      //  plotRecords();

	        dataseries.setName("Data"); 
		
	        barChart.getData().addAll(dataseries);

	        
	        
	        borderPane.setCenter(barChart);
			break;
				
		case SCATTER:
			NumberAxis xAxis2 = new NumberAxis();
	        NumberAxis yAxis2 = new NumberAxis();  

	        	        
	        ScatterChart<Number,Number> scatterGraph = new ScatterChart<Number,Number>(xAxis2,yAxis2);
	        xAxis2.setLabel(getXCategory().getSQL());                
	        yAxis2.setLabel(getYCategory().getSQL());
	        scatterGraph.setTitle(getYCategory().getSQL() + " vs " + getXCategory().getSQL()); //toString
	        plotRecords();

	        dataseries.setName("Data"); 
	        

	        scatterGraph.getData().addAll(dataseries);



	        borderPane.setCenter(scatterGraph);
			break;
			
		case LINE:
			NumberAxis xAxis3 = new NumberAxis();
		    NumberAxis yAxis3 = new NumberAxis();

		    xAxis3.setLabel(getXCategory().getSQL());
		    LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis3,yAxis3);
		    lineChart.setTitle("Line Chart");
		    
	        dataseries.setName("Data"); 
	        lineChart.getData().addAll(dataseries);


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
	
	/**
	 * Get the current graph type
	 * @return the graph type
	 */
	public GraphType getGraphType() {
		return type;
	}
	
	
	/**
	 * Listener for when selected graph type is changed
	 */
	public void selectGraphListener() {
		selectGraph.getSelectionModel().selectedItemProperty().addListener(
		         (ObservableValue<? extends GraphType> observable_value, GraphType old_type, GraphType new_type) -> {
		 			borderPane.setCenter(null); //clears whatever graph is in the pane in order to add a new graph
		            setGraphType(new_type);
		    		System.out.println("Selected graph type: " + getGraphType()); //testing purposes
		    		createGraph(new_type);
		      });
	}
	
	/**
	 * Setting the x category
	 * @param xAxisCategory the x category from the combobox
	 */
	public void setXAxisCategory(DataCategory xAxisCategory) {
		this.xAxisCategory = xAxisCategory;
	}
	
	/**
	 * Setting the y category
	 * @param yAxisCategory the y category from the combobox
	 */
	public void setYAxisCategory(DataCategory yAxisCategory) {
		this.yAxisCategory = yAxisCategory;
	}
	
	/**
	 * Get the y category 
	 * @return y category
	 */
	public DataCategory getXCategory() {
		return xAxisCategory;
	}
	
	/**
	 * Get the x category
	 * @return x category
	 */
	public DataCategory getYCategory() {
		return yAxisCategory;
	}
	
	
	/**
	 * Listener for when selected x category is changed
	 * @return xAxisCategory - the DataCategory for the xAxis
	 */
	public DataCategory selectXCategoryListener() {
		
		xCategory.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			   try {
				DataCategory xAxisCategory = DataCategory.getCategoryFromString(newValue);
				setXAxisCategory(xAxisCategory);
				createGraph(type);
			} catch (UnsupportedCategoryException e) {
				e.printStackTrace();
			}
			}); 
		return xAxisCategory;   
	}
	/**
	 * Listener for when selected y category is changed
	 * @return yAxisCategory - the DataCategory for the yAxis
	 */
	public DataCategory selectYCategoryListener() {
		
		yCategory.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
				try {
					DataCategory yAxisCategory = DataCategory.getCategoryFromString(newValue);
					setYAxisCategory(yAxisCategory);
					createGraph(type);
				} catch (UnsupportedCategoryException e) {
					e.printStackTrace();
				}
			}); 
		return yAxisCategory;   	
	}

	
	/**
	 * Initialize method
	 */
	public void initialize() {
			
		
		ObservableList<String> categories = 
			    FXCollections.observableArrayList(      
			        "DATEOFOCCURRENCE",
			        "PRIMARYDESCRIPTION",
			        "BEAT",
			        "WARD"
			        
			    );
		
		xCategory.getItems().setAll(categories);
		yCategory.getItems().setAll(categories);
		selectGraph.getItems().setAll(GraphType.values());
		selectXCategoryListener();
		selectYCategoryListener();
		
		selectGraphListener();
		setGraphType(type);

	}
	
	
	/**
	 * Loops through active records to grab each record's record value from the xcategory and ycategory and adds those to dataseries 
	 */
	public void plotRecords() {
		 
		ArrayList<CrimeRecord> activeRecords = DBMS.getActiveData().getActiveRecords();
		
		
		dataseries.getData().clear();
		System.out.println("cleared"); //testing

		
		for (CrimeRecord record: activeRecords) {
			
				try {
					dataseries.getData().add(new XYChart.Data(getXCategory().getRecordValue(record), getYCategory().getRecordValue(record)));
				} catch (UnsupportedCategoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

		

		
	}
	
}}