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

import java.lang.reflect.Type;
import java.util.ResourceBundle;

import net.jodah.typetools.TypeResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.JavaView;
import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;

/**
 * This class can be used to load MvvmFX views. It provides overloaded loading methods that return {@link de.saxsys.jfx.mvvm.viewloader.ViewTuple}
 * instances that contain the loaded view. 
 * 
 * Instead of using this class you can also use the {@link de.saxsys.jfx.mvvm.viewloader.FluentViewLoader}
 * to load views. It provides a fluent api to specify your params instead of using overloaded methods of this class.
 * The usage of the {@link de.saxsys.jfx.mvvm.viewloader.FluentViewLoader} is the recommended way of using MvvmFX.
 *
 * @author alexander.casall, manuel.mauky
 */
public final class ViewLoader {
	
	private static final Logger LOG = LoggerFactory.getLogger(ViewLoader.class);
	
	private final FxmlViewLoader fxmlViewLoader = new FxmlViewLoader();
	
	private final JavaViewLoader javaViewLoader = new JavaViewLoader();
	
	/**
	 * Load a ViewTuple for the given View type. The view type has to implement either the
	 * {@link de.saxsys.jfx.mvvm.api.FxmlView} or {@link de.saxsys.jfx.mvvm.api.JavaView} interface.
	 *
	 * @param viewType
	 *            the class type of the view to be loaded.
	 * @param <ViewType>
	 *            the generic type of the ViewModel.
	 *
	 * @return the ViewTuple that contains the view and the viewModel.
	 */
	public <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadViewTuple(
			Class<? extends ViewType> viewType) {
		return loadViewTuple(viewType, null);
	}
	
	/**
	 * Load a ViewTuple for the given View type. The view type has to implement either the
	 * {@link de.saxsys.jfx.mvvm.api.FxmlView} or {@link de.saxsys.jfx.mvvm.api.JavaView} interface.
	 * <p/>
	 * This method can be used when you need to load a {@link java.util.ResourceBundle} with the view.
	 *
	 * @param viewType
	 *            the class type of the view to be loaded.
	 * @param resourceBundle
	 *            the resourceBundle that is loaded with the view.
	 * @param <ViewType>
	 *            the generic type of the ViewModel.
	 *
	 * @return the ViewTuple that contains the view and the viewModel.
	 */
	public <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadViewTuple(
			Class<? extends ViewType> viewType, ResourceBundle resourceBundle) {
		
		return loadViewTuple(viewType, resourceBundle, null, null);
		
	}
	
	
	/**
	 * Load a ViewTuple for the given parameters.
	 * <br/>
	 * This method is useful if you need to define one of the additional not so common params like 'codeBehind' or
	 * 'root'.
	 * <br/>
	 * With the "codeBehind" param you can use an existing codeBehind instance for this view. This can be useful when
	 * you need to create the codeBehind class outside of the normal dependency injection mechanism or you like to reuse
	 * an existing instance for a second view.
	 * <br/>
	 * With the "root" param you can use an existing JavaFX node as the root for the loaded View.
	 * <br/>
	 * The most common use-case for both "codeBehind" and "root" params is when you like to create a custom component
	 * with MvvmFX with the <a href="http://docs.oracle.com/javafx/2/fxml_get_started/custom_control.htm">fx:root element</a>. In this case you will subclass from a JavaFX component and use the viewLoader in the constructor of
	 * your custom component. See the following example:
	 * 
	 * <pre>
	 * 
	 *     public class MyComponent extends VBox implements FxmlView {@code<MyViewModel>} {
	 *         
	 *         public MyComponent(){
	 *             ViewLoader viewLoader = new ViewLoader();
	 *             viewLoader.loadViewTuple(this.getClass(), null, this, this);
	 *         }
	 *     }
	 * 
	 * </pre>
	 * 
	 * 
	 * 
	 * @param viewType
	 *            the class type of the view to be loaded.
	 * @param resourceBundle
	 *            the resourceBundle that is loaded with the view. This param is optional and can be <code>null</code>.
	 * @param codeBehind
	 *            the codeBehind instance that will be used. This param is optional and can be <code>null</code>. When
	 *            this param is <code>null</code> a new instance will be created / obtained by the dependency injection
	 *            mechanism.
	 * @param root
	 *            the javafx node that is used as the root element. This param is optional and can be <code>null</code>.
	 * @param <ViewType>
	 *            the generic type of the View
	 * @param <ViewModelType>
	 *            the generic type of the ViewModel
	 * @return a ViewTuple instance that contains the loaded view.
	 */
	public <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadViewTuple(
			Class<? extends ViewType> viewType, ResourceBundle resourceBundle, ViewType codeBehind, Object root) {
		Type type = TypeResolver.resolveGenericType(FxmlView.class, viewType);
		
		if (type != null) {
			LOG.debug("Loading view '{}' of type {}.", type, FxmlView.class.getSimpleName());
			
			return fxmlViewLoader.loadFxmlViewTuple(viewType, resourceBundle, codeBehind, root, null);
		}
		
		type = TypeResolver.resolveGenericType(JavaView.class, viewType);
		
		if (type != null) {
			LOG.debug("Loading view '{}' of type {}.", type, JavaView.class.getSimpleName());
			
			return javaViewLoader.loadJavaViewTuple(viewType, resourceBundle);
		}
		
		final String errorMessage = String.format(
				"Loading view '%s' failed. Can't detect the view type. Your view has to implement '%s' or '%s'.",
				viewType, FxmlView.class.getName(), JavaView.class.getName());
		throw new IllegalArgumentException(errorMessage);
	}
	
	
	/**
	 * Load the view (Code behind + Node from FXML) by a given resource path.
	 *
	 * @param fxmlPath
	 *            path to the FXML which should be loaded
	 * 
	 *
	 * @return the ViewTuple that contains the view and the viewModel.
	 */
	public <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadViewTuple(
			final String fxmlPath) {
		return loadViewTuple(fxmlPath, null);
	}
	
	/**
	 * Load the view (Code behind + Node from FXML) by a given resource path.
	 *
	 * @param fxmlPath
	 *            path to the FXML which should be loaded
	 * @param resourceBundle
	 *            which is passed to the viewloader
	 *
	 * @return the ViewTuple that contains the view and the viewModel.
	 */
	public <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadViewTuple(
			final String fxmlPath, ResourceBundle resourceBundle) {
		return fxmlViewLoader.loadFxmlViewTuple(fxmlPath, resourceBundle, null, null, null);
	}
}
