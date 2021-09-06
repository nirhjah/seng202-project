package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

/**
 * A textual description of the location where the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class LocationDescription extends DataCategory implements Importable {

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
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
	public String getRecordValue(CrimeRecord record) {
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

	@Override
	public String getSQL() {
		return "locationDescription";
	}

}
