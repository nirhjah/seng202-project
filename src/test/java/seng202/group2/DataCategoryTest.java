package seng202.group2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the utility methods of DataCategory for setting and getting the value of a particular
 * category/attribute in a CrimeRecord instance.
 * 
 * @author Connor Dunlop
 *
 */
class DataCategoryTest {
	
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
	 * Checks the setCategoryValue method of CASE_NUM sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetCaseNum() throws UnsupportedCategoryException {
		DataCategory category = DataCategory.CASE_NUM;
		
		String caseNum = "JE163990";
		
		// Set value of record attribute using DataCategory method
		category.setCategoryValue(record, caseNum);
		
		// Check value set correctly
		assertEquals(caseNum, record.getCaseNum());
		
	}
	
	/**
	 * Checks the getCategoryValue method of CASE_NUM gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetCaseNum() {
		DataCategory category = DataCategory.CASE_NUM;
		
		String caseNum = "JE163990";
		
		// Set value of record attribute
		record.setCaseNum(caseNum);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), caseNum);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof String);
	}
	
	
	/**
	 * Checks the setCategoryValue method of DATE sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetDate() throws UnsupportedCategoryException {
		DataCategory category = DataCategory.DATE;
		
		Calendar date = new GregorianCalendar(2020, 11, 23, 15, 5);
		
		// Set value of record attribute using DataCategory method
		category.setCategoryValue(record, date);
		
		// Check value set correctly
		assertEquals(date, record.getDate());
		
	}
	
	/**
	 * Checks the getCategoryValue method of DATE gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetDate() {
		DataCategory category = DataCategory.DATE;
		
		Calendar date = new GregorianCalendar(2020, 11, 23, 15, 5);
		
		// Set value of record attribute
		record.setDate(date);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), date);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof Calendar);
	}
	
	
	/**
	 * Checks the setCategoryValue method of BLOCK sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetBlock() throws UnsupportedCategoryException {
		DataCategory category = DataCategory.BLOCK;
		
		String block = "073XX S SOUTH SHORE DR";
		
		// Set value of record attribute using DataCategory method
		category.setCategoryValue(record, block);
		
		// Check value set correctly
		assertEquals(block, record.getBlock());
		
	}
	
	/**
	 * Checks the getCategoryValue method of BLOCK gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetBlock() {
		DataCategory category = DataCategory.BLOCK;
		
		String block = "073XX S SOUTH SHORE DR";
		
		// Set value of record attribute
		record.setBlock(block);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), block);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof String);
	}
	
	
	/**
	 * Checks the setCategoryValue method of IUCR sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetIucr() throws UnsupportedCategoryException {
		DataCategory category = DataCategory.IUCR;
		
		IUCRCode iucr = new IUCRCode("820", "THEFT", "$500 AND UNDER", true);
		
		// Set value of record attribute using DataCategory method
		category.setCategoryValue(record, iucr);
		
		// Check value set correctly
		assertEquals(iucr, record.getIucr());
		
	}
	
	/**
	 * Checks the getCategoryValue method of IUCR gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetIucr() {
		DataCategory category = DataCategory.IUCR;
		
		IUCRCode iucr = new IUCRCode("110", "HOMICIDE", "FIRST DEGREE MURDER", true);
		
		// Set value of record attribute
		record.setIucr(iucr);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), iucr);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof IUCRCode);
	}
	
	
	/**
	 * Checks the setCategoryValue method of PRIMARY_DESCRIPTION sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetPrimaryDescription() {
		DataCategory category = DataCategory.PRIMARY_DESCRIPTION;
		
		String primaryDescription = "THEFT";
		
		// Set value of record attribute using DataCategory method
		assertThrows(UnsupportedCategoryException.class, () -> { category.setCategoryValue(record, primaryDescription); });
	}
	
	/**
	 * Checks the getCategoryValue method of PRIMARY_DESCRIPTION gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetPrimaryDescription() {
		DataCategory category = DataCategory.PRIMARY_DESCRIPTION;
		
		IUCRCode iucr = new IUCRCode("110", "HOMICIDE", "FIRST DEGREE MURDER", true);
		
		// Set value of record attribute
		record.setIucr(iucr);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), iucr.PRIMARY_DESCRIPTION);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof String);
	}
	
	
	/**
	 * Checks the setCategoryValue method of SECONDARY_DESCRIPTION sets the attribute value 
	 * of a crime record correctly.
	 */
	@Test
	void testSetSecondaryDescription() {
		DataCategory category = DataCategory.SECONDARY_DESCRIPTION;
		
		String secondaryDescription = "$500 AND UNDER";
		
		// Set value of record attribute using DataCategory method
		assertThrows(UnsupportedCategoryException.class, () -> { category.setCategoryValue(record, secondaryDescription); });
	}
	
	/**
	 * Checks the getCategoryValue method of SECONDARY_DESCRIPTION gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetSecondaryDescription() {
		DataCategory category = DataCategory.SECONDARY_DESCRIPTION;
		
		IUCRCode iucr = new IUCRCode("110", "HOMICIDE", "FIRST DEGREE MURDER", true);
		
		// Set value of record attribute
		record.setIucr(iucr);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), iucr.SECONDARY_DESCRIPTION);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof String);
	}
	
	
	/**
	 * Checks the setCategoryValue method of LOCATION_DESCRIPTION sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetLocationDescription() throws UnsupportedCategoryException {
		DataCategory category = DataCategory.LOCATION_DESCRIPTION;
		
		String locationDescription = "APARTMENT";
		
		// Set value of record attribute using DataCategory method
		category.setCategoryValue(record, locationDescription);
		
		// Check value set correctly
		assertEquals(locationDescription, record.getLocationDescription());
		
	}
	
	/**
	 * Checks the getCategoryValue method of CASE_NUM gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetLocationDescription() {
		DataCategory category = DataCategory.LOCATION_DESCRIPTION;
		
		String locationDescription = "APARTMENT";
		
		// Set value of record attribute
		record.setLocationDescription(locationDescription);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), locationDescription);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof String);
	}
	
	
	/**
	 * Checks the setCategoryValue method of ARREST sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetArrest() throws UnsupportedCategoryException {
		DataCategory category = DataCategory.ARREST;
		
		boolean arrest = false;
		
		// Set value of record attribute using DataCategory method
		category.setCategoryValue(record, arrest);
		
		// Check value set correctly
		assertEquals(arrest, record.getArrest());
		
	}
	
	/**
	 * Checks the getCategoryValue method of ARREST gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetArrest() {
		DataCategory category = DataCategory.ARREST;
		
		boolean arrest = false;
		
		// Set value of record attribute
		record.setArrest(arrest);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), arrest);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof Boolean);
	}
	
	
	/**
	 * Checks the setCategoryValue method of DOMESTIC sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetDomestic() throws UnsupportedCategoryException {
		DataCategory category = DataCategory.DOMESTIC;
		
		boolean domestic = false;
		
		// Set value of record attribute using DataCategory method
		category.setCategoryValue(record, domestic);
		
		// Check value set correctly
		assertEquals(domestic, record.getDomestic());
		
	}
	
	/**
	 * Checks the getCategoryValue method of DOMESTIC gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetDomestic() {
		DataCategory category = DataCategory.DOMESTIC;
		
		boolean domestic = false;
		
		// Set value of record attribute
		record.setDomestic(domestic);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), domestic);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof Boolean);
	}
	
	
	/**
	 * Checks the setCategoryValue method of BEAT sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetBeat() throws UnsupportedCategoryException {
		DataCategory category = DataCategory.BEAT;
		
		short beat = 334;
		
		// Set value of record attribute using DataCategory method
		category.setCategoryValue(record, beat);
		
		// Check value set correctly
		assertEquals(beat, record.getBeat());
		
	}
	
	/**
	 * Checks the getCategoryValue method of BEAT gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetBeat() {
		DataCategory category = DataCategory.BEAT;
		
		short beat = 334;
		
		// Set value of record attribute
		record.setBeat(beat);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), beat);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof Short);
	}
	
	
	/**
	 * Checks the setCategoryValue method of WARD sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetWard() throws UnsupportedCategoryException {
		DataCategory category = DataCategory.WARD;
		
		short ward = 7;
		
		// Set value of record attribute using DataCategory method
		category.setCategoryValue(record, ward);
		
		// Check value set correctly
		assertEquals(ward, record.getWard());
		
	}
	
	/**
	 * Checks the getCategoryValue method of WARD gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetWard() {
		DataCategory category = DataCategory.WARD;
		
		short ward = 7;
		
		// Set value of record attribute
		record.setWard(ward);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), ward);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof Short);
	}
	
	
	/**
	 * Checks the setCategoryValue method of FBI_CODE sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetFbiCode() throws UnsupportedCategoryException {
		DataCategory category = DataCategory.FBI_CODE;
		
		NIBRSCode fbiCode = new NIBRSCode("13C", "INTIMIDATION", "PERSON", "A");
		
		// Set value of record attribute using DataCategory method
		category.setCategoryValue(record, fbiCode);
		
		// Check value set correctly
		assertEquals(fbiCode, record.getFbiCode());
		
	}
	
	/**
	 * Checks the getCategoryValue method of FBI_CODE gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetFbiCode() {
		DataCategory category = DataCategory.FBI_CODE;
		
		NIBRSCode fbiCode = new NIBRSCode("13C", "INTIMIDATION", "PERSON", "A");
		
		// Set value of record attribute
		record.setFbiCode(fbiCode);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), fbiCode);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof NIBRSCode);
	}
	
	
	/**
	 * Checks the setCategoryValue method of LATITUDE sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetLatitude() throws UnsupportedCategoryException {
		DataCategory category = DataCategory.LATITUDE;
		
		float latitude = 41.7484863f;
		
		// Set value of record attribute using DataCategory method
		category.setCategoryValue(record, latitude);
		
		// Check value set correctly
		assertEquals(latitude, record.getLatitude());
		
	}
	
	/**
	 * Checks the getCategoryValue method of LATITUDE gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetLatitude() {
		DataCategory category = DataCategory.LATITUDE;
		
		float latitude = 41.7484863f;
		
		// Set value of record attribute
		record.setLatitude(latitude);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), latitude);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof Float);
	}
	
	
	/**
	 * Checks the setCategoryValue method of LONGITUDE sets the attribute value 
	 * of a crime record correctly.
	 * @throws UnsupportedCategoryException 
	 */
	@Test
	void testSetLongitude() throws UnsupportedCategoryException {
		DataCategory category = DataCategory.LONGITUDE;
		
		float longitude = -87.6026751f;
		
		// Set value of record attribute using DataCategory method
		category.setCategoryValue(record, longitude);
		
		// Check value set correctly
		assertEquals(longitude, record.getLongitude());
		
	}
	
	/**
	 * Checks the getCategoryValue method of LONGITUDE gets the attribute value
	 * of a crime record correctly (returns the correct value and type).
	 */
	@Test
	void testGetLongitude() {
		DataCategory category = DataCategory.LONGITUDE;
		
		float longitude = -87.6026751f;
		
		// Set value of record attribute
		record.setLongitude(longitude);
		
		// Check value of record attribute gotten using DataCategory method is correct
		assertEquals(category.getCategoryValue(record), longitude);
		
		// Check return type of DataCategory method correct
		assertTrue(category.getCategoryValue(record) instanceof Float);
	}
	
}
