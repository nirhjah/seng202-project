package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

/**
 * The longitudinal location where the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class Longitude extends DataCategory implements Importable {

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

}
