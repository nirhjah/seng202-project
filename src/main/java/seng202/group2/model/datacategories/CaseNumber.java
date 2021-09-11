package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The case number associated with the crime record in the police database.
 * 
 * @author Connor Dunlop
 *
 */
public class CaseNumber extends DataCategory implements Importable {

	/** The case number associated with the crime record in the police database */
	private String caseNum = null;

	private static final Set<String> identifierStrings = new HashSet<>(Arrays.asList(
			"CASE#"
	));
	private static final CaseNumber instance = new CaseNumber();
	public static CaseNumber getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null)
			throw new IllegalArgumentException("Cannot set attribute value of null record.");
		
		if (data == null)
			record.setCaseNum(null);
		else if (data instanceof String)
			record.setCaseNum((String) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for CaseNumber.");
	}

	@Override
	public String getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getCaseNum();
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
		return "caseNum";
	}

	@Override
	public boolean matchesString(String identifier) {
		return identifierStrings.contains(identifier);
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.caseNum = null;
		else if (value instanceof String)
			this.caseNum = (String) value;
		else
			throw new IllegalArgumentException("Data was of an incorrect type for CaseNumber.");
	}

	@Override
	public String getValue() {
		return this.caseNum;
	}

	@Override
	public String toString() {
		return "Case Number";
	}
}
