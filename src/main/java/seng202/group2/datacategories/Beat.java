package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

/**
 * Police district where the crime incident occurred.
 * (Area of the city broken down for patrol and statistical purposes)
 * 
 * @author Connor Dunlop
 *
 */
public class Beat extends DataCategory {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (data == null)
			record.setBeat(null);
		else if (data instanceof Short)
			record.setBeat((Short) data);
		else
			throw new IllegalArgumentException();
	}

	@Override
	public Short getCategoryValue(CrimeRecord record) {
		return record.getBeat();
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
