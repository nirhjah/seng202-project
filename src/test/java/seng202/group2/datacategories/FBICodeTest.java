package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.CrimeRecord;

/**
 * Unit tests for FBICode data category.
 * 
 * @author Connor Dunlop
 *
 */
class FBICodeTest {

	/** A crime record to use when testing methods of DataCategory */
	private CrimeRecord record;
	
	private CaseNumber category = new CaseNumber();
	
	/**
	 * Reset the values of the test record's attributes to default before each test.
	 */
	@BeforeEach
	void initialiseRecord() {
		record = new CrimeRecord();
	}

	/**
	 * Checks the setCategoryValue method of FBICode sets the attribute value 
	 * of a crime record correctly.
	 */
	@Test
	void testSetCategoryValue_ValidData() {
		String fbiCode = "08A";
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, fbiCode);
		
		// Check value set correctly
		assertEquals(fbiCode, record.getFbiCode());
	}
	
	/**
	 * Checks setCategoryValue method throws an error when using invalid data.
	 */
	@Test
	void testSetCategoryValue_InvalidData() {
		Integer fbiCode = 6;
		
		// Try to set value of record attribute to incorrect data type
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(record, fbiCode);
		});
	}
	
	/**
	 * Checks setCategoryValue with null data.
	 */
	@Test
	void testSetCategoryValue_NullData() {
		String fbiCode = null;
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, fbiCode);
		
		// Check value set correctly
		assertEquals(fbiCode, record.getFbiCode());
	}
	
	/**
	 * Checks setCategoryValue with null CrimeRecord.
	 */
	@Test
	void testSetCategoryValue_NullRecord() {
		String fbiCode = "08A";
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(null, fbiCode);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of FBICode gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		String fbiCode = "08A";
		
		// Set value of record attribute
		record.setFbiCode(fbiCode);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), fbiCode);
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof String);
	}
	
	/**
	 * Checks the getCategoryValue method of FBICode throws an error when
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
	 * Checks the parseString method of FBICode returns the string passed to it.
	 */
	@Test
	void testParseString_Valid() {
		String fbiCode = "08A";
		
		assertEquals(fbiCode, category.parseString(fbiCode));
	}
	
	/**
	 * Checks the parseString method of FBICode returns null when passed an empty string.
	 */
	@Test
	void testParseString_Empty() {
		String fbiCode = "";
		
		assertEquals(null, category.parseString(fbiCode));
	}
	
	/**
	 * Checks the parseString method of FBICode throws an exception when trying to parse null.
	 */
	@Test
	void testParseString_Null() {
			assertThrows(IllegalArgumentException.class, () -> {
			category.parseString(null);
		});
	}

}
