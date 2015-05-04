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
 * A view that is implemented with with pure Java.
 * </p>
 * 
 * <p>
 * The implementing class will typically extend from one of JavaFX`s controls or containers etc. For Example:
 * </p>
 * <br>
 * 
 * <pre>
 * public class MyCoolView extends VBox implements JavaView{@code <MyCoolViewModel>} {
 *     ...
 *     
 *     public MyCoolView(){
 *         getChildren().add(new Label("Hello World"));
 *     }
 * }
 * </pre>
 * 
 * 
 * <p>
 * It is recommended to implement views with FXML and therefore use {@link FxmlView} instead of this interface. But
 * there may be use-cases where FXML isn't suitable or not supported (i.e. the "Compact-1" profile introduced in Java 8
 * doesn't support FXML) or the developer doesn't like FXML. For this cases this interface provides a way to implement
 * the view without FXML in pure Java.
 * </p>
 *
 * @param <ViewModelType>
 *            the type of the viewModel.
 *
 * @author manuel.mauky
 */
public interface JavaView<ViewModelType extends ViewModel> extends View<ViewModelType> {
}
