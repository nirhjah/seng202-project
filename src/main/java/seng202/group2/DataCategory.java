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

		public String parseString(String value) {
			if (value == "")
				return null;
			return value;
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

		public Calendar parseString(String value) {
			if (value == "")
				return null;
			
			// Parses data from format "dd/mm/yyyy hh:mm:ss PM"
			try {
				String[] splitValue = value.split(" ");
				
				// Split date/time into date, time and am/pm
				String date = splitValue[0];
				String time = splitValue[1];
				String am_pm = splitValue[2];
				
				// Get date in day, month, year
				String[] splitDate = date.split("/");
				int day = Integer.parseInt(splitDate[1]);
				int month = Integer.parseInt(splitDate[0]);
				int year = Integer.parseInt(splitDate[2]);
				
				// Get time in hour, minute, second
				String[] splitTime = time.split(":");
				int hour = (am_pm == "PM") ?  Integer.parseInt(splitTime[0]) + 12 : Integer.parseInt(splitTime[0]);
				int minute = Integer.parseInt(splitTime[1]);
				int second = Integer.parseInt(splitTime[2]);
				
				// Return a calender object representing date/time
				return new GregorianCalendar(year, month, day, hour, minute, second);
				
			} catch (Exception error) {
				throw new IllegalArgumentException(error);
			}
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

		public String parseString(String value) {
			if (value == "")
				return null;
			return value;
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
			else if (data instanceof IUCRCode)
				record.setIucr((IUCRCode) data);
			else
				throw new IllegalArgumentException();
		}
		
		public IUCRCode getCategoryValue(CrimeRecord record) {
			return record.getIucr();
		}
		
		public String toString() {
			return "IUCR";
		}

		public IUCRCode parseString(String value) {
			if (value == "")
				return null;
			while (value.startsWith("0"))
				value = value.replaceFirst("0", "");
			return IUCRCodeDictionary.getCode(value);
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

		public String parseString(String value) {
			if (value == "")
				return null;
			return value;
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

		public String parseString(String value) {
			if (value == "")
				return null;
			return value;
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

		public String parseString(String value) {
			if (value == "")
				return null;
			return value;
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

		public Boolean parseString(String value) {
			if (value == "")
				return null;
			else if (value.equals("Y"))
				return true;
			else if (value.equals("N"))
				return false;
			else
				throw new IllegalArgumentException();
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

		public Boolean parseString(String value) {
			if (value == "")
				return null;
			else if (value.equals("Y"))
				return true;
			else if (value.equals("N"))
				return false;
			else
				throw new IllegalArgumentException();
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

		public Short parseString(String value) {
			if (value == "")
				return null;
			
			try {
				return Short.parseShort(value);
			} catch (NumberFormatException error) {
				throw new IllegalArgumentException(error);
			}
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

		public Short parseString(String value) {
			if (value == "")
				return null;

			try {
				return Short.parseShort(value);
			} catch (NumberFormatException error) {
				throw new IllegalArgumentException(error);
			}
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
			else if (data instanceof NIBRSCode)
				record.setFbiCode((NIBRSCode) data);
			else
				throw new IllegalArgumentException();
		}
		
		public NIBRSCode getCategoryValue(CrimeRecord record) {
			return record.getFbiCode();
		}
		
		public String toString() {
			return "FBI Code";
		}

		public NIBRSCode parseString(String value) {
			if (value == "")
				return null;
			while (value.startsWith("0"))
				value = value.replaceFirst("0", "");
			return NIBRSCodeDictionary.getCode(value);
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
		
		public Float parseString(String value) {
			if (value == "")
				return null;

			try {
				return Float.parseFloat(value);
			} catch (NumberFormatException error) {
				throw new IllegalArgumentException(error);
			}
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
		
		public Float parseString(String value) {
			if (value == "")
				return null;

			try {
				return Float.parseFloat(value);
			} catch (NumberFormatException error) {
				throw new IllegalArgumentException(error);
			}
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
	
	/**
	 * Parses a string representing a value of data corresponding to this DataCategory
	 * into whatever type is used by a CrimeRecord to store data corresponding to this DataCategory.
	 * @param value A value string representing a value of data corresponding to this DataCategory.
	 * @return The value represented by the value string parsed into the type used to store data corresponding to this DataCategory.
	 */
	public abstract Object parseString(String value);
}
