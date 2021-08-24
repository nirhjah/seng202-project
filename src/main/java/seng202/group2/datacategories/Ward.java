package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

/**
 * Election precinct where the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class Ward extends DataCategory {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (data == null)
			record.setWard(null);
		else if (data instanceof Short)
			record.setWard((Short) data);
		else
			throw new IllegalArgumentException();
	}

	@Override
	public Short getCategoryValue(CrimeRecord record) {
		return record.getWard();
	}

	@Override
	public Short parseString(String value) {
		if (value == "")
			return null;

		try {
			return Short.parseShort(value);
		} catch (NumberFormatException error) {
			throw new IllegalArgumentException(error);
		}
	}

}
