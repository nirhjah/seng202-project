package seng202.group2;

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
	 * @see  https://catalog.data.gov/dataset/chicago-police-department-illinois-uniform-crime-reporting-iucr-codes
	 */
	private String iucr;
	
	/** A textual description of the type of the crime incident */
	private String primaryDescription;
	
	/**
	 * A textual description giving more details supplementing the categorization
	 * of the crime type provided in the primary description
	 */
	private String secondaryDescription;
	
	/** A textual description of the location where the crime incident occurred */
	private String locationDescription;
	
	/** True if the crime incident resulted in an arrest being made */
	private boolean arrest;
	
	/** True if the crime incident was classified as domestic */
	private boolean domestic;
	
	/** 
	 * Police district where the crime incident occurred.
	 * (Area of the city broken down for patrol and statistical purposes)
	 */
	private short beat;
	
	/** Election precinct where the crime incident occurred. */
	private short ward;
	
	/**
	 * FBI crime code assigned to the crime incident.
	 * Used to categorize crime incidents by the type of crime that occurred. 
	 * @see https://ucr.fbi.gov/nibrs/2011/resources/nibrs-offense-codes/view
	 */
	private String fbiCode;
	
	/** The latitudinal location where the crime incident occurred. */
	private float latitude;
	
	/** The longitudinal location where the crime incident occurred. */
	private float longitude;
	
	
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
	public void setIucr(String iucr) {
		this.iucr = iucr;
	}
	
	/**
	 * Gets the Illinois Uniform Crime Reporting code.
	 * @return The Illinois Uniform Crime Reporting code classifying the crime incident.
	 */
	public String getIucr() {
		return iucr;
	}
	
	/**
	 * Sets the primary description of the crime incident.
	 * @param description A textual description of the type of the crime incident
	 */
	public void setPrimaryDescription(String description) {
		primaryDescription = description;
	}
	
	/**
	 * Gets the primary description of the crime incident.
	 * @return A textual description of the type of the crime incident
	 */
	public String getPrimaryDescription() {
		return primaryDescription;
	}
	
	/**
	 * Sets the secondary description of the crime incident.
	 * @param description A textual description giving supplementing details about the type of crime incident
	 */
	public void setSecondaryDescription(String description) {
		secondaryDescription = description;
	}
	
	/**
	 * Gets the secondary description of the crime incident.
	 * @return A textual description giving supplementing details about the type of crime incident
	 */
	public String getSecondaryDescription() {
		return secondaryDescription;
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
	public void setArrest(boolean arrest) {
		this.arrest = arrest;
	}
	
	/**
	 * Gets the boolean value arrest, which is used to indicate whether or not the crime incident resulted in an arrest.
	 * @return True if the crime incident resulted in an arrest being made.
	 */
	public boolean getArrest() {
		return arrest;
	}
	
	/**
	 * Sets the boolean value domestic, which is used to indicate whether or not the crime incident was classified as domestic.
	 * @param domestic Set to true if the crime incident was classified as domestic
	 */
	public void setDomestic(boolean domestic) {
		this.domestic = domestic;
	}
	
	/**
	 * Gets the boolean value domestic, which is used to indicate whether or not the crime incident was classified as domestic.
	 * @return True if the crime incident was classified as domestic
	 */
	public boolean getDomestic() {
		return domestic;
	}
	
	/**
	 * Sets the beat where the crime incident occurred.
	 * @param beat Police district where the crime incident occurred.
	 */
	public void setBeat(short beat) {
		this.beat = beat;
	}
	
	/**
	 * Gets the beat where the crime incident occurred.
	 * @return Police district where the crime incident occurred.
	 */
	public short getBeat() {
		return beat;
	}
	
	/**
	 * Sets the ward where the crime incident occurred.
	 * @param ward Election precinct where the crime incident occurred.
	 */
	public void setWard(short ward) {
		this.ward = ward;
	}
	
	/**
	 * Gets the ward where the crime incident occurred.
	 * @return Election precinct where the crime incident occurred.
	 */
	public short getWard() {
		return ward;
	}
	
	/**
	 * Sets the FBI code used to categorize the crime incident based on type.
	 * @param fbiCode FBI crime code assigned to the crime incident.
	 */
	public void setFbiCode(String fbiCode) {
		this.fbiCode = fbiCode;
	}
	
	/**
	 * Gets the FBI code used to categorize the crime incident based on type.
	 * @return FBI crime code assigned to the crime incident.
	 */
	public String getFbiCode() {
		return fbiCode;
	}
	
	/**
	 * Sets the latitudinal location where the crime incident occurred.
	 * @param latitude The latitudinal location where the crime incident occurred.
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * Gets the latitudinal location where the crime incident occurred.
	 * @return The latitudinal location where the crime incident occurred.
	 */
	public float getLatitude() {
		return latitude;
	}
	
	/**
	 * Sets the longitudinal location where the crime incident occurred.
	 * @param longitude The longitudinal location where the crime incident occurred.
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * gets the longitudinal location where the crime incident occurred.
	 * @return The longitudinal location where the crime incident occurred.
	 */
	public float getLongitude() {
		return longitude;
	}
}
