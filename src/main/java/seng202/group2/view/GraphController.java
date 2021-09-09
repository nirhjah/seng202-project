package seng202.group2.view;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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

	
	/**
	 * Creates the graph based on the graph type, categories and records
	 * TODO need to add records and categories (setCategories method)  as well as create the graphs
	 * @param type the type of the graph to be used
	 */
	public void createGraph(GraphType type) { 
		
		switch(type) {
		case BAR:
			//create bar
			
			break;
		case SCATTER:
			//create scatter
			break;
		case LINE:
			//create line
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
	 * Initialize method which automatically runs on stage startup - populates combobox with Enum values
	 */
	public void initialize() {
		selectGraph.getItems().setAll(GraphType.values());
		selectGraph.getSelectionModel().select(0);
		setGraphType(type);
		
		selectGraph.getSelectionModel().selectedItemProperty().addListener(
		         (ObservableValue<? extends GraphType> observable_value, GraphType old_type, GraphType new_type) -> {
		            setGraphType(new_type);
		    		System.out.println("Selected graph type: " + getGraphType()); 

		      });

		
	}
	

}