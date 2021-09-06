package seng202.group2.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Active data class manages the filters, allowing the views to display a subset of the database
 *
 * TODO Add Junit tests.
 */
public class ActiveData extends DataSource {
    /** List of filters */
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
     * Get all records that match the filters as CrimeRecords.
     *
     * @return ArrayList\<CrimeRecord\> Of all records that match the filters
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<CrimeRecord> getActiveRecords() throws SQLException, ClassNotFoundException {
        //Generate Query string with filters
        String whereQuery = "";
        String sortQuery = "SELECT id FROM records";
        boolean first = true;

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
        //System.out.println(sortQuery + whereQuery + ";");
        ResultSet results = DBMS.customQuery(sortQuery + whereQuery + ";");
        ArrayList<Integer> IDs = new ArrayList<>();

        //Format to ArrayList
        while (results.next()) {
            IDs.add(results.getInt("id"));
        }

        //Get CrimeRecords
        return DBMS.getRecords(IDs);
    }
}
