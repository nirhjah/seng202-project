package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;
import seng202.group2.NIBRSCode;
import seng202.group2.NIBRSCodeDictionary;

/**
 * FBI crime code assigned to the crime incident.
 * Used to categorize crime incidents by the type of crime that occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class FBICode extends DataCategory {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (data == null)
			record.setFbiCode(null);
		else if (data instanceof NIBRSCode)
			record.setFbiCode((NIBRSCode) data);
		else
			throw new IllegalArgumentException();
	}

	@Override
	public NIBRSCode getCategoryValue(CrimeRecord record) {
		return record.getFbiCode();
	}

	@Override
	public NIBRSCode parseString(String value) {
		if (value == "")
			return null;
		while (value.startsWith("0"))
			value = value.replaceFirst("0", "");
		return NIBRSCodeDictionary.getCode(value);
	}

}
