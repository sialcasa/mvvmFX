/*
 * Copyright 2013 Alexander Casall - Saxonia Systems AG
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package de.saxsys.jfx.mvvm.base;

import javafx.fxml.Initializable;

/**
 * Abstract class for a MVVMView - you have to say which viewmodel it uses. Then
 * you can use the embedded {@link MVVMViewModel} property which is typed
 * correctly.
 * 
 * @author alexander.casall
 * 
 * @param <ViewModel>
 *            type
 */
public abstract class MVVMView<ViewModel extends MVVMViewModel> implements
		Initializable {

	/**
	 * Viewmodel.
	 */
	protected ViewModel viewModel;

	/**
	 * Set the Viewmodel.
	 * 
	 * @param viewModel
	 *            to set
	 */
	public final void setViewModel(final ViewModel viewModel) {
		beforeViewModelInitialization();
		this.viewModel = viewModel;
		afterViewModelInitialization();
	}

	/**
	 * @return the viewmodel
	 */
	public ViewModel getViewModel() {
		return viewModel;
	}

	/**
	 * This method is called before the viewmodel is going to be set.
	 */
	public abstract void beforeViewModelInitialization();

	/**
	 * This method is called after the viewmodel was set.
	 */
	public abstract void afterViewModelInitialization();

}
