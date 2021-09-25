package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

/**
 * A simple interface used to mark DataCategory classes as being importable. 
 * 
 * @author Connor Dunlop
 *
 */
public interface Importable {
    public Object parseString(String value);
    public Object getRecordValue(CrimeRecord record);
}
