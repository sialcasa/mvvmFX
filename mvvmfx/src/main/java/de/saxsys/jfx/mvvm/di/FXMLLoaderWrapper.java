package de.saxsys.jfx.mvvm.di;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

public class FXMLLoaderWrapper {

	/**
	 * @see FXMLLoader#load(URL)
	 */
	public ViewTuple<? extends ViewModel> load(URL location) throws IOException {
		FXMLLoader fxmlLoader = createFxmlLoader(location, null);
		return new ViewTuple<>((View<?>) fxmlLoader.getController(),
				(Parent) fxmlLoader.getRoot());
	}

	/**
	 * @see FXMLLoader#load(URL, ResourceBundle)
	 */
	public ViewTuple<? extends ViewModel> load(URL location,
			ResourceBundle resourceBundle) throws IOException {
		FXMLLoader fxmlLoader = createFxmlLoader(location, resourceBundle);
		return new ViewTuple<>((View<?>) fxmlLoader.getController(),
				(Parent) fxmlLoader.getRoot());
	}

	private FXMLLoader createFxmlLoader(URL location,
			ResourceBundle resourceBundle) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		DependencyInjector injectionFacade = DependencyInjector.getInstance();

		if (injectionFacade.isCustomInjectorDefined()) {
			fxmlLoader
					.setControllerFactory(injectionFacade.getCustomInjector());
		}

		fxmlLoader.setResources(resourceBundle);
		fxmlLoader.setLocation(location);
		fxmlLoader.load(location.openStream());
		return fxmlLoader;
	}

}
