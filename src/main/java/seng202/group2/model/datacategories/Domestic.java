package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

/**
 * True if the crime incident was classified as domestic.
 *
 * @author Connor Dunlop
 *
 */
public class Domestic extends DataCategory implements Importable {

	private static final Domestic instance = new Domestic();

	/**
	 * 1 if the crime incident was classified as domestic
	 * 0 if the crime incident was not classified as domestic
	 * null if there is no information provided
	 */
	private Boolean domestic = null;

	public static Domestic getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");

		if (data == null)
			record.setDomestic(null);
		else if (data instanceof Boolean)
			record.setDomestic((Boolean) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Domestic.");
	}

	@Override
	public Boolean getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");

		return record.getDomestic();
	}

	@Override
	public Boolean parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");

		if (value == "")
			return null;
		else if (value.equals("Y") || value.equals("TRUE"))
			return true;
		else if (value.equals("N") || value.equals("FALSE"))
			return false;
		else
			throw new IllegalArgumentException();
	}

	@Override
	public String getSQL() {
		return "domestic";
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.domestic = null;
		else if (value instanceof Boolean)
			this.domestic = (Boolean) value;
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Domestic.");
	}

	@Override
	public Boolean getValue() {
		return this.domestic;
	}

}
