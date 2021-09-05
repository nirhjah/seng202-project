package seng202.group2.controller.viewcontollers;

import seng202.group2.controller.viewcontollers.CamsApplication;

/**
 * This class is used to run the CAMS application GUI.
 * It has only a main function which calls the Application launch function on
 * the CamsApplication class
 *
 * @see CamsApplication
 * @author Sam Clark
 */
public class Main {

    /**
     * The main method for the running of the CAMS application GUI.
     * This method calls the Application launch method of the {@link CamsApplication} class,
     * to initialise the GUI using javaFX.
     *
     * @param args - the arguments to pass the CamsApplication launch method.
     */
    public static void main(String[] args) {
        CamsApplication.main(args);
    }
}
