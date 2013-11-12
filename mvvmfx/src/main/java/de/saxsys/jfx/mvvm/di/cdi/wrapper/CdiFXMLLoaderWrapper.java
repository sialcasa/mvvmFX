/*******************************************************************************
 * Copyright 2013 Alexander Casall
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
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
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
class CdiFXMLLoaderWrapper implements FXMLLoaderWrapper {
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
	public ViewTuple<? extends ViewModel> load(URL location) throws IOException {
		fxmlLoader.setLocation(location);
		fxmlLoader.load(location.openStream());

		return new ViewTuple<>((View<?>) fxmlLoader.getController(),
				(Parent) fxmlLoader.getRoot());
	}

}
