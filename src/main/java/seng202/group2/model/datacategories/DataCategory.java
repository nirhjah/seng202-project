package seng202.group2.model.datacategories;

import org.reflections.Reflections;
import seng202.group2.model.CrimeRecord;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

/**
 * This class facilitates the selection of the various categories within a CrimeRecord,
 * providing the methods setCategoryValue and getCategoryValue for setting and getting the value
 * of an attribute of a CrimeRecord instance through the corresponding DataCategory.
 * 
 * @author Connor Dunlop
 *
 */
public abstract class DataCategory {

	public static final Set<DataCategory> getCategories() {
		return dataCategories;
	}

	private static Set<DataCategory> dataCategories = new HashSet<>();

	static {
		Reflections reflections = new Reflections("seng202.group2");
		Set<Class<? extends DataCategory>> dataCategoryClasses = reflections.getSubTypesOf(DataCategory.class);

		for (Class<? extends DataCategory> dataCategoryClass : dataCategoryClasses) {
			try {
				DataCategory categoryInstance = (DataCategory) dataCategoryClass.getMethod("getInstance").invoke(null);
				dataCategories.add(categoryInstance);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sets the attribute of CrimeRecord record, which corresponds to this DataCategory
	 * enumerator to the value stored in data. Type casting is handled within the method. 
	 * @param record The CrimeRecord instance whose attribute value is to be set.
	 * @param data The data to set the CrimeRecord's attribute value to.
	 * @throws UnsupportedCategoryException 
	 */
	public abstract void setRecordValue(CrimeRecord record, Object data) throws UnsupportedCategoryException;
	
	/**
	 * Gets the value of the attribute of CrimeRecord record, which corresponds to this DataCategory
	 * enumerator. Type casting is handled within the method.
	 * @param record The CrimeRecord instance whose attribute value is to be retrieved.
	 * @return The value of the CrimeRecord's attribute.
	 * @throws UnsupportedCategoryException 
	 */
	public abstract Object getRecordValue(CrimeRecord record) throws UnsupportedCategoryException;
	
	/**
	 * Parses a string representing a value of data corresponding to this DataCategory
	 * into whatever type is used by a CrimeRecord to store data corresponding to this DataCategory.
	 * @param value A value string representing a value of data corresponding to this DataCategory.
	 * @return The value represented by the value string parsed into the type used to store data corresponding to this DataCategory.
	 * @throws UnsupportedCategoryException 
	 */
	public abstract Object parseString(String value) throws UnsupportedCategoryException;
	
	/**
	 * Returns the SQL string format of the category
	 *
	 * @return SQL String
	 */
	public abstract String getSQL();

	/**
	 * Returns true if the DataCategory is known to be represented by the given identifier string, false otherwise.
	 * @param identifier The string used to represent a DataCategory.
	 * @return true if the DataCategory is known to be represented by the given identifier string, false otherwise.
	 */
	public abstract boolean matchesString(String identifier);
	
	public static DataCategory getCategoryFromString(String identifier) throws UnsupportedCategoryException {
		if (identifier == null)
			throw new IllegalArgumentException("No category string specified.");

		String noWhitespaceIdentifier = identifier.replaceAll("\\s", "");

		for (DataCategory category : getCategories()) {
			if (category.matchesString(noWhitespaceIdentifier))
				return category;
		}

		throw new UnsupportedCategoryException("The string \"" + identifier + "\" could not be resolved to a DataCategory class.");
	}
	
	/**
	 * Sets the value of the data stored within this DataCategory.
	 * @param value The value of data to be stored in this category.
	 */
	public abstract void setValue(Object value);
	
	/**
	 * Gets the value of the data stored within this DataCategory.
	 * @return The value of the data stored in this category.
	 */
	public abstract Object getValue();
}
