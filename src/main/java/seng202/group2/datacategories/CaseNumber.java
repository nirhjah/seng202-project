package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

public class CaseNumber extends DataCategory {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (data == null)
			record.setCaseNum(null);
		else if (data instanceof String)
			record.setCaseNum((String) data);
		else
			throw new IllegalArgumentException();
	}

	@Override
	public String getCategoryValue(CrimeRecord record) {
		return record.getCaseNum();
	}

	@Override
	public String parseString(String value) {
		if (value == "")
			return null;
		return value;
	}

}
