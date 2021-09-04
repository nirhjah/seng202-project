package seng202.group2.controller;

import seng202.group2.model.CrimeRecord;

import java.util.ArrayList;

public class ObserverTest extends DataObserver{
    public void updateModel(ArrayList<CrimeRecord> records) {
        for (CrimeRecord record : records) {
            System.out.println(record.getCaseNum());
        }
    }
}
