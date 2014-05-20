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

import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is a wrapper for javaFX`s {@link FXMLLoader}. It is used to load a {@link ViewTuple} for a fxml file.
 * 
 * @author manuel.mauky, alexander.casall 
 */
public class FXMLLoaderWrapper {

    /**
     * @see FXMLLoader#load(URL)
     */
    public ViewTuple<? extends View, ? extends ViewModel> load(URL location) throws IOException {
        FXMLLoader fxmlLoader = createFxmlLoader(location, null);
        
        return new ViewTuple((View)fxmlLoader.getController(),
                (Parent) fxmlLoader.getRoot());
    }

    /**
     * @see FXMLLoader#load(URL, ResourceBundle)
     */
    public ViewTuple<? extends View, ? extends ViewModel> load(URL location,
            ResourceBundle resourceBundle) throws IOException {
        FXMLLoader fxmlLoader = createFxmlLoader(location, resourceBundle);
        return new ViewTuple((View<?>) fxmlLoader.getController(),
                (Parent) fxmlLoader.getRoot());
    }

    private FXMLLoader createFxmlLoader(URL location,
            ResourceBundle resourceBundle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return DependencyInjector.getInstance().getInstanceOf(aClass);
            }
        });

        fxmlLoader.setResources(resourceBundle);
        fxmlLoader.setLocation(location);
        fxmlLoader.load(location.openStream());
        return fxmlLoader;
    }

}
