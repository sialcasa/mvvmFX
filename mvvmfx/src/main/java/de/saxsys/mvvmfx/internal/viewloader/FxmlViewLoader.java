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
package de.saxsys.mvvmfx.internal.viewloader;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This viewLoader is used to load views that are implementing {@link de.saxsys.mvvmfx.FxmlView}.
 * 
 * @author manuel.mauky
 */
public class FxmlViewLoader {
	
	private static final Logger LOG = LoggerFactory.getLogger(FxmlViewLoader.class);
	
	/**
	 * Load the viewTuple by it`s ViewType.
	 * 
	 * @param viewType
	 *            the type of the view to be loaded.
	 * @param resourceBundle
	 *            the resourceBundle that is passed to the {@link javafx.fxml.FXMLLoader}.
	 * @param codeBehind
	 *            the controller instance that is passed to the {@link javafx.fxml.FXMLLoader}
	 * @param root
	 *            the root object that is passed to the {@link javafx.fxml.FXMLLoader}
	 * @param viewModel
	 *            the viewModel instance that is used when loading the viewTuple.
<<<<<<< HEAD
	 * 			
=======
	 *
>>>>>>> release
	 * @param <ViewType>
	 *            the generic type of the view.
	 * @param <ViewModelType>
	 *            the generic type of the viewModel.
<<<<<<< HEAD
	 * 			
=======
	 *
>>>>>>> release
	 * @return the loaded ViewTuple.
	 */
	public <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadFxmlViewTuple(
			Class<? extends ViewType> viewType, ResourceBundle resourceBundle, ViewType codeBehind, Object root,
			ViewModelType viewModel) {
		final String pathToFXML = createFxmlPath(viewType);
		return loadFxmlViewTuple(pathToFXML, resourceBundle, codeBehind, root, viewModel);
	}
	
	/**
	 * This method is used to create a String with the path to the FXML file for a given View class.
	 * 
	 * This is done by taking the package of the view class (if any) and replace "." with "/". After that the Name of
	 * the class and the file ending ".fxml" is appended.
	 * 
	 * Example: de.saxsys.myapp.ui.MainView as view class will be transformed to "/de/saxsys/myapp/ui/MainView.fxml"
	 * 
	 * Example 2: MainView (located in the default package) will be transformed to "/MainView.fxml"
	 * 
	 * @param viewType
	 *            the view class type.
	 * @return the path to the fxml file as string.
	 */
	private String createFxmlPath(Class<?> viewType) {
		final StringBuilder pathBuilder = new StringBuilder();
		
		pathBuilder.append("/");
		
		if (viewType.getPackage() != null) {
			pathBuilder.append(viewType.getPackage().getName().replaceAll("\\.", "/"));
			pathBuilder.append("/");
		}
		
		pathBuilder.append(viewType.getSimpleName());
		pathBuilder.append(".fxml");
		
		return pathBuilder.toString();
	}
	
	/**
	 * Load the viewTuple by the path of the fxml file.
	 * 
	 * @param resource
	 *            the string path to the fxml file that is loaded.
<<<<<<< HEAD
	 * 			
=======
	 *
>>>>>>> release
	 * @param resourceBundle
	 *            the resourceBundle that is passed to the {@link javafx.fxml.FXMLLoader}.
	 * @param codeBehind
	 *            the controller instance that is passed to the {@link javafx.fxml.FXMLLoader}
	 * @param root
	 *            the root object that is passed to the {@link javafx.fxml.FXMLLoader}
	 * @param viewModel
	 *            the viewModel instance that is used when loading the viewTuple.
<<<<<<< HEAD
	 * 			
=======
	 *
>>>>>>> release
	 * @param <ViewType>
	 *            the generic type of the view.
	 * @param <ViewModelType>
	 *            the generic type of the viewModel.
<<<<<<< HEAD
	 * 			
=======
	 *
>>>>>>> release
	 * @return the loaded ViewTuple.
	 */
	public <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadFxmlViewTuple(
			final String resource, ResourceBundle resourceBundle, final ViewType codeBehind, final Object root,
			ViewModelType viewModel) {
		try {
			
			final FXMLLoader loader = createFxmlLoader(resource, resourceBundle, codeBehind, root, viewModel);
			
			loader.load();
			
			final ViewType loadedController = loader.getController();
			final Parent loadedRoot = loader.getRoot();
			
			if (loadedController == null) {
				throw new IOException("Could not load the controller for the View " + resource
						+ " maybe your missed the fx:controller in your fxml?");
			}
			
			
			// the actually used ViewModel instance. We need this so we can return it in the ViewTuple
			ViewModelType actualViewModel;
			
			// if no existing viewModel was provided...
			if (viewModel == null) {
				// ... we try to find the created ViewModel from the codeBehind.
				// this is only possible when the codeBehind has a field for the VM and the VM was injected
				actualViewModel = ViewLoaderReflectionUtils.getExistingViewModel(loadedController);
				
				// otherwise we create a new ViewModel. This is needed because the ViewTuple has to contain a VM even if
				// the codeBehind doesn't need one
				if (actualViewModel == null) {
					actualViewModel = ViewLoaderReflectionUtils.createViewModel(loadedController);
				}
			} else {
				actualViewModel = viewModel;
			}
			if (actualViewModel != null) {
				ViewLoaderReflectionUtils.injectScope(actualViewModel);
			}
			
			return new ViewTuple<>(loadedController, loadedRoot, actualViewModel);
			
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	private FXMLLoader createFxmlLoader(String resource, ResourceBundle resourceBundle, View codeBehind, Object root,
			ViewModel viewModel)
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
		
		// when the user provides a viewModel but no codeBehind, we need to use the custom controller factory.
		// in all other cases the default factory can be used.
		if (viewModel != null && codeBehind == null) {
			fxmlLoader.setControllerFactory(new ControllerFactoryForCustomViewModel(viewModel, resourceBundle));
		} else {
			fxmlLoader.setControllerFactory(new DefaultControllerFactory(resourceBundle));
		}
		
		// When the user provides a codeBehind instance we take care of the injection of the viewModel to this
		// controller here.
		if (codeBehind != null) {
			fxmlLoader.setController(codeBehind);
			
			if (viewModel == null) {
				handleInjection(codeBehind, resourceBundle);
			} else {
				handleInjection(codeBehind, resourceBundle, viewModel);
			}
		}
		
		return fxmlLoader;
	}
	
	/**
	 * This controller factory will try to create and inject a viewModel instance to every requested controller that is
	 * a view.
	 */
	private static class DefaultControllerFactory implements Callback<Class<?>, Object> {
		private final ResourceBundle resourceBundle;
		
		public DefaultControllerFactory(ResourceBundle resourceBundle) {
			this.resourceBundle = resourceBundle;
		}
		
		@Override
		public Object call(Class<?> type) {
			Object controller = DependencyInjector.getInstance().getInstanceOf(type);
			
			if (controller instanceof View) {
				View codeBehind = (View) controller;
				
				handleInjection(codeBehind, resourceBundle);
			}
			
			return controller;
		}
	}
	
	
	private static void handleInjection(View codeBehind, ResourceBundle resourceBundle) {
		ResourceBundleInjector.injectResourceBundle(codeBehind, resourceBundle);
		
		final Optional viewModelOptional = ViewLoaderReflectionUtils.createAndInjectViewModel(codeBehind);
		
		if (viewModelOptional.isPresent()) {
			final Object viewModel = viewModelOptional.get();
			if (viewModel instanceof ViewModel) {
				ResourceBundleInjector.injectResourceBundle(viewModel, resourceBundle);
                ViewLoaderReflectionUtils.injectScope(viewModel);
				ViewLoaderReflectionUtils.initializeViewModel((ViewModel) viewModel);
			}
		}
	}
	
	private static void handleInjection(View codeBehind, ResourceBundle resourceBundle, ViewModel viewModel) {
		ResourceBundleInjector.injectResourceBundle(codeBehind, resourceBundle);
		
		if (viewModel != null) {
			ResourceBundleInjector.injectResourceBundle(viewModel, resourceBundle);
            ViewLoaderReflectionUtils.injectScope(viewModel);
			
			ViewLoaderReflectionUtils.injectViewModel(codeBehind, viewModel);
		}
	}
	
	/**
	 * A controller factory that is used for the special case where the user provides an existing viewModel to be used
	 * while loading.
	 * 
	 * This factory will use this existing viewModel instance for injection of the <strong>first</strong> view that is
	 * requested from this factory. For all later requests this factory will work the same way as the default factory
	 * {@link de.saxsys.mvvmfx.internal.viewloader.FxmlViewLoader.DefaultControllerFactory}.
	 * 
	 * The problem we are facing here is the following: The user wants to load a specific View with a specific ViewModel
	 * instance. But this root View (fxml file) can declare other sub views. Only the root View has to get the existing
	 * ViewModel instance, all other sub Views have to get their ViewModels via the default way (i.e.
	 * DependencyInjection or a new instance every time).
	 * 
	 * But, from the perspective of the controller factory, when a View instance is requested, we can't know if this is
	 * the root View or a sub View. How do we know when to use the existing ViewModel instance?
	 * 
	 * To fix this we depend on the standard JavaFX behaviour of the {@link FXMLLoader}: The first instance that the
	 * FXMLLoader will request from the controller factory will always be the controller for the root fxml file. In this
	 * case we can use the existing ViewModel. All subsequent requests will be handled with the default behaviour.
	 */
	private static class ControllerFactoryForCustomViewModel implements Callback<Class<?>, Object> {
		
		private boolean customViewModelInjected = false;
		
		private final ViewModel customViewModel;
		
		private final ResourceBundle resourceBundle;
		
		public ControllerFactoryForCustomViewModel(ViewModel customViewModel, ResourceBundle resourceBundle) {
			this.customViewModel = customViewModel;
			this.resourceBundle = resourceBundle;
		}
		
		@Override
		public Object call(Class<?> type) {
			Object controller = DependencyInjector.getInstance().getInstanceOf(type);
			
			if (controller instanceof View) {
				View codeBehind = (View) controller;
				
				if (!customViewModelInjected) {
					ResourceBundleInjector.injectResourceBundle(customViewModel, resourceBundle);
					ResourceBundleInjector.injectResourceBundle(codeBehind, resourceBundle);
					
					ViewLoaderReflectionUtils.injectViewModel(codeBehind, customViewModel);
					
					
					customViewModelInjected = true;
					return codeBehind;
				}
				
				handleInjection(codeBehind, resourceBundle);
			}
			
			return controller;
		}
	}
}
