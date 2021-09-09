package seng202.group2.controller;

import seng202.group2.model.CrimeRecord;

import java.util.ArrayList;
import java.util.HashSet;

public class ObserverTest extends DataObserver {
    public void updateModel(ArrayList<CrimeRecord> records, HashSet<Integer> selectedData) {
        for (CrimeRecord record : records) {
            System.out.println(record.getCaseNum());
        }
    }
}
