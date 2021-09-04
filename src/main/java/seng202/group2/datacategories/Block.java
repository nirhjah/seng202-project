package seng202.group2.datacategories;

import seng202.group2.CrimeRecord;

/**
 * The address of the crime incident at a city block level.
 * Zip code with last two digits anonymized followed by street name.
 * 
 * @author Connor Dunlop
 *
 */
public class Block extends DataCategory implements Importable {

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setBlock(null);
		else if (data instanceof String)
			record.setBlock((String) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Block.");
	}

	@Override
	public String getCategoryValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getBlock();
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
		return "block";
	}

}
