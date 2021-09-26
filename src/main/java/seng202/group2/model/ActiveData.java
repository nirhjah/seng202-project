package seng202.group2.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Active data class manages the filters, frame size and selected data, allowing the views to display a subset of the database
 */
public class ActiveData extends DataSource {
    //List of filters
    private ArrayList<Filter> filters = new ArrayList<>();
    //List of record ids selected by the user
    private HashSet<Integer> selectedRecords = new HashSet<>();

    //Frame variables
    private int frameSize = 1000;
    private int recordCount = 0;
    private int currentMin = 0;
    private int currentMax = 0;

    /**
     * Update the frame size
     * @param newSize New frame size
     */
    public void updateFrameSize(int newSize) {
        ActiveData activeData = DBMS.getActiveData();
        recordCount = DBMS.getActiveRecordsSize();

        frameSize = newSize;
        currentMax = Math.min(activeData.currentMin + frameSize, recordCount);

        updateFrame();
    }

    /**
     * Increment the current frame
     */
    public void incrementFrame() {
        recordCount = DBMS.getActiveRecordsSize();
        currentMax = Math.min(currentMax + frameSize, recordCount);
        currentMin = Math.max(Math.min(currentMin + frameSize, recordCount - frameSize), 0);

        updateFrame();
    }

    /**
     * Decrement the current frame
     */
    public void decrementFrame() {
        recordCount = DBMS.getActiveRecordsSize();
        currentMin = Math.max(currentMin - frameSize, 0);
        currentMax = Math.max(currentMax - frameSize, Math.max(Math.min(recordCount, frameSize), 0));

        updateFrame();
    }

    /**
     * Add a filter to the filter list
     *
     * @param filter Filter object to add
     * @param update Update the observers
     */
    public void addFilter(Filter filter, boolean update) {
        filters.add(filter);

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
        filters.add(filter);

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
        filters = new ArrayList<>();
        if (update)
            updateActiveData();
    }

    /**
     * Generate conditions string based on Filters
     *
     * @return {String} representing the filters in SQL
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
     * Updates the ActiveRecords database in DBMS. This updates using all current filters.
     */
    public void updateActiveRecords() {
        //Dont update if there are no filters
        if (filters.size() <= 0) {
            return;
        }

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
     * Get all currently active records.
     *
     * @return ArrayList<CrimeRecords> of active records.
     */
    public ArrayList<CrimeRecord> getActiveRecords() {
        return DBMS.getActiveRecords(currentMin, frameSize, generateOrderString());
    }

    public ArrayList<CrimeRecord> getAllActiveRecords() {
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
    }

    /**
     * Removes a crime record from the set of selected crime records.
     * @param id The id of the CrimeRecord to deselect.
     */
    public void deselectRecord(Integer id) {
        selectedRecords.remove(id);
    }

    /**
     * Removes all crime records from the set of selected crime records.
     */
    public void clearSelection() {
        selectedRecords = new HashSet<>();
    }

    /**
     * Checks if a crime record is selected.
     * @param id The id of the CrimeRecord the selection of which is to be determined.
     * @return True if the CrimeRecord is selected, false if not.
     */
    public boolean isSelected(Integer id) {
        return selectedRecords.contains(id);
    }

    public ArrayList<Filter> getFilters() {
        return filters;
    }

    public HashSet<Integer> getSelectedRecords() {
        return selectedRecords;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public int getCurrentMin() {
        return currentMin;
    }

    public int getCurrentMax() {
        return currentMax;
    }
}
