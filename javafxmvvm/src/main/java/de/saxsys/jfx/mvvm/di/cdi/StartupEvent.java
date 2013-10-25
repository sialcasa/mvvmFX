package de.saxsys.jfx.mvvm.di.cdi;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * CDI event class that is used at applications startup. This event 
 * will be fired when the javafx application is ready. 
 * 
 * @author manuel.mauky
 *
 */
public class StartupEvent {
	
	private final Stage primaryStage;
	
	private final Application.Parameters parameters;

	public StartupEvent(Stage primaryStage, Application.Parameters parameters){
		this.primaryStage = primaryStage;
		this.parameters = parameters;
	}

	/**
	 * @return the primary javafx {@link Stage}.
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * @return the parameters from the command line.
	 */
	public Application.Parameters getParameters() {
		return parameters;
	}
	
}
