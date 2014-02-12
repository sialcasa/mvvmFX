/*******************************************************************************
 * Copyright 2013 Alexander Casall
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
package de.saxsys.jfx.mvvm.base.view;

import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import de.saxsys.jfx.mvvm.di.DependencyInjector;
import javafx.fxml.Initializable;
import net.jodah.typetools.TypeResolver;

/**
 * Abstract class for a MVVMView - you have to say which View Model it uses.
 * Then you can use the embedded {@link ViewModel} property which is typed
 * correctly.
 * 
 * @author alexander.casall
 * 
 * @param <ViewModelType>
 *            type
 */
public abstract class View<ViewModelType extends ViewModel> implements
		Initializable {

	/**
	 * Creates a View. If no View model has been set and the child class not a
	 * {@link ViewWithoutViewModel}, an exception is going to be thrown.
	 */
	public View() {
		if (returnedClass() == null && !(this instanceof ViewWithoutViewModel)) {
			throw new IllegalStateException(
					"The View has no defined View Model. If you want to archive this use the class ViewWithoutViewModel.java instead of View.java for the inheritance");
		}
	}

	// View Model
	private ViewModelType viewModel;

	private DependencyInjector injectionFacade = DependencyInjector.getInstance();

	/**
	 * @return the View Model which represents the data that should be displayed
	 *         by the view
	 */
	public final ViewModelType getViewModel() {
		if (viewModel == null && !(viewModel instanceof ViewWithoutViewModel)) {
			viewModel = injectionFacade.getInstanceOf(returnedClass());
		}
		return viewModel;
	}

	/**
	 * Method returns class.
	 * 
	 * @return Class<T extends ViewModelType>
	 */
	@SuppressWarnings("unchecked")
	private Class<ViewModelType> returnedClass() {
        Class<?> type = TypeResolver.resolveRawArgument(View.class, getClass());
        if(type == TypeResolver.Unknown.class){
            return null;
        }else{
            return (Class<ViewModelType>) type;
        }
	}
}
