package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;
import seng202.group2.controller.IUCRCodeDictionary;

/**
 * Illinois Uniform Crime Reporting code.
 * Four digit code used to classify the criminal incident
 * 
 * @author Connor Dunlop
 *
 */
public class IUCRCode extends DataCategory implements Importable {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
			
		if (data == null)
			record.setIucr(null);
		else if (data instanceof seng202.group2.controller.IUCRCode)
			record.setIucr((seng202.group2.controller.IUCRCode) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for IUCRCode.");
	}

	@Override
	public seng202.group2.controller.IUCRCode getCategoryValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getIucr();
	}

	@Override
	public seng202.group2.controller.IUCRCode parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		else if (value == "")
			return null;
		while (value.startsWith("0"))
			value = value.replaceFirst("0", "");
		return IUCRCodeDictionary.getCode(value);
	}

	@Override
	public String getSQL() {
		return "IUCR";
	}

}
