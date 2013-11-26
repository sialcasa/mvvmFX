package de.saxsys.jfx.mvvm.di;

import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class FXMLLoaderWrapper {

    public ViewTuple<? extends ViewModel> load(URL location) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        DependencyInjector injectionFacade = DependencyInjector.getInstance();

        if(injectionFacade.isCustomInjectorDefined()){
            fxmlLoader.setControllerFactory(injectionFacade.getCustomInjector());
        }

        fxmlLoader.setLocation(location);
        fxmlLoader.load(location.openStream());

        return new ViewTuple<>((View<?>) fxmlLoader.getController(),(Parent)fxmlLoader.getRoot());
    }

}
