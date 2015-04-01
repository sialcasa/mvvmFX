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
package de.saxsys.mvvmfx;

import de.saxsys.mvvmfx.internal.viewloader.View;

/**
 * <p>
 * A view that is implemented with FXML.
 * </p>
 * <p>
 * There has to be an fxml file in the same package with the same name (case-sensitive) as the implementing view class.
 * So for example when your view class is named <code>MyCoolView</code> (filename: MyCoolView.java) then the fxml file
 * has to be named <code>MyCoolView.fxml</code>.
 * </p>
 * 
 * <p>
 * The instance of this class is also known as the "code-behind" of the View in terms of the Model-View-ViewModel
 * pattern.
 * </p>
 * 
 * @param <ViewModelType>
 *            the type of the viewModel.
 * 
 * @author manuel.mauky
 */
public interface FxmlView<ViewModelType extends ViewModel> extends View<ViewModelType> {
}
