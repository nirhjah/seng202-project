package seng202.group2;

/**
 * A simple data structure for storing National Incident-Based Reporting System Code information.
 * 
 * @author Connor Dunlop
 *
 */
public class NIBRSCode {
	
	/** The offense (NIBRS/FBI) code */
	public final String NIBRS;
	/** The description of the crime type this code corresponds to */
	public final String DESCRIPTION;
	/** A classification of the party affected by the offense. E.g. person, property, society. */
	public final String AGAINST;
	
	/**
	 * The group of the offense code.
	 * Group A offenses are used to report all incidents committed within a law enforcement
	 * agency's jurisdiction to the NIBRS, whereas group B offenses are used only when reporting
	 * arrest data.
	 */
	public final String GROUP;
	
	public NIBRSCode(String nibrs, String description, String against, String group) {
		this.NIBRS = nibrs;
		this.DESCRIPTION = description;
		this.AGAINST = against;
		this.GROUP = group;
	}
	
	/**
	 * Use the NIBRS code when displaying NIBRS.
	 */
	public String toString() {
		return NIBRS;
	}
}