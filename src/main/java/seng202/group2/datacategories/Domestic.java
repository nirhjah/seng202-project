package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

/**
 * True if the crime incident was classified as domestic.
 * 
 * @author Connor Dunlop
 *
 */
public class Domestic extends DataCategory {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setDomestic(null);
		else if (data instanceof Boolean)
			record.setDomestic((Boolean) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Domestic.");
	}

	@Override
	public Boolean getCategoryValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getDomestic();
	}

	@Override
	public Boolean parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		
		if (value == "")
			return null;
		else if (value.equals("Y") || value.equals("TRUE"))
			return true;
		else if (value.equals("N") || value.equals("FALSE"))
			return false;
		else
			throw new IllegalArgumentException();
	}

}
