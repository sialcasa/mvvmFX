package de.saxsys.jfx;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import de.saxsys.jfx.mvvm.di.cdi.StartupEvent;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;

/**
 * This class is invoked by the CDI container with the {@link StartupEvent}. It
 * contains the JavaFX specific code to start the application.
 * 
 * @author manuel.mauky
 * 
 */
public class App {

	// Get the MVVM View Loader
	@Inject
	private ViewLoader viewLoader;

	/**
	 * Listen for the {@link StartupEvent} and create the main scene for the
	 * application.
	 */
	public void startApplication(@Observes StartupEvent startupEvent) {
		Stage stage = startupEvent.getPrimaryStage();
		// Locate View for loaded FXML file

		final Scene scene = new Scene(new Pane(), 300, 300);
		stage.setScene(scene);
		stage.show();
	}

}
