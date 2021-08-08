package seng202.group2;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class ConnorTestClassTest extends TestCase {
	private final PrintStream systemOut = System.out;
	private final ByteArrayOutputStream testStream = new ByteArrayOutputStream();
	
	@BeforeEach
	public void captureSystemOut() {
		System.out.println("Comandeering output stream");
		System.setOut(new PrintStream(testStream));
	}
	
	@Test
	void testCountTo10() {
		ConnorTestClass c = new ConnorTestClass();
		
		c.countTo10();
		
		assertEquals("12345678910", testStream.toString().trim());
	}
	
	@AfterEach
	public void returnSystemOut() {
		System.setOut(systemOut);
		System.out.println("Output returned to system stream");
		ConnorTestClass c = new ConnorTestClass();
	}
	
}
