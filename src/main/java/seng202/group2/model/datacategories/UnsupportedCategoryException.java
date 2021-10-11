package seng202.group2.model.datacategories;

/**
 * This exception type is thrown when a process tries to perform an operation on or using
 * data from a category that is unsupported, or when trying to add data to an attribute
 * corresponding to a DataCategory which is unsupported or does not exist. 
 * @author Connor Dunlop
 *
 */
public class UnsupportedCategoryException extends Exception {
	/**
	 * A simple wrapper initializer for creating this exception with an error message.
	 * @param category A string representing the category causing the error.
	 */
	public UnsupportedCategoryException(String category) {
		super(category);
	}
}
