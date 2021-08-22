package seng202.group2;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * CrimeRecord stores the categorical data associated with a record of a crime.
 * 
 * TODO: Implement validity checking when setting attributes that should follow a format,
 * or whose domain is a proper subset of its type, such as fbiCode.
 * 
 * @author Connor Dunlop
 *
 */
public class CrimeRecord {

	//Variables:

	/** The ID of the crime record as stored in the HashMap in CrimeData
	 * NOTE: This is not final as the CrimeData must assign it an ID
	 * TODO Discuss if this is what we are wanting.
	 */
	private Integer ID;
	
	/** The case number associated with the crime record in the police database */
	private String caseNum;
	
	/** The date and time at which the crime incident occurred */
	private Calendar date;
	
	/**
	 * The address of the crime incident at a city block level.
	 * Zip code with last two digits anonymized followed by street name.
	 */
	private String block;
	
	/**
	 * Illinois Uniform Crime Reporting code.
	 * Four digit code used to classify the criminal incident
	 * @see <a href="https://catalog.data.gov/dataset/chicago-police-department-illinois-uniform-crime-reporting-iucr-codes">DataSet</a>
	 */
	private IUCRCode iucr;
	
	/** A textual description of the location where the crime incident occurred */
	private String locationDescription;
	
	/** 
	 * 1 if the crime incident resulted in an arrest being made
	 * 0 if the crime incident did not result in an arrest being made
	 * -1 if there is no information provided
	 */
	private Boolean arrest;
	
	/**
	 * 1 if the crime incident was classified as domestic
	 * 0 if the crime incident was not classified as domestic
	 * -1 if there is no information provided
	 * */
	private Boolean domestic;
	
	/** 
	 * Police district where the crime incident occurred.
	 * (Area of the city broken down for patrol and statistical purposes)
	 */
	private Short beat;
	
	/** Election precinct where the crime incident occurred. */
	private Short ward;
	
	/**
	 * FBI crime code assigned to the crime incident.
	 * Used to categorize crime incidents by the type of crime that occurred. 
	 * @see <a href="https://ucr.fbi.gov/nibrs/2011/resources/nibrs-offense-codes/view">Offense Codes</a>
	 */
	private NIBRSCode nibrs;
	
	/** The latitudinal location where the crime incident occurred. */
	private Float latitude;
	
	/** The longitudinal location where the crime incident occurred. */
	private Float longitude;

	
	public String toString() {
		String dateString = DateTimeFormatter.ISO_INSTANT.format(date.toInstant());
		
		return "Crime record: " + ID + "\n" +
				"Case Number: " + caseNum + "\n" +
				"Date: " + dateString + "\n" +
				"Block: " + block + "\n" +
				"IUCR: " + iucr + "\n" +
				"Primary Description: " + iucr.PRIMARY_DESCRIPTION + "\n" +
				"Secondary Description: " + iucr.SECONDARY_DESCRIPTION + "\n" +
				"Location Description: " + locationDescription + "\n" +
				"Arrest: " + arrest + "\n" +
				"Domestic: " + domestic + "\n" +
				"Beat: " + beat + "\n" +
				"Ward: " + ward + "\n" +
				"FBI Code: " + nibrs + "\n" +
				"Latitude: " + latitude + "\n" +
				"Longitude: " + longitude;
	}

	//Getters/Setters:

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	/**
	 * Sets the caseNum
	 * @param caseNum The case number associated with the crime record in the police database
	 */
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
	
	/**
	 * Gets the caseNum
	 * @return The case number associated with the crime record in the police database
	 */
	public String getCaseNum() {
		return caseNum;
	}
	
	/**
	 * Sets the date/time on which the crime incident occurred.
	 * @param date The date/time on which the crime incident occurred.
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	/**
	 * Gets the date/time on which the crime incident occurred.
	 * @return The date/time on which the crime incident occurred.
	 */
	public Calendar getDate() {
		return date;
	}
	
	/**
	 * Sets the block where the crime incident occurred.
	 * @param block The address of the crime incident at a city block level.
	 */
	public void setBlock(String block) {
		this.block = block;
	}
	
	/**
	 * Gets the block where the crime incident occurred.
	 * @return The address of the crime incident at a city block level.
	 */
	public String getBlock() {
		return block;
	}
	
	/**
	 * Sets the Illinois Uniform Crime Reporting code.
	 * @param iucr The Illinois Uniform Crime Reporting code classifying the crime incident.
	 */
	public void setIucr(IUCRCode iucr) {
		this.iucr = iucr;
	}
	
	/**
	 * Gets the Illinois Uniform Crime Reporting code.
	 * @return The Illinois Uniform Crime Reporting code classifying the crime incident.
	 */
	public IUCRCode getIucr() {
		return iucr;
	}
	
	/**
	 * Gets the primary description of the crime incident.
	 * @return A textual description of the type of the crime incident
	 */
	public String getPrimaryDescription() {
		return iucr.PRIMARY_DESCRIPTION;
	}
	
	/**
	 * Gets the secondary description of the crime incident.
	 * @return A textual description giving supplementing details about the type of crime incident
	 */
	public String getSecondaryDescription() {
		return iucr.SECONDARY_DESCRIPTION;
	}
	
	/**
	 * Sets the location description of the crime incident.
	 * @param description A textual description of the location where the crime incident occurred
	 */
	public void setLocationDescription(String description) {
		locationDescription = description;
	}
	
	/**
	 * Gets the location description of the crime incident.
	 * @return A textual description of the location where the crime incident occurred
	 */
	public String getLocationDescription() {
		return locationDescription;
	}
	
	/**
	 * Sets the boolean value arrest, which is used to indicate whether or not the crime incident resulted in an arrest.
	 * @param arrest Set to true if the crime incident resulted in an arrest being made.
	 */
	public void setArrest(Boolean arrest) {
		this.arrest = arrest;
	}
	
	/**
	 * Gets the boolean value arrest, which is used to indicate whether or not the crime incident resulted in an arrest.
	 * @return True if the crime incident resulted in an arrest being made.
	 */
	public Boolean getArrest() {
		return arrest;
	}
	
	/**
	 * Sets the boolean value domestic, which is used to indicate whether or not the crime incident was classified as domestic.
	 * @param domestic Set to true if the crime incident was classified as domestic
	 */
	public void setDomestic(Boolean domestic) {
		this.domestic = domestic;
	}
	
	/**
	 * Gets the boolean value domestic, which is used to indicate whether or not the crime incident was classified as domestic.
	 * @return True if the crime incident was classified as domestic
	 */
	public Boolean getDomestic() {
		return domestic;
	}
	
	/**
	 * Sets the beat where the crime incident occurred.
	 * @param beat Police district where the crime incident occurred.
	 */
	public void setBeat(Short beat) {
		this.beat = beat;
	}
	
	/**
	 * Gets the beat where the crime incident occurred.
	 * @return Police district where the crime incident occurred.
	 */
	public Short getBeat() {
		return beat;
	}
	
	/**
	 * Sets the ward where the crime incident occurred.
	 * @param ward Election precinct where the crime incident occurred.
	 */
	public void setWard(Short ward) {
		this.ward = ward;
	}
	
	/**
	 * Gets the ward where the crime incident occurred.
	 * @return Election precinct where the crime incident occurred.
	 */
	public Short getWard() {
		return ward;
	}
	
	/**
	 * Sets the FBI code used to categorize the crime incident based on type.
	 * @param fbiCode FBI crime code assigned to the crime incident.
	 */
	public void setFbiCode(NIBRSCode nibrs) {
		this.nibrs = nibrs;
	}
	
	/**
	 * Gets the FBI code used to categorize the crime incident based on type.
	 * @return FBI crime code assigned to the crime incident.
	 */
	public NIBRSCode getFbiCode() {
		return nibrs;
	}
	
	/**
	 * Sets the latitudinal location where the crime incident occurred.
	 * @param latitude The latitudinal location where the crime incident occurred.
	 */
	public void setLatitude(Float latitude) {
		if (latitude != null)
			if (!(-90.0f <= latitude && latitude <= 90.0f))
				throw new IllegalArgumentException("Latitude out of bounds");
		
		this.latitude = latitude;
	}
	
	/**
	 * Gets the latitudinal location where the crime incident occurred.
	 * @return The latitudinal location where the crime incident occurred.
	 */
	public Float getLatitude() {
		return latitude;
	}
	
	/**
	 * Sets the longitudinal location where the crime incident occurred.
	 * @param longitude The longitudinal location where the crime incident occurred.
	 */
	public void setLongitude(Float longitude) {
		if (longitude != null)
			if (!(-180.0f <= longitude && longitude <= 180.0f))
				throw new IllegalArgumentException("Longitude out of bounds");
		
		this.longitude = longitude;
	}
	
	/**
	 * gets the longitudinal location where the crime incident occurred.
	 * @return The longitudinal location where the crime incident occurred.
	 */
	public Float getLongitude() {
		return longitude;
	}
}
