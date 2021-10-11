package seng202.group2.model.datacategories;

/** A basic interface used to mark DataCategory subtypes as being exportable. */
public interface Exportable extends DataClassification {
    /**
     * Returns the value currently stored by the DataCategory in String format.
     *
     * @return The value currently stored by the DataCategory in String format.
     */
    public String getValueString();
}
