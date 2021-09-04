package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.model.CrimeRecord;
import seng202.group2.model.datacategories.Latitude;

/**
 * Unit tests for Latitude data category class.
 * 
 * @author Connor Dunlop
 *
 */
class LatitudeTest {

	/** A crime record to use when testing methods of DataCategory */
	private CrimeRecord record;
	
	private Latitude category = new Latitude();
	
	/**
	 * Reset the values of the test record's attributes to default before each test.
	 */
	@BeforeEach
	void initialiseRecord() {
		record = new CrimeRecord();
	}

	/**
	 * Checks the setCategoryValue method of Latitude sets the attribute value 
	 * of a crime record correctly.
	 */
	@Test
	void testSetCategoryValue_ValidData() {
		Float latitude = 41.748486f;
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, latitude);
		
		// Check value set correctly
		assertEquals(latitude, record.getLatitude());
	}
	
	/**
	 * Checks setCategoryValue method throws an error when using invalid data.
	 */
	@Test
	void testSetCategoryValue_InvalidData() {
		Integer latitude = 41;
		
		// Try to set value of record attribute to incorrect data type
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(record, latitude);
		});
	}
	
	/**
	 * Checks setCategoryValue with null data.
	 */
	@Test
	void testSetCategoryValue_NullData() {
		Float latitude = null;
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, latitude);
		
		// Check value set correctly
		assertEquals(latitude, record.getLatitude());
	}
	
	/**
	 * Checks setCategoryValue with null CrimeRecord.
	 */
	@Test
	void testSetCategoryValue_NullRecord() {
		Float latitude = 41.748486f;
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(null, latitude);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of Latitude gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		Float latitude = 41.748486f;
		
		// Set value of record attribute
		record.setLatitude(latitude);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(latitude, category.getCategoryValue(record));
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof Float);
	}
	
	/**
	 * Checks the getCategoryValue method of Latitude throws an error when
	 * passing a null record.
	 */
	@Test
	void testGetCategoryValue_NullRecord() {
		// Check value of record attribute gotten using DataCategory method is correct
		assertThrows(IllegalArgumentException.class, () -> {
			category.getCategoryValue(null);
		});
	}
	
	/**
	 * Checks the parseString method of Latitude returns the date correctly
	 * parsed into a Boolean.
	 */
	@Test
	void testParseString_Valid() {
		String[] validLatitudes = {
			"-90.0000",
			"90.00000",
			"0",
			"0.000",
			"000.000",
			"001.100",
			"000.100",
			"000.001",
		};
		
		Float[] parsedLatitudes = {
			-90.0f,
			90.0f,
			0.0f,
			0.0f,
			0.0f,
			1.1f,
			0.1f,
			0.001f
		};
		
		for (int i = 0; i < validLatitudes.length; i++) {
			assertTrue(parsedLatitudes[i].equals(category.parseString(validLatitudes[i])));
		}
	}
	
	/**
	 * Checks the parseString method of Latitude throws an exception when passed invalid strings.
	 */
	@Test
	void testParseString_Invalid() {
		String[] invalidLatitudes = {
			"-360.0",
			"360.0",
			"notfloat"
		};
		
		for (String invalidLatitude : invalidLatitudes) {
			assertThrows(IllegalArgumentException.class, () -> {
				category.parseString(invalidLatitude);
			});
		}
	}
	
	/**
	 * Checks the parseString method of Latitude returns null when passed an empty string.
	 */
	@Test
	void testParseString_Empty() {
		String latitude = "";
		
		assertEquals(null, category.parseString(latitude));
	}
	
	/**
	 * Checks the parseString method of Latitude throws an error correctly when passed null.
	 */
	@Test
	void testParseString_Null() {
		assertThrows(IllegalArgumentException.class, () -> {
			category.parseString(null);
		});
	}

}
