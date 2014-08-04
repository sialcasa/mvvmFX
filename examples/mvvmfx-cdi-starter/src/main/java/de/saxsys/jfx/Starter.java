package de.saxsys.jfx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;

import de.saxsys.jfx.exampleapplication.view.maincontainer.MainContainerView;
import de.saxsys.jfx.exampleapplication.viewmodel.maincontainer.MainContainerViewModel;
import de.saxsys.jfx.mvvm.cdi.MvvmfxCdiApplication;
import de.saxsys.jfx.mvvm.viewloader.FluentViewLoader;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

/**
 * The application entry point.
 *
 * @author manuel.mauky
 */
public class Starter extends MvvmfxCdiApplication {
	
	public static void main(String... args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		ViewTuple<MainContainerView, MainContainerViewModel> tuple =
				FluentViewLoader.fxmlView(MainContainerView.class).load();
		
		Parent view = tuple.getView();
		
		final Scene scene = new Scene(view);
		stage.setScene(scene);
		stage.show();
	}
}
