package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.CrimeRecord;

/**
 * Unit testing for Block data category.
 * 
 * @author Connor Dunlop
 *
 */
class BlockTest {

	/** A crime record to use when testing methods of Block */
	private CrimeRecord record;
	
	private Block category = new Block();
	
	/**
	 * Reset the values of the test record's attributes to default before each test.
	 */
	@BeforeEach
	void initialiseRecord() {
		record = new CrimeRecord();
	}

	/**
	 * Checks the setCategoryValue method of Block sets the attribute value 
	 * of a crime record correctly.
	 */
	@Test
	void testSetCategoryValue_ValidData() {
		String block = "073XX S SOUTH SHORE DR";
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, block);
		
		// Check value set correctly
		assertEquals(block, record.getBlock());
	}
	
	/**
	 * Checks setCategoryValue method throws an error when using invalid data.
	 */
	@Test
	void testSetCategoryValue_InvalidData() {
		Integer block = 163990;
		
		// Try to set value of record attribute to incorrect data type
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(record, block);
		});
	}
	
	/**
	 * Checks setCategoryValue with null data.
	 */
	@Test
	void testSetCategoryValue_NullData() {
		String block = null;
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, block);
		
		// Check value set correctly
		assertEquals(block, record.getBlock());
	}
	
	/**
	 * Checks setCategoryValue with null CrimeRecord.
	 */
	@Test
	void testSetCategoryValue_NullRecord() {
		String block = "073XX S SOUTH SHORE DR";
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(null, block);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of Block gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		String block = "073XX S SOUTH SHORE DR";
		
		// Set value of record attribute
		record.setBlock(block);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), block);
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof String);
	}
	
	/**
	 * Checks the getCategoryValue method of Block throws an error when
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
	 * Checks the parseString method of Block returns the string passed to it.
	 */
	@Test
	void testParseString_Valid() {
		String block = "073XX S SOUTH SHORE DR";
		
		assertEquals(block, category.parseString(block));
	}
	
	/**
	 * Checks the parseString method of Block returns null when passed an empty string.
	 */
	@Test
	void testParseString_Empty() {
		String block = "";
		
		assertEquals(null, category.parseString(block));
	}
	
	/**
	 * Checks the parseString method of Block throws an exception when trying to parse null.
	 */
	@Test
	void testParseString_Null() {
			assertThrows(IllegalArgumentException.class, () -> {
			category.parseString(null);
		});
	}

}
