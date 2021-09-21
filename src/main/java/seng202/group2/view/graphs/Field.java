package seng202.group2.view.graphs;

/**
 * @author Connor Dunlop
 */
public class Field<DataClassification> {
    public final String name;
    private DataClassification dataCategory = null;

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

    public void setDataCategory(DataClassification dataCategory) {
        this.dataCategory = dataCategory;
    }

    public DataClassification getDataCategory() {
        return this.dataCategory;
    }
}
