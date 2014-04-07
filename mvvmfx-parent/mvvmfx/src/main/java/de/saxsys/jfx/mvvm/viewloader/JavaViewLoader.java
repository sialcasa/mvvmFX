package de.saxsys.jfx.mvvm.viewloader;

import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.di.DependencyInjector;
import javafx.scene.Parent;

import java.util.ResourceBundle;

/**
 *
 */
class JavaViewLoader {

    <ViewType extends ViewModel> ViewTuple<ViewType> loadJavaViewTuple(Class<? extends View<ViewType>>
            viewType, ResourceBundle resourceBundle) {

        DependencyInjector injectionFacade = DependencyInjector.getInstance();

        View<ViewType> view = injectionFacade.getInstanceOf(viewType);

        return new ViewTuple<>(view, (Parent) view);
    }

}
