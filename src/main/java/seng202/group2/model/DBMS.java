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
    //String.format("%d/%02d/%02d %02d:%02d:%02d ", year, month, day, hour, minute, second) + ((a == 0)? "AM": "PM")
    private static final String DATE_FORMAT = "yyyy/MM/dd hh:mm:ss a";

    /**
     * Connect to the database
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private static void getConnection() throws ClassNotFoundException, SQLException {
        //Create db connection
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:CrimeRecords.db");

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
            crimeRecord.setID(record.getInt("id"));
            crimeRecord.setCaseNum(record.getString("caseNum"));

            //Convert String from db to Calendar
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(record.getString("date")));
            crimeRecord.setDate(cal);

            crimeRecord.setBlock(record.getString("block"));
            crimeRecord.setIucr(IUCRCodeDictionary.getCode(record.getString("IUCR")));
            crimeRecord.setLocationDescription(record.getString("locationDescription"));
            crimeRecord.setArrest(record.getBoolean("arrest"));
            crimeRecord.setDomestic(record.getBoolean("domestic"));
            crimeRecord.setBeat(record.getShort("beat"));
            crimeRecord.setWard(record.getShort("ward"));
            crimeRecord.setFbiCode(record.getString("fbiCode"));
            crimeRecord.setLatitude(record.getFloat("latitude"));
            crimeRecord.setLongitude(record.getFloat("longitude"));

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
     * Adds a record to the database.
     *
     * @param record -- CrimeRecord to add. This CrimeRecord does not need an id assigned
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void addRecord(CrimeRecord record) throws SQLException, ClassNotFoundException {
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
        PreparedStatement state = conn.prepareStatement("INSERT INTO records values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
        state.setInt(1, idCounter);
        state.setString(2, record.getCaseNum());

        Calendar time = record.getDate();
        int year = time.get(Calendar.YEAR);
        int month = time.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = time.get(Calendar.DAY_OF_MONTH);
        int hour = time.get(Calendar.HOUR_OF_DAY);
        int minute = time.get(Calendar.MINUTE);
        int second = time.get(Calendar.SECOND);
        int a = time.get(Calendar.AM_PM);
        state.setString(3, String.format("%d/%02d/%02d %02d:%02d:%02d ", year, month, day, hour, minute, second) + ((a == 0)? "AM": "PM"));


        state.setString(4,record.getBlock());
        state.setString(5, record.getIucr().IUCR);
        state.setString(6,record.getPrimaryDescription());
        state.setString(7,record.getSecondaryDescription());
        state.setString(8, record.getLocationDescription());
        state.setBoolean(9, record.getArrest());
        state.setBoolean(10, record.getDomestic());
        state.setShort(11, record.getBeat());
        state.setShort(12, record.getWard());
        state.setString(13, record.getFbiCode());
        state.setFloat(14, record.getLatitude());
        state.setFloat(15, record.getLongitude());
        state.execute();
        idCounter++;
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
