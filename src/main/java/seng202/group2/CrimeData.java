package seng202.group2;

import java.util.ArrayList;

public class CrimeData {

	/**
	 * incremented counter used to give each instance of crimeRecord a unique identifier
	 */
	private int idCounter;
	
	/**
	 * An arraylist storing each crime record
	 */
	private ArrayList<CrimeRecord> crimeRecords;
	
	/**
	 * The active data instance used by this crimeData instance for filtering crime data
	 * 
	 * TODO implement ActiveData class to avoid this error
	 */
	private ActiveData activeData;
	
	/**
	 * Increments the idCounter attribute so that each instance of CrimeRecord has a unique ID
	 * 
	 * TODO ask why this method returns an int on the class diagram
	 */
	public void incrementCounter() {
		idCounter++;
	}
	
	/**
	 * Adds an instance of CrimeRecord to the crimeRecords arraylist to store.
	 * 
	 * @param crimeRecord - an instance of crimeRecord to add to the crime data.
	 */
	public void addRecord(CrimeRecord crimeRecord) {
		crimeRecords.add(crimeRecord);
	}
	
	/**
	 * TODO find out how this is to be implemented, should crimeRecords arraylist be a hashmap or do deleted files not get removed from the arraylist etc
	 * TODO After finding out how this works I need to add the Javadoc for it
	 * 
	 * @param id - The id of the crime record requested.
	 * @return the crime record requested by giving the method the crime records id.
	 */
	public CrimeRecord getCrimeRecord(int id) {
		
	}
	
	/**
	 * Takes an ArrayList of ids and adds their corresponding crime records to an array list and returns it.
	 * TODO note to self (and Moses) this requires the getCrimeRecord method to work.
	 * @see getCrimeRecord 
	 * 
	 * @param ids - an Arraylist of the ids of crimes for with crime records should be returned.
	 * @return an arraylist of each of the crime records requested in the arraylist of crime ids.
	 */
	public ArrayList<CrimeRecord> getCrimeRecords(ArrayList<Integer> ids) {
		ArrayList<CrimeRecord> records = new ArrayList<CrimeRecord>();
		for (int id : ids)
		{
			records.add(this.getCrimeRecord(id));
		}
		return records;
	}
	
	/**
	 * TODO this method has no int param in the class diagram, check how deletion works for this and getCrimeRecord methods
	 * @param id
	 */
	public void deleteRecord(int id) {
		
	}
	
	/**
	 * TODO Again I have no idea how this all works. Thanks Moses.
	 * TODO this method also requires the DataCategory enumeration to work.
	 * @return
	 */
	public ArrayList<DataCategory> getCategories() {
		
	}
}
