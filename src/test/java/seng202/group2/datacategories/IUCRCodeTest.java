package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.CrimeRecord;

/**
 * Unit tests for IUCRCode data category.
 * 
 * @author Connor Dunlop
 *
 */
class IUCRCodeTest {

	/** A crime record to use when testing methods of IUCRCode */
	private CrimeRecord record;
	
	private IUCRCode category = new IUCRCode();
	
	/**
	 * Reset the values of the test record's attributes to default before each test.
	 */
	@BeforeEach
	void initialiseRecord() {
		record = new CrimeRecord();
	}

	/**
	 * Checks the setCategoryValue method of IUCRCode sets the attribute value 
	 * of a crime record correctly.
	 */
	@Test
	void testSetCategoryValue_ValidData() {
		seng202.group2.IUCRCode iucr = new seng202.group2.IUCRCode("820", "THEFT", "$500 AND UNDER", true);
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, iucr);
		
		// Check value set correctly
		assertEquals(iucr, record.getIucr());
	}
	
	/**
	 * Checks setCategoryValue method throws an error when using invalid data.
	 */
	@Test
	void testSetCategoryValue_InvalidData() {
		Integer iucr = 820;
		
		// Try to set value of record attribute to incorrect data type
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(record, iucr);
		});
	}
	
	/**
	 * Checks setCategoryValue with null data.
	 */
	@Test
	void testSetCategoryValue_NullData() {
		seng202.group2.IUCRCode iucr = null;
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, iucr);
		
		// Check value set correctly
		assertEquals(iucr, record.getBlock());
	}
	
	/**
	 * Checks setCategoryValue with null CrimeRecord.
	 */
	@Test
	void testSetCategoryValue_NullRecord() {
		seng202.group2.IUCRCode iucr = new seng202.group2.IUCRCode("820", "THEFT", "$500 AND UNDER", true);
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(null, iucr);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of IUCRCode gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		seng202.group2.IUCRCode iucr = new seng202.group2.IUCRCode("820", "THEFT", "$500 AND UNDER", true);
		
		// Set value of record attribute
		record.setIucr(iucr);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), iucr);
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof seng202.group2.IUCRCode);
	}
	
	/**
	 * Checks the getCategoryValue method of IUCRCode throws an error when
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
	 * Checks the parseString method of IUCRCode returns an IUCRCode instance as expected.
	 */
	@Test
	void testParseString_Valid() {
		String stringIucr = "820";
		seng202.group2.IUCRCode iucr = seng202.group2.IUCRCodeDictionary.getCode(stringIucr);
		
		assertEquals(iucr, category.parseString(stringIucr));
	}
	
	/**
	 * Checks the parseString method of IUCRCode returns null when passed an empty string.
	 */
	@Test
	void testParseString_Empty() {
		String iucr = "";
		
		assertEquals(null, category.parseString(iucr));
	}
	
	/**
	 * Checks the parseString method of IUCRCode throws an exception when trying to parse null.
	 */
	@Test
	void testParseString_Null() {
			assertThrows(IllegalArgumentException.class, () -> {
			category.parseString(null);
		});
	}

}
