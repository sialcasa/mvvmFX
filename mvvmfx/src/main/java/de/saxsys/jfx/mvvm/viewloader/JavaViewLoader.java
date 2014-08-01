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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;

/**
 * This viewLoader is used to load views that are implementing {@link de.saxsys.jfx.mvvm.api.JavaView}.
 *
 * @author manuel.mauky
 */
class JavaViewLoader {
	private static final Logger LOG = LoggerFactory.getLogger(JavaViewLoader.class);
	
	
	private static final String NAMING_CONVENTION_RESOURCES_IDENTIFIER = "resources";
	private static final String NAMING_CONVENTION_INITIALIZE_IDENTIFIER = "initialize";
	
	/**
	 * Loads the java written view of the given type and injects the ViewModel for this view.
	 * <p/>
	 * If the given view type implements the {@link javafx.fxml.Initializable} interface the "initialize" method of this
	 * interface will be invoked. When this is not the case an implicit initialization will be done that is working
	 * similar to the way the {@link javafx.fxml.FXMLLoader} is working.
	 * <p/>
	 * When there is a <strong>public</strong> no-args method named "initialize" is available this method will be
	 * called. When there is a <strong>public</strong> field of type {@link java.util.ResourceBundle} named "resources"
	 * is available this field will get the provided ResourceBundle injected.
	 * <p/>
	 * The "initialize" method (whether from the {@link javafx.fxml.Initializable} interface or implicit) will be
	 * invoked <strong>after</strong> the viewModel was injected. This way the user can create bindings to the viewModel
	 * in the initialize method.
	 *
	 * @param viewType
	 *            class of the view.
	 * @param resourceBundle
	 *            optional ResourceBundle that will be injected into the view.
	 * @param <ViewType>
	 *            the generic type of the view.
	 *
	 * @return a fully loaded and initialized instance of the view.
	 */
	<ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadJavaViewTuple(
			Class<? extends ViewType>
			viewType, ResourceBundle resourceBundle) {
		DependencyInjector injectionFacade = DependencyInjector.getInstance();
		
		final ViewType view = injectionFacade.getInstanceOf(viewType);
		
		if(!(view instanceof Node)){
			throw new IllegalArgumentException("The view class has to be a subclass of 'javafx.scene.Node'");
		}
		
		
		if (view instanceof Initializable) {
			Initializable initializable = (Initializable) view;
			initializable.initialize(null, resourceBundle);
		} else {
			injectResourceBundle(view, resourceBundle);
			callInitialize(view);
		}

		final ViewModelType viewModel = DependencyInjector.getInstance().getViewModel(view);
		
		return new ViewTuple<>(view, (Node) view, viewModel);
	}
	
	/**
	 * This method is trying to invoke the initialize method of the given view by reflection. This is done to meet the
	 * conventions of the {@link javafx.fxml.FXMLLoader}. The conventions say that when there is a
	 * <strong>public</strong> no-args method called "initialize" and the class does not implement the
	 * {@link javafx.fxml.Initializable} interface, the initialize method will be invoked.
	 * <p/>
	 * This method is package scoped for better testability.
	 *
	 * @param view
	 *            the view instance of which the initialize method will be invoked.
	 * @param <ViewModelType>
	 *            the generic type of the view.
	 */
	<ViewModelType extends ViewModel> void callInitialize(View<? extends ViewModelType> view) {
		try {
			final Method initializeMethod = view.getClass().getMethod(NAMING_CONVENTION_INITIALIZE_IDENTIFIER);
			
			initializeMethod.invoke(view);
		} catch (NoSuchMethodException e) {
			// This exception means that there is no initialize method declared.
			// While it's possible that the user has no such method by design, 
			// normally and in most cases you need an initialize method in your view (either with Initialize interface or implicit).
			// So it's likely that the user has misspelled the method name or uses a wrong naming convention. 
			// For this reason we give the user the log message.
			LOG.debug("There is no '{}' method declared at the view {}", NAMING_CONVENTION_INITIALIZE_IDENTIFIER, view);
		} catch (InvocationTargetException e) {
			LOG.warn("The '{}' method of the view {} has thrown an exception!",
					NAMING_CONVENTION_INITIALIZE_IDENTIFIER, view);

			Throwable cause = e.getCause();
			if(cause instanceof RuntimeException){
				throw (RuntimeException) cause;
			}else{
				throw new RuntimeException(cause);
			}
		} catch (IllegalAccessException e) {
			LOG.warn("Can't invoke the '{}' method of the view {} because it is not accessible",
					NAMING_CONVENTION_INITIALIZE_IDENTIFIER, view);
		}
	}
	
	/**
	 * Injects the given ResourceBundle into the given view using reflection. This is done to meet the conventions of
	 * the {@link javafx.fxml.FXMLLoader}. The resourceBundle is only injected when there is a <strong>public</strong>
	 * field of the type {@link java.util.ResourceBundle} named "resources".
	 * <p/>
	 * This method is package scoped for better testability.
	 *
	 * @param view
	 *            the view instance that gets the resourceBundle injected.
	 * @param resourceBundle
	 *            the resourceBundle instance that will be injected.
	 * @param <ViewModelType>
	 *            the generic type of the view.
	 */
	<ViewModelType extends ViewModel> void injectResourceBundle(View<? extends ViewModelType> view,
			ResourceBundle resourceBundle) {
		try {
			Field resourcesField = view.getClass().getField(NAMING_CONVENTION_RESOURCES_IDENTIFIER);
			
			if (resourcesField.getType().isAssignableFrom(ResourceBundle.class)) {
				resourcesField.set(view, resourceBundle);
			}
		} catch (NoSuchFieldException e) {
			// This exception means that there is no field for the ResourceBundle. 
			// This is no exceptional case but is normal when you don't need a resourceBundle in a specific view. 
			// Therefore it's save to silently catch the exception.
		} catch (IllegalAccessException e) {
			LOG.warn("Can't inject the ResourceBundle into the view {} because the field isn't accessible", view);
		}
		
		
	}
	
	
}
