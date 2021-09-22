package seng202.group2.view.graphs;

import seng202.group2.model.datacategories.DataCategory;

/**
 * This class is used to specify data fields for graphing, which may take on a series of data that is of a certain
 * data classification (Categorical, Numerical, ...).
 *
 * A field is constructed using the newField method, passing a string identifier (must be unique within the scope of the Field),
 * and the Class object of the DataClassification type used to restrict the DataCategory subtypes which the field may be bound to
 * to only those subtypes which match the given DataClassification.
 *
 * A field is initially not bound to any DataCategory, (it is bound to null), but may be bound to any DataCategory which
 * matches the given DataClassification.
 *
 * @author Connor Dunlop
 */
public class Field<DataClassification> {

    /**
     * A string identifier used to select fields within a given context.
     * This identifier should be unique within that context.
     */
    public final String name;

    /**
     * An instance of the DataCategory subtype bound to this field.
     * When binding a field to a DataCategory, it is checked that the DataCategory
     * is a subtype of DataClassification.
     * Note that the DataCategory this field is bound to is null until first bound.
     */
    private DataCategory dataCategory = null;

    /**
     * The Class object of the DataClassification type which this field supports.
     * This is used to restrict the DataCategory subtypes which this field can be bound to,
     * to those categories which are of a certain classification (Categorical, Numerical, ...).
     */
    private final Class<DataClassification> classification;

    /**
     * Creates a new Field with the given string identifier, and sets the classification of DataCategory subtypes
     * which this field may be bound to.
     * This constructor has been made private to enforce the use of the newField method below, which reduces boilerplate.
     *
     * @param name A String identifier used to select and represent the field.
     * @param classification An interface or superclass used to specify a DataCategory of being a particular classification of data (Categorical, Numerical, ...).
     */
    private Field(String name, Class<DataClassification> classification) {
        this.name = name;
        this.classification = classification;
    }

    /**
     * Creates a new Field with the given string identifier, and sets the classification of DataCategory subtypes
     * which this field may be bound to.
     * This method is to be used in place of calling the Field constructor directly, as it reduces boilerplate.
     *
     * @param name A String identifier used to select and represent the field.
     * @param classification The Class object of the DataClassification type used to restrict binding to DataCategory types.
     * @param <DataClassification> The DataClassification type used to restrict binding to DataCategory types.
     * @return A new Field object with the given string identifier which accepts DataCategory subtypes that inherit from the DataClassification type.
     */
    public static <DataClassification> Field<DataClassification> newField(String name, Class<DataClassification> classification) {
        return new Field<DataClassification>(name, classification);
    }

    /**
     * Gets the DataClassification Class object which determines which classification of DataCategory types are
     * accepted by this Field.
     *
     * @return The DataClassification Class object which classifies DataCategory types accepted by this field.
     */
    public Class<DataClassification> getDataClassification() {
        return classification;
    }

    /**
     * Binds this field to the given DataCategory subtype.
     * This DataCategory subtype is checked against the DataClassification of this field, to ensure the given
     * DataCategory is of the correct classification for this field.
     * If the provided DataCategory is not of the correct classification, then an IllegalArgumentException is thrown.
     *
     * @param dataCategory A DataCategory subtype to bind this field to.
     */
    public void setDataCategory(DataCategory dataCategory) {
        if (classification.isInstance(dataCategory))
            this.dataCategory = dataCategory;
        else
            throw new IllegalArgumentException(dataCategory.toString() + " is not of the correct classification for field " + name);
    }

    /**
     * Gets the DataCategory to which this field is bound.
     * Note that if this field has not yet been bound to any DataCategory, the return value is null.
     *
     * @return The DataCategory to which this field is bound.
     */
    public DataCategory getDataCategory() {
        return this.dataCategory;
    }
}
