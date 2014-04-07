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

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.di.FXMLLoaderWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This viewLoader is used to load views that are implementing {@link de.saxsys.jfx.mvvm.api.FxmlView}.
 * 
 * @author manuel.mauky 
 */
class FxmlViewLoader {

    private static final Logger LOG = LoggerFactory.getLogger(FxmlViewLoader.class);

    private FXMLLoaderWrapper fxmlLoaderWrapper = new FXMLLoaderWrapper();


    /**
     * Load the viewTuple by it`s ViewType.
     */
    <ViewType extends ViewModel> ViewTuple<ViewType> loadFxmlViewTuple(Class<? extends FxmlView<ViewType>>
            viewType, ResourceBundle resourceBundle) {
        String pathToFXML = "/"
                + viewType.getPackage().getName().replaceAll("\\.", "/") + "/"
                + viewType.getSimpleName() + ".fxml";

        return (ViewTuple<ViewType>) loadFxmlViewTuple(pathToFXML, resourceBundle);
    }

    /**
     * Load the viewTuple by the path of the fxml file.
     */
    ViewTuple<? extends ViewModel> loadFxmlViewTuple(final String resource,
            ResourceBundle resourceBundle) {
        // Load FXML file
        final URL location = FxmlViewLoader.class.getResource(resource);
        if (location == null) {
            LOG.error("Error loading FXML - can't load from given resourcepath: "
                    + resource);
            return null;
        }

        try {

            ViewTuple<? extends ViewModel> tuple = fxmlLoaderWrapper.load(
                    location, resourceBundle);
            if (tuple.getCodeBehind() == null) {
                LOG.warn("Could not load the code behind class for the following FXML file: "
                        + resource
                        + " please check whether you have set the fx:controller attribute in the FXML!");
            }
            if (tuple.getView() == null) {
                LOG.error("Could not load the view for the following FXML file: "
                        + resource
                        + " This is a serious error and caused an exception.");
            }

            return tuple;
        } catch (final IOException ex) {
            LOG.error("Error loading FXML :", ex);
            return null;
        }
    }

}
