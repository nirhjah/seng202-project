package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.model.CrimeRecord;
import seng202.group2.model.datacategories.IUCRCode;
import seng202.group2.model.datacategories.IUCRCodeDictionary;

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
		String iucr = "820";
		
		// Set value of record attribute using  DataCategory method
		category.setRecordValue(record, iucr);
		
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
			category.setRecordValue(record, iucr);
		});
	}
	
	/**
	 * Checks setCategoryValue with null data.
	 */
	@Test
	void testSetCategoryValue_NullData() {
		String iucr = null;
		
		// Set value of record attribute using  DataCategory method
		category.setRecordValue(record, iucr);
		
		// Check value set correctly
		assertEquals(iucr, record.getBlock());
	}
	
	/**
	 * Checks setCategoryValue with null CrimeRecord.
	 */
	@Test
	void testSetCategoryValue_NullRecord() {
		String iucr = "820";
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setRecordValue(null, iucr);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of IUCRCode gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		String iucr = "820";
		
		// Set value of record attribute
		record.setIucr(iucr);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getRecordValue(record), iucr);
		// Check return type of DataCategory method correct
		assertTrue(category.getRecordValue(record) instanceof String);
	}
	
	/**
	 * Checks the getCategoryValue method of IUCRCode throws an error when
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
	 * Checks the parseString method of IUCRCode returns an IUCRCode instance as expected.
	 */
	@Test
	void testParseString_Valid() {
		String iucr = "820";
		
		assertEquals(iucr, category.parseString(iucr));
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
