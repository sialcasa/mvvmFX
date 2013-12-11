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
package de.saxsys.jfx.mvvm.di;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

/**
 * This is a wrapper for javaFX`s {@link FXMLLoader}. It is used to load
 * a {@link ViewTuple} for a fxml file.
 */
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
