package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Election precinct where the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class Ward extends DataCategory implements Importable, Numerical, Categorical {

	/** Election precinct where the crime incident occurred. */
	private Short ward = null;

	private static final Set<String> identifierStrings = new HashSet<>(Arrays.asList(
			"WARD"
	));
	private static final Ward instance = new Ward();
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
	public DataCategory getRecordCategory(CrimeRecord record) {
		return record.getWardCategory();
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
	public String getValueString() {
		return ward.toString();
	}

	@Override
	public boolean matchesString(String identifier) {
		return identifierStrings.contains(identifier);
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
	public Class<? extends Object> getValueType() {
		return Short.class;
	}
}
