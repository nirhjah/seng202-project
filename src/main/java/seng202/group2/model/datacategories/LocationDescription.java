package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A textual description of the location where the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class LocationDescription extends DataCategory implements Importable {

	/** A textual description of the location where the crime incident occurred */
	private String locationDescription = null;

	private static final Set<String> identifierStrings = new HashSet<>(Arrays.asList(
			"LOCATIONDESCRIPTION"
	));
	private static final LocationDescription instance = new LocationDescription();
	public static LocationDescription getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setLocationDescription(null);
		else if (data instanceof String)
			record.setLocationDescription((String) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for LocationDescription.");
	}

	@Override
	public String getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getLocationDescription();
	}

	@Override
	public DataCategory getRecordCategory(CrimeRecord record) {
		return record.getLocationDescriptionCategory();
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
		return "locationDescription";
	}

	@Override
	public String getValueString() {
		return locationDescription.toString();
	}

	@Override
	public boolean matchesString(String identifier) {
		return identifierStrings.contains(identifier);
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.locationDescription = null;
		else if (value instanceof String)
			this.locationDescription = (String) value;
		else
			throw new IllegalArgumentException("Data was of an incorrect type for LocationDescription.");
	}

	@Override
	public String getValue() {
		return this.locationDescription;
	}

	@Override
	public String toString() {
		return "Location Description";
	}

	@Override
	public Class<? extends Object> getValueType() {
		return String.class;
	}
}
