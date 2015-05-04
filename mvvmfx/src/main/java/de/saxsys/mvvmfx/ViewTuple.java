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
 * <p>
 * Tuple for carrying view / code-behind pair. The code-behind part is the class which is known as the controller class
 * behind a FXML file.
 * </p>
 * 
 * <p>
 * As a user you typically won't create instances of this class on your own. Instead you will obtain instances of this
 * class with the {@link FluentViewLoader} when you are loading a view.
 * </p>
 * 
 * <p>
 * Instances of this class are immutable.
 * </p>
 * 
 * 
 * @param <ViewType>
 *            the generic type of the view that was loaded.
 * @param <ViewModelType>
 *            the generic type of the viewModel that was loaded.
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
	 * @param viewModel
	 *            the viewModel for this viewTuple
	 */
	public ViewTuple(final ViewType codeBehind, final Parent view, final ViewModelType viewModel) {
		this.codeBehind = codeBehind;
		this.view = view;
		this.viewModel = viewModel;
	}
	
	/**
	 * <p>
	 * The code behind part of the view. When using FXML ({@link FxmlView}) this will be an instance of the class that
	 * is specified in the fxml file with <code>fx:controller</code>.
	 * </p>
	 * 
	 * <p>
	 * When the view is implemented in pure java ({@link JavaView}) the instance returned by this method will typically
	 * be the same instance that is returned by {@link #getView()}.
	 * </p>
	 * 
	 * @return the code behind of the View.
	 */
	public ViewType getCodeBehind() {
		return codeBehind;
	}
	
	/**
	 * The root object of the view. This can be added to the scene graph.
	 * 
	 * @return the view
	 */
	public Parent getView() {
		return view;
	}
	
	/**
	 * @return the viewModel
	 */
	public ViewModelType getViewModel() {
		return viewModel;
	}
}
