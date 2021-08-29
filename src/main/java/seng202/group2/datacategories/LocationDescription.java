package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

/**
 * A textual description of the location where the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class LocationDescription extends DataCategory {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setLocationDescription(null);
		else if (data instanceof String)
			record.setLocationDescription((String) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for LocationDescription.");
	}

	@Override
	public String getCategoryValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getLocationDescription();
	}

	@Override
	public String parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		else if (value == "")
			return null;
		return value;
	}

}
