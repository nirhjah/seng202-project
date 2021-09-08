package seng202.group2.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Active data class manages the filters, allowing the views to display a subset of the database
 *
 * TODO Add Junit tests.
 */
public class ActiveData extends DataSource{
    //List of filters
    private ArrayList<Filter> filters = new ArrayList<>();

    /**
     * Add a filter to the filter list
     *
     * @param filter Filter object to add
     */
    public void addFilter(Filter filter) {
        filters.add(filter);
        updateObservers();
    }

    /**
     * Remove filter from the list
     *
     * @param filter Filter object to remove
     */
    public void removeFilter(Filter filter) {
        if (filters.contains(filter)) {
            filters.remove(filter);

            updateObservers();
        }
    }

    /**
     * Updates the ActiveRecords database in DBMS. This updates using all current filters.
     */
    public void updateActiveRecords() {
        //Generate Query string with filters
        String whereQuery = "";
        String sortQuery = "SELECT id FROM records";
        boolean first = true;

        //Create SQL string
        for (Filter filter: filters){
            //If the filter is of type sort, you need to add to start instead of end
            if ((filter.getType()).equals(FilterType.SORT)) {
                if (!first) {
                    sortQuery += " AND ";
                } else {
                    sortQuery += " ORDER BY ";
                }

                sortQuery += filter.getSQLText();
            } else {
                if (!first) {
                    whereQuery += ", ";
                } else {
                    whereQuery += " WHERE ";
                }

                whereQuery += filter.getSQLText();
            }

            first = false;
        }

        if (first) {
            sortQuery += " ORDER BY id";
        }

        //Get list of IDs
        ResultSet results = DBMS.customQuery(sortQuery + whereQuery + ";");

        //Format to ArrayList
        ArrayList<Integer> IDs = new ArrayList<>();
        try {
            while (results.next()) {
                IDs.add(results.getInt("id"));
            }

            //Update ActiveRecords database
            DBMS.updateActiveDatabase(IDs);
        } catch (SQLException e) {
            System.out.println("Failed to add results to ArrayList. DBMS:updateActiveRecords:86");
        }
    }

    /**
     * Get all currently active records.
     *
     * @return ArrayList<CrimeRecords> of active records.
     */
    public ArrayList<CrimeRecord> getActiveRecords() {
        return DBMS.getActiveRecords(0, -1);
    }

    /**
     * Get currently active records from a range of indices.
     * From (min) to (min+limit).
     *
     * @param start -- The Smallest row index to get from database (EXCLUSIVE)
     * @param limit -- The range of values to get (INCLUSIVE)
     * @return ArrayList<CrimeRecords> of active records between start and end.
     */
    public ArrayList<CrimeRecord> getActiveRecords(int start, int limit) {
        return DBMS.getActiveRecords(start, limit);
    }
}
