package seng202.group2.view.graphs;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A simple class used to interface setting graphing options between a Graph subtype and the parent class Graph.
 *
 * @author Connor Dunlop
 */
public abstract class GraphOption {

    /**
     * A string identifier of the option.
     * This must be kept unique within the context of the graph.
     * This identifier is used to label the option when the option is visible to the user.
     */
    protected final String NAME;
    protected final Label NAME_LABEL;

    /**
     * Creates a new GraphOption with the provided string identifier.
     * @param name A string identifier for the option. Used to label the option. Must be unique within scope of the Graph.
     */
    GraphOption(String name) {
        this.NAME = name;
        this.NAME_LABEL = new Label(name);
    }

    /** Gets the string identifier of this option. */
    public final String getName() {
        return NAME;
    }

    /**
     * Gets the root node of the option GUI. This root node is used to display the option in the option pane.
     * @return A root node whose children display, and allow the user to interact with, this graph option.
     */
    public abstract Node getRoot();

    /**
     * A flag which indicates whether this option has invalid and valid states,
     * where a valid state is required to plot the graph.
     * False by default.
     */
    protected boolean isRequired = false;

    /**
     * Sets whether this option must be set to be able to plot the graph to which this option applies.
     * @param isRequired True if this option must be set for plotting, false if it does not need to be set.
     */
    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * Checks whether this option has a valid state set for plotting.
     * @return True if this option is in a valid state for plotting, false if the option's state does not meet the requirements for plotting.
     */
    public abstract boolean requirementsMet();
}
