package seng202.group2.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

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
     * @param update Update the observers
     */
    public void addFilter(Filter filter, boolean update) {
        filters.add(filter);

        if (update) {
            updateObservers();
        }
    }

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
     * Remove filter from the list
     *
     * @param filter Filter object to remove
     * @param update Update the observers
     */
    public void removeFilter(Filter filter, boolean update) {
        if (filters.contains(filter)) {
            filters.remove(filter);

            if (update)
                updateObservers();
        }
    }

    /**
     * Remove all filters
     * @param update Update the observers
     */
    public void clearFilters(boolean update) {
        filters = new ArrayList<>();
        if (update)
            updateObservers();
    }

    /**
     * Generate conditions string based on Filters
     * @return String representing the filters in SQL
     */
    private String generateFilterString() {
        String query = " WHERE ";
        String equality = "(";
        boolean hasComparison = false;
        boolean hasEquality = false;
        boolean isCondition = false;

        //Go through all filters
        for (Filter filter: filters){
            if ((filter.getType()).equals(FilterType.SORT)) {
                //If the filter is of type sort, you have to skip it, the sort is used only on retrieval

                continue;
            } else if ((filter.getType()).equals(FilterType.EQ)) {
                //If the filter is of type EQ, you have to chain then together seperatly using OR

                if (hasEquality)
                    equality += " OR ";

                equality += filter.getSQLText();
                hasEquality = true;
            } else {
                //Chain together using AND

                if (hasComparison)
                    query += " AND ";

                query += filter.getSQLText();
                hasComparison = true;
            }

            isCondition = true;
        }
        equality += ")";

        //Need an AND if we are using both <|> and =
        if (hasComparison && hasEquality)
            equality = " AND " + equality;

        //Add equality checks to the end
        if (hasEquality)
            query += equality;

        //Erase if there are no filters
        if (!isCondition)
            query = "";

        //System.out.println(query);

        return query;
    }

    /**
     * Updates the ActiveRecords database in DBMS. This updates using all current filters.
     */
    public void updateActiveRecords() {
        //Get list of IDs
        ResultSet results = DBMS.customQuery("SELECT id FROM Records" + generateFilterString() + ";");

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
     * Generates an order string using Filters to enable SQL sorting.
     */
    private String generateOrderString() {
        String orderString = "";
        boolean firstSort = true;

        for (Filter filter : filters) {
            if (filter.getType() == FilterType.SORT) {
                if (!firstSort) {
                    orderString += ", ";
                } else {
                    orderString += " ORDER BY ";
                    firstSort = false;
                }

                orderString += filter.getSQLText();
            }
        }
        return orderString;
    }

    /**
     * Get all currently active records.
     *
     * @return ArrayList<CrimeRecords> of active records.
     */
    public ArrayList<CrimeRecord> getActiveRecords() {
        return DBMS.getActiveRecords(0, -1, generateOrderString());
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
        return DBMS.getActiveRecords(start, limit, generateOrderString());
    }

    public ArrayList<Filter> getFilters() {
        return filters;
    }
}
