package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

/**
 * The address of the crime incident at a city block level.
 * Zip code with last two digits anonymized followed by street name.
 * 
 * @author Connor Dunlop
 *
 */
public class Block extends DataCategory implements Importable {
	
	/**
	 * The address of the crime incident at a city block level.
	 * Zip code with last two digits anonymized followed by street name.
	 */
	private String block = null;

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
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
	public String getRecordValue(CrimeRecord record) {
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

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.block = null;
		else if (value instanceof String)
			this.block = (String) value;
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Block.");
	}

	@Override
	public String getValue() {
		return this.block;
	}

}
