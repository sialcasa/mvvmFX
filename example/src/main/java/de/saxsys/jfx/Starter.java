package de.saxsys.jfx;

import java.util.List;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.google.inject.Inject;
import com.google.inject.Module;

import de.saxsys.jfx.exampleapplication.view.maincontainer.MainContainerView;
import de.saxsys.jfx.mvvm.MVVMApplication;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

/**
 * Entry point of the application.
 * 
 * @author sialcasa
 * 
 */
public class Starter extends MVVMApplication {

	// Get the MVVM View Loader
	@Inject
	private ViewLoader viewLoader;

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		final ViewTuple tuple = viewLoader
				.loadViewTuple(MainContainerView.class);
		// Locate View for loaded FXML file
		final Parent view = tuple.getView();

		final Scene scene = new Scene(view);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initGuiceModules(List<Module> modules) throws Exception {

	}

}
