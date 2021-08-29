package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

/**
 * True if the crime incident resulted in an arrest being made.
 * 
 * @author Connor Dunlop
 *
 */
public class Arrest extends DataCategory {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setArrest(null);
		else if (data instanceof Boolean)
			record.setArrest((Boolean) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Arrest.");
	}

	@Override
	public Boolean getCategoryValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getArrest();
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
			throw new IllegalArgumentException("");
	}

}
