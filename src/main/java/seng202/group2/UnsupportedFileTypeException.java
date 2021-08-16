package seng202.group2;

public class UnsupportedFileTypeException extends Exception {

	public UnsupportedFileTypeException(String suffix) {
		super(suffix);
	}
	
}
