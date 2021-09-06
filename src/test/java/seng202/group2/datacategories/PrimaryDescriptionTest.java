package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.model.CrimeRecord;
import seng202.group2.model.datacategories.PrimaryDescription;
import seng202.group2.model.datacategories.UnsupportedCategoryException;

/**
 * Unit tests for PrimaryDescription data category.
 * 
 * @author Connor Dunlop
 *
 */
class PrimaryDescriptionTest {

	/** A crime record to use when testing methods of DataCategory */
	private CrimeRecord record;
	
	private PrimaryDescription category = new PrimaryDescription();
	
	/**
	 * Reset the values of the test record's attributes to default before each test.
	 */
	@BeforeEach
	void initialiseRecord() {
		record = new CrimeRecord();
	}

	/**
	 * Checks the setCategoryValue method of PrimaryDescription throws an UnsupportedCategoryException
	 */
	@Test
	void testSetCategoryValue() {
		String description = "THEFT";
		
		// Try to set value of record attribute to incorrect data type
		assertThrows(UnsupportedCategoryException.class, () -> {
			category.setRecordValue(record, description);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of PrimaryDescription gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		String description = "THEFT";
		
		// Set value of record attribute
		record.setPrimaryDescription(description);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(description, category.getRecordValue(record));
		// Check return type of DataCategory method correct
		assertTrue(category.getRecordValue(record) instanceof String);
	}
	
	/**
	 * Checks the getCategoryValue method of PrimaryDescription throws an error when
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
	 * Checks the parseString method of PrimaryDescription returns the string passed to it.
	 */
	@Test
	void testParseString_Valid() {
		String description = "THEFT";
		
		assertEquals(description, category.parseString(description));
	}
	
	/**
	 * Checks the parseString method of PrimaryDescription returns null when passed an empty string.
	 */
	@Test
	void testParseString_Empty() {
		String description = "";
		
		assertEquals(null, category.parseString(description));
	}
	
	/**
	 * Checks the parseString method of PrimaryDescription throws an exception when trying to parse null.
	 */
	@Test
	void testParseString_Null() {
			assertThrows(IllegalArgumentException.class, () -> {
			category.parseString(null);
		});
	}

}
