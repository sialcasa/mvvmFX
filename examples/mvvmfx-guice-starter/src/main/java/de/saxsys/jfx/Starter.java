package de.saxsys.jfx;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.inject.Inject;

import com.google.inject.Module;

import de.saxsys.jfx.mvvm.di.guice.MvvmGuiceApplication;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;

/**
 * Entry point of the application.
 * 
 * @author sialcasa
 * 
 */
public class Starter extends MvvmGuiceApplication {

	// Get the MVVM View Loader
	@Inject
	private ViewLoader viewLoader;

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		final Scene scene = new Scene(new Pane(), 300, 300);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initGuiceModules(List<Module> modules) throws Exception {

	}

}
