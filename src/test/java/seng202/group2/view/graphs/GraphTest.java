package seng202.group2.view.graphs;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Graph superclass.
 *
 * @author Connor Dunlop
 */
class GraphTest {

    /** A custom graph implementation to enable testing of Graph class directly. */
    private static Graph testGraph = new Graph() {
        @Override
        public String getName() {
            return "Test Graph";
        }

        @Override
        public void plotGraph() {
            return;
        }
    };

    /** To create JFX components JFX must be initialized. */
    @BeforeAll
    static void initializeJavaFXEnvironment() {
        JFXPanel p = new JFXPanel();
    }

    /** Clear test graph's options list after every test. */
    @AfterEach
    void clearTestGraphOptions() {
        testGraph.clearOptions();
    }

    /** Test the reflexive discovery of different graph types. */
    @Test
    void reflexiveGraphTypeTest() {
        Set<Class<? extends Graph>> graphClasses = new HashSet<>();
        for (Graph graphType : Graph.getGraphTypes()) {
            graphClasses.add(graphType.getClass());
        }

        assertTrue(graphClasses.contains(BarGraph.class));
        assertTrue(graphClasses.contains(ScatterGraph.class));
        assertTrue(graphClasses.contains(LineGraph.class));
        assertTrue(graphClasses.contains(TimeSeriesGraph.class));
        assertTrue(graphClasses.contains(CrimesOverTimeGraph.class));
    }

    /** Test instantiation of a new Graph instance using an existing instance. */
    @Test
    void graphInstantiationFromClassTest() throws InstantiationException {
        for (Graph oldGraph : Graph.getGraphTypes()) {
            Graph newGraph = Graph.newGraph(oldGraph.getClass());

            assertEquals(oldGraph.getClass(), newGraph.getClass());
            assertNotSame(oldGraph, newGraph);
        }
    }

    /** Test instantiation of a new Graph instance through the class object of a Graph subtype. */
    @Test
    void graphInstantiationFromObjectTest() throws InstantiationException {
        for (Graph oldGraph : Graph.getGraphTypes()) {
            Graph newGraph = Graph.newGraph(oldGraph);

            assertEquals(oldGraph.getClass(), newGraph.getClass());
            assertNotSame(oldGraph, newGraph);
        }
    }

    /** Simple test for adding GraphOptions to the graph. */
    @Test
    void optionAddGetTest() {
        BooleanGraphOption option = new BooleanGraphOption("Option 1");

        testGraph.addOption(option);
        assertSame(option, testGraph.getOption(option.getName()));
    }

    /** Testing adding multiple GraphOptions to the graph. */
    @Test
    void optionAddGetMultipleTest() {
        BooleanGraphOption option1 = new BooleanGraphOption("Option 1");
        SelectionGraphOption<String> option2 = new SelectionGraphOption<>("Option 2");
        NumericGraphOption option3 = new NumericGraphOption("Option 3");

        testGraph.addOptions(option1, option2, option3);
        assertSame(option1, testGraph.getOption(option1.getName()));
        assertSame(option2, testGraph.getOption(option2.getName()));
        assertSame(option3, testGraph.getOption(option3.getName()));
    }

    /** Ensuring correct behaviour when trying to add a null GraphOption to the graph. */
    @Test
    void optionAddNullTest() {
        assertThrows(IllegalArgumentException.class, () -> testGraph.addOption(null));
    }

    /**
     * Testing of methods used to determine whether all GraphOptions are in a state ready for plotting,
     * and for retrieving any GraphOptions which are in an invalid state.
     */
    @Test
    void invalidOptionsTest() {
        BooleanGraphOption option1 = new BooleanGraphOption("Option 1");
        SelectionGraphOption<String> option2 = new SelectionGraphOption<>("Option 2");
        SelectionGraphOption<String> option3 = new SelectionGraphOption<>("Option 3", true);

        testGraph.addOptions(option1, option2);
        assertTrue(testGraph.optionStatesValid());

        testGraph.addOption(option3);
        assertFalse(testGraph.optionStatesValid());

        assertEquals(1, testGraph.getInvalidOptions().size());
        assertTrue(testGraph.getInvalidOptions().contains(option3));
    }

}