package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;
/**
 * FBI crime code assigned to the crime incident.
 * Used to categorize crime incidents by the type of crime that occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class FBICode extends DataCategory implements Importable {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
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
	public String getCategoryValue(CrimeRecord record) {
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

}
