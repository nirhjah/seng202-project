package seng202.group2.controller;

import seng202.group2.model.ActiveData;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import seng202.group2.model.datacategories.IUCRCodeDictionary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *adasdasing DATE_FORMAT = "yyyy/MM/dd hh:mm:ss a";

    public static  void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        ActiveData activeData = new ActiveData();
        ResultSet results;

        DBMS.clearDB();

        //Add some data
        for (int i=1; i < 5; i++) {
            String num = Integer.toString(i);
            CrimeRecord record = new CrimeRecord();
            record.setCaseNum("TEST" + num);

            //Convert String from db to Calendar
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse("2001/12/12 03:40:12 PM"));
            record.setDate(cal);

            record.setBlock("Block" + num);
            record.setIucr(IUCRCodeDictionarya.getCode("110"));
            record.setLocationDescription("lDesc" + num);
            record.setArrest(false);
            record.setDomestic(false);
            record.setWard((short) Integer.parseInt(num));
            record.setBeat((short) Integer.parseInt(num));
            record.setFbiCode("fbiCode" + num);
            record.setLongitude((float)Integer.parseInt(num));
            record.setLatitude((float)Integer.parseInt(num));
            DBMS.addRecord(record, false);
        }

        //Convert String from db to Calendar
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse("2001/12/12 03:40:12 PM"));

        Calendar time = cal;
        int year = time.get(Calendar.YEAR);
        int month = time.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = time.get(Calendar.DAY_OF_MONTH);
        int hour = time.get(Calendar.HOUR_OF_DAY);
        int minute = time.get(Calendar.MINUTE);
        int second = time.get(Calendar.SECOND);
        int a = time.get(Calendar.AM_PM);

        System.out.println(String.format("%d/%02d/%02d %02d:%02d:%02d ", year, month, day, hour, minute, second) + ((a == 0)? "AM": "PM")); // + ((a == 0)? "AM": "PM")

        CrimeRecord record = DBMS.getRecord(1);

        System.out.println(record.getDate().toString());
    }
}

 */
