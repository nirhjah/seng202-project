package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A textual description of the type of the crime incident.
 * 
 * @author Connor Dunlop
 *
 */
public class PrimaryDescription extends DataCategory implements Importable, Exportable, Categorical {

	/** The primary description of the crime type this code corresponds to */
	private String primaryDescription = null;

	private static final Set<String> identifierStrings = new HashSet<>(Arrays.asList(
			"PRIMARYDESCRIPTION"
	));
	private static final PrimaryDescription instance = new PrimaryDescription();
	public static PrimaryDescription getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
			
		if (data == null)
			record.setPrimaryDescription(null);
		else if (data instanceof String)
			record.setPrimaryDescription((String) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for PrimaryDescription.");
	}

	@Override
	public String getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getPrimaryDescription();
	}

	@Override
	public DataCategory getRecordCategory(CrimeRecord record) {
		return record.getPrimaryDescriptionCategory();
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
		return "primaryDescription";
	}

	@Override
	public String getValueString() {
		if (primaryDescription == null)
			throw new NullPointerException("Cannot convert null value stored by " + this.toString() + " to string.");

		return primaryDescription.toString();
	}

	@Override
	public boolean matchesString(String identifier) {
		return identifierStrings.contains(identifier);
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.primaryDescription = null;
		else if (value instanceof String)
			this.primaryDescription = (String) value;
		else
			throw new IllegalArgumentException("Data was of an incorrect type for PrimaryDescription.");
	}

	@Override
	public String getValue() {
		return this.primaryDescription;
	}

	@Override
	public String toString() {
		return "Primary Description";
	}

	@Override
	public Class<? extends Object> getValueType() {
		return String.class;
	}
}
