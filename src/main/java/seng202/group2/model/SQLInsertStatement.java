package seng202.group2.model;

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
     * Set a value of the new item.
     *
     * @param category -- Category to set
     * @param value -- Value to set to
     */
    public void setValue(String category, String value) {
        if (value == null) {
            return;
        }

        if (first) {
            intoString += category;
            valuesString += "'" + value +"'";
            first = false;
        } else {
            intoString += ", " + category;
            valuesString += ", '" + value +"'";
        }
    }

    public String getStatement() {
        return intoString + ")" + valuesString + ");";
    }
}
