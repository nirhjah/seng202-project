package seng202.group2.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Active data class manages the filters, allowing the views to display a subset of the database
 *
 * TODO Add Junit tests.
 */
public class ActiveData extends DataSource {
    //List of filters
    private LinkedList<Filter> filters = new LinkedList<>();
    /** List of record ids selected by the user. */
    private HashSet<Integer> selectedRecords = new HashSet<>();

    /**
     * Add a filter to the filter list
     *
     * @param filter Filter object to add
     * @param update Update the observers
     */
    public void addFilter(Filter filter, boolean update) {
        filters.addLast(filter);

        if (update) {
            updateActiveData();
        }
    }

    /**
     * Add a filter to the filter list
     *
     * @param filter Filter object to add
     */
    public void addFilter(Filter filter) {
        filters.addLast(filter);

        updateActiveData();
    }

    /**
     * Remove filter from the list
     *
     * @param filter Filter object to remove
     */
    public void removeFilter(Filter filter) {
        if (filters.contains(filter)) {
            filters.remove(filter);

            updateActiveData();
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
                updateActiveData();
        }
    }

    /**
     * Remove all filters
     * @param update Update the observers
     */
    public void clearFilters(boolean update) {
        filters = new LinkedList<>();
        if (update)
            updateActiveData();
    }

    /**
     * Updates the ActiveRecords database in DBMS. This updates using all current filters.
     */
    public void updateActiveRecords() {
        //Generate Query string with filters
        String whereQuery = "";
        String sortQuery = "";
        boolean firstSort = true;
        boolean firstCondition = true;

        //Create SQL string
        for (Filter filter: filters){
            //If the filter is of type sort, you need to add to start instead of end
            if ((filter.getType()).equals(FilterType.SORT)) {
                if (!firstSort) {
                    sortQuery += ", ";
                } else {
                    sortQuery += " ORDER BY ";
                }

                sortQuery += filter.getSQLText();
                firstSort = false;
            } else {
                if (!firstCondition) {
                    whereQuery += " AND ";
                } else {
                    whereQuery += " WHERE ";
                }

                whereQuery += filter.getSQLText();
                firstCondition = false;
            }
        }

        if (firstSort) {
            sortQuery += " ORDER BY id";
        }

        //Get list of IDs
        ResultSet results = DBMS.customQuery("SELECT id FROM Records" + whereQuery + sortQuery  + ";");

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

    public LinkedList<Filter> getFilters() {
        return filters;
    }

    /**
     * Selects a record if it is not selected, otherwise deselects a record if it is selected.
     * @param id The id of the CrimeRecord to (de)select.
     */
    public void toggleSelectRecord(Integer id) {
        if (selectedRecords.contains(id))
            deselectRecord(id);
        else
            selectRecord(id);
    }

    /**
     * Adds a crime record to the set of selected crime records.
     * @param id The id of the CrimeRecord to select.
     */
    public void selectRecord(Integer id) {
        selectedRecords.add(id);
        updateActiveData();
    }

    /**
     * Removes a crime record from the set of selected crime records.
     * @param id The id of the CrimeRecord to deselect.
     */
    public void deselectRecord(Integer id) {
        selectedRecords.remove(id);
        updateActiveData();
    }

    /**
     * Removes all crime records from the set of selected crime records.
     */
    public void clearSelection() {
        selectedRecords = new HashSet<>();
        updateActiveData();
    }

    /**
     * Returns the set of selected crime records.
     * @return The set of selected crime record ids.
     */
    public HashSet<Integer> getSelectedRecords() {
        return selectedRecords;
    }

    /**
     * Checks if a crime record is selected.
     * @param id The id of the CrimeRecord the selection of which is to be determined.
     * @return True if the CrimeRecord is selected, false if not.
     */
    public boolean isSelected(Integer id) {
        return selectedRecords.contains(id);
    }
}
