package seng202.group2.model;

import seng202.group2.model.datacategories.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * CrimeRecord stores the categorical data associated with a record of a crime.
 * 
 * @author Connor Dunlop
 */
public class CrimeRecord {

	//Variables:

	/** The ID of the crime record as stored in the HashMap in CrimeData
	 * NOTE: This is not final as the CrimeData must assign it an ID
	 */
	private ID ID = new ID();
	
	/** The case number associated with the crime record in the police database */
	private CaseNumber caseNum = new CaseNumber();
	
	/** The date and time at which the crime incident occurred */
	private Date date = new Date();
	
	/**
	 * The address of the crime incident at a city block level.
	 * Zip code with last two digits anonymized followed by street name.
	 */
	private Block block = new Block();
	
	/**
	 * Illinois Uniform Crime Reporting code.
	 * Four digit code used to classify the criminal incident
	 * @see <a href="https://catalog.data.gov/dataset/chicago-police-department-illinois-uniform-crime-reporting-iucr-codes">DataSet</a>
	 */
	private IUCRCode iucr = new IUCRCode();

	/** A textual description of the type of the crime incident. */
	private PrimaryDescription primaryDescription = new PrimaryDescription();

	/**
	 * A textual description giving more details supplementing the categorization
	 * of the crime type provided in the primary description.
	 */
	private SecondaryDescription secondaryDescription = new SecondaryDescription();
	
	/** A textual description of the location where the crime incident occurred */
	private LocationDescription locationDescription = new LocationDescription();
	
	/** 
	 * 1 if the crime incident resulted in an arrest being made
	 * 0 if the crime incident did not result in an arrest being made
	 * null if there is no information provided
	 */
	private Arrest arrest = new Arrest();
	
	/**
	 * 1 if the crime incident was classified as domestic
	 * 0 if the crime incident was not classified as domestic
	 * null if there is no information provided
	 * */
	private Domestic domestic = new Domestic();
	
	/** 
	 * Police district where the crime incident occurred.
	 * (Area of the city broken down for patrol and statistical purposes)
	 */
	private Beat beat = new Beat();
	
	/** Election precinct where the crime incident occurred. */
	private Ward ward = new Ward();
	
	/**
	 * FBI crime code assigned to the crime incident.
	 * Used to categorize crime incidents by the type of crime that occurred. 
	 * @see <a href="https://ucr.fbi.gov/nibrs/2011/resources/nibrs-offense-codes/view">Offense Codes</a>
	 */
	private FBICode fbiCode = new FBICode();
	
	/** The latitudinal location where the crime incident occurred. */
	private Latitude latitude = new Latitude();
	
	/** The longitudinal location where the crime incident occurred. */
	private Longitude longitude = new Longitude();

	
	public String toString() {
		
		return "Crime record: " + ID + "\n" +
				"Case Number: " + caseNum.getValue() + "\n" +
				"Date: " + date.getValue() + "\n" +
				"Block: " + block.getValue() + "\n" +
				"IUCR: " + iucr.getValue() + "\n" +
				"Primary Description: " + primaryDescription.getValue() + "\n" +
				"Secondary Description: " + secondaryDescription.getValue() + "\n" +
				"Location Description: " + locationDescription.getValue() + "\n" +
				"Arrest: " + arrest.getValue() + "\n" +
				"Domestic: " + domestic.getValue() + "\n" +
				"Beat: " + beat.getValue() + "\n" +
				"Ward: " + ward.getValue() + "\n" +
				"FBI Code: " + fbiCode.getValue() + "\n" +
				"Latitude: " + latitude.getValue() + "\n" +
				"Longitude: " + longitude.getValue() + "\n";
	}

	//Getters/Setters:

	public int getID() {
		return this.ID.getValue();
	}

	public void setID(int ID) {
		this.ID.setValue(ID);
	}

	public ID getIDCategory() {
		return this.ID;
	}

	/**
	 * Sets the caseNum
	 * @param caseNum The case number associated with the crime record in the police database
	 */
	public void setCaseNum(String caseNum) {
		this.caseNum.setValue(caseNum);
	}
	
	/**
	 * Gets the caseNum
	 * @return The case number associated with the crime record in the police database
	 */
	public String getCaseNum() {
		return caseNum.getValue();
	}

	public CaseNumber getCaseNumCategory() {
		return caseNum;
	}
	
	/**
	 * Sets the date/time on which the crime incident occurred.
	 * @param date The date/time on which the crime incident occurred.
	 */
	public void setDate(Calendar date) {
		this.date.setValue(date);
	}

	/**
	 * Gets the date/time on which the crime incident occurred.
	 * @return The date/time on which the crime incident occurred.
	 */
	public Calendar getDate() {
		return date.getValue();
	}

	public Date getDateCategory() {
		return date;
	}

	/**
	 * Sets the block where the crime incident occurred.
	 * @param block The address of the crime incident at a city block level.
	 */
	public void setBlock(String block) {
		this.block.setValue(block);
	}
	
	/**
	 * Gets the block where the crime incident occurred.
	 * @return The address of the crime incident at a city block level.
	 */
	public String getBlock() {
		return block.getValue();
	}

	public Block getBlockCategory() {
		return block;
	}
	
	/**
	 * Sets the Illinois Uniform Crime Reporting code.
	 * @param iucr The Illinois Uniform Crime Reporting code classifying the crime incident.
	 */
	public void setIucr(String iucr) {
		this.iucr.setValue(iucr);
	}
	
	/**
	 * Gets the Illinois Uniform Crime Reporting code.
	 * @return The Illinois Uniform Crime Reporting code classifying the crime incident.
	 */
	public String getIucr() {
		return iucr.getValue();
	}

	public IUCRCode getIucrCategory() {
		return iucr;
	}

	/**
	 * Sets the primary description of the crime incident.
	 * @param primaryDescription The primary description of the crime incident.
	 */
	public void setPrimaryDescription(String primaryDescription) {
		this.primaryDescription.setValue(primaryDescription);
	}
	
	/**
	 * Gets the primary description of the crime incident.
	 * @return A textual description of the type of the crime incident
	 */
	public String getPrimaryDescription() {
		return primaryDescription.getValue();
	}

	public PrimaryDescription getPrimaryDescriptionCategory() {
		return primaryDescription;
	}

	/**
	 * Sets the secondary description of the crime incident.
	 * @param secondaryDescription The secondary description of the crime incident.
	 */
	public void setSecondaryDescription(String secondaryDescription) {
		this.secondaryDescription.setValue(secondaryDescription);
	}
	
	/**
	 * Gets the secondary description of the crime incident.
	 * @return A textual description giving supplementing details about the type of crime incident
	 */
	public String getSecondaryDescription() {
		return secondaryDescription.getValue();
	}

	public SecondaryDescription getSecondaryDescriptionCategory() {
		return secondaryDescription;
	}
	
	/**
	 * Sets the location description of the crime incident.
	 * @param locationDescription A textual description of the location where the crime incident occurred
	 */
	public void setLocationDescription(String locationDescription) {
		this.locationDescription.setValue(locationDescription);
	}
	
	/**
	 * Gets the location description of the crime incident.
	 * @return A textual description of the location where the crime incident occurred
	 */
	public String getLocationDescription() {
		return locationDescription.getValue();
	}

	public LocationDescription getLocationDescriptionCategory() {
		return locationDescription;
	}
	
	/**
	 * Sets the boolean value arrest, which is used to indicate whether or not the crime incident resulted in an arrest.
	 * @param arrest Set to true if the crime incident resulted in an arrest being made.
	 */
	public void setArrest(Boolean arrest) {
		this.arrest.setValue(arrest);
	}
	
	/**
	 * Gets the boolean value arrest, which is used to indicate whether or not the crime incident resulted in an arrest.
	 * @return True if the crime incident resulted in an arrest being made.
	 */
	public Boolean getArrest() {
		return arrest.getValue();
	}

	public Arrest getArrestCategory() {
		return arrest;
	}
	
	/**
	 * Sets the boolean value domestic, which is used to indicate whether or not the crime incident was classified as domestic.
	 * @param domestic Set to true if the crime incident was classified as domestic
	 */
	public void setDomestic(Boolean domestic) {
		this.domestic.setValue(domestic);
	}
	
	/**
	 * Gets the boolean value domestic, which is used to indicate whether or not the crime incident was classified as domestic.
	 * @return True if the crime incident was classified as domestic
	 */
	public Boolean getDomestic() {
		return domestic.getValue();
	}

	public Domestic getDomesticCategory() {
		return domestic;
	}
	
	/**
	 * Sets the beat where the crime incident occurred.
	 * @param beat Police district where the crime incident occurred.
	 */
	public void setBeat(Short beat) {
		this.beat.setValue(beat);
	}
	
	/**
	 * Gets the beat where the crime incident occurred.
	 * @return Police district where the crime incident occurred.
	 */
	public Short getBeat() {
		return beat.getValue();
	}

	public Beat getBeatCategory() {
		return beat;
	}
	
	/**
	 * Sets the ward where the crime incident occurred.
	 * @param ward Election precinct where the crime incident occurred.
	 */
	public void setWard(Short ward) {
		this.ward.setValue(ward);
	}
	
	/**
	 * Gets the ward where the crime incident occurred.
	 * @return Election precinct where the crime incident occurred.
	 */
	public Short getWard() {
		return ward.getValue();
	}

	public Ward getWardCategory() {
		return ward;
	}
	
	/**
	 * Sets the FBI code used to categorize the crime incident based on type.
	 * @param fbiCode FBI crime code assigned to the crime incident.
	 */
	public void setFbiCode(String fbiCode) {
		this.fbiCode.setValue(fbiCode);
	}
	
	/**
	 * Gets the FBI code used to categorize the crime incident based on type.
	 * @return FBI crime code assigned to the crime incident.
	 */
	public String getFbiCode() {
		return fbiCode.getValue();
	}

	public FBICode getFbiCodeCategory() {
		return fbiCode;
	}
	
	/**
	 * Sets the latitudinal location where the crime incident occurred.
	 * @param latitude The latitudinal location where the crime incident occurred.
	 */
	public void setLatitude(Float latitude) {
		this.latitude.setValue(latitude);
	}
	
	/**
	 * Gets the latitudinal location where the crime incident occurred.
	 * @return The latitudinal location where the crime incident occurred.
	 */
	public Float getLatitude() {
		return latitude.getValue();
	}

	public Latitude getLatitudeCategory() {
		return latitude;
	}
	
	/**
	 * Sets the longitudinal location where the crime incident occurred.
	 * @param longitude The longitudinal location where the crime incident occurred.
	 */
	public void setLongitude(Float longitude) {
		this.longitude.setValue(longitude);
	}
	
	/**
	 * gets the longitudinal location where the crime incident occurred.
	 * @return The longitudinal location where the crime incident occurred.
	 */
	public Float getLongitude() {
		return longitude.getValue();
	}

	public Longitude getLongitudeCategory() {
		return longitude;
	}
}
