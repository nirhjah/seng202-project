package seng202.group2;

import java.util.Calendar;

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
			record.setCaseNum((String) data);
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getCaseNum();
		}
	},
	
	/** The date and time at which the crime incident occurred */
	DATE {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setDate((Calendar) data);
		}
		
		public Calendar getCategoryValue(CrimeRecord record) {
			return record.getDate();
		}
	},
	
	/**
	 * The address of the crime incident at a city block level.
	 * Zip code with last two digits anonymized followed by street name.
	 */
	BLOCK {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setBlock((String) data);
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getBlock();
		}
	},
	
	/**
	 * Illinois Uniform Crime Reporting code.
	 * Four digit code used to classify the criminal incident
	 */
	IUCR {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setIucr((String) data);
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getIucr();
		}
	},
	
	/** A textual description of the type of the crime incident */
	PRIMARY_DESCRIPTION {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setPrimaryDescription((String) data);
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getPrimaryDescription();
		}
	},
	
	/**
	 * A textual description giving more details supplementing the categorization
	 * of the crime type provided in the primary description
	 */
	SECONDARY_DESCRIPTION {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setSecondaryDescription((String) data);
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getSecondaryDescription();
		}
	},
	
	/** A textual description of the location where the crime incident occurred */
	LOCATION_DESCRIPTION {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setLocationDescription((String) data);
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getLocationDescription();
		}
	},
	
	/** True if the crime incident resulted in an arrest being made */
	ARREST {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setArrest((Boolean) data);
		}
		
		public Boolean getCategoryValue(CrimeRecord record) {
			return record.getArrest();
		}
	},
	
	/** True if the crime incident was classified as domestic */
	DOMESTIC {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setDomestic((Boolean) data);
		}
		
		public Boolean getCategoryValue(CrimeRecord record) {
			return record.getDomestic();
		}
	},
	
	/** 
	 * Police district where the crime incident occurred.
	 * (Area of the city broken down for patrol and statistical purposes)
	 */
	BEAT {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setBeat((Short) data);
		}
		
		public Short getCategoryValue(CrimeRecord record) {
			return record.getBeat();
		}
	},
	
	/** Election precinct where the crime incident occurred. */
	WARD {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setWard((Short) data);
		}
		
		public Short getCategoryValue(CrimeRecord record) {
			return record.getWard();
		}
	},
	
	/**
	 * FBI crime code assigned to the crime incident.
	 * Used to categorize crime incidents by the type of crime that occurred. 
	 */
	FBI_CODE {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setFbiCode((String) data);
		}
		
		public String getCategoryValue(CrimeRecord record) {
			return record.getFbiCode();
		}
	},
	
	/** The latitudinal location where the crime incident occurred. */
	LATITUDE {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setLatitude((Float) data);
		}
		
		public Float getCategoryValue(CrimeRecord record) {
			return record.getLatitude();
		}
	},
	
	/** The longitudinal location where the crime incident occurred. */
	LONGITUDE {
		public void setCategoryValue(CrimeRecord record, Object data) {
			record.setLongitude((Float) data);
		}
		
		public Float getCategoryValue(CrimeRecord record) {
			return record.getLongitude();
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
