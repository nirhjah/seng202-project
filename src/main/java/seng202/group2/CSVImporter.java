package seng202.group2;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;

public class CSVImporter extends DataImporter {
	
	private HashMap<Integer, DataCategory> categoryMap = new HashMap<Integer, DataCategory>();
	
	public CSVImporter(String fileName) throws FileNotFoundException {
		super(fileName);
		
		parseCategories();
	}
	
	private void parseCategories() {
		String headerLine = fileScanner.nextLine();
		String[] categories = headerLine.split(",");
		
		for (int i = 0; i < categories.length; i++) {
			DataCategory category;
			try {
				category = DataCategory.getCategoryFromString(categories[i]);
				categoryMap.put(i, category);
			} catch (UnsupportedCategoryException e) {
				System.out.println("Could not import unsupported data category: " + categories[i]);
			}
			
		}
	}
	
	private Object parseValue(DataCategory category, String value) {
		switch (category) {
		case CASE_NUM:
		case BLOCK:
		case IUCR:
		case PRIMARY_DESCRIPTION:
		case SECONDARY_DESCRIPTION:
		case LOCATION_DESCRIPTION:
		case FBI_CODE:
			return value;
		
		case DATE:
			return parseDate(value);
		case ARREST:
			return parseArrest(value);
		case DOMESTIC:
			return parseDomestic(value);
		case BEAT:
			return parseBeat(value);
		case WARD:
			return parseWard(value);
		case LATITUDE:
			return parseLatitude(value);
		case LONGITUDE:
			return parseLongitude(value);
		default:
			throw new IllegalArgumentException();
		}
	}
	
	private Calendar parseDate(String date) {
		// 11/23/2020 03:05:00 PM
		String[] splitDate = date.split(" ");
		
		// Split date/time into date and time
		date = splitDate[0];
		String time = splitDate[1];
		String am_pm = splitDate[2];
		
		// Get date in day, month, year
		splitDate = date.split("/");
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
	}
	
	private boolean parseArrest(String arrest) {
		return (arrest == "Y") ? true : false;
	}
	
	private boolean parseDomestic(String domestic) {
		return (domestic == "Y") ? true : false;
	}
	
	private Object parseBeat(String beat) {
		if (beat == "")
			return null;
		return Short.parseShort(beat);
	}
	
	private Object parseWard(String ward) {
		if (ward == "")
			return null;
		return Short.parseShort(ward);
	}
	
	private Object parseLatitude(String latitude) {
		if (latitude == "")
			return null;
		return Float.parseFloat(latitude);
	}
	
	private Object parseLongitude(String longitude) {
		if (longitude == "")
			return null;
		return Float.parseFloat(longitude);
	}


	
	private CrimeRecord parseRecord(String recordLine) {
		String[] values = recordLine.split(",", -1);
		
		CrimeRecord record = new CrimeRecord();
		
		for (int i : categoryMap.keySet()) {
			try {
				DataCategory category = categoryMap.get(i);
				Object value = parseValue(category, values[i]);
				category.setCategoryValue(record, value);
			} catch (IllegalArgumentException e) {
				// TODO Define error behaviour
				e.printStackTrace();
			}
		}
		
		return record;
	}
	
	@Override
	public CrimeData importAllRecords() {
		CrimeData crimeData = new CrimeData();
		
		while (fileScanner.hasNextLine()) {
			String recordLine = fileScanner.nextLine();
			crimeData.addRecord(parseRecord(recordLine));
		}
		
		return crimeData;
	}
	
	

}
