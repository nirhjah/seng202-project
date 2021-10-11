package seng202.group2.controller;

import seng202.group2.view.CamsApplication;
import seng202.group2.model.DBMS;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;

/**
 * Program Entry
 */
public class ApplicationEnvironment {
	//Enable logging
	private static final boolean logMessages = true;

	/**
	 * Program Entry, startup
	 * @param args Extra start parameters
	 */
	public static void main(String[] args) throws IOException {
		if (logMessages) setSystemToLog();
		DBMS.clearDB();
		CamsApplication.main(args);
	}
	
	/**
	 * This sets up the system to log things in generated log files instead of in the console.
	 */
    private static void setSystemToLog() {
    	try {
    		//This gets a unique file name for the log and creates the log file
    		LocalDateTime currentTime = java.time.LocalDateTime.now();
    		File outputFile = new File("./logs/" + String.valueOf(currentTime).replaceAll(":", "-") + ".txt");
    		outputFile.createNewFile();
    		
    		//This sets the output streams to the log file.
    		PrintStream logOutput = new PrintStream(outputFile);
    		System.setOut(logOutput);
    		System.setErr(logOutput);
    		System.out.println("System log of CAMS application for session (" + currentTime + ") :\n");
    	} catch(Exception e) {
    		System.out.println("Your current session is not being logged for the following reason(s):\n");
    		e.printStackTrace();
    	}
    }
}
