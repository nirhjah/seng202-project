package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

/**
 * A textual description of the type of the crime incident.
 * 
 * @author Connor Dunlop
 *
 */
public class PrimaryDescription extends DataCategory {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) throws UnsupportedCategoryException {
		throw new UnsupportedCategoryException("Cannot set value of primary description");
	}

	@Override
	public String getCategoryValue(CrimeRecord record) {
		return record.getPrimaryDescription();
	}

	@Override
	public String parseString(String value) {
		if (value == "")
			return null;
		return value;
	}

}
