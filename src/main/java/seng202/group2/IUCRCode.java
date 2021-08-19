package seng202.group2;

/**
 * A simple data structure for storing Illinois Uniform Crime Reporting Code information.
 * 
 * @author Connor Dunlop
 *
 */
public class IUCRCode {
	
	/** The IUCR code */
	public final String IUCR;
	/** The primary description of the crime type this code corresponds to */
	public final String PRIMARY_DESCRIPTION;
	/** The secondary description of the crime type this code corresponds to */
	public final String SECONDARY_DESCRIPTION;
	/** 
	 * If an IUCR code is an index code, then the crime type represented by this IUCR code
	 * can be found under a NIBRS offense code, which are codes used by the FBI for collating
	 * and processing crime data.
	 */
	public final Boolean INDEX;
	
	public IUCRCode(String iucr, String primaryDescription, String secondaryDescription, Boolean index) {
		this.IUCR = iucr;
		this.PRIMARY_DESCRIPTION = primaryDescription;
		this.SECONDARY_DESCRIPTION = secondaryDescription;
		this.INDEX = index;
	}
	
	/**
	 * Use the IUCR code when displaying IUCR.
	 */
	public String toString() {
		return IUCR;
	}
}
