package seng202.group2;



import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import junit.framework.TestCase;

public class ConnorTestClassTest extends TestCase {
	private final PrintStream systemOut = System.out;
	private final ByteArrayOutputStream testStream = new ByteArrayOutputStream();
	
	
	public void captureSystemOut() {
		System.out.println("Comandeering output stream");
		System.setOut(new PrintStream(testStream));
	}
	
	
	public void testCountTo10() {
		captureSystemOut();
		
		ConnorTestClass c = new ConnorTestClass();
		c.countTo10();
		assertEquals("12345678910", testStream.toString().trim());
		
		returnSystemOut();
	}
	
	
	public void returnSystemOut() {
		System.setOut(systemOut);
		System.out.println("Output returned to system stream");
	}
	
}
