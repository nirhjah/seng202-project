package seng202.group2.datacategories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit test for DataCategory class methods.
 * 
 * @author Connor Dunlop
 *
 */
class DataCategoryTest {
	
	/** List of valid category strings. */
	private String[] validStrings = { 
			"CASE #",
			"CASE#",
			"DATE OF OCCURRENCE",
			"BLOCK",
			"IUCR",
			"PRIMARY DESCRIPTION",
			"SECONDARY DESCRIPTION",
			"LOCATION DESCRIPTION",
			"ARREST",
			"DOMESTIC",
			"BEAT",
			"WARD",
			"FBI CD",
			"LATITUDE",
			"LONGITUDE"
	};
	
	/**
	 * List of category classes.
	 * Index of classes matches index of corresponding string.
	 */
	private Class<?>[] categoryClasses = {
			CaseNumber.class,
			CaseNumber.class,
			Date.class,
			Block.class,
			IUCRCode.class,
			PrimaryDescription.class,
			SecondaryDescription.class,
			LocationDescription.class,
			Arrest.class,
			Domestic.class,
			Beat.class,
			Ward.class,
			FBICode.class,
			Latitude.class,
			Longitude.class
	};
	
	/** List of invalid category strings. */
	private String[] invalidStrings = {
			"",
			" ",
			"NOT A REAL CATEGORY",
			"0",
			"999999",
			" ".repeat(1000000),
			"invalid".repeat(1000000)
	};

	/**
	 * Checks that each valid category string passed to the getCategoryFromString method
	 * of DataCategory returns an object of the correct type.
	 */
	@Test
	void testGetCategoryFromString_ValidStrings() {
		try {
			for (int i = 0; i < validStrings.length; i++) {
				DataCategory categoryObject = DataCategory.getCategoryFromString(validStrings[i]);
				assertTrue(categoryObject.getClass() == categoryClasses[i]);
			}
		} catch (UnsupportedCategoryException error) {
			fail(error.toString());
		}
	}
	
	/**
	 * Checks that each invalid category string passed to the getCategoryFromString method
	 * of DataCategory throws an error.
	 */
	@Test
	void testGetCategoryFromString_InvalidStrings() {
		for (String categoryString : invalidStrings) {
			assertThrows(UnsupportedCategoryException.class, () -> {
				DataCategory.getCategoryFromString(categoryString);
			});
		}
	}
	
	/**
	 * Checks null string behaviour of getCategoryFromString method of
	 * DataCategory.
	 */
	@Test
	void testGetCategoryFromString_NullStrings() {
		assertThrows(IllegalArgumentException.class, () -> {
			DataCategory.getCategoryFromString(null);
		});
	}

}
