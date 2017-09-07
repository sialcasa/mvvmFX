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

import de.saxsys.mvvmfx.internal.viewloader.ResourceBundleManager;
import javafx.util.Callback;

import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenterFactory;
import de.saxsys.mvvmfx.internal.viewloader.DependencyInjector;

import java.util.ResourceBundle;

/**
 * This class is a facade that is used by the user to access classes and services from the framework.
 */
public class MvvmFX {

	private MvvmFX() {}
	
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
}
