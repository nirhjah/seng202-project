package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.CrimeRecord;

/**
 * Unit tests for Domestic data category class.
 * 
 * @author Connor Dunlop
 *
 */
class DomesticTest {

	/** A crime record to use when testing methods of DataCategory */
	private CrimeRecord record;
	
	private Domestic category = new Domestic();
	
	/**
	 * Reset the values of the test record's attributes to default before each test.
	 */
	@BeforeEach
	void initialiseRecord() {
		record = new CrimeRecord();
	}

	/**
	 * Checks the setCategoryValue method of Domestic sets the attribute value 
	 * of a crime record correctly.
	 */
	@Test
	void testSetCategoryValue_ValidData() {
		Boolean domestic = false;
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, domestic);
		
		// Check value set correctly
		assertEquals(domestic, record.getDomestic());
	}
	
	/**
	 * Checks setCategoryValue method throws an error when using invalid data.
	 */
	@Test
	void testSetCategoryValue_InvalidData() {
		Integer domestic = 163990;
		
		// Try to set value of record attribute to incorrect data type
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(record, domestic);
		});
	}
	
	/**
	 * Checks setCategoryValue with null data.
	 */
	@Test
	void testSetCategoryValue_NullData() {
		Boolean domestic = null;
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, domestic);
		
		// Check value set correctly
		assertEquals(domestic, record.getArrest());
	}
	
	/**
	 * Checks setCategoryValue with null CrimeRecord.
	 */
	@Test
	void testSetCategoryValue_NullRecord() {
		Boolean domestic = false;
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(null, domestic);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of Domestic gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		Boolean domestic = false;
		
		// Set value of record attribute
		record.setDomestic(domestic);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(domestic, category.getCategoryValue(record));
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof Boolean);
	}
	
	/**
	 * Checks the getCategoryValue method of Domestic throws an error when
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
	 * Checks the parseString method of Domestic returns the date correctly
	 * parsed into a Boolean.
	 */
	@Test
	void testParseString_Valid() {
		String[] validDomestics = {
			"N",
			"Y",
			"FALSE",
			"TRUE"
		};
		
		Boolean[] parsedDomestics = {
			false,
			true,
			false,
			true
		};
		
		for (int i = 0; i < validDomestics.length; i++) {
			assertTrue(parsedDomestics[i].equals(category.parseString(validDomestics[i])));
		}
	}
	
	/**
	 * Checks the parseString method of Domestic throws an exception when passed invalid strings.
	 */
	@Test
	void testParseString_Invalid() {
		String[] invalidDomestics = {
			"DOMESTIC",
			"NO",
			"LONG".repeat(1000)
		};
		
		for (String invalidDomestic : invalidDomestics) {
			assertThrows(IllegalArgumentException.class, () -> {
				category.parseString(invalidDomestic);
			});
		}
	}
	
	/**
	 * Checks the parseString method of Domestic returns null when passed an empty string.
	 */
	@Test
	void testParseString_Empty() {
		String domestic = "";
		
		assertEquals(null, category.parseString(domestic));
	}
	
	/**
	 * Checks the parseString method of Domestic throws an error correctly when passed null.
	 */
	@Test
	void testParseString_Null() {
		assertThrows(IllegalArgumentException.class, () -> {
			category.parseString(null);
		});
	}

}
