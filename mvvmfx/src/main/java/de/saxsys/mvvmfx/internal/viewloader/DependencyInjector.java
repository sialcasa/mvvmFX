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
package de.saxsys.mvvmfx.internal.viewloader;

import javafx.util.Callback;

/**
 * This class handles the dependency injection for the mvvmFX framework.
 *
 * The main reason for this class is to make it possible for the user to use her own dependency injection
 * mechanism/framework. The user can define how instances should be retrieved by setting an callback that returns an
 * instance for a given class type (see {@link DependencyInjector#setCustomInjector}.
 *
 * @author manuel.mauky
 */
public class DependencyInjector {
	
	private Callback<Class<?>, Object> customInjector;
	
	private static DependencyInjector singleton = new DependencyInjector();
	
	DependencyInjector() {
	}
	
	public static DependencyInjector getInstance() {
		return singleton;
	}
	
	
	/**
	 * Define a custom injector that is used to retrieve instances. This can be used as a bridge to you dependency
	 * injection framework.
	 *
	 * The callback has to return an instance for the given class type. This is same way as it is done in the
	 * {@link javafx.fxml.FXMLLoader#setControllerFactory(javafx.util.Callback)} method.
	 *
	 * @param callback
	 *            the callback that returns instances of a specific class type.
	 */
	public void setCustomInjector(Callback<Class<?>, Object> callback) {
		this.customInjector = callback;
	}
	
	/**
	 * Returns an instance of the given type. When there is a custom injector defined (See:
	 * {@link #setCustomInjector(javafx.util.Callback)}) then this injector is used. Otherwise a new instance of the
	 * desired type is created. This is done by a call to {@link Class#newInstance()} which means that all constraints
	 * of the newInstance method are also need to be satisfied.
	 *
	 * @param type
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getInstanceOf(Class<? extends T> type) {
		if (isCustomInjectorDefined()) {
			return (T) customInjector.call(type);
		} else {
			try {
				// use default creation
				return type.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("Can't create instance of type " + type.getName() +
						". Make sure that the class has a public no-arg constructor.", e);
			}
		}
	}
	
	/**
	 * See {@link #setCustomInjector(javafx.util.Callback)} for more details.
	 * 
	 * @return the defined custom injector if any
	 */
	Callback<Class<?>, Object> getCustomInjector() {
		return customInjector;
	}
	
	
	/**
	 * @return true when a custom injector is defined, otherwise false.
	 */
	boolean isCustomInjectorDefined() {
		return customInjector != null;
	}
	
}
