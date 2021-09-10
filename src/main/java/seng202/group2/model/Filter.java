package seng202.group2.model;

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
     * @return Type
     */
    public FilterType getType() {
        return type;
    }
}
