package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The longitudinal location where the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class Longitude extends DataCategory implements Importable, Exportable, Numerical {

	/** The longitudinal location where the crime incident occurred. */
	private Float longitude = null;

	private static final Set<String> identifierStrings = new HashSet<>(Arrays.asList(
			"LONGITUDE"
	));
	private static final Longitude instance = new Longitude();
	public static Longitude getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setLongitude(null);
		else if (data instanceof Float)
			record.setLongitude((Float) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Longitude.");
	}

	@Override
	public Float getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getLongitude();
	}

	@Override
	public DataCategory getRecordCategory(CrimeRecord record) {
		return record.getLongitudeCategory();
	}

	@Override
	public Float parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		else if (value == "")
			return null;

		try {
			Float longitude = Float.parseFloat(value);
			
			if (!(-180.0f <= longitude && longitude <= 180.0f))
				throw new IllegalArgumentException("Latitude out of bounds.");
			
			return longitude;
		} catch (NumberFormatException error) {
			throw new IllegalArgumentException(error);
		}
	}

	@Override
	public String getSQL() {
		return "longitude";
	}

	@Override
	public String getValueString() {
		if (longitude == null)
			throw new NullPointerException("Cannot convert null value stored by " + this.toString() + " to string.");

		return longitude.toString();
	}

	@Override
	public boolean matchesString(String identifier) {
		return identifierStrings.contains(identifier);
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.longitude = null;
		else if (value instanceof Float) {
			Float longitude = (Float) value;
			if (!(-180.0f <= longitude && longitude <= 180.0f))
				throw new IllegalArgumentException("Longitude out of bounds");
			
			this.longitude = longitude;
		}
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Longitude.");
	}

	@Override
	public Float getValue() {
		return this.longitude;
	}

	@Override
	public String toString() {
		return "Longitude";
	}

	@Override
	public Class<? extends Object> getValueType() {
		return Float.class;
	}
}
