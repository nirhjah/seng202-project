package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.model.CrimeRecord;
import seng202.group2.model.datacategories.LocationDescription;

/**
 * Unit tests for LocationDescription data category.
 * 
 * @author Connor Dunlop
 *
 */
class LocationDescriptionTest {

	/** A crime record to use when testing methods of DataCategory */
	private CrimeRecord record;
	
	private LocationDescription category = new LocationDescription();
	
	/**
	 * Reset the values of the test record's attributes to default before each test.
	 */
	@BeforeEach
	void initialiseRecord() {
		record = new CrimeRecord();
	}

	/**
	 * Checks the setCategoryValue method of LocationDescription sets the attribute value 
	 * of a crime record correctly.
	 */
	@Test
	void testSetCategoryValue_ValidData() {
		String location = "APARTMENT";
		
		// Set value of record attribute using  DataCategory method
		category.setRecordValue(record, location);
		
		// Check value set correctly
		assertEquals(location, record.getLocationDescription());
	}
	
	/**
	 * Checks setCategoryValue method throws an error when using invalid data.
	 */
	@Test
	void testSetCategoryValue_InvalidData() {
		Integer location = 6;
		
		// Try to set value of record attribute to incorrect data type
		assertThrows(IllegalArgumentException.class, () -> {
			category.setRecordValue(record, location);
		});
	}
	
	/**
	 * Checks setCategoryValue with null data.
	 */
	@Test
	void testSetCategoryValue_NullData() {
		String location = null;
		
		// Set value of record attribute using  DataCategory method
		category.setRecordValue(record, location);
		
		// Check value set correctly
		assertEquals(location, record.getFbiCode());
	}
	
	/**
	 * Checks setCategoryValue with null CrimeRecord.
	 */
	@Test
	void testSetCategoryValue_NullRecord() {
		String location = "APARTMENT";
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setRecordValue(null, location);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of LocationDescription gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		String location = "APARTMENT";
		
		// Set value of record attribute
		record.setLocationDescription(location);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(location, category.getRecordValue(record));
		// Check return type of DataCategory method correct
		assertTrue(category.getRecordValue(record) instanceof String);
	}
	
	/**
	 * Checks the getCategoryValue method of LocationDescription throws an error when
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
	 * Checks the parseString method of LocationDescription returns the string passed to it.
	 */
	@Test
	void testParseString_Valid() {
		String location = "APARTMENT";
		
		assertEquals(location, category.parseString(location));
	}
	
	/**
	 * Checks the parseString method of LocationDescription returns null when passed an empty string.
	 */
	@Test
	void testParseString_Empty() {
		String location = "";
		
		assertEquals(null, category.parseString(location));
	}
	
	/**
	 * Checks the parseString method of LocationDescription throws an exception when trying to parse null.
	 */
	@Test
	void testParseString_Null() {
			assertThrows(IllegalArgumentException.class, () -> {
			category.parseString(null);
		});
	}

}
