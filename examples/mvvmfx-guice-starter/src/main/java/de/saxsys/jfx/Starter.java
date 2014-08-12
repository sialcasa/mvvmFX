package de.saxsys.jfx;

import java.util.List;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.google.inject.Module;

import de.saxsys.jfx.exampleapplication.view.maincontainer.MainContainerView;
import de.saxsys.jfx.exampleapplication.viewmodel.maincontainer.MainContainerViewModel;
import de.saxsys.mvvmfx.guice.MvvmfxGuiceApplication;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;

/**
 * Entry point of the application.
 *
 * @author sialcasa
 */
public class Starter extends MvvmfxGuiceApplication {
	
	public static void main(final String[] args) {
		launch(args);
	}
	
	@Override
	public void startMvvmfx(final Stage stage) throws Exception {
		ViewTuple<MainContainerView, MainContainerViewModel> tuple =
				FluentViewLoader.fxmlView(MainContainerView.class).load();
		
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
