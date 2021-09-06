package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

/**
 * The latitudinal location where the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class Latitude extends DataCategory implements Importable {

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

}
