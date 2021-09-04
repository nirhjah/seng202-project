package seng202.group2.datacategories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import seng202.group2.CrimeRecord;

/**
 * The date and time at which the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class Date extends DataCategory implements Importable {
	
	/** Date formats that may be encountered while parsing */
	public static String[] dateFormats = {
			"MM'/'dd'/'yyyy hh':'mm':'ss a",
			"MM'/'dd'/'yyyy HH':'mm':'ss",
			"MM'-'dd'-'yyyy HH':'mm':'ss",
			"yyyy'-'MM'-'dd'T'HH':'mm':'ss"
	};

	@Override
	public void setCategoryValue(CrimeRecord record, Object data) {
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
	public Calendar getCategoryValue(CrimeRecord record) {
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

}
