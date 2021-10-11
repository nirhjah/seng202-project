package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

/**
 * A simple interface used to mark DataCategory classes as being importable. 
 * 
 * @author Connor Dunlop
 *
 */
public interface Importable extends DataClassification {
    /**
     * Parses a string representing a value of data corresponding to this DataCategory
     * into whatever type is used by a CrimeRecord to store data corresponding to this DataCategory.
     *
     * @param value A value string representing a value of data corresponding to this DataCategory.
     * @return The value represented by the value string parsed into the type used to store data corresponding to this DataCategory.
     */
    public Object parseString(String value);

    /**
     * Gets the value of the attribute of CrimeRecord record, which corresponds to this DataCategory
     * enumerator. Type casting is handled within the method.
     *
     * @param record The CrimeRecord instance whose attribute value is to be retrieved.
     * @return The value of the CrimeRecord's attribute.
     */
    public Object getRecordValue(CrimeRecord record);
}
