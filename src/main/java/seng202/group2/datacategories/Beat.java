package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

/**
 * Police district where the crime incident occurred.
 * (Area of the city broken down for patrol and statistical purposes)
 * 
 * @author Connor Dunlop
 *
 */
public class Beat extends DataCategory implements Importable {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setBeat(null);
		else if (data instanceof Short)
			record.setBeat((Short) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Beat.");
	}

	@Override
	public Short getCategoryValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getBeat();
	}

	@Override
	public Short parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		else if (value == "")
			return null;
		
		try {
			return Short.parseShort(value);
		} catch (NumberFormatException error) {
			throw new IllegalArgumentException(error);
		}
	}

	@Override
	public String getSQL() {
		return "beat";
	}

}
