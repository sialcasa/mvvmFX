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
package de.saxsys.jfx.mvvm.viewloader;


import javafx.scene.Parent;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;

/**
 * Tuple for carrying view / code-behind pair. The code-behind part is the class
 * which is known as the controller class behind a FXML file.
 */
public class ViewTuple <ViewModelType extends ViewModel> {

	private final View<ViewModelType> codeBehind;
	private final Parent view;

	/**
	 * @param codeBehind
	 *            to set
	 * @param view
	 *            to set
	 */
	public ViewTuple(final View<ViewModelType> codeBehind, final Parent view) {
		this.codeBehind = codeBehind;
		this.view = view;
	}

	/**
	 * @return the code behind of the FXML File (known as controller class in
	 *         JavaFX)
	 */
	public View<ViewModelType> getCodeBehind() {
		return codeBehind;
	}

	/**
	 * @return the view
	 */
	public Parent getView() {
		return view;
	}
}
