/*******************************************************************************
 * Copyright 2013 Alexander Casall, Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.jfx.mvvm.viewloader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.di.DependencyInjector;

/**
 * This viewLoader is used to load views that are implementing
 * {@link de.saxsys.jfx.mvvm.api.FxmlView}.
 * 
 * @author manuel.mauky
 */
class FxmlViewLoader {

	private static final Logger LOG = LoggerFactory
			.getLogger(FxmlViewLoader.class);

	/**
	 * Load the viewTuple by it`s ViewType.
	 */
	<ViewType extends View<ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadFxmlViewTuple(
			Class<? extends ViewType> viewType, ResourceBundle resourceBundle,
			Object controller, Object root) {
		final String pathToFXML = "/"
				+ viewType.getPackage().getName().replaceAll("\\.", "/") + "/"
				+ viewType.getSimpleName() + ".fxml";

		return loadFxmlViewTuple(pathToFXML, resourceBundle, controller, root);
	}

	/**
	 * Load the viewTuple by the path of the fxml file.
	 */
	<ViewType extends View<ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadFxmlViewTuple(
			final String resource, ResourceBundle resourceBundle,
			final Object controller, final Object root) {
		try {
			final FXMLLoader loader = createFxmlLoader(resource,
					resourceBundle, controller, root);

			loader.load();

			final View loadedController = (View) loader.getController();
			final Parent loadedRoot = (Parent) loader.getRoot();

			if (loadedController == null) {
				LOG.warn("No code behind class (fx:controller) has been set for the following FXML file: "
						+ resource);
			}

			return new ViewTuple(loadedController, loadedRoot);

		} catch (final IOException ex) {
			LOG.error("Error loading FXML :", ex);
			return null;
		}
	}

	private FXMLLoader createFxmlLoader(String resource,
			ResourceBundle resourceBundle, Object controller, Object root)
			throws IOException {

		// Load FXML file
		final URL location = FxmlViewLoader.class.getResource(resource);
		if (location == null) {
			throw new IOException(
					"Error loading FXML - can't load from given resourcepath: "
							+ resource);
		}

		final FXMLLoader fxmlLoader = new FXMLLoader();

		fxmlLoader.setRoot(root);
		fxmlLoader.setResources(resourceBundle);
		fxmlLoader.setLocation(location);
		fxmlLoader.setController(controller);

		fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> aClass) {
				return DependencyInjector.getInstance().getInstanceOf(aClass);
			}
		});

		return fxmlLoader;
	}
}
