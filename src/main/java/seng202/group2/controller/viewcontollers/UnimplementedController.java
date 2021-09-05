package seng202.group2.controller.viewcontollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This is a temporary JavaFx Controller.
 *
 * This Controller controls the unimplemented.fxml file.
 * An 'unimplemented' window is opened whenever an interaction occurs with a GUI component that
 * is yet to be implemented. This window consists of an informative message and a close button.
 * When the close button is clicked the window simply closes returning the user to where they were.
 *
 * If an unimplemented window occurs in a released version it needs to me finished.
 *
 * @author Sam Clark
 */
public class UnimplementedController 
{
	/** The button for closing this window */
	@FXML private Button closeButton;

	/**
	 * Closes the 'unimplemented' window returning the user to where they were.
	 */
	public void closeWindow()
	{
		Stage stage = (Stage) closeButton.getScene().getWindow();
		
		stage.close();
	}
}
