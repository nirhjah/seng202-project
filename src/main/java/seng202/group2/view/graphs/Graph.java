package seng202.group2.view.graphs;

import javafx.scene.layout.BorderPane;
import org.reflections.Reflections;
import seng202.group2.model.datacategories.DataClassification;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 *
 * @author Connor Dunlop
 */
public abstract class Graph {

    /**
     * A set of all graph subtype classes found using reflection.
     * This set is populated when the method getGraphTypes is first called.
     */
    private static Set<Class<? extends Graph>> graphTypes = null;

    /**
     * Gets a set of the Class objects of each Graph subtype.
     *
     * @return A set of the Class objects of each Graph subtype found using reflection.
     */
    public static final Set<Class<? extends Graph>> getGraphTypes() {
        if (graphTypes == null)
            lookupGraphTypes();
        return graphTypes;
    }

    /**
     * Searches for Graph subtypes in the graphs package using reflection.
     * Returns a set populated with the Class object of each Graph subtype.
     *
     * @return A set of the Class objects of each Graph subtype.
     */
    private static void lookupGraphTypes() {
        System.out.println("Scanning for Graph subtypes.");

        Reflections reflections = new Reflections("seng202.group2.view.graphs");
        graphTypes = reflections.getSubTypesOf(Graph.class);

        System.out.println("Found Graph subtypes: " + graphTypes);
    }

    /**
     * Returns a new instance of the given graph type.
     *
     * @param graphType The Class object of a particular graph type.
     * @return A new instance of the given graph type.
     * @throws InstantiationException If an instance of the given graph type could not be created.
     */
    public static Graph newGraph(Class<? extends Graph> graphType) throws InstantiationException {
        try {
            Constructor<?> graphConstructor = graphType.getConstructor();
            return (Graph) graphConstructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new InstantiationException("The graph type " + graphType + " could not be instantiated using a constructor of the form GraphType()");
        }
    }

    /**
     * Sets up the graph and sets it as the center of the provided pane.
     * @param borderPane The pane to add the graph to.
     */
    public abstract void initialize(BorderPane borderPane);

    /**
     * Gets a set of Field objects specifying the graph's supported fields.
     * @return The graph's supported fields.
     */
    public abstract Set<Field<? extends DataClassification>> getFields();

    /**
     * Generates the graph using the stored settings.
     */
    public abstract void plotGraph();
}
