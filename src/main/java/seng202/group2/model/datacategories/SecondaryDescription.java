package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A textual description giving more details supplementing the categorization
 * of the crime type provided in the primary description.
 * 
 * @author Connor Dunlop
 *
 */
public class SecondaryDescription extends DataCategory implements Importable, Categorical {

	/** The secondary description of the crime type this code corresponds to */
	private String secondaryDescription = null;

	private static final Set<String> identifierStrings = new HashSet<>(Arrays.asList(
			"SECONDARYDESCRIPTION"
	));
	private static final SecondaryDescription instance = new SecondaryDescription();
	public static SecondaryDescription getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
			
		if (data == null)
			record.setSecondaryDescription(null);
		else if (data instanceof String)
			record.setSecondaryDescription((String) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for SecondaryDescription.");
	}

	@Override
	public String getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getSecondaryDescription();
	}

	@Override
	public DataCategory getRecordCategory(CrimeRecord record) {
		return record.getSecondaryDescriptionCategory();
	}

	@Override
	public String parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		else if (value == "")
			return null;
		return value;
	}

	@Override
	public String getSQL() {
		return "secondaryDescription";
	}

	@Override
	public String getValueString() {
		return secondaryDescription.toString();
	}

	@Override
	public boolean matchesString(String identifier) {
		return identifierStrings.contains(identifier);
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.secondaryDescription = null;
		else if (value instanceof String)
			this.secondaryDescription = (String) value;
		else
			throw new IllegalArgumentException("Data was of an incorrect type for SecondaryDescription.");
	}

	@Override
	public String getValue() {
		return this.secondaryDescription;
	}

	@Override
	public String toString() {
		return "Secondary Description";
	}

	@Override
	public Class<? extends Object> getValueType() {
		return String.class;
	}
}
