package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * The address of the crime incident at a city block level.
 * Zip code with last two digits anonymized followed by street name.
 * 
 * @author Connor Dunlop
 *
 */
public class Block extends DataCategory implements Importable, Exportable, Categorical {
	
	/**
	 * The address of the crime incident at a city block level.
	 * Zip code with last two digits anonymized followed by street name.
	 */
	private String block = null;

	private static final Set<String> identifierStrings = new HashSet<>(Arrays.asList(
			"BLOCK"
	));
	private static final Block instance = new Block();

	/**
	 * Returns the static instance of this class, stored by this class.
	 * @return The static instance of this class, stored by this class.
	 */
	public static Block getInstance() {
		return instance;
	}

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
	public DataCategory getRecordCategory(CrimeRecord record) {
		return record.getBlockCategory();
	}

	@Override
	public String parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		else if (value.equals(""))
			return null;
		return value;
	}

	@Override
	public String getSQL() {
		return "block";
	}

	@Override
	public String getValueString() {
		if (block == null)
			throw new NullPointerException("Cannot convert null value stored by " + this.toString() + " to string.");

		return block.toString();
	}

	@Override
	public boolean matchesString(String identifier) {
		return identifierStrings.contains(identifier);
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

	@Override
	public String toString() {
		return "Block";
	}

	@Override
	public Class<? extends Object> getValueType() {
		return String.class;
	}
}
