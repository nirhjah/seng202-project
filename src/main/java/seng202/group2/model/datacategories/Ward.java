package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

/**
 * Election precinct where the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class Ward extends DataCategory implements Importable {

	private static final Ward instance = new Ward();

	/** Election precinct where the crime incident occurred. */
	private Short ward = null;

	public static Ward getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setWard(null);
		else if (data instanceof Short)
			record.setWard((Short) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Ward.");
	}

	@Override
	public Short getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getWard();
	}

	@Override
	public Short parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		else if (value == "")
			return null;

		try {
			return Short.parseShort(value);
		} catch (NumberFormatException error) {
			throw new IllegalArgumentException(error);
		}
	}

	@Override
	public String getSQL() {
		return "ward";
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.ward = null;
		else if (value instanceof Short)
			this.ward = (Short) value;
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Ward.");
	}

	@Override
	public Short getValue() {
		return this.ward;
	}

	@Override
	public String toString() {
		return "Ward";
	}

	@Override
	public boolean isString() {
		return false;
	}
}
