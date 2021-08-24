package seng202.group2.datacategories;

/**
 * This exception type is thrown when a process tries to perform an operation on or using
 * data from a category that is unsupported, or when trying to add data to an attribute
 * corresponding to a DataCategory which is unsupported or does not exist. 
 * @author Connor Dunlop
 *
 */
public class UnsupportedCategoryException extends Exception {
	
	public UnsupportedCategoryException(String category) {
		super(category);
	}
}
