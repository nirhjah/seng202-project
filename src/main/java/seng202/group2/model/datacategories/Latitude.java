package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The latitudinal location where the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class Latitude extends DataCategory implements Importable, Numerical {

	/** The latitudinal location where the crime incident occurred. */
	private Float latitude = null;

	private static final Set<String> identifierStrings = new HashSet<>(Arrays.asList(
			"LATITUDE"
	));
	private static final Latitude instance = new Latitude();
	public static Latitude getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setLatitude(null);
		else if (data instanceof Float)
			record.setLatitude((Float) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Latitude.");
	}

	@Override
	public Float getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getLatitude();
	}

	@Override
	public DataCategory getRecordCategory(CrimeRecord record) {
		return record.getLatitudeCategory();
	}

	@Override
	public Float parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		else if (value == "")
			return null;

		try {
			Float latitude = Float.parseFloat(value);
			
			if (!(-90.0f <= latitude && latitude <= 90.0f))
				throw new IllegalArgumentException("Latitude out of bounds.");
			
			return latitude;
		} catch (NumberFormatException error) {
			throw new IllegalArgumentException(error);
		}
	}

	@Override
	public String getSQL() {
		return "latitude";
	}

	@Override
	public String getValueString() {
		if (latitude == null)
			throw new NullPointerException("Cannot convert null value stored by " + this.toString() + " to string.");

		return latitude.toString();
	}

	@Override
	public boolean matchesString(String identifier) {
		return identifierStrings.contains(identifier);
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.latitude = null;
		else if (value instanceof Float) {
			Float latitude = (Float) value;
			if (!(-90.0f <= latitude && latitude <= 90.0f))
				throw new IllegalArgumentException("Latitude out of bounds");
			
			this.latitude = latitude;
		}
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Latitude.");
	}

	@Override
	public Float getValue() {
		return this.latitude;
	}

	@Override
	public String toString() {
		return "Latitude";
	}

	@Override
	public Class<? extends Object> getValueType() {
		return Float.class;
	}
}
