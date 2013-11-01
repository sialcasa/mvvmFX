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

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * CDI-Producer for {@link FXMLLoader} instances.
 * 
 * @author manuel.mauky
 * 
 */
public class CdiFXMLLoaderProducer {

	@Inject
	private Instance<Object> instance;

	/**
	 * Creates an instance of the {@link FXMLLoader} that has a CDI specific
	 * ControllerFactory assigned.
	 */
	@Produces
	public FXMLLoader produceFXMLLoader() {
		final FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> classType) {
				return classType == null ? null : instance.select(classType)
						.get();
			}
		});

		return fxmlLoader;
	}

}
