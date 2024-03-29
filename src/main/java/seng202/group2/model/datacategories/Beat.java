package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Police district where the crime incident occurred.
 * (Area of the city broken down for patrol and statistical purposes)
 * 
 * @author Connor Dunlop
 *
 */
public class Beat extends DataCategory implements Importable, Numerical, Categorical {

	/**
	 * Police district where the crime incident occurred.
	 * (Area of the city broken down for patrol and statistical purposes)
	 */
	private Short beat = null;

	private static final Set<String> identifierStrings = new HashSet<>(Arrays.asList(
			"BEAT"
	));
	private static final Beat instance = new Beat();
	public static Beat getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setBeat(null);
		else if (data instanceof Short)
			record.setBeat((Short) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Beat.");
	}

	@Override
	public Short getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getBeat();
	}

	@Override
	public DataCategory getRecordCategory(CrimeRecord record) {
		return record.getBeatCategory();
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
		return "beat";
	}

	@Override
	public String getValueString() {
		if (beat == null)
			throw new NullPointerException("Cannot convert null value stored by " + this.toString() + " to string.");

		return beat.toString();
	}

	@Override
	public boolean matchesString(String identifier) {
		return identifierStrings.contains(identifier);
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.beat = null;
		else if (value instanceof Short)
			this.beat = (Short) value;
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Beat.");
	}

	@Override
	public Short getValue() {
		return this.beat;
	}

	@Override
	public String toString() {
		return "Beat";
	}

	@Override
	public Class<? extends Object> getValueType() {
		return Short.class;
	}
}
