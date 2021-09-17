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

	private static final IUCRCode instance = new IUCRCode();

	/** The IUCR code */
	private String iucrCode = null;

	public static IUCRCode getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
			
		if (data == null)
			record.setIucr(null);
		else if (data instanceof String)
			record.setIucr((String) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for IUCRCode.");
	}

	@Override
	public String getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getIucr();
	}

	@Override
	public String parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		else if (value == "")
			return null;
		while (value.startsWith("0"))
			value = value.replaceFirst("0", "");
		return value;
	}

	@Override
	public String getSQL() {
		return "IUCR";
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.iucrCode = null;
		else if (value instanceof String)
			this.iucrCode = (String) value;
		else
			throw new IllegalArgumentException("Data was of an incorrect type for IUCRCode.");
	}

	@Override
	public String getValue() {
		return this.iucrCode;
	}

	@Override
	public String toString() {
		return "IUCR Code";
	}

	@Override
	public boolean isString() {
		return true;
	}
}
