package seng202.group2.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Filters allow specific data and data order to be retrieved from the database.
 */
public class Filter {
    /** SQL string representation of filter */
    private String SQLText;
    /** Type of filter */
    private FilterType type;

    /**
     * Create filter object.
     *
     * @param pattern SQL String representing the filter
     * @param type Type of filter
     */
    public Filter(String pattern, FilterType type) {
        this.SQLText = pattern;
        this.type = type;
    }

    /**
     * @return SQLText
     */
    public String getSQLText() {
        return SQLText;
    }

    /**
     * Get the english string representation of the filter
     * @return String rep of filter
     */
    public String getStringRepresentation() {
        String[] temp = SQLText.split(" ");

        //If date
        if (type == FilterType.RANGE) {
            //date between
            String result = temp[0] + " between ";

            Calendar cal = Calendar.getInstance();
            long epoch = Long.parseLong(temp[2]);
            cal.setTimeInMillis(epoch * 1000L);

            result += (new SimpleDateFormat("dd/MM/yyyy")).format(cal.getTime()) + " and ";

            Calendar cal2 = Calendar.getInstance();
            long epoch2 = Long.parseLong(temp[4]);
            cal2.setTimeInMillis(epoch2 * 1000L);

            result += (new SimpleDateFormat("dd/MM/yyyy")).format(cal2.getTime());

            return result;
        } else {
            return SQLText;
        }
    }

    /**
     * @return Type
     */
    public FilterType getType() {
        return type;
    }
}
