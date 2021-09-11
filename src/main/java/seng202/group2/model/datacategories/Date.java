package seng202.group2.model.datacategories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import seng202.group2.model.CrimeRecord;

/**
 * The date and time at which the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class Date extends DataCategory implements Importable {

	private static final Date instance = new Date();
	
	/** The date and time at which the crime incident occurred */
	private Calendar date = null;
	
	/** Date formats that may be encountered while parsing */
	public static String[] dateFormats = {
			"MM'/'dd'/'yyyy hh':'mm':'ss a",
			"MM'/'dd'/'yyyy HH':'mm':'ss",
			"MM'-'dd'-'yyyy HH':'mm':'ss",
			"MM'/'dd'/'yyyy HH':'mm",
			"yyyy'-'MM'-'dd'T'HH':'mm':'ss"
	};

	public static Date getInstance() {
		return instance;
	}

	@Override
	public void setRecordValue(CrimeRecord record, Object data) {
		if (record == null) 
			throw new IllegalArgumentException("Cannot set attribute value of null record");
		
		if (data == null)
			record.setDate(null);
		else if (data instanceof Calendar)
			record.setDate((Calendar) data);
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Date.");
	}

	@Override
	public Calendar getRecordValue(CrimeRecord record) {
		if (record == null)
			throw new IllegalArgumentException("Cannot get attribute value of null record.");
		
		return record.getDate();
	}

	@Override
	public Calendar parseString(String value) {
		if (value == null)
			throw new IllegalArgumentException("Cannot parse null string.");
		else if (value == "")
			return null;
		
		java.util.Date date = null;
		for (int i = 0; i < dateFormats.length; i++) {
			try {
				date = new SimpleDateFormat(dateFormats[i]).parse(value);
				break;
			} catch (ParseException e) {
				date = null;
			}
		}
		
		if (date == null)
			throw new IllegalArgumentException("Date was not in a supported format.");
		
		Calendar parsedDate = Calendar.getInstance();
		parsedDate.setTime(date);
		return parsedDate;
	}

	@Override
	public String getSQL() {
		return "date";
	}

	@Override
	public void setValue(Object value) {
		if (value == null)
			this.date = null;
		else if (value instanceof Calendar)
			this.date = (Calendar) value;
		else
			throw new IllegalArgumentException("Data was of an incorrect type for Date.");
	}

	@Override
	public Calendar getValue() {
		return this.date;
	}

}
