package de.saxsys.jfx.mvvm.di.guice;

import java.io.IOException;
import java.net.URL;

import javafx.scene.Parent;

import javax.inject.Inject;

import com.cathive.fx.guice.GuiceFXMLLoader;
import com.cathive.fx.guice.GuiceFXMLLoader.Result;

import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.di.FXMLLoaderWrapper;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

/**
 * Guice specific implementation of the {@link FXMLLoaderWrapper}.
 * 
 * This class uses the {@link GuiceFXMLLoader} that is provided by the fx-guice
 * framework.
 * 
 * @author manuel.mauky
 * 
 */
public class GuiceFXMLLoaderWrapper implements FXMLLoaderWrapper {

	@Inject
	private GuiceFXMLLoader fxmlLoader;

	@Override
	public ViewTuple load(URL location) throws IOException {
		Result result = fxmlLoader.load(location);

		return new ViewTuple((View<?>) result.getController(),
				(Parent) result.getRoot());
	}

}
