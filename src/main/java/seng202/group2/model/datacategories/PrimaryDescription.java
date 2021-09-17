package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

/**
 * A textual description of the type of the crime incident.
 * 
 * @author Connor Dunlop
 *
 */
public class PrimaryDescription extends DataCategory implements Importable {

	private static final PrimaryDescription instance = new PrimaryDescription();

	/** The primary description of the crime type this code corresponds to */
	private String primaryDescription = null;

	public static PrimaryDescription getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) throws UnsupportedCategoryException {
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
	public boolean isString() {
		return true;
	}
}
