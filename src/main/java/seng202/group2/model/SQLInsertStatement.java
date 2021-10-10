package seng202.group2.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * SQL Insert String manager. Used to create SQL strings with unknown parameters
 */
public class SQLInsertStatement {
    private String intoString;
    private String valuesString;
    private boolean first = true;

    /**
     * Initialize the string.
     * @param database -- Database to use.
     */
    public SQLInsertStatement(String database) {
        intoString = "INSERT INTO "+ database + "(";
        valuesString = " values(";
    }

    /**
     * Format value to add to SQL statment
     *
     * @param category SQL string representing category
     * @param value Value to maniplulate
     * @param isString is a string
     * @return New formatted value
     */
    public static String formatValue(String category, String value, boolean isString) {
        if (value == null) {
            return null;
        }

        //Change dates to seconds-epoch
        if (category == "date") {
            SimpleDateFormat sdf = new SimpleDateFormat(DBMS.DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(sdf.parse(value));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long epoch = cal.getTimeInMillis() / 1000L;
            value = Long.toString(epoch);
        }

        //Changes boolean values to uppercase
        if (value == "true" || value == "false") {
            value = value.toUpperCase();
        }

        //Adds quotes if string
        if (isString) {
            value = "'" + value + "'";
        }

        return value;
    }

    /**
     * Set a value of the new item.
     *
     * @param category -- Category to set
     * @param value -- Value to set to
     */
    public void setValue(String category, String value, boolean isString) {
        value = formatValue(category, value, isString);

        if (first) {
            intoString += category;
            valuesString += value;
            first = false;
        } else {
            intoString += ", " + category;
            valuesString += ", " + value;
        }
    }

    public String getStatement() {
        return intoString + ")" + valuesString + ");";
    }
}
