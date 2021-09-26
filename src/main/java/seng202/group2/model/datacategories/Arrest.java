package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * True if the crime incident resulted in an arrest being made.
 * 
 * @author Connor Dunlop
 *
 */
public class Arrest extends DataCategory implements Importable {
	
	/**
	 * 1 if the crime incident resulted in an arrest being made
	 * 0 if the crime incident did not result in an arrest being made
	 * null if there is no information provided
	 */
	private Boolean arrest = null;

	private static final Set<String> identifierStrings = new HashSet<>(Arrays.asList(
			"ARREST"
	));
	private static final Arrest instance = new Arrest();
	public static Arrest getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setArrest(null);
		else if (data instanceof Boolean)
			record.setArrest((Boolean) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Arrest.");
	}

	@Override
	public Boolean getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getArrest();
	}

	@Override
	public DataCategory getRecordCategory(CrimeRecord record) {
		return record.getArrestCategory();
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
			throw new IllegalArgumentException("String was invalid.");
	}

	@Override
	public String getSQL() {
		return "arrest";
	}

	@Override
	public String getValueString() {
		return arrest.toString();
	}

	@Override
	public boolean matchesString(String identifier) {
		return identifierStrings.contains(identifier);
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.arrest = null;
		else if (value instanceof Boolean)
			this.arrest = (Boolean) value;
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Arrest.");
	}

	@Override
	public Boolean getValue() {
		return this.arrest;
	}

	@Override
	public String toString() {
		return "Arrest";
	}

	@Override
	public Class<? extends Object> getValueType() {
		return Boolean.class;
	}
}
