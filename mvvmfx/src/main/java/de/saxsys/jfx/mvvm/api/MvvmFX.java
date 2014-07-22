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
package de.saxsys.jfx.mvvm.api;

import de.saxsys.jfx.mvvm.viewloader.DependencyInjector;
import de.saxsys.jfx.mvvm.notifications.NotificationCenter;
import de.saxsys.jfx.mvvm.notifications.NotificationCenterFactory;
import javafx.util.Callback;

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
     * can tell the framework how it can get instances of classes f.e. services or business facades.
     * <p/>
     * The callback has to return an instance for a given class type. This is the same way as it is done with the {@link
     * javafx.fxml.FXMLLoader#setControllerFactory(javafx.util.Callback)} that is normally used with JavaFX's fxml.
     *
     * @param injector a callback that returns instances for given class types.
     */
    public static void setCustomDependencyInjector(final Callback<Class<?>, Object> injector) {
        DependencyInjector.getInstance().setCustomInjector(injector);
    }
}
