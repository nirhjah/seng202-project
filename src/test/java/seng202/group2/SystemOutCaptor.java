package seng202.group2;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * This class is used to test classes/methods which print output to the System.out output stream.
 * 
 * Usage:
 * 1. Call captureSystemOut to begin capturing output printed to the System.out output stream.
 * While System.out is captured by this class, no output is printed to the standard output stream (the console).
 * 2. Call readSystemOut to get all output currently stored in the stream as a string.
 * This will also clear the contents of the output stream.
 * 3. Call releaseSystemOut to return control of System.out to the standard output stream (the console).
 * After releasing System.out, output printed to the stream will be printed to the standard output again (the console).
 * 
 * Note: This class uses a static implementation of stream capture to prevent System.out being
 * set to the incorrect output stream, as when using multiple instances of this class concurrently, the order which
 * instances release System.out is not consistent. The class therefore cannot be instantiated.
 * 
 * @author Connor Dunlop
 */
public class SystemOutCaptor {

	/** A variable to keep the standard output stream in memory while this class is capturing System.out */
	private static PrintStream systemOut = null;
	/** A stream used to replace the standard output stream. When capturing System.out, this is the stream that output will be printed to. */
	private static ByteArrayOutputStream captorStream = new ByteArrayOutputStream();
	
	/** This class may not be instantiated. Instantiation is prevented by setting the visibility of the constructor to private. */
	private SystemOutCaptor() {}

	/**
	 * Begin capturing output printed to System.out
	 * After calling this method, anything printed to System.out will be printed to captorStream.
	 * Anything printed to System.out while this class is capturing output to it is NOT printed to the standard output stream (the console). 
	 */
	public static void captureSystemOut() {
		// Check that SystemOutCaptor is not already capturing System.out
		if (systemOut != null)
			throw new Error("System.out has already been captured by an instance of SystemOutCaptor.\n"
					+ "SystemOutCaptor must release System.out before it can be captured again.");
		
		System.out.println("Capturing System.out");
		systemOut = System.out;
		System.setOut(new PrintStream(captorStream));
	}

	/**
	 * Stop capturing output printed to System.out and set System.out back to the standard output stream (the console).
	 * Anything printed to System.out after this will be printed to the standard output stream (the console).
	 */
	public static void releaseSystemOut() {
		System.setOut(systemOut);
		systemOut = null;
		System.out.println("System.out has been released");
	}
	
	/**
	 * Reads the output captured from System.out
	 * Calling this method clears the captor stream, such that any subsequent output captured by this class is not
	 * prefixed by the output currently stored by the captor stream.
	 * 
	 * @return A string containing all output captured by this class from the System.out stream
	 */
	public static String readSystemOutput() {
		String output = captorStream.toString();
		captorStream.reset();
		return output;
	}
}
