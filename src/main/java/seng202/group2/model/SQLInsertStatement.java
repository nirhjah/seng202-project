package seng202.group2.model;

public class SQLInsertStatement {
    private String intoString;
    private String valuesString;
    private boolean first = true;

    public SQLInsertStatement(String database) {
        intoString = "INSERT INTO "+ database + "(";
        valuesString = " values(";
    }

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
