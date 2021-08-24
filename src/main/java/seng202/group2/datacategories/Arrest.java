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
		if (data == null)
			record.setArrest(null);
		else if (data instanceof Boolean)
			record.setArrest((Boolean) data);
		else
			throw new IllegalArgumentException();
	}

	@Override
	public Boolean getCategoryValue(CrimeRecord record) {
		return record.getArrest();
	}

	@Override
	public Boolean parseString(String value) {
		if (value == "")
			return null;
		else if (value.equals("Y"))
			return true;
		else if (value.equals("N"))
			return false;
		else
			throw new IllegalArgumentException();
	}

}
