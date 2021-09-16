package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;
/**
 * FBI crime code assigned to the crime incident.
 * Used to categorize crime incidents by the type of crime that occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class FBICode extends DataCategory implements Importable {

	private static final FBICode instance = new FBICode();
	
	/**
	 * FBI crime code assigned to the crime incident.
	 * Used to categorize crime incidents by the type of crime that occurred. 
	 * @see <a href="https://ucr.fbi.gov/nibrs/2011/resources/nibrs-offense-codes/view">Offense Codes</a>
	 */
	private String fbiCode = null;

	public static FBICode getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setFbiCode(null);
		else if (data instanceof String)
			record.setFbiCode((String) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for FBICode.");
	}

	@Override
	public String getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getFbiCode();
	}

	@Override
	public String parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		else if (value == "")
			return null;
		while (value.startsWith("0"))
			value = value.replaceFirst("0", "");
		return value;
	}

	@Override
	public String getSQL() {
		return "fbiCode";
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.fbiCode = null;
		else if (value instanceof String)
			this.fbiCode = (String) value;
		else
			throw new IllegalArgumentException("Data was of an incorrect type for FBICode.");
	}

	@Override
	public String getValue() {
		return this.fbiCode;
	}

	@Override
	public String toString() {
		return "FBI Code";
	}
}
