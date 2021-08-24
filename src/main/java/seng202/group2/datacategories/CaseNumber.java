package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

/**
 * The case number associated with the crime record in the police database.
 * 
 * @author Connor Dunlop
 *
 */
public class CaseNumber extends DataCategory {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setCaseNum(null);
		else if (data instanceof String)
			record.setCaseNum((String) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for CaseNumber.");
	}

	@Override
	public String getCategoryValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getCaseNum();
	}

	@Override
	public String parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		else if (value == "")
			return null;
		return value;
	}

}
