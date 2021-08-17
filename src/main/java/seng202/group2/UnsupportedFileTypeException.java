package seng202.group2;

/**
 * This exception type is thrown when trying to import a file which
 * has an unsupported file format.
 * @author Connor Dunlop
 *
 */
public class UnsupportedFileTypeException extends Exception {

	public UnsupportedFileTypeException(String suffix) {
		super(suffix);
	}
	
}
