package seng202.group2;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * DataCategory enumerates the different categories of data stored in the attributes of CrimeRecord.
 * 
 * This enumeration facilitates the selection of the various categories within a CrimeRecord,
 * providing the methods setCategoryValue and getCategoryValue for setting and getting the value
 * of an attribute of a CrimeRecord instance through the corresponding DataCategory.
 * 
 * 
 * The following example shows how to set the value of the caseNum attribute of a crime record, using 
 * DataCategory to select the attribute:
 * 
 * 		// Make a crime record
 * 		CrimeRecord record = new CrimeRecord();
 * 		// Set value of record.caseNum using DataCategory method
 * 		DataCategory.CASE_NUM.setCategoryValue(record, "ExampleCaseNum");
 * 
 * The following example shows how to get the value of the beat attribute of a crime record, using
 * DataCategory to select the attribute:
 * 
 * 		// Make a crime record
 * 		CrimeRecord record = new CrimeRecord();
 * 		// Set the value of record.beat, so there is some value to get
 * 		record.setBeat(18);
 * 		// Get value of record.beat using DataCategory method
 * 		short beat = DataCategory.BEAT.getCategoryValue(record);
 * 
 * 
 * @author Connor Dunlop
 *
 */
public enum DataCategory {
	
	/** The case number associated with the crime record in the police database */
	CASE_NUM {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setCaseNum(null);
			else if (data instanceof String)
				record.setCaseNum((String) data);
			else
				throw new IllegalArgumentException();
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getCaseNum();
		}
		
		public String toString() {
			return "Case Number";
		}
	},
	
	/** The date and time at which the crime incident occurred */
	DATE {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setDate(null);
			if (data instanceof Calendar)
				record.setDate((Calendar) data);
			else
				throw new IllegalArgumentException();
		}
		
		public Calendar getCategoryValue(CrimeRecord record) {
			return record.getDate();
		}
		
		public String toString() {
			return "Date";
		}
	},
	
	/**
	 * The address of the crime incident at a city block level.
	 * Zip code with last two digits anonymized followed by street name.
	 */
	BLOCK {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setBlock(null);
			else if (data instanceof String)
				record.setBlock((String) data);
			else
				throw new IllegalArgumentException();
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getBlock();
		}
		
		public String toString() {
			return "Block";
		}
	},
	
	/**
	 * Illinois Uniform Crime Reporting code.
	 * Four digit code used to classify the criminal incident
	 */
	IUCR {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setIucr(null);
			else if (data instanceof String)
				record.setIucr((String) data);
			else
				throw new IllegalArgumentException();
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getIucr();
		}
		
		public String toString() {
			return "IUCR";
		}
	},
	
	/** A textual description of the type of the crime incident */
	PRIMARY_DESCRIPTION {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setPrimaryDescription(null);
			else if (data instanceof String)
				record.setPrimaryDescription((String) data);
			else
				throw new IllegalArgumentException();
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getPrimaryDescription();
		}
		
		public String toString() {
			return "Primary Description";
		}
	},
	
	/**
	 * A textual description giving more details supplementing the categorization
	 * of the crime type provided in the primary description
	 */
	SECONDARY_DESCRIPTION {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setSecondaryDescription(null);
			else if (data instanceof String)
				record.setSecondaryDescription((String) data);
			else
				throw new IllegalArgumentException();
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getSecondaryDescription();
		}
		
		public String toString() {
			return "Secondary Description";
		}
	},
	
	/** A textual description of the location where the crime incident occurred */
	LOCATION_DESCRIPTION {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setLocationDescription(null);
			else if (data instanceof String)
				record.setLocationDescription((String) data);
			else
				throw new IllegalArgumentException();
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getLocationDescription();
		}
		
		public String toString() {
			return "Location Description";
		}
	},
	
	/** True if the crime incident resulted in an arrest being made */
	ARREST {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setArrest(null);
			else if (data instanceof Boolean)
				record.setArrest((Boolean) data);
			else
				throw new IllegalArgumentException();
		}
		
		public Boolean getCategoryValue(CrimeRecord record) {
			return record.getArrest();
		}
		
		public String toString() {
			return "Arrest";
		}
	},
	
	/** True if the crime incident was classified as domestic */
	DOMESTIC {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setDomestic(null);
			else if (data instanceof Boolean)
				record.setDomestic((Boolean) data);
			else
				throw new IllegalArgumentException();
		}
		
		public Boolean getCategoryValue(CrimeRecord record) {
			return record.getDomestic();
		}
		
		public String toString() {
			return "Domestic";
		}
	},
	
	/** 
	 * Police district where the crime incident occurred.
	 * (Area of the city broken down for patrol and statistical purposes)
	 */
	BEAT {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setBeat(null);
			else if (data instanceof Short)
				record.setBeat((Short) data);
			else
				throw new IllegalArgumentException();
		}
		
		public Short getCategoryValue(CrimeRecord record) {
			return record.getBeat();
		}
		
		public String toString() {
			return "Beat";
		}
	},
	
	/** Election precinct where the crime incident occurred. */
	WARD {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setWard(null);
			else if (data instanceof Short)
				record.setWard((Short) data);
			else
				throw new IllegalArgumentException();
		}
		
		public Short getCategoryValue(CrimeRecord record) {
			return record.getWard();
		}
		
		public String toString() {
			return "Ward";
		}
	},
	
	/**
	 * FBI crime code assigned to the crime incident.
	 * Used to categorize crime incidents by the type of crime that occurred. 
	 */
	FBI_CODE {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setFbiCode(null);
			else if (data instanceof String)
				record.setFbiCode((String) data);
			else
				throw new IllegalArgumentException();
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getFbiCode();
		}
		
		public String toString() {
			return "FBI Code";
		}
	},
	
	/** The latitudinal location where the crime incident occurred. */
	LATITUDE {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setLatitude(null);
			else if (data instanceof Float)
				record.setLatitude((Float) data);
			else
				throw new IllegalArgumentException();
		}
		
		public Float getCategoryValue(CrimeRecord record) {
			return record.getLatitude();
		}
		
		public String toString() {
			return "Latitude";
		}
	},
	
	/** The longitudinal location where the crime incident occurred. */
	LONGITUDE {
		public void setCategoryValue(CrimeRecord record, Object data) {
			if (data == null)
				record.setLongitude(null);
			else if (data instanceof Float)
				record.setLongitude((Float) data);
			else
				throw new IllegalArgumentException();
		}
		
		public Float getCategoryValue(CrimeRecord record) {
			return record.getLongitude();
		}
		
		public String toString() {
			return "Longitude";
		}
	};
	
	
	/**
	 * Sets the attribute of CrimeRecord record, which corresponds to this DataCategory
	 * enumerator to the value stored in data. Type casting is handled within the method. 
	 * @param record The CrimeRecord instance whose attribute value is to be set.
	 * @param data The data to set the CrimeRecord's attribute value to.
	 */
	public abstract void setCategoryValue(CrimeRecord record, Object data);
	
	/**
	 * Gets the value of the attribute of CrimeRecord record, which corresponds to this DataCategory
	 * enumerator. Type casting is handled within the method.
	 * @param record The CrimeRecord instance whose attribute value is to be retrieved.
	 * @return The value of the CrimeRecord's attribute.
	 */
	public abstract Object getCategoryValue(CrimeRecord record);
	
	/**
	 * Gets the DataCategory from a string representing a category.
	 * Throws UnsupportedCategoryException if the string is not known to represent any category.
	 * 
	 * TODO: Add other strings which may identify a DataCategory
	 * TODO: Improve efficiency by not checking every string against every valid string
	 * 
	 * @param categoryString The string which may represent a DataCategory
	 * @return The DataCategroy represented by the category string
	 * @throws UnsupportedCategoryException
	 */
	public static DataCategory getCategoryFromString(String categoryString) throws UnsupportedCategoryException {
		switch (categoryString.replaceAll("\\s", "")) {
			case "CASE#":
				return CASE_NUM;
			case "DATEOFOCCURRENCE":
				return DATE;
			case "BLOCK":
				return BLOCK;
			case "IUCR":
				return IUCR;
			case "PRIMARYDESCRIPTION":
				return PRIMARY_DESCRIPTION;
			case "SECONDARYDESCRIPTION":
				return SECONDARY_DESCRIPTION;
			case "LOCATIONDESCRIPTION":
				return LOCATION_DESCRIPTION;
			case "ARREST":
				return ARREST;
			case "DOMESTIC":
				return DOMESTIC;
			case "BEAT":
				return BEAT;
			case "WARD":
				return WARD;
			case "FBICD":
				return FBI_CODE;
			case "LATITUDE":
				return LATITUDE;
			case "LONGITUDE":
				return LONGITUDE;
			default:
				throw new UnsupportedCategoryException(categoryString);
		}
	}
}
