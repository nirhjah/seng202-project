package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

/**
 * The latitudinal location where the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class Latitude extends DataCategory {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (data == null)
			record.setLatitude(null);
		else if (data instanceof Float)
			record.setLatitude((Float) data);
		else
			throw new IllegalArgumentException();
	}

	@Override
	public Float getCategoryValue(CrimeRecord record) {
		return record.getLatitude();
	}

	@Override
	public Float parseString(String value) {
		if (value == "")
			return null;

		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException error) {
			throw new IllegalArgumentException(error);
		}
	}

}
