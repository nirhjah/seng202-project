package seng202.group2.datacategories;

import java.util.Calendar;
import java.util.GregorianCalendar;

import seng202.group2.CrimeRecord;

/**
 * The date and time at which the crime incident occurred.
 * 
 * @author Connor Dunlop
 *
 */
public class Date extends DataCategory {

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
		
		// Parses data from format "dd/mm/yyyy hh:mm:ss PM"
		try {
			String[] splitValue = value.split(" ");
			
			// Split date/time into date, time and am/pm
			String date = splitValue[0];
			String time = splitValue[1];
			String am_pm = splitValue[2];
			
			// Get date in day, month, year
			String[] splitDate = date.split("/");
			int day = Integer.parseInt(splitDate[1]);
			int month = Integer.parseInt(splitDate[0]);
			int year = Integer.parseInt(splitDate[2]);
			
			// Get time in hour, minute, second
			String[] splitTime = time.split(":");
			int hour = (am_pm == "PM") ?  Integer.parseInt(splitTime[0]) + 12 : Integer.parseInt(splitTime[0]);
			int minute = Integer.parseInt(splitTime[1]);
			int second = Integer.parseInt(splitTime[2]);
			
			// Return a calender object representing date/time
			return new GregorianCalendar(year, month, day, hour, minute, second);
			
		} catch (Exception error) {
			throw new IllegalArgumentException(error);
		}
	}

}
