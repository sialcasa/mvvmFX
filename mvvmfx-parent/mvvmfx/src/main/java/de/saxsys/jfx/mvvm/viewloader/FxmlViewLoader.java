package de.saxsys.jfx.mvvm.viewloader;

import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.di.FXMLLoaderWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

class FxmlViewLoader {

    private static final Logger LOG = LoggerFactory.getLogger(FxmlViewLoader.class);

    private FXMLLoaderWrapper fxmlLoaderWrapper = new FXMLLoaderWrapper();


    <ViewType extends ViewModel> ViewTuple<ViewType> loadFxmlViewTuple(Class<? extends View<ViewType>>
            viewType, ResourceBundle resourceBundle) {
        String pathToFXML = "/"
                + viewType.getPackage().getName().replaceAll("\\.", "/") + "/"
                + viewType.getSimpleName() + ".fxml";

        return (ViewTuple<ViewType>) loadFxmlViewTuple(pathToFXML, resourceBundle);
    }

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
