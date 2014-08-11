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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;

/**
 * This viewLoader is used to load views that are implementing {@link de.saxsys.jfx.mvvm.api.FxmlView}.
 * 
 * @author manuel.mauky
 */
class FxmlViewLoader {
	
	private static final Logger LOG = LoggerFactory.getLogger(FxmlViewLoader.class);
	
	/**
	 * Load the viewTuple by it`s ViewType.
	 */
	<ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadFxmlViewTuple(
			Class<? extends ViewType> viewType, ResourceBundle resourceBundle, Object controller, Object root, ViewModelType viewModel) {
		final String pathToFXML = "/" + viewType.getPackage().getName().replaceAll("\\.", "/") + "/"
				+ viewType.getSimpleName() + ".fxml";
		
		return loadFxmlViewTuple(pathToFXML, resourceBundle, controller, root, viewModel);
	}
	
	/**
	 * Load the viewTuple by the path of the fxml file.
	 */
	<ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadFxmlViewTuple(
			final String resource, ResourceBundle resourceBundle, final Object controller, final Object root, ViewModelType viewModel) {
		try {
			
			final FXMLLoader loader = createFxmlLoader(resource, resourceBundle, controller, root, viewModel);
						
			loader.load();
			
			final ViewType loadedController = loader.getController();
			final Parent loadedRoot = loader.getRoot();
			
			if (loadedController == null) {
				throw new IOException("Could not load the controller for the View " + resource
						+ " maybe your missed the fx:controller in your fxml?");
			}


			ViewModelType loadedViewModel = ReflectionUtils.getViewModel(loadedController);
			
			if(loadedViewModel == null){
				loadedViewModel = ReflectionUtils.createViewModel(loadedController);
			}

			return new ViewTuple<>(loadedController, loadedRoot, loadedViewModel);
			
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private FXMLLoader createFxmlLoader(String resource, ResourceBundle resourceBundle, Object controller, Object root, ViewModel viewModel)
			throws IOException {
		
		// Load FXML file
		final URL location = FxmlViewLoader.class.getResource(resource);
		if (location == null) {
			throw new IOException("Error loading FXML - can't load from given resourcepath: " + resource);
		}
		
		final FXMLLoader fxmlLoader = new FXMLLoader();
		
		fxmlLoader.setRoot(root);
		fxmlLoader.setResources(resourceBundle);
		fxmlLoader.setLocation(location);
		
		// when the user provides a viewModel but no controller, we need to use the custom controller factory.
		// in all other cases the default factory can be used.
		if(viewModel != null && controller == null){
			fxmlLoader.setControllerFactory(new ControllerFactoryForCustomViewModel(viewModel));
		}else {
			fxmlLoader.setControllerFactory(new DefaultControllerFactory());
		}
		
		// When the user provides a controller instance we take care of the injection of the viewModel to this controller here.
		if (controller != null) {
			fxmlLoader.setController(controller);
			
			if (controller instanceof View) {
				View view = (View) controller;
				if(viewModel == null){
					ReflectionUtils.injectViewModel(view, ReflectionUtils.createViewModel(view));
				}else{
					ReflectionUtils.injectViewModel(view, viewModel);
				}
			}
		}
		
		return fxmlLoader;
	}

	/**
	 * This controller factory will try to create and inject a viewModel instance to every requested 
	 * controller that is a view. 
	 */
	private static class DefaultControllerFactory implements Callback<Class<?>, Object>{

		@Override 
		public Object call(Class<?> type) {
			Object controller = DependencyInjector.getInstance().getInstanceOf(type);

			if(controller instanceof View){
				View view = (View) controller;

				ReflectionUtils.injectViewModel(view, ReflectionUtils.createViewModel(view));
			}

			return controller;
		}
	}

	/**
	 * A controller factory that is used for the special case where the user privides an existing
	 * view model to be used while loading.
	 * 
	 * This factory will use this existing viewModel instance for injection of the <strong>first</strong>
	 * view that is requested from this factory.
	 * For all later requests this factory will work the same way as 
	 * the default factory {@link de.saxsys.jfx.mvvm.viewloader.FxmlViewLoader.DefaultControllerFactory}.
	 * 
	 * The reason for this behaviour is that the existing viewModel belongs to the view class that the user provides
	 * to the ViewLoader. This view will be the root element of the scene graph to load. 
	 * The first method call that the FXMLLoader will make on the controller factory will always be for the controller
	 * of this root element. This way we can be sure that only the root element gets the existing viewModel injected.
	 */
	private static class ControllerFactoryForCustomViewModel implements Callback<Class<?>, Object> {
		
		private boolean customViewModelInjected = false;
		
		private ViewModel customViewModel;
		
		public ControllerFactoryForCustomViewModel(ViewModel customViewModel){
			this.customViewModel = customViewModel;
		}

		@Override 
		public Object call(Class<?> type) {
			Object controller = DependencyInjector.getInstance().getInstanceOf(type);

			if(controller instanceof View){
				View view = (View) controller;
				
				if(!customViewModelInjected){
					ReflectionUtils.injectViewModel(view, customViewModel);
					
					customViewModelInjected = true;
					return view;
				}
				
				ReflectionUtils.injectViewModel(view, ReflectionUtils.createViewModel(view));
			}

			return controller;
		}
	}
}
