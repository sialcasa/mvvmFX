package de.saxsys.mvvmfx.examples.welcome;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import de.saxsys.mvvmfx.examples.welcome.view.maincontainer.MainContainerView;
import de.saxsys.mvvmfx.examples.welcome.viewmodel.maincontainer.MainContainerViewModel;
import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;

/**
 * The application entry point.
 *
 * @author manuel.mauky
 */
public class CdiStarter extends MvvmfxCdiApplication {
	
	public static void main(String... args) {
		launch(args);
	}
	
	@Override
	public void startMvvmfx(Stage stage) {
		ViewTuple<MainContainerView, MainContainerViewModel> tuple =
				FluentViewLoader.fxmlView(MainContainerView.class).load();
		
		Parent view = tuple.getView();
		
		final Scene scene = new Scene(view);
		stage.setScene(scene);
		stage.show();
	}
}
