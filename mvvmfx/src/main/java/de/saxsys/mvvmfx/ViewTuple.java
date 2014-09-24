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
package de.saxsys.mvvmfx;

import javafx.scene.Parent;

import de.saxsys.mvvmfx.internal.viewloader.View;

/**
 * Tuple for carrying view / code-behind pair. The code-behind part is the class which is known as the controller class
 * behind a FXML file.
 * 
 * @param <ViewType> the generic type of the view that was loaded.
 * @param <ViewModelType> the generic type of the viewModel that was loaded.
 */
public class ViewTuple<ViewType extends View<? extends ViewModelType>, ViewModelType extends ViewModel> {
	
	private final ViewType codeBehind;
	private final Parent view;
	private final ViewModelType viewModel;
	
	/**
	 * @param codeBehind
	 *            the codeBehind for this viewTuple
	 * @param view
	 *            the view for this viewTuple
	 * @param viewModel the viewModel for this viewTuple
	 */
	public ViewTuple(final ViewType codeBehind, final Parent view, final ViewModelType viewModel) {
		this.codeBehind = codeBehind;
		this.view = view;
		this.viewModel = viewModel;
	}
	
	/**
	 * @return the code behind of the View. (known as controller class in JavaFX FXML)
	 */
	public ViewType getCodeBehind() {
		return codeBehind;
	}
	
	/**
	 * @return the view
	 */
	public Parent getView() {
		return view;
	}

	/**
	 * @return the viewModel
	 */
	public ViewModelType getViewModel(){
		return viewModel;
	}
}
