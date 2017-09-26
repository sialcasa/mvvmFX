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
package de.saxsys.mvvmfx;

import de.saxsys.mvvmfx.internal.viewloader.GlobalBuilderFactory;
import de.saxsys.mvvmfx.internal.viewloader.ResourceBundleManager;
import javafx.util.BuilderFactory;
import javafx.util.Callback;

import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenterFactory;
import de.saxsys.mvvmfx.internal.viewloader.DependencyInjector;

import java.util.ResourceBundle;

/**
 * This class is a facade that is used by the user to access classes and services from the framework.
 */
public class MvvmFX {
	
	/**
	 * @return an instance of the {@link NotificationCenter}.
	 */
	public static NotificationCenter getNotificationCenter() {
		return NotificationCenterFactory.getNotificationCenter();
	}
	
	/**
	 * This method is used to integrate the mvvmFX framework into your dependency injection environment. This way you
	 * can tell the framework how it can get instances of classes f.e. services or business facades. <br>
	 * The callback has to return an instance for a given class type. This is the same way as it is done with the
	 * {@link javafx.fxml.FXMLLoader#setControllerFactory(javafx.util.Callback)} that is normally used with JavaFX's
	 * fxml.
	 *
	 * @param injector
	 *            a callback that returns instances for given class types.
	 */
	public static void setCustomDependencyInjector(final Callback<Class<?>, Object> injector) {
		DependencyInjector.getInstance().setCustomInjector(injector);
	}

	/**
	 * This method is used to set a global {@link ResourceBundle} for the application.
	 * 
	 * This resource bundle is automatically loaded for all views. If there is an resourceBundle provided
	 * while loading the view (via {@link FluentViewLoader.FxmlViewStep#resourceBundle(ResourceBundle)} or 
	 * {@link FluentViewLoader.JavaViewStep#resourceBundle(ResourceBundle)}) both resourceBundles will be merged.
	 * <p>
	 * The global resourceBundle set by this method will have a lower priority then the ones provided while loading. 
	 * If there are keys available in both resourceBundles, the values of the global resourceBundle will be overwritten.
	 * 
	 * @param resourceBundle the resourceBundle
	 */
	public static void setGlobalResourceBundle(ResourceBundle resourceBundle) {
		ResourceBundleManager.getInstance().setGlobalResourceBundle(resourceBundle);
	}

	/**
	 * Add a {@link BuilderFactory} to be used by mvvmFX.
	 * <br/>
	 * A {@link BuilderFactory} is used to enable custom controls that need special initialization to be used with FXML.
	 * MvvmFX can manage multiple builder factories. If you add multiple factories that can provide builders for the same type,
	 * the last added builder factory will be used. This way it's possible to "overwrite" a more abstract builder factory with a more specific
	 * factory.
	 * <br/>
	 * MvvmFX also takes care for handling the default {@link javafx.fxml.JavaFXBuilderFactory}. If no custom builder factory
	 * is able to provide a builder for a given type the default JavaFX builder factory will be used as last resort.
	 * This way you don't have to take care for standard JavaFX types in your builder factory.
	 * <br/>
	 * While most of the time using a global builder factory is the best approach, for some special use cases
	 * it's needed to define a special builder factory that is only used for a single loading procedure.
	 * For such use cases one can use the {@link FluentViewLoader} with the parameter
	 * {@link de.saxsys.mvvmfx.FluentViewLoader.FxmlViewStep#builderFactory(BuilderFactory)} instead.
	 * In this case the provided builder factory is again combined with the global factories (if defined).
	 * Builder factories provided via {@link FluentViewLoader} have a higher priority then global builder factories.
	 *
	 * @param factory the builder factory
	 */
	public static void addGlobalBuilderFactory(BuilderFactory factory) {
		GlobalBuilderFactory.getInstance().addBuilderFactory(factory);
	}
}
