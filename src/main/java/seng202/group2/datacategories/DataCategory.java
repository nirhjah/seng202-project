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
//		case "DATEOFOCCURRENCE":
//			return Date.class;
//		case "BLOCK":
//			return Block.class;
//		case "IUCR":
//			return Iucr.class;
//		case "PRIMARYDESCRIPTION":
//			return PrimaryDescription.class;
//		case "SECONDARYDESCRIPTION":
//			return SecondaryDescription.class;
//		case "LOCATIONDESCRIPTION":
//			return LocationDescription.class;
//		case "ARREST":
//			return Arrest.class;
//		case "DOMESTIC":
//			return Domestic.class;
//		case "BEAT":
//			return Beat.class;
//		case "WARD":
//			return Ward.class;
//		case "FBICD":
//			return FBICode.class;
//		case "LATITUDE":
//			return Latitude.class;
//		case "LONGITUDE":
//			return Longitude.class;
		default:
			throw new UnsupportedCategoryException(categoryString);
	}
	}
}
