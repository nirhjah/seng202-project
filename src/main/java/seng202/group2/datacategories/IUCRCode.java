package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;
import seng202.group2.IUCRCodeDictionary;

/**
 * Illinois Uniform Crime Reporting code.
 * Four digit code used to classify the criminal incident
 * 
 * @author Connor Dunlop
 *
 */
public class IUCRCode extends DataCategory {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (data == null)
			record.setIucr(null);
		else if (data instanceof seng202.group2.IUCRCode)
			record.setIucr((seng202.group2.IUCRCode) data);
		else
			throw new IllegalArgumentException();
	}

	@Override
	public seng202.group2.IUCRCode getCategoryValue(CrimeRecord record) {
		return record.getIucr();
	}

	@Override
	public seng202.group2.IUCRCode parseString(String value) {
		if (value == "")
			return null;
		while (value.startsWith("0"))
			value = value.replaceFirst("0", "");
		return IUCRCodeDictionary.getCode(value);
	}

}
