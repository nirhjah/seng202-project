package seng202.group2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * THIS IS A TEMPORARY TESTING CLASS. DO NOT HAVE THIS WHEN MERGING WITH MASTER
 */

public class Test {
    public static  void main(String[] args) throws SQLException, ClassNotFoundException {
        ActiveData activeData = new ActiveData();
        ResultSet results;

        DBMS.clearDB();

        //Add some data
        DBMS.addRecord("TEST1", "2001/12/12 03:40:12 PM", "Block1", "IUCR1", "pDesc1",  "SDec1",
                "lDesc1", false, false, (short)1, (short)1, "fbiCode1", 1.0f, 1.0f);
        DBMS.addRecord("TEST2", "2001/12/12 03:40:12 PM", "Block2", "IUCR2", "pDesc2",  "SDec2",
                "lDesc2", false, false, (short)2, (short)2, "fbiCode2", 2.0f, 2.0f);
        DBMS.addRecord("TEST2", "2001/12/12 03:40:12 PM", "Block2", "IUCR2", "pDesc2",  "SDec2",
                "lDesc2", false, false, (short)2, (short)2, "fbiCode2", 2.0f, 2.0f);
        DBMS.addRecord("TEST3", "2001/12/12 03:40:12 PM", "Block3", "IUCR3", "pDesc3",  "SDec3",
                "lDesc3", false, false, (short)3, (short)3, "fbiCode3", 3.0f, 3.0f);


        CrimeRecord record = DBMS.getRecord(1);

        System.out.println(record.getDate());
    }
}
