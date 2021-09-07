package seng202.group2.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Active data class manages the filters, allowing the views to display a subset of the database
 *
 * TODO Add Junit tests.
 */
public class ActiveData extends DataSource{
    //List of filters
    private ArrayList<Filter> filters = new ArrayList<>();
    //Currently-active data. Originally set to empty
    private ArrayList<CrimeRecord> activeRecords = new ArrayList<>();

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
     * Get all records from the database that match the filters as CrimeRecords.
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateActiveRecords() throws SQLException, ClassNotFoundException, InterruptedException {
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
        ArrayList<Integer> IDs = new ArrayList<>();

        //Format to ArrayList
        while (results.next()) {
            IDs.add(results.getInt("id"));
        }

        activeRecords = DBMS.getRecords(IDs);
    }

    /**
     * Get currently active records
     *
     * @return ArrayList<CrimeRecords> of active records
     */
    public ArrayList<CrimeRecord> getActiveRecords() {
        return activeRecords;
    }

    /**
     * Get currently active records from a range of indices.
     *
     * @return ArrayList<CrimeRecords> of active records between start and end
     */
    public ArrayList<CrimeRecord> getActiveRecords(int start, int end) {
        return new ArrayList<>(activeRecords.subList(start, Math.min(end, activeRecords.size())));
    }
}
