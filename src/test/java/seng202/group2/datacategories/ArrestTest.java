package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.CrimeRecord;

/**
 * Unit tests for Arrest data category.
 * 
 * @author Connor Dunlop
 *
 */
class ArrestTest {

	/** A crime record to use when testing methods of DataCategory */
	private CrimeRecord record;
	
	private Arrest category = new Arrest();
	
	/**
	 * Reset the values of the test record's attributes to default before each test.
	 */
	@BeforeEach
	void initialiseRecord() {
		record = new CrimeRecord();
	}

	/**
	 * Checks the setCategoryValue method of Arrest sets the attribute value 
	 * of a crime record correctly.
	 */
	@Test
	void testSetCategoryValue_ValidData() {
		Boolean arrest = false;
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, arrest);
		
		// Check value set correctly
		assertEquals(arrest, record.getArrest());
	}
	
	/**
	 * Checks setCategoryValue method throws an error when using invalid data.
	 */
	@Test
	void testSetCategoryValue_InvalidData() {
		Integer arrest = 163990;
		
		// Try to set value of record attribute to incorrect data type
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(record, arrest);
		});
	}
	
	/**
	 * Checks setCategoryValue with null data.
	 */
	@Test
	void testSetCategoryValue_NullData() {
		Boolean arrest = null;
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, arrest);
		
		// Check value set correctly
		assertEquals(arrest, record.getArrest());
	}
	
	/**
	 * Checks setCategoryValue with null CrimeRecord.
	 */
	@Test
	void testSetCategoryValue_NullRecord() {
		Boolean arrest = false;
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(null, arrest);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of Arrest gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		Boolean arrest = false;
		
		// Set value of record attribute
		record.setArrest(arrest);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), arrest);
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof Boolean);
	}
	
	/**
	 * Checks the getCategoryValue method of Arrest throws an error when
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
	 * Checks the parseString method of Arrest returns the date correctly
	 * parsed into a Boolean.
	 */
	@Test
	void testParseString_Valid() {
		String[] validArrests = {
			"N",
			"Y",
			"FALSE",
			"TRUE"
		};
		
		Boolean[] parsedArrests = {
			false,
			true,
			false,
			true
		};
		
		for (int i = 0; i < validArrests.length; i++) {
			assertTrue(parsedArrests[i].equals(category.parseString(validArrests[i])));
		}
	}
	
	/**
	 * Checks the parseString method of Arrest throws an exception when passed invalid strings.
	 */
	@Test
	void testParseString_Invalid() {
		String[] invalidArrests = {
			"ARREST",
			"MADE",
			"LONG".repeat(1000)
		};
		
		for (String invalidArrest : invalidArrests) {
			assertThrows(IllegalArgumentException.class, () -> {
				category.parseString(invalidArrest);
			});
		}
	}
	
	/**
	 * Checks the parseString method of Arrest returns null when passed an empty string.
	 */
	@Test
	void testParseString_Empty() {
		String arrest = "";
		
		assertEquals(null, category.parseString(arrest));
	}
	
	/**
	 * Checks the parseString method of Arrest throws an error correctly when passed null.
	 */
	@Test
	void testParseString_Null() {
		assertThrows(IllegalArgumentException.class, () -> {
			category.parseString(null);
		});
	}
}
