package de.saxsys.jfx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import de.saxsys.jfx.exampleapplication.view.maincontainer.MainContainerView;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import de.saxsys.jfx.mvvm.di.cdi.StartupEvent;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

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

		final ViewTuple<ViewModel> tuple = viewLoader
				.loadViewTuple(MainContainerView.class);
		// Locate View for loaded FXML file
		final Parent view = tuple.getView();

		final Scene scene = new Scene(view);
		stage.setScene(scene);
		stage.show();
	}

}
