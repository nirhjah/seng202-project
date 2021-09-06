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

    private static Connection conn;
    private static ActiveData activeData = new ActiveData();
    private static int idCounter = -1;
    private static  boolean hasDataBase = false;
    private static final String DATE_FORMAT = "yyyy/MM/dd hh:mm:ss a";
    //String.format("%d/%02d/%02d %02d:%02d:%02d ", year, month, day, hour, minute, second) + ((a == 0)? "AM": "PM")

    /**
     * Connect to the database
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private static void getConnection() throws ClassNotFoundException, SQLException {
        //Create db connection
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");

        //Initialize tables
        initialize();
    }

    /**
     * Create the database and tables if they do not exist. Every computer will run this once, but you can also delete
     * the CrimeRecords.db file, and it will recreate it.
     *
     * @throws SQLException
     */
    private static void initialize() throws SQLException {
        if (!hasDataBase) {
            hasDataBase = true;

            //Get the database and select the table
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='records'");

            //If the table does not exist
            if (!result.next()) {
                System.out.println("Building table");

                //Build table
                Statement state2 = conn.createStatement();
                state2.execute("CREATE TABLE records(id integer,"
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
        }
    }

    /**
     * Run a custom query on the database
     *
     * @param query String representation of the query
     * @return ResultSet containing results
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ResultSet customQuery(String query) throws SQLException, ClassNotFoundException {
        if (conn == null) {
            getConnection();
        }

        Statement state = conn.createStatement();
        return state.executeQuery(query);
    }

    /**
     * Converts a ResultSet containing one record into a Crime Record object
     *
     * @param record ResultSet containing a single record
     * @return CrimeRecord Generated by the function
     */
    private static CrimeRecord generateCrimeRecord(ResultSet record) {
        CrimeRecord crimeRecord = new CrimeRecord();

        //Set the data
        try {
            ResultSetMetaData rsmd = record.getMetaData();
            int columnCount = rsmd.getColumnCount();
            boolean isNull = false;

            // The column count starts from 1
            for (int i = 1; i <= columnCount; i++ ) {
                String name = rsmd.getColumnName(i);

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
     * Gets a record by ID.
     *
     * @param id Integer representing the ID of the record
     * @return CrimeRecord retrieved from generateCrimeRecords
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static CrimeRecord getRecord(int id) throws SQLException, ClassNotFoundException {
        if (conn == null) {
            getConnection();
        }

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM records WHERE id =" + id);
        return generateCrimeRecord(result);
    }

    /**
     * Get multiple records as an ArrayList
     *
     * @param ids List of integer IDs to retrieve.
     * @return ArrayList of CrimeRecords
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ArrayList<CrimeRecord> getRecords(ArrayList<Integer> ids) throws SQLException, ClassNotFoundException {
        if (conn == null) {
            getConnection();
        }
        ArrayList<CrimeRecord> results = new ArrayList<>();

        for (int id: ids) {
            results.add(getRecord(id));
        }

        return results;
    }

    /**
     * Gets all records in the database.
     *
     * @return ResultSet (java.sql) Representing Query results
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ResultSet getAllRecords() throws SQLException, ClassNotFoundException {
        if (conn == null) {
            getConnection();
        }

        Statement state = conn.createStatement();
        return state.executeQuery("SELECT * FROM records;");
    }

    /**
     * Adds a record to the database. Update the observers if desired
     *
     * @param record -- CrimeRecord to add. This CrimeRecord does not need an id assigned
     * @param update -- If true, the observers will be updated
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void addRecord(CrimeRecord record, boolean update) throws SQLException, ClassNotFoundException {
        if (conn == null) {
            getConnection();
        }

        //If we don't already have a counter
        if (idCounter == -1) {
            //Find the current ID
            Statement maxID = conn.createStatement();
            ResultSet res = maxID.executeQuery("SELECT MAX(id) as max FROM records;");
            idCounter = res.getInt("max") + 1;
        }


        //Insert into database
        SQLInsertStatement state = new SQLInsertStatement("records");
        state.setValue("id", Integer.toString(idCounter));
        state.setValue("caseNum", record.getCaseNum());

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

        Statement insert = conn.createStatement();
        insert.execute(state.getStatement());
        idCounter++;

        if (update) {
            activeData.updateObservers();
        }
    }

    /**
     * Add an ArrayList of CrimeRecords to the database.
     *
     * @param records
     */
    public static void addRecords(ArrayList<CrimeRecord> records) {
        int count = 0;
        System.out.println("Adding crime records to local database.");
        for (CrimeRecord record : records) {
            try {
                addRecord(record, false);
                System.out.print("\rAdded " + ++count + " records to database");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println();

        activeData.updateObservers();
    }

    /**
     * Delete record from database by ID
     *
     * @param id Integer ID of the record to delete
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void deleteRecord(int id) throws SQLException, ClassNotFoundException {
        if (conn == null) {
            getConnection();
        }

        Statement state = conn.createStatement();
        state.execute("DELETE FROM records WHERE id =" + id);
    }

    /**
     * Remove all records from the database and reset ID counter
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void clearDB() throws SQLException, ClassNotFoundException {
        if (conn == null) {
            getConnection();
        }

        //Delete Records
        Statement state = conn.createStatement();
        state.execute("DELETE FROM records;");

        //Reset counter
        idCounter = -1;
    }

    /**
     * Find the number of records in the database
     *
     * @return Number of records in DB
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static int getDBSize() throws SQLException, ClassNotFoundException {
        if (conn == null) {
            getConnection();
        }

        Statement state = conn.createStatement();
        ResultSet res = state.executeQuery("SELECT COUNT(DISTINCT id) as total FROM records;");

        res.next();
        return res.getInt("total");
    }

    public static ActiveData getActiveData() {
        return activeData;
    }
}
