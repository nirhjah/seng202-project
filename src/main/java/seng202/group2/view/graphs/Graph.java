package seng202.group2.view.graphs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.reflections.Reflections;
import seng202.group2.view.CamsApplication;
import seng202.group2.view.GraphController;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A superclass specifying all methods which a Graph type must implement in order to interface with control classes.
 * Some utility methods are provided for getting a set of the Class objects of all Graph subtypes within this package,
 * using reflection.
 *
 * @author Connor Dunlop
 */
public abstract class Graph {

    public abstract String getName();

    /*************************************************************************************************************
     *                                   Dynamic Graph Type Collection                                           *
     *************************************************************************************************************/

    /**
     * A set of all graph subtype instances, from classes found using reflection.
     * This set is populated when the method getGraphTypes is first called.
     */
    private static Set<Graph> graphTypes = null;

    /**
     * Gets a set of the Class objects of each Graph subtype.
     *
     * @return A set of instances of each Graph subtype found using reflection.
     */
    public static final Set<Graph> getGraphTypes() {
        if (graphTypes == null)
            lookupGraphTypes();
        return graphTypes;
    }

    /**
     * Searches for Graph subtypes in the graphs package using reflection.
     * Adds an instance of each Graph subtype to the graphTypes set.
     */
    private static void lookupGraphTypes() {
        Reflections reflections = new Reflections("seng202.group2.view.graphs");
        Set<Class<? extends Graph>> graphTypeClasses = reflections.getSubTypesOf(Graph.class);

        graphTypes = new HashSet<>();
        for (Class<? extends Graph> graphClass : graphTypeClasses) {
            try {
                Constructor<?> graphConstructor = graphClass.getConstructor();
                Graph graphInstance = (Graph) graphConstructor.newInstance();
                if (graphInstance == null)
                    throw new NullPointerException("Static instance of Graph " + graphClass + " was not initialised.");
                graphTypes.add(graphInstance);
            } catch (IllegalAccessException e) {
                System.out.println("Could not get instance of Graph " + graphClass);
                System.out.println("The default constructor of " + graphClass + " is not accessible.");
            } catch (InvocationTargetException e) {
                System.out.println("Could not get instance of Graph " + graphClass);
                System.out.println("The default constructor of " + graphClass + " threw an error:");
                System.out.println(e.getCause().toString());
                e.getCause().printStackTrace();
            } catch (NoSuchMethodException e) {
                System.out.println("Could not get instance of Graph " + graphClass);
                System.out.println("The default constructor of " + graphClass + " does not exist.");
            } catch (InstantiationException e) {
                System.out.println("Could not get instance of Graph " + graphClass);
                System.out.println("The graph class " + graphClass + " could not be instantiated.");
            }
        }
    }

    /**
     * Returns a new instance of the given graph type.
     *
     * @param graphType An instance of a particular graph type.
     * @return A new instance of the given graph type.
     * @throws InstantiationException If an instance of the given graph type could not be created.
     */
    public static Graph newGraph(Graph graphType) throws InstantiationException {
        return newGraph(graphType.getClass());
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

    /*************************************************************************************************************
     *                                   Graph Option Interfacing                                                *
     *************************************************************************************************************/

    /** A node used to display graph options/settings to the user. */
    private VBox optionList;
    /** A map of option names to their option objects. */
    private LinkedHashMap<String, GraphOption> options = new LinkedHashMap<>();

    /**
     * Adds an option to the option pane.
     * The root node produced by the GraphOption is added to the option list.
     * @param option A GraphOption to add to the option pane.
     */
    public void addOption(GraphOption option) {
        if (option == null)
            throw new IllegalArgumentException("Cannot add a null GraphOption.");
        if (options.containsKey(option.getName()))
            throw new IllegalArgumentException("Tried to add a GraphOption with name " + option.getName() + ", but a GraphOption with that name already exists.");

        options.put(option.getName(), option);
        optionList.getChildren().add(option.getRoot());
    }

    /**
     * Adds a collection of options, in the order they are passed, to the option pane.
     * The root node produced by each GraphOption is added to the option list.
     * @param options A collection of GraphOptions to add to the option pane.
     */
    public void addOptions(GraphOption... options) {
        for (GraphOption graphOption : options) {
            addOption(graphOption);
        }
    }

    /**
     * Returns the GraphOption associated with the given name/identifier currently displayed in the options pane.
     * Returns null if no GraphOption is associated with the given name.
     *
     * @param name The name/string identifier of the GraphOption to retrieve.
     * @return The GraphOption associated with the given name/identifier.
     */
    public GraphOption getOption(String name) {
        return options.get(name);
    }

    /**
     * Clears all graph options from the options pane.
     */
    public void clearOptions() {
        optionList.getChildren().clear();
        options.clear();
    }

    /**
     * Checks that all GraphOptions are in a valid state for plotting.
     * I.e. checks that all required options are in a valid configuration.
     * @return False if any options are in an invalid state for plotting, true if all options are in a valid state.
     */
    public boolean optionStatesValid() {
        for (GraphOption option : options.values()) {
            if (!option.requirementsMet())
                return false;
        }
        return true;
    }

    /**
     * Returns a set of all GraphOptions which are not in a valid state for plotting.
     * @return A set of all GraphOptions which are not in a valid state for plotting.
     */
    public Set<GraphOption> getInvalidOptions() {
        LinkedHashSet<GraphOption> invalidOptions = new LinkedHashSet<>();
        for (GraphOption option : options.values()) {
            if (!option.requirementsMet())
                invalidOptions.add(option);
        }
        return invalidOptions;
    }

    /**
     * Creates a dialogue box indicating that not all options are in a valid state for plotting.
     */
    public void displayInvalidOptionsDialogue() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CamsApplication.class.getClassLoader().getResource("graph-settings-dialog.fxml"));
            Parent root = fxmlLoader.load();
            GraphConfigurationDialogController dialogController = fxmlLoader.getController();

            dialogController.addInvalidOptions(getInvalidOptions());

            Stage stage = new Stage();

            // Prevent use of the application while dialogue is open.
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            stage.setTitle("Error: Invalid Graph Configuration");
            stage.setScene(new Scene(root, 400, 300));

            stage.show();
        } catch (IOException e) {
            // This is where you would enter the error handling code, for now just print the stacktrace
            e.printStackTrace();
        }
    }

    /*************************************************************************************************************
     *                                    Graph plotting.                                                        *
     *************************************************************************************************************/

    /** A node used to display a chart. */
    private BorderPane graphPane;

    /**
     * Adds a chart to the graph display pane.
     * @param chart The graph to display.
     */
    protected void setChart(Chart chart) {
        chart.setAnimated(false);
        chart.setLegendVisible(false);
        this.graphPane.setCenter(chart);
    }

    /**
     * Sets up the graph and sets it as the center of the provided pane.
     * @param graphPane The pane to add the graph to.
     */
    public void initialize(BorderPane graphPane, VBox optionList) {
        this.graphPane = graphPane;
        this.optionList = optionList;
    }

    /**
     * Generates the graph using the stored settings.
     */
    public abstract void plotGraph();

}
