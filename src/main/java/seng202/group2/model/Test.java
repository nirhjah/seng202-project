package seng202.group2.model;

import seng202.group2.controller.ObserverTest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * THIS IS A TEMPORARY MANUAL TESTING CLASS. Use this as you wish! I have left this here so that you can see some methods of testing.
 */

public class Test {
    private static final String DATE_FORMAT = "yyyy/MM/dd hh:mm:ss a";

    public static  void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        ActiveData activeData = DBMS.getActiveData();
        ObserverTest observer = new ObserverTest();
        ResultSet results;

        DBMS.clearDB();

        //Add some data
        for (int i=1; i <= 5; i++) {
            String num = Integer.toString(i);
            CrimeRecord record = new CrimeRecord();
            record.setCaseNum("TEST" + num);

            //Convert String from db to Calendar
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse("2001/12/12 03:40:12 PM"));
            record.setDate(cal);

            record.setBlock("Block" + num);
            record.setIucr("IUCR" + num);
            record.setPrimaryDescription("pDesc" + num);
            record.setSecondaryDescription("sDesc" + num);
            record.setLocationDescription("lDesc" + num);
            record.setArrest(false);
            record.setDomestic(false);
            record.setWard((short) (int) Integer.valueOf(num));
            record.setBeat((short) (int) Integer.valueOf(num));
            record.setFbiCode("fbiCode" + num);
            record.setLongitude((float)(int) Integer.valueOf(num));
            record.setLatitude((float)(int) Integer.valueOf(num));
            DBMS.addRecord(record);
        }

        activeData.addObserver(observer);
        activeData.updateObservers();
    }
}
