package seng202.group2.controller.viewcontollers;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * ImportController is the controller class for the crime import GUI.
 *
 * This class uses the 'import.fxml' FXML file for laying it out.
 * The GUI for this class allows a user-friendly way to get a filepath to a csv file to be imported,
 * either by browsing a file explorer for it or entering it manually in a textField.
 * This class uses a {@link FileChooser} to select a file using a file explorer.
 *
 * @author Sam Clark
 *
 */
public class ImportController {

	/** The textField used for entering a filepath. */
	@FXML private TextField importPathTextField;

	/**
	 * importFileFromField takes the giving filepath and passes it the importer.
	 *
	 * This method is called when the 'import' button is clicked. It takes the filepath from
	 * {@link ImportController#importPathTextField} and passes it to TODO add importer info
	 * to import the crime data into the database.
	 *
	 * TODO use this with connors import method.
	 */
	public void importFileFromField()
	{
		Stage stage = (Stage) importPathTextField.getScene().getWindow();
		
		stage.close();
		System.out.println("Retrieving file : " + importPathTextField.getText());
	}

	/**
	 * Opens a FileChooser to browse for a file to import.
	 *
	 * This method uses a {@link FileChooser} to select a file to import. When a file is selected
	 * it sets the text of {@link ImportController#importPathTextField} to the selected file path,
	 * ready to be imported.
	 */
	public void browseImportDirectory()
	{
		FileChooser fileChooser = new FileChooser();
		File chosenFile = fileChooser.showOpenDialog(null);
		
		if (chosenFile != null) {
			importPathTextField.setText(chosenFile.getAbsolutePath());
		}
	}
}
