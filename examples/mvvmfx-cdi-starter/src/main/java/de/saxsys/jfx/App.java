package de.saxsys.jfx;

import de.saxsys.jfx.exampleapplication.view.maincontainer.MainContainerView;
import de.saxsys.jfx.exampleapplication.viewmodel.maincontainer.MainContainerViewModel;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;

import de.saxsys.jfx.mvvm.viewloader.ViewLoader;

/**
 * This class is invoked by the CDI container with the {@link StartupEvent}. It
 * contains the JavaFX specific code to start the application.
 * 
 * @author manuel.mauky
 * 
 */
public class App{

	// Get the MVVM View Loader
	@Inject
	private ViewLoader viewLoader;

	public void startApplication(Stage stage) {
        final ViewTuple<MainContainerViewModel> tuple = viewLoader
                .loadViewTuple(MainContainerView.class);

        Parent view = tuple.getView();

        final Scene scene = new Scene(view);
		stage.setScene(scene);
		stage.show();
	}

}
