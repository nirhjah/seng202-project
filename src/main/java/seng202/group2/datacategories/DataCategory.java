package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

public abstract class DataCategory {
	
	/**
	 * Sets the attribute of CrimeRecord record, which corresponds to this DataCategory
	 * enumerator to the value stored in data. Type casting is handled within the method. 
	 * @param record The CrimeRecord instance whose attribute value is to be set.
	 * @param data The data to set the CrimeRecord's attribute value to.
	 * @throws UnsupportedCategoryException 
	 */
	public abstract void setCategoryValue(CrimeRecord record, Object data) throws UnsupportedCategoryException;
	
	/**
	 * Gets the value of the attribute of CrimeRecord record, which corresponds to this DataCategory
	 * enumerator. Type casting is handled within the method.
	 * @param record The CrimeRecord instance whose attribute value is to be retrieved.
	 * @return The value of the CrimeRecord's attribute.
	 * @throws UnsupportedCategoryException 
	 */
	public abstract Object getCategoryValue(CrimeRecord record) throws UnsupportedCategoryException;
	
	/**
	 * Parses a string representing a value of data corresponding to this DataCategory
	 * into whatever type is used by a CrimeRecord to store data corresponding to this DataCategory.
	 * @param value A value string representing a value of data corresponding to this DataCategory.
	 * @return The value represented by the value string parsed into the type used to store data corresponding to this DataCategory.
	 * @throws UnsupportedCategoryException 
	 */
	public abstract Object parseString(String value) throws UnsupportedCategoryException;
	
	public static DataCategory getCategoryFromString(String categoryString) throws UnsupportedCategoryException {
		switch (categoryString.replaceAll("\\s", "")) {
		case "CASE#":
			return new CaseNumber();
		case "DATEOFOCCURRENCE":
			return new Date();
		case "BLOCK":
			return new Block();
		case "IUCR":
			return new IUCRCode();
		case "PRIMARYDESCRIPTION":
			return new PrimaryDescription();
		case "SECONDARYDESCRIPTION":
			return new SecondaryDescription();
		case "LOCATIONDESCRIPTION":
			return new LocationDescription();
		case "ARREST":
			return new Arrest();
		case "DOMESTIC":
			return new Domestic();
		case "BEAT":
			return new Beat();
		case "WARD":
			return new Ward();
		case "FBICD":
			return new FBICode();
		case "LATITUDE":
			return new Latitude();
		case "LONGITUDE":
			return new Longitude();
		default:
			throw new UnsupportedCategoryException(categoryString);
	}
	}
}
