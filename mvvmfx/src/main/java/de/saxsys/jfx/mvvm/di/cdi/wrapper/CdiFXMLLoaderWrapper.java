package de.saxsys.jfx.mvvm.di.cdi.wrapper;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.di.FXMLLoaderWrapper;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

/**
 * CDI specific implementation of {@link FXMLLoaderWrapper}. It uses an CDI
 * enabled FXMLLoader to load fxml content and create and inject controller
 * classes.
 * 
 * @author manuel.mauky
 * 
 */
public class CdiFXMLLoaderWrapper implements FXMLLoaderWrapper {
	@Inject
	private Instance<Object> instance;

	private FXMLLoader fxmlLoader;

	/**
	 * Creates an instance of the {@link FXMLLoader} that has a CDI specific
	 * ControllerFactory assigned.
	 */
	@PostConstruct
	void createFxmlLoader() {
		fxmlLoader = new FXMLLoader();
		fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> classType) {
				return classType == null ? null : instance.select(classType)
						.get();
			}
		});
	}

	@Override
	public ViewTuple load(URL location) throws IOException {
		fxmlLoader.setLocation(location);
		fxmlLoader.load(location.openStream());

		return new ViewTuple((View<?>) fxmlLoader.getController(),
				(Parent) fxmlLoader.getRoot());
	}

}
