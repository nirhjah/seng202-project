package seng202.group2.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group2.model.ActiveData;
import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test that child classes of DataObserver are correctly notified of updates
 */
public class DataObserverTest {
    private ObserverTestClass observer;

    /**
     * Add records to the database to test other methods.
     *
     * @param max
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ParseException
     */
    void addRecords(int max) throws SQLException, ClassNotFoundException, ParseException {
        for (int i=1; i <= max; i++) {
            CrimeRecord record = new CrimeRecord();
            record.setCaseNum("TEST" + i);

            //Convert String from db to Calendar
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse("2001/12/12 03:40:12 PM"));
            record.setDate(cal);

            record.setBlock("Block" + i);
            record.setIucr("430");
            record.setPrimaryDescription("pDesc" + i);
            record.setSecondaryDescription("sDesc" + i);
            record.setLocationDescription("lDesc" + i);
            record.setArrest(false);
            record.setDomestic(false);
            record.setWard((short) i);
            record.setBeat((short) i);
            record.setFbiCode("fbiCode" + i);
            record.setLongitude((float) i);
            record.setLatitude((float) i);
            DBMS.addRecord(record,false);
        }
    }

    /**
     * Add observers
     */
    @BeforeEach
    void addObserver() {
        try {
            DBMS.clearDB();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        observer = new ObserverTestClass();
        DBMS.getActiveData().addObserver(observer);
    }

    /**
     * Test the updateModel and getTestRecords method by forcing an update. This does not test if adding filters will
     * correctly update.
     */
    @Test
    void updateModelTest() {
        try {
            addRecords(7);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        //Update data
        ActiveData activeData = DBMS.getActiveData();
        observer.updateModel(activeData.getActiveRecords(), activeData.getSelectedRecords());

        ArrayList<CrimeRecord> results = observer.getTestRecords();
        int num = 1;

        for (CrimeRecord result : results) {
            //System.out.println(result.getCaseNum() + ", " + result.getID());
            assertEquals(num, result.getID());
            num++;
        }
    }
}
