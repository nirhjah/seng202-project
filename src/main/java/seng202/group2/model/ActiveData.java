package seng202.group2.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Active data class manages the filters, allowing the views to display a subset of the database
 *
 * TODO Add Junit tests.
 */
public class ActiveData extends DataSource {
    //List of filters
    private ArrayList<Filter> filters = new ArrayList<>();
    //Currently-active data. Originally set to empty
    private ArrayList<CrimeRecord> activeRecords = new ArrayList<>();
    /** List of record ids selected by the user. */
    private HashSet<Integer> selectedRecords = new HashSet<>();

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
        updateObservers();
    }

    /**
     * Removes a crime record from the set of selected crime records.
     * @param id The id of the CrimeRecord to deselect.
     */
    public void deselectRecord(Integer id) {
        selectedRecords.remove(id);
        updateObservers();
    }

    /**
     * Removes all crime records from the set of selected crime records.
     */
    public void clearSelection() {
        selectedRecords = new HashSet<>();
        updateObservers();
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
