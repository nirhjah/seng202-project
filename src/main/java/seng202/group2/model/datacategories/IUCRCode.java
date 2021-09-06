package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

/**
 * Illinois Uniform Crime Reporting code.
 * Four digit code used to classify the criminal incident
 * 
 * @author Connor Dunlop
 *
 */
public class IUCRCode extends DataCategory implements Importable {
	
	/** The IUCR code */
	public final String IUCR;
	/** The primary description of the crime type this code corresponds to */
	public final String PRIMARY_DESCRIPTION;
	/** The secondary description of the crime type this code corresponds to */
	public final String SECONDARY_DESCRIPTION;
	/** 
	 * If an IUCR code is an index code, then the crime type represented by this IUCR code
	 * can be found under a NIBRS offense code, which are codes used by the FBI for collating
	 * and processing crime data.
	 */
	public final Boolean INDEX;
	
	public IUCRCode(String iucr, String primaryDescription, String secondaryDescription, Boolean index) {
		this.IUCR = iucr;
		this.PRIMARY_DESCRIPTION = primaryDescription;
		this.SECONDARY_DESCRIPTION = secondaryDescription;
		this.INDEX = index;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
			
		if (data == null)
			record.setIucr(null);
		else if (data instanceof IUCRCode)
			record.setIucr((IUCRCode) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for IUCRCode.");
	}

	@Override
	public IUCRCode getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getIucr();
	}

	@Override
	public IUCRCode parseString(String value) {
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
