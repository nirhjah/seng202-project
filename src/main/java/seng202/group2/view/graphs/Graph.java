package seng202.group2.view.graphs;

import org.reflections.Reflections;

import java.util.Set;

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
    private static Set<Class<? extends Graph>> lookupGraphTypes() {
        System.out.println("Scanning for Graph subtypes.");

        Reflections reflections = new Reflections("seng202.group2.view.graphs");
        Set<Class<? extends Graph>> graphClasses = reflections.getSubTypesOf(Graph.class);

        System.out.println("Found Graph subtypes: " + graphClasses);
        return graphClasses;
    }

}
