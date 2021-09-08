package seng202.group2.controller;

import seng202.group2.model.CrimeRecord;
import seng202.group2.model.DBMS;

import java.util.ArrayList;

public class ObserverTest extends DataObserver {
    public void updateModel() {
        ArrayList<CrimeRecord> records = DBMS.getActiveData().getActiveRecords();
        for (CrimeRecord record : records) {
            System.out.println(record.getCaseNum());
        }
    }
}
