package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.model.CrimeRecord;
import seng202.group2.model.datacategories.Beat;

/**
 * Unit tests for Beat data category class.
 * @author Connor Dunlop
 *
 */
class BeatTest {

	/** A crime record to use when testing methods of DataCategory */
	private CrimeRecord record;
	
	private Beat category = new Beat();
	
	/**
	 * Reset the values of the test record's attributes to default before each test.
	 */
	@BeforeEach
	void initialiseRecord() {
		record = new CrimeRecord();
	}

	/**
	 * Checks the setCategoryValue method of Beat sets the attribute value 
	 * of a crime record correctly.
	 */
	@Test
	void testSetCategoryValue_ValidData() {
		Short beat = 334;
		
		// Set value of record attribute using  DataCategory method
		category.setRecordValue(record, beat);
		
		// Check value set correctly
		assertEquals(beat, record.getBeat());
	}
	
	/**
	 * Checks setCategoryValue method throws an error when using invalid data.
	 */
	@Test
	void testSetCategoryValue_InvalidData() {
		String beat = "334";
		
		// Try to set value of record attribute to incorrect data type
		assertThrows(IllegalArgumentException.class, () -> {
			category.setRecordValue(record, beat);
		});
	}
	
	/**
	 * Checks setCategoryValue with null data.
	 */
	@Test
	void testSetCategoryValue_NullData() {
		Short beat = null;
		
		// Set value of record attribute using  DataCategory method
		category.setRecordValue(record, beat);
		
		// Check value set correctly
		assertEquals(beat, record.getBeat());
	}
	
	/**
	 * Checks setCategoryValue with null CrimeRecord.
	 */
	@Test
	void testSetCategoryValue_NullRecord() {
		Short beat = 334;
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setRecordValue(null, beat);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of Beat gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		Short beat = 334;
		
		// Set value of record attribute
		record.setBeat(beat);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(beat, category.getRecordValue(record));
		// Check return type of DataCategory method correct
		assertTrue(category.getRecordValue(record) instanceof Short);
	}
	
	/**
	 * Checks the getCategoryValue method of Beat throws an error when
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
	 * Checks the parseString method of Beat returns the date correctly
	 * parsed into a Boolean.
	 */
	@Test
	void testParseString_Valid() {
		String[] validBeats = {
			"-32768",
			"32767",
			"0",
			"000000",
			"000001",
			"334",
			"631",
			"1115"
		};
		
		Short[] parsedBeats = {
			-32768,
			32767,
			0,
			0,
			1,
			334,
			631,
			1115
		};
		
		for (int i = 0; i < validBeats.length; i++) {
			assertTrue(parsedBeats[i].equals(category.parseString(validBeats[i])));
		}
	}
	
	/**
	 * Checks the parseString method of Beat throws an exception when passed invalid strings.
	 */
	@Test
	void testParseString_Invalid() {
		String[] invalidBeats = {
			"-32769",
			"32768",
			"222222",
			"-2222222",
			"letters",
			"75letters"
		};
		
		for (String invalidBeat : invalidBeats) {
			assertThrows(IllegalArgumentException.class, () -> {
				category.parseString(invalidBeat);
			});
		}
	}
	
	/**
	 * Checks the parseString method of Beat returns null when passed an empty string.
	 */
	@Test
	void testParseString_Empty() {
		String beat = "";
		
		assertEquals(null, category.parseString(beat));
	}
	
	/**
	 * Checks the parseString method of Beat throws an error correctly when passed null.
	 */
	@Test
	void testParseString_Null() {
		assertThrows(IllegalArgumentException.class, () -> {
			category.parseString(null);
		});
	}

}
