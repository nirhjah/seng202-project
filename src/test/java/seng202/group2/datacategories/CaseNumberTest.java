package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.CrimeRecord;

/**
 * Unit tests for methods of CaseNumber data category.
 * 
 * @author Connor Dunlop
 *
 */
class CaseNumberTest {
	
	/** A crime record to use when testing methods of DataCategory */
	private CrimeRecord record;
	
	/**
	 * Reset the values of the test record's attributes to default before each test.
	 */
	@BeforeEach
	void initialiseRecord() {
		record = new CrimeRecord();
	}

	/**
	 * Checks the setCategoryValue method of CaseNumber sets the attribute value 
	 * of a crime record correctly.
	 */
	@Test
	void testSetCategoryValue_ValidData() {
		CaseNumber category = new CaseNumber();
		String caseNum = "JE163990";
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, caseNum);
		
		// Check value set correctly
		assertEquals(caseNum, record.getCaseNum());
	}
	
	/**
	 * Checks setCategoryValue method throws an error when using invalid data.
	 */
	@Test
	void testSetCategoryValue_InvalidData() {
		CaseNumber category = new CaseNumber();
		Integer caseNum = 163990;
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(null, caseNum);
		});
	}
	
	/**
	 * Checks setCategoryValue with null data.
	 */
	@Test
	void testSetCategoryValue_NullData() {
		CaseNumber category = new CaseNumber();
		String caseNum = null;
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, caseNum);
		
		// Check value set correctly
		assertEquals(caseNum, record.getCaseNum());
	}
	
	/**
	 * Checks setCategoryValue with null CrimeRecord.
	 */
	@Test
	void testSetCategoryValue_NullRecord() {
		CaseNumber category = new CaseNumber();
		String caseNum = "JE163990";
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(null, caseNum);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of CaseNumber gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		CaseNumber category = new CaseNumber();
		String caseNum = "JE163990";
		
		// Set value of record attribute
		record.setCaseNum(caseNum);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), caseNum);
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof String);
	}
	
	/**
	 * Checks the getCategoryValue method of CaseNumber throws an error when
	 * passing a null record.
	 */
	@Test
	void testGetCategoryValue_NullRecord() {
		CaseNumber category = new CaseNumber();
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertThrows(IllegalArgumentException.class, () -> {
			category.getCategoryValue(null);
		});
	}
	
	/**
	 * Checks the parseString method of CaseNumber returns the string passed to it.
	 */
	@Test
	void testParseStringValid() {
		
	}
}
