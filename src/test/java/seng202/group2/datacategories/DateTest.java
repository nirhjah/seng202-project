package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.group2.CrimeRecord;

class DateTest {

	/** A crime record to use when testing methods of DataCategory */
	private CrimeRecord record;
	private Date category = new Date();
	
	/**
	 * Reset the values of the test record's attributes to default before each test.
	 */
	@BeforeEach
	void initialiseRecord() {
		record = new CrimeRecord();
	}

	/**
	 * Checks the setCategoryValue method of Date sets the attribute value 
	 * of a crime record correctly.
	 */
	@Test
	void testSetCategoryValue_ValidData() {
		Calendar date = new GregorianCalendar(2020, 11, 23, 15, 5);
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, date);
		
		// Check value set correctly
		assertEquals(date, record.getDate());
	}
	
	/**
	 * Checks setCategoryValue method throws an error when using invalid data.
	 */
	@Test
	void testSetCategoryValue_InvalidData() {
		String date = "23/11/2020 15:05:00";
		
		// Try to set value of record attribute to incorrect data type
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(record, date);
		});
	}
	
	/**
	 * Checks setCategoryValue with null data.
	 */
	@Test
	void testSetCategoryValue_NullData() {
		Calendar date = null;
		
		// Set value of record attribute using  DataCategory method
		category.setCategoryValue(record, date);
		
		// Check value set correctly
		assertEquals(date, record.getDate());
	}
	
	/**
	 * Checks setCategoryValue with null Date.
	 */
	@Test
	void testSetCategoryValue_NullRecord() {
		Calendar date = new GregorianCalendar(2020, 11, 23, 15, 5);
		
		// Try to set value of null records attribute using DataCategory method
		assertThrows(IllegalArgumentException.class, () -> {
			category.setCategoryValue(null, date);
		});
	}
	
	/**
	 * Checks the getCategoryValue method of Date gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCategoryValue() {
		Calendar date = new GregorianCalendar(2020, 11, 23, 15, 5);
		
		// Set value of record attribute
		record.setDate(date);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(date, category.getCategoryValue(record));
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof Calendar);
	}
	
	/**
	 * Checks the getCategoryValue method of Date throws an error when
	 * passing a null record.
	 */
	@Test
	void testGetCategoryValue_NullRecord() {
		// Try to get value of null record
		assertThrows(IllegalArgumentException.class, () -> {
			category.getCategoryValue(null);
		});
	}
	
	/**
	 * Checks the parseString method of Date returns the date correctly
	 * parsed into a Date object.
	 */
	@Test
	void testParseString_Valid() {
		String[] validDates = {
				"0000-00-00 00:00:00 AM",
				"0000-00-00 00:00:00",
				"06/15/2021 09:30:00 AM",
				"06/15/2021 08:10:00 AM",
				"11/23/2020 03:05:00 PM",
				"07/29/2020 09:00:00 PM",
				
				"0000-00-00T00:00:00",
				"2020-11-23T15:05:00",
				"2020-08-10T07:30:00",
				"9999-12-31T24:59:59"
		};
		
		Calendar[] parsedDates = {
				new GregorianCalendar(0, 0, 0, 0, 0, 0),
				new GregorianCalendar(0, 0, 0, 0, 0, 0),
				new GregorianCalendar(2021, 6, 15, 9, 30, 0),
				new GregorianCalendar(2021, 6, 15, 8, 10, 0),
				new GregorianCalendar(2020, 11, 23, 15, 5, 0),
				new GregorianCalendar(2020, 7, 29, 21, 0, 0),
				new GregorianCalendar(0, 0, 0, 0, 0, 0),
				new GregorianCalendar(2020, 11, 23, 15, 5, 0),
				new GregorianCalendar(2020, 8, 10, 7, 30, 0),
				new GregorianCalendar(9999, 12, 31, 24, 59, 59)
		};
		
		for (int i = 0; i < validDates.length; i++) {
			assertTrue(parsedDates[i].equals(category.parseString(validDates[i])));
		}
	}
	
	/**
	 * Checks the parseString method of Date throws an exception when passed invalid dates.
	 */
	@Test
	void testParseString_Invalid() {
		String[] invalidDates = {
				"//::",
				" ",
				"Not a valid Date",
				"13/32/9999 25:60:60",
				"06/27/2020 13:50:50 PM",
				"00/00/0000 00:00:00 24",
				"9999-13-32T25:60:60"
		};
		
		for (String invalidDate : invalidDates) {
			assertThrows(IllegalArgumentException.class, () -> {
				category.parseString(invalidDate);
			});
		}
	}
	
	/**
	 * Checks the parseString method of Date returns null when passed an empty string.
	 */
	@Test
	void testParseString_Empty() {
		String date = "";
		
		assertEquals(null, category.parseString(date));
	}
	
	/**
	 * Checks the parseString method of Date throws an error correctly when .
	 */
	@Test
	void testParseString_Null() {
		assertThrows(IllegalArgumentException.class, () -> {
			category.parseString(null);
		});
	}
}
