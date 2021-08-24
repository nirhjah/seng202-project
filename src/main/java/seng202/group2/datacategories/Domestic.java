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
		if (data == null)
			record.setDomestic(null);
		else if (data instanceof Boolean)
			record.setDomestic((Boolean) data);
		else
			throw new IllegalArgumentException();
	}

	@Override
	public Boolean getCategoryValue(CrimeRecord record) {
		return record.getDomestic();
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
