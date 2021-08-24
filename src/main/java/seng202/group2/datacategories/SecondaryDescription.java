package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

/**
 * A textual description giving more details supplementing the categorization
 * of the crime type provided in the primary description.
 * 
 * @author Connor Dunlop
 *
 */
public class SecondaryDescription extends DataCategory {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) throws UnsupportedCategoryException {
		throw new UnsupportedCategoryException("Cannot set value of secondary description");
	}

	@Override
	public String getCategoryValue(CrimeRecord record) {
		return record.getSecondaryDescription();
	}

	@Override
	public String parseString(String value) {
		if (value == "")
			return null;
		return value;
	}

}
