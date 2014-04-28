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

import de.saxsys.jfx.mvvm.api.JavaView;
import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.di.DependencyInjector;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.util.ResourceBundle;

/**
 * This viewLoader is used to load views that are implementing {@link de.saxsys.jfx.mvvm.api.JavaView}.
 * 
 * @author manuel.mauky 
 */
class JavaViewLoader {

    <ViewType extends ViewModel> ViewTuple<ViewType> loadJavaViewTuple(Class<? extends JavaView<ViewType>>
            viewType, ResourceBundle resourceBundle) {

        DependencyInjector injectionFacade = DependencyInjector.getInstance();

        View<ViewType> view = injectionFacade.getInstanceOf(viewType);
        
        if(view instanceof Initializable){
            Initializable initializable = (Initializable) view;
            initializable.initialize(null, resourceBundle);
        }
        
        return new ViewTuple<>(view, (Parent) view);
    }

}
