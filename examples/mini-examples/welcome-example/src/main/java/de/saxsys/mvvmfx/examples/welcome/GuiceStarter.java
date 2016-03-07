package de.saxsys.mvvmfx.examples.welcome;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import de.saxsys.mvvmfx.examples.welcome.view.maincontainer.MainContainerView;
import de.saxsys.mvvmfx.examples.welcome.viewmodel.maincontainer.MainContainerViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.guice.MvvmfxGuiceApplication;

/**
 * Entry point of the application.
 *
 * @author sialcasa
 */
public class GuiceStarter extends MvvmfxGuiceApplication {

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void startMvvmfx(final Stage stage) throws Exception {
		final ViewTuple<MainContainerView, MainContainerViewModel> tuple
				= FluentViewLoader.fxmlView(MainContainerView.class).load();

		// Locate View for loaded FXML file
		final Parent view = tuple.getView();

		final Scene scene = new Scene(view);
		stage.setScene(scene);
		stage.show();
	}

}
