package seng202.group2.view.graphs;

import seng202.group2.model.datacategories.DataCategory;

/**
 * @author Connor Dunlop
 */
public class Field<DataClassification> {
    public final String name;
    private DataCategory dataCategory = null;

    private final Class<DataClassification> classification;

    private Field(String name, Class<DataClassification> classification) {
        this.name = name;
        this.classification = classification;
    }

    public static <DataClassification> Field<DataClassification> newField(String name, Class<DataClassification> classification) {
        return new Field<DataClassification>(name, classification);
    }

    public Class<DataClassification> getDataClassification() {
        return classification;
    }

    public void setDataCategory(DataCategory dataCategory) {
        if (classification.isInstance(dataCategory))
            this.dataCategory = dataCategory;
        else
            throw new IllegalArgumentException(dataCategory.toString() + " is not of the correct classification for field " + name);
    }

    public DataCategory getDataCategory() {
        return this.dataCategory;
    }
}
