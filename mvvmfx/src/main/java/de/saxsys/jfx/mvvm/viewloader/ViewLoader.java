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
 * Loader class for loading FXML and code behind from Fs. There are following options for loading the FXML:
 * <p/>
 * <ul>
 * <li>Providing the code behind class (controller) by calling {@link #loadViewTuple(Class)}</li>
 * <li>Providing a path to the FXML file by calling {@link #loadViewTuple(String)}</li>
 * </ul>
 *
 * @author alexander.casall, manuel.mauky
 */
public final class ViewLoader {
	
	private Object codeBehind;
	private Object root;
	
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
		Type type = TypeResolver.resolveGenericType(FxmlView.class, viewType);
		
		if (type != null) {
			LOG.debug("Loading view '{}' of type {}.", type, FxmlView.class.getSimpleName());
			
			return fxmlViewLoader.loadFxmlViewTuple(viewType, resourceBundle, codeBehind, root);
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
	 * @param resource
	 *            path to the FXML which should be loaded
	 * 
	 *
	 * @return the ViewTuple that contains the view and the viewModel.
	 */
	public <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadViewTuple(
			final String resource) {
		return loadViewTuple(resource, null);
	}
	
	/**
	 * Load the view (Code behind + Node from FXML) by a given resource path.
	 *
	 * @param resource
	 *            path to the FXML which should be loaded
	 * @param resourceBundle
	 *            which is passed to the viewloader
	 *
	 * @return the ViewTuple that contains the view and the viewModel.
	 */
	public <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewTuple<ViewType, ViewModelType> loadViewTuple(
			final String resource, ResourceBundle resourceBundle) {
		return fxmlViewLoader.loadFxmlViewTuple(resource, resourceBundle, codeBehind, root);
	}
	
	/**
	 * Returns the codeBehind instance.
	 *
	 * @return codeBehind
	 */
	@SuppressWarnings("unchecked")
	public <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> ViewType getCodeBehind() {
		// The codeBehind-field is of type Object but the only way to set this field is by the setter, which has a
		// parameter of type `ViewType`.
		// Therefore we know that the codeBehind can only be of the type `ViewType` and therefore this cast is safe.
		return (ViewType) codeBehind;
	}
	
	/**
	 * @see #getCodeBehind()
	 *
	 * @param codeBehind
	 *
	 */
	public <ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> void setCodeBehind(
			ViewType codeBehind) {
		this.codeBehind = codeBehind;
	}
	
	/**
	 * Returns the root of the loaded view.
	 *
	 * @return root
	 */
	public Object getRoot() {
		return root;
	}
	
	/**
	 * @see #getRoot()
	 * @param root
	 */
	public void setRoot(Object root) {
		this.root = root;
	}
	
}
