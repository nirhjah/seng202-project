package seng202.group2.model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * DataBaseManagementSystem. This controls the SQLite database and connects the data model together.
 * All SQL should be run through here.
 */
public class DBMS {

    private static ActiveData activeData = new ActiveData();               //Active data class
    private static final String DATE_FORMAT = "yyyy/MM/dd hh:mm:ss a";     //Date format
    private static  boolean hasDataBase = false;                           //Database is created
    private static Connection conn;                                        //Database connection
    private static int idCounter = -1;                                     //Current item ID

    /**
     * Connect to the database
     */
    private static void getConnection() {
        try {
            //Create db connection
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite::memory:");

            //Initialize tables
            initialize();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Failed to get Connection DBMS:getConnection:34");
            //e.printStackTrace();
        }
    }

    /**
     * Create the database and tables if they do not exist. Every computer will run this once, but you can also delete
     * the CrimeRecords.db file, and it will recreate it.
     *
     * TODO Test if you can use IF NOT EXISTS
     */
    private static void initialize() {
        if (!hasDataBase) {
            hasDataBase = true;

            try {
                //Get the database and select the table
                Statement state = conn.createStatement();
                ResultSet result = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND tbl_name='Records'");

                //If the table does not exist
                if (!result.next()) {
                    System.out.println("Building table");

                    //Build table
                    Statement state2 = conn.createStatement();
                    state2.execute("CREATE TABLE Records(id integer,"
                            + "caseNum string,"
                            + "date string,"
                            + "block string,"
                            + "IUCR string,"
                            + "primaryDescription string,"
                            + "secondaryDescription string,"
                            + "locationDescription string,"
                            + "arrest boolean,"
                            + "domestic boolean,"
                            + "beat short,"
                            + "ward short,"
                            + "fbiCode string,"
                            + "latitude float,"
                            + "longitude float,"
                            + "primary key(id));"
                    );
                }
            } catch (SQLException e) {
                System.out.println("Failed to create Records table in database. DBMS:initialize:78");
            }

            try {
                //Get the database and select the table
                Statement state = conn.createStatement();
                ResultSet result = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND tbl_name='ActiveRecords'");

                //If the table does not exist
                if (!result.next()) {
                    System.out.println("Building table");

                    //Build table
                    Statement state2 = conn.createStatement();
                    state2.execute("CREATE TABLE ActiveRecords(id integer,"
                            + "caseNum string,"
                            + "date string,"
                            + "block string,"
                            + "IUCR string,"
                            + "primaryDescription string,"
                            + "secondaryDescription string,"
                            + "locationDescription string,"
                            + "arrest boolean,"
                            + "domestic boolean,"
                            + "beat short,"
                            + "ward short,"
                            + "fbiCode string,"
                            + "latitude float,"
                            + "longitude float,"
                            + "primary key(id));"
                    );
                }
            } catch (SQLException e) {
                System.out.println("Failed to create ActiveRecords table in database. DBMS:initialize:111");
            }
        }
    }

    /**
     * Run a custom SQL query on the database
     *
     * @param query String representation of the SQL query
     * @return ResultSet containing results, NULL if failed.
     */
    public static ResultSet customQuery(String query) {
        if (conn == null) {
            getConnection();
        }

        try {
            Statement state = conn.createStatement();
            return state.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Could not run custom query. Query shown below. DBMS:customQuery:131");
            System.out.println(query);
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts a ResultSet containing one record into a CrimeRecord object
     *
     * @param record ResultSet containing a single record
     * @return CrimeRecord Generated by the function
     */
    private static CrimeRecord generateCrimeRecord(ResultSet record) {
        CrimeRecord crimeRecord = new CrimeRecord();

        //Set the data
        try {
            ResultSetMetaData metaData = record.getMetaData();
            int columnCount = metaData.getColumnCount();
            boolean isNull = false;

            // The column count starts from 1
            for (int i = 1; i <= columnCount; i++ ) {
                String name = metaData.getColumnName(i);

                //If the value is null, don't try to put it in.
                try {
                    String result = record.getString(name);
                    if (result == null) {
                        continue;
                    }
                } catch (NullPointerException e) {
                    continue;
                }

                //Else insert it into the CrimeData object
                switch (name) {
                    case "id":
                        crimeRecord.setID(record.getInt("id"));
                        break;
                    case "caseNum":
                        crimeRecord.setCaseNum(record.getString("caseNum"));
                        break;
                    case "date":
                        //Convert String from db to Calendar
                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(sdf.parse(record.getString("date")));
                        crimeRecord.setDate(cal);
                        break;
                    case "block":
                        crimeRecord.setBlock(record.getString("block"));
                        break;
                    case "IUCR":
                        crimeRecord.setIucr(record.getString("IUCR"));
                        break;
                    case "primaryDescription":
                        crimeRecord.setPrimaryDescription(record.getString("primaryDescription"));
                        break;
                    case "secondaryDescription":
                        crimeRecord.setSecondaryDescription(record.getString("secondaryDescription"));
                        break;
                    case "locationDescription":
                        crimeRecord.setLocationDescription(record.getString("locationDescription"));
                        break;
                    case "arrest":
                        crimeRecord.setArrest(record.getBoolean("arrest"));
                        break;
                    case "domestic":
                        crimeRecord.setDomestic(record.getBoolean("domestic"));
                        break;
                    case "beat":
                        crimeRecord.setBeat(record.getShort("beat"));
                        break;
                    case "ward":
                        crimeRecord.setWard(record.getShort("ward"));
                        break;
                    case "fbiCode":
                        crimeRecord.setFbiCode(record.getString("fbiCode"));
                        break;
                    case "latitude":
                        crimeRecord.setLatitude(record.getFloat("latitude"));
                        break;
                    case "longitude":
                        crimeRecord.setLongitude(record.getFloat("longitude"));
                        break;
                }
            }
        } catch (SQLException | ParseException e) {
            e.getStackTrace();
        }


        return crimeRecord;
    }

    /**
     * Gets a record from Records table by ID.
     *
     * @param id Integer representing the ID of the record
     * @return CrimeRecord retrieved from Records. NULL if the record does not exist
     */
    public static CrimeRecord getRecord(int id) {
        if (conn == null) {
            getConnection();
        }

        try {
            //Get record
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM Records WHERE id =" + id);
            return generateCrimeRecord(result);
        } catch (SQLException e) {
            System.out.println("Could not retrieve record with ID: " + id + " from Records table. DBMS:getRecord:248");
        }

        return null;
    }


    /**
     * Get multiple records from Records table as an ArrayList.
     *
     * @return ArrayList of CrimeRecords.
     */
    public static ArrayList<CrimeRecord> getRecords(int min, int limit) {
        ArrayList<CrimeRecord> records = new ArrayList<>();
        ResultSet results;

        if (conn == null) {
            getConnection();
        }

        try {
            //Select records from Records
            Statement state = conn.createStatement();
            results = state.executeQuery("SELECT * FROM Records LIMIT " + limit + " OFFSET " + min);

            //Generate and add the records to the records ArrayList
            while (results.next()) {
                records.add(generateCrimeRecord(results));
            }
        } catch (SQLException e) {
            System.out.println("Could not retrieve records from Records table. DBMS:getRecords:278");
            //e.printStackTrace();
        }

        return records;
    }

    /**
     * Gets a record from ActiveRecords table by ID.
     *
     * @param id Integer representing the ID of the record
     * @return CrimeRecord retrieved from Records. NULL if the record does not exist
     */
    public static CrimeRecord getActiveRecord(int id) {
        if (conn == null) {
            getConnection();
        }

        try {
            //Get record
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM ActiveRecords WHERE id =" + id);
            return generateCrimeRecord(result);
        } catch (SQLException e) {
            System.out.println("Could not retrieve record with ID: " + id + " from ActiveRecords table. DBMS:getActiveRecord:302");
        }

        return null;
    }

    /**
     * Get multiple records from ActiveRecords table as an ArrayList.
     *
     * @return ArrayList of CrimeRecords.
     */
    public static ArrayList<CrimeRecord> getActiveRecords(int min, int limit) {
        ArrayList<CrimeRecord> records = new ArrayList<>();
        ResultSet results;

        if (conn == null) {
            getConnection();
        }

        try {
            //Select records from Records
            Statement state = conn.createStatement();
            results = state.executeQuery("SELECT * FROM ActiveRecords LIMIT " + limit + " OFFSET " + min);

            //Generate and add the records to the records ArrayList
            while (results.next()) {
                records.add(generateCrimeRecord(results));
            }
        } catch (SQLException e) {
            System.out.println("Could not retrieve records from ActiveRecords table. DBMS:getActiveRecords:331");
            //e.printStackTrace();
        }

        return records;
    }

    /**
     * Gets all records in the database.
     *
     * @return ResultSet (java.sql) Representing Query results. NULL if an error occurs.
     */
    public static ResultSet getAllRecords() {
        if (conn == null) {
            getConnection();
        }

        try {
            Statement state = conn.createStatement();
            return state.executeQuery("SELECT * FROM Records;");
        } catch (SQLException e) {
            System.out.println("Could not get all records from the database. DBMS:getAllRecords:352");
            //e.printStackTrace();
        }

        return null;
    }

    /**
     * Adds a record to the database. Update the observers if desired
     *
     * @param record -- CrimeRecord to add. This CrimeRecord does not need an id assigned
     * @param update -- If true, the ActiveData will be updated
     */
    public static void addRecord(CrimeRecord record, boolean update) {
        if (conn == null) {
            getConnection();
        }

        //If we don't already have a counter
        if (idCounter == -1) {
            //Find the current ID
            try {
                Statement maxID = conn.createStatement();
                ResultSet res = maxID.executeQuery("SELECT MAX(id) as max FROM Records;");
                idCounter = res.getInt("max") + 1;
            } catch (SQLException e) {
                System.out.println("Could not find the MAX ID in the Records table. DBMS:addRecord:378");
            }
        }


        //Insert into database
        SQLInsertStatement state = new SQLInsertStatement("Records");
        state.setValue("id", Integer.toString(idCounter));
        state.setValue("caseNum", record.getCaseNum());

        //Convert date
        Calendar time = record.getDate();
        if (time != null) {
            int year = time.get(Calendar.YEAR);
            int month = time.get(Calendar.MONTH) + 1; // Note: zero based!
            int day = time.get(Calendar.DAY_OF_MONTH);
            int hour = time.get(Calendar.HOUR_OF_DAY);
            int minute = time.get(Calendar.MINUTE);
            int second = time.get(Calendar.SECOND);
            int a = time.get(Calendar.AM_PM);
            state.setValue("date", String.format("%d/%02d/%02d %02d:%02d:%02d ", year, month, day, hour, minute, second) + ((a == 0) ? "AM" : "PM"));
        }

        state.setValue("block", record.getBlock());

        //Set IUCR to data, or null
        try {
            state.setValue("IUCR", record.getIucr());
            state.setValue("primaryDescription", record.getPrimaryDescription());
            state.setValue("secondaryDescription", record.getSecondaryDescription());
        } catch (NullPointerException e) {
            System.out.println("IUCR Error in addData");
            state.setValue("IUCR", null);
            state.setValue("primaryDescription", null);
            state.setValue("secondaryDescription",null);
        }
        state.setValue("locationDescription", record.getLocationDescription());
        state.setValue("arrest", String.valueOf(record.getArrest()));
        state.setValue("domestic", String.valueOf(record.getDomestic()));
        state.setValue("beat", String.valueOf(record.getBeat()));
        state.setValue("ward", String.valueOf(record.getWard()));
        state.setValue("fbiCode", record.getFbiCode());
        state.setValue("latitude", String.valueOf(record.getLatitude()));
        state.setValue("longitude", String.valueOf(record.getLongitude()));

        try {
            //Insert into the database
            Statement insert = conn.createStatement();
            insert.execute(state.getStatement());

            //Increment ID
            idCounter++;

            //Update observers
            if (update) {
                activeData.updateActiveRecords();
            }
        } catch (SQLException e) {
            System.out.println("Could not insert record into Records table. DBMS:addRecord:436");
        }
    }

    /**
     * Add an ArrayList of CrimeRecords to the database.
     *
     * @param records -- ArrayList of CrimeRecords to add to the database
     */
    public static void addRecords(ArrayList<CrimeRecord> records) {
        int count = 0;
        for (CrimeRecord record : records) {
            addRecord(record, false);
            System.out.print("\rAdded " + ++count + " records to database");
        }
        System.out.println();

        activeData.updateActiveRecords();
        activeData.updateObservers();
    }

    /**
     * Update the ActiveDatabase with records from Records that match the given IDs.
     *
     * @param IDs -- ArrayList of IDs to match
     */
    public static void updateActiveDatabase(ArrayList<Integer> IDs) {
        if (conn == null) {
            getConnection();
        }

        //Clear the database
        try {
            Statement deleteStatement = conn.createStatement();
            deleteStatement.execute("DELETE FROM ActiveRecords;");

        } catch (SQLException e) {
            System.out.println("Could not clear ActiveRecords. DBMS:updateActiveDatabase:473");
        }

        //Copy data in blocks to reduce string size
        final int blockSize = 200;
        final int size = IDs.size();
        int start = 0;
        int end = Math.min(size, blockSize);

        while (start < size) {
            //Create copy string
            StringBuilder conditions = new StringBuilder("WHERE id IN (");
            boolean first = true;

            for (int i=start; i < end; i++) {
                if (first) {
                    conditions.append(Integer.toString(IDs.get(i)));
                    first = false;
                }
                else {
                    conditions.append(",").append(Integer.toString(IDs.get(i)));
                }
            }
            conditions.append(");");

            try {
                //Execute
                Statement getActive = conn.createStatement();
                getActive.execute("INSERT INTO ActiveRecords SELECT * FROM Records " + conditions);
            } catch (SQLException e) {
                System.out.println("Could not copy block to ActiveRecords. DBMS:updateActiveDatabase:508");
            }

            //Update block count
            start = end;
            end = Math.min(end + blockSize, size);

            System.out.print("\rAdded " + start + " records to activeRecords database.");
        }
    }

    /**
     * Delete record from database by ID
     *
     * @param id Integer ID of the record to delete
     */
    public static void deleteRecord(int id) {
        if (conn == null) {
            getConnection();
        }

        try {
            Statement state = conn.createStatement();
            state.execute("DELETE FROM Records WHERE id =" + id);
            Statement state2 = conn.createStatement();
            state2.execute("DELETE FROM ActiveRecords WHERE id =" + id);
        } catch (SQLException e) {
            System.out.println("Could not delete record with ID: " + id + " from database. DBMS:deleteRecord:535");
        }
    }

    /**
     * Remove all records from the database and reset ID counter
     */
    public static void clearDB() {
        if (conn == null) {
            getConnection();
        }

        try {
            //Delete Records
            Statement state = conn.createStatement();
            state.execute("DELETE FROM Records;");
            Statement state2 = conn.createStatement();
            state2.execute("DELETE FROM ActiveRecords;");
        } catch (SQLException e) {
            System.out.println("Could not delete all records from database. DBMS:clearDB:554");
        }

        //Reset counter
        idCounter = -1;
    }

    /**
     * Find the number of records in the Records table
     *
     * @return Number of records in Record table. Returns -1 on failure.
     */
    public static int getRecordsSize() {
        if (conn == null) {
            getConnection();
        }

        try {
            Statement state = conn.createStatement();
            ResultSet res = state.executeQuery("SELECT COUNT(DISTINCT id) as total FROM Records;");

            res.next();
            return res.getInt("total");
        } catch (SQLException e) {
            System.out.println("Failed to get size of Record table. DBMS:getRecordsSize:578");
        }

        return -1;
    }

    /**
     * Find the number of records in the ActiveRecords table
     *
     * @return Number of records in ActiveRecords table. Returns -1 on failure.
     */
    public static int getActiveRecordsSize() {
        if (conn == null) {
            getConnection();
        }

        try {
            Statement state = conn.createStatement();
            ResultSet res = state.executeQuery("SELECT COUNT(DISTINCT id) as total FROM ActiveRecords;");

            res.next();
            return res.getInt("total");
        } catch (SQLException e) {
            System.out.println("Failed to get size of ActiveRecord table. DBMS:getActiveRecordsSize:578");
        }

        return -1;
    }

    /**
     * Get ActiveData object associated with this DBMS
     */
    public static ActiveData getActiveData() {
        return activeData;
    }
}
