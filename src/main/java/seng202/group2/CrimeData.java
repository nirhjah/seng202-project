package seng202.group2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * CrimeData class is the main connector and manager of the core CrimeRecords.
 *
 * Any changes (Aside from update, that can be managed directly using CrimeRecord setters)to the core CrimeRecords should be done through here.
 * ActiveData is a subset of crimeRecords stored here.
 *
 * @author Moses Wescombe, Sam Clark
 */
public class CrimeData {

	//Variables:

	/** Incremented counter used to give each instance of crimeRecord a unique identifier */
	private int idCounter = 0;
	
	/** A HashMap storing each CrimeRecord with its 'ID' */
	private final HashMap<Integer, CrimeRecord> crimeRecords = new HashMap<Integer, CrimeRecord>();
	
	/**
	 * The ActiveData instance used by this crimeData instance for filtering crime data
	 * @see ActiveData
	 */
	private ActiveData activeData;


	//Functions:

	/** Increments the idCounter attribute so that each instance of CrimeRecord has a unique ID */
	private void incrementCounter() {
		idCounter++;
	}
	
	/**
	 * Adds an instance of CrimeRecord to the crimeRecords HashMap and increments counter
	 * 
	 * @param crimeRecord - an instance of crimeRecord to add to the crime data.
	 */
	public void addRecord(CrimeRecord crimeRecord) {
		crimeRecord.setID(idCounter); //Set The CrimeRecord's ID variable
		crimeRecords.put(idCounter, crimeRecord);
		incrementCounter();
	}

	/**
	 * Delete record form CrimeData if it exits.
	 *
	 * @param id ID of the CrimeRecord
	 * @return bool, true if CrimeRecords was removed, else false
	 */
	public boolean deleteRecord(int id) {
		if (crimeRecords.containsKey(id)) {
			crimeRecords.remove(id);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Using the given ID getCrimeRecord returns the matched CrimeRecord using the private crimeRecords HashMap.
	 * If there is no CrimeRecord with the given ID, return null
	 *
	 * @param id - The id of the crime record requested.
	 * @return CrimeRecord - The requested CrimerRecord with ID
	 */
	public CrimeRecord getCrimeRecord(int id) {
		if (crimeRecords.containsKey(id)) {
			return crimeRecords.get(id);
		} else {
			return null;
		}
	}
	
	/**
	 * Takes an ArrayList of ids and adds their corresponding crime records to an array list and returns it.
	 * If there is no CrimeRecord with the given ID, null will be added to the array.
	 *
	 * @see CrimeRecord getCrimeRecord(int id)
	 * 
	 * @param ids - an Arraylist of the ids of crimes for with crime records should be returned.
	 * @return an ArrayList of each of the crime records requested in the ArrayList of crime ids.
	 */
	public ArrayList<CrimeRecord> getCrimeRecords(ArrayList<Integer> ids) {
		ArrayList<CrimeRecord> records = new ArrayList<CrimeRecord>();
		for (int id : ids)
		{
			if (crimeRecords.containsKey(id)) {
				records.add(getCrimeRecord(id));
			} else {
				records.add(null);
			}
		}
		return records;
	}

	/**
	 * Get CrimeRecord size
	 * @return Integer -- Number of stored CrimeRecords
	 */
	public int getCrimeRecordCount() {
		return crimeRecords.size();
	}


	//Getters/Setters:
	/** 
	 * Gets the current value of idCounter
	 * @return Integer -- the current idCounter value
	 */
	public int getIdCounter() {
		return idCounter;
	}
}
