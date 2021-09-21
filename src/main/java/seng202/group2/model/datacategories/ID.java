package seng202.group2.model.datacategories;

import seng202.group2.model.CrimeRecord;

/**
 * The ID associated with the crime record in the police database.
 *
 * @author Connor Dunlop
 *
 */
public class ID extends DataCategory implements Importable, Numerical, Categorical {

    private static final ID instance = new ID();

    /** The ID associated with the crime record in the police database */
    private Integer ID = -1;

    public static ID getInstance() {
        return instance;
    }

    @Override
    public void setRecordValue(CrimeRecord record, Object data) {
        if (record == null)
            throw new IllegalArgumentException("Cannot set attribute value of null record.");

        if (data == null)
            record.setID(-1);
        else if (data instanceof Integer)
            record.setID((int) data);
        else
            throw new IllegalArgumentException("Data was of an incorrect type for ID.");
    }

    @Override
    public Integer getRecordValue(CrimeRecord record) {
        if (record == null)
            throw new IllegalArgumentException("Cannot get attribute value of null record.");

        return record.getID();
    }

    @Override
    public String parseString(String value) {
        if (value == null)
            throw new IllegalArgumentException("Cannot parse null string.");
        else if (value == "")
            return null;
        return value.toString();
    }

    @Override
    public String getSQL() {
        return "id";
    }

    @Override
    public void setValue(Object value) {
        if (value == null)
            this.ID = -1;
        else if (value instanceof Integer)
            this.ID = (int) value;
        else
            throw new IllegalArgumentException("Data was of an incorrect type for ID.");
    }

    @Override
    public Integer getValue() {
        return this.ID;
    }

    @Override
    public String toString() {
        return "ID";
    }

    @Override
    public Class<? extends Object> getValueType() {
        return Integer.class;
    }
}