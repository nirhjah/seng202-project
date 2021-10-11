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

	/************************************************************************************************************
	 * 																											*
	 * 																											*
	 * 									Collection of DataCategory subtypes.									*
	 * 																											*
	 * 																											*
	 ************************************************************************************************************/

	/**
	 * A set of instances of all DataCategory subtypes found using reflection.
	 * This set is populated when the method getCategories is first called.
	 */
	private static Set<DataCategory> dataCategories = null;

	/**
	 * Gets the set of static DataCategory instances of DataCategory subtypes.
	 *
	 * @return A set of the instances stored by each DataCategory subtype found using reflection.
	 */
	public static final Set<DataCategory> getCategories() {
		if (dataCategories == null)
			lookupCategories();
		return dataCategories;
	}

	/**
	 * Gets the set of static DataCategory instances of DataCategory subtypes that have been given a certain classification.
	 * @param classification The class object of a DataClassification type used to classify DataCategory subtypes.
	 * @return A set of the instances stored by each DataCategory subtype found using reflection.
	 */
	public static final Set<DataCategory> getCategories(Class<? extends DataClassification> classification) {
		Set<DataCategory> subSetCategories = new HashSet<>();
		for (DataCategory dataCategory : getCategories()) {
			if (classification.isInstance(dataCategory))
				subSetCategories.add(dataCategory);
		}
		return subSetCategories;
	}

	/**
	 * Searches for DataCategory subtypes in the datacategories package using reflection.
	 * Returns a set populated with the static instances stored by each DataCategory subtype,
	 * using the getInstance method of each DataCategory to retrieve the stored instance.
	 *
	 * @return A set of instance of DataCategory subtypes found using reflection.
	 */
	private static Set<DataCategory> lookupCategories() {
		Reflections reflections = new Reflections("seng202.group2.model.datacategories");
		Set<Class<? extends DataCategory>> dataCategoryClasses = reflections.getSubTypesOf(DataCategory.class);

		dataCategories = new HashSet<>();
		for (Class<? extends DataCategory> dataCategoryClass : dataCategoryClasses) {
			try {
				DataCategory categoryInstance = (DataCategory) dataCategoryClass.getMethod("getInstance").invoke(null);
				if (categoryInstance == null)
					throw new NullPointerException("Static instance of DataCategory " + dataCategoryClass + " was not initialised.");
				dataCategories.add(categoryInstance);
			} catch (IllegalAccessException e) {
				System.out.println("Could not get instance of DataCategory " + dataCategoryClass);
				System.out.println("The method \"getInstance\" of " + dataCategoryClass + " is not accessible.");
			} catch (InvocationTargetException e) {
				System.out.println("Could not get instance of DataCategory " + dataCategoryClass);
				System.out.println("The method \"getInstance\" of " + dataCategoryClass + " threw an error:");
				System.out.println(e.getCause().toString());
				e.getCause().printStackTrace();
			} catch (NoSuchMethodException e) {
				System.out.println("Could not get instance of DataCategory " + dataCategoryClass);
				System.out.println("The method \"getInstance\" of " + dataCategoryClass + " does not exist.");
			}
		}
		return dataCategories;
	}

	/**
	 * Returns the first DataCategory instance in dataCategories which is known to be represented by the given
	 * identifier string.
	 * The given identifier string is stripped of its whitespace before being tested with each DataCategory subtype
	 * using the matchesString method of the DataCategory subtype.
	 *
	 * @param identifier The string used to represent a DataCategory.
	 * @return The first DataCategory instance found to be represented by the given identifier string.
	 * @throws UnsupportedCategoryException If no DataCategory is known to be represented by the identifier string.
	 */
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

	/************************************************************************************************************
	 * 																											*
	 * 																											*
	 * 									Subtype operations on data.												*
	 * 																											*
	 * 																											*
	 ************************************************************************************************************/

	/**
	 * Sets the attribute of CrimeRecord record, which corresponds to this DataCategory
	 * enumerator to the value stored in data. Type casting is handled within the method.
	 *
	 * @param record The CrimeRecord instance whose attribute value is to be set.
	 * @param data The data to set the CrimeRecord's attribute value to.
	 */
	public abstract void setRecordValue(CrimeRecord record, Object data);

	/**
	 * Gets the value of the attribute of CrimeRecord record, which corresponds to this DataCategory
	 * enumerator. Type casting is handled within the method.
	 *
	 * @param record The CrimeRecord instance whose attribute value is to be retrieved.
	 * @return The value of the CrimeRecord's attribute.
	 */
	public abstract Object getRecordValue(CrimeRecord record);

	/**
	 * Gets the DataCategory of this subtype stored by the specified CrimeRecord.
	 *
	 * @param record The CrimeRecord instance whose DataCategory subtype instance is to be retrieved.
	 * @return The DataCategory subtype instance.
	 */
	public abstract DataCategory getRecordCategory(CrimeRecord record);

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

	/**
	 * Parses a string representing a value of data corresponding to this DataCategory
	 * into whatever type is used by a CrimeRecord to store data corresponding to this DataCategory.
	 *
	 * @param value A value string representing a value of data corresponding to this DataCategory.
	 * @return The value represented by the value string parsed into the type used to store data corresponding to this DataCategory.
	 */
	public abstract Object parseString(String value);


	/************************************************************************************************************
	 * 																											*
	 * 																											*
	 *										Subtype utility methods.											*
	 * 																											*
	 * 																											*
	 ************************************************************************************************************/

	/**
	 * Returns the SQL string format of the category
	 *
	 * @return SQL String
	 */
	public abstract String getSQL();

	/**
	 * Returns the value currently stored by the DataCategory in String format.
	 *
	 * @return The value currently stored by the DataCategory in String format.
	 */
	public abstract String getValueString();

	/**
	 * Returns true if the DataCategory is known to be represented by the given identifier string, false otherwise.
	 *
	 * @param identifier The string used to represent a DataCategory.
	 * @return true if the DataCategory is known to be represented by the given identifier string, false otherwise.
	 */
	public abstract boolean matchesString(String identifier);

	/**
	 * Returns the Class object of the type used to store a piece of data within a DataCategory instance.
	 * @return The Class object of the type used to store a piece of data within a DataCategory instance.
	 */
	public abstract Class<? extends Object> getValueType();
}
