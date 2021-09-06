package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.model.CrimeRecord;
import seng202.group2.model.datacategories.Longitude;

/**
 * Unit tests for Longitude data category class.
 * 
 * @author Connor Dunlop
 *
 */
class LongitudeTest {

	/** A crime record to use when testing methods of DataCategory */
	private CrimeRecord record;
	
	private Longitude category = new Longitude();
	
	/**
	 * Reset the values of the test record's attributes to default before each test.
	 */
	@BeforeEach
	void initialiseRecord() {
		record = new CrimeRecord();
	}

	/**
	 * Checks the setCategoryValue method of Longitude sets the attribute value 
	 * of a crime record correctly.
	 */
	@Test
	void testSetCategoryValue_ValidData() {
		Float longitude = -87.602675f;
		
		// Set value of record attribute using  DataCategory method
		category.setRecordValue(record, longitude);
		
		// Check value set correctly
		assertEquals(longitude, record.getLongitude());
	}
	
	/**
	 * Checks setCategoryValue method throws an error when using invalid data.
	 */
	@Test
	void testSetCategoryValue_InvalidData() {
		Integer longitude = -87;
		
		// Try to set value of record attribute to incorrect data type
		assertThrows(IllegalArgumentException.class, () -> {
			category.setRecordValue(record, longitude);
		});
	}
	
	/**
	 * Checks setCategoryValue with null data.
	 */
	@Test
	void testSetCategoryValue_NullData() {
		Float longitude = null;
		
		// Set value of record attribute using  DataCategory method
		category.setRecordValue(record, longitude);
		
		// Check value set correctly
		assertEquals(longitude, record.getLongitude());
	}
	
	/**
	 * Checks setCategoryValue with null CrimeRecord.
	 */
	@Test
	void testSetCategoryValue_NullRecord() {
		Float longitude = -87.602675f;
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setRecordValue(null, longitude);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of Longitude gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		Float longitude = -87.602675f;
		
		// Set value of record attribute
		record.setLongitude(longitude);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(longitude, category.getRecordValue(record));
		// Check return type of DataCategory method correct
		assertTrue(category.getRecordValue(record) instanceof Float);
	}
	
	/**
	 * Checks the getCategoryValue method of Longitude throws an error when
	 * passing a null record.
	 */
	@Test
	void testGetCategoryValue_NullRecord() {
		// Check value of record attribute gotten using DataCategory method is correct
		assertThrows(IllegalArgumentException.class, () -> {
			category.getRecordValue(null);
		});
	}
	
	/**
	 * Checks the parseString method of Longitude returns the date correctly
	 * parsed into a Boolean.
	 */
	@Test
	void testParseString_Valid() {
		String[] validLongitudes = {
			"-180.0",
			"-180",
			"-90.0000",
			"90.00000",
			"180.0",
			"79.9999",
			"0",
			"0.000",
			"000.000",
			"001.100",
			"000.100",
			"000.001",
		};
		
		Float[] parsedLongitudes = {
			-180.0f,
			-180.0f,
			-90.0f,
			90.0f,
			180.0f,
			79.9999f,
			0.0f,
			0.0f,
			0.0f,
			1.1f,
			0.1f,
			0.001f
		};
		
		for (int i = 0; i < validLongitudes.length; i++) {
			assertTrue(parsedLongitudes[i].equals(category.parseString(validLongitudes[i])));
		}
	}
	
	/**
	 * Checks the parseString method of Longitude throws an exception when passed invalid strings.
	 */
	@Test
	void testParseString_Invalid() {
		String[] invalidLongitudes = {
			"-360.0",
			"-180.00001",
			"360.0",
			"180.00001",
			"notfloat"
		};
		
		for (String invalidLongitude : invalidLongitudes) {
			assertThrows(IllegalArgumentException.class, () -> {
				category.parseString(invalidLongitude);
			});
		}
	}
	
	/**
	 * Checks the parseString method of Longitude returns null when passed an empty string.
	 */
	@Test
	void testParseString_Empty() {
		String longitude = "";
		
		assertEquals(null, category.parseString(longitude));
	}
	
	/**
	 * Checks the parseString method of Longitude throws an error correctly when passed null.
	 */
	@Test
	void testParseString_Null() {
		assertThrows(IllegalArgumentException.class, () -> {
			category.parseString(null);
		});
	}

}
