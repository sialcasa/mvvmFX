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

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.JavaView;
import de.saxsys.mvvmfx.ViewModel;

/**
 * <p>
 * This Interface is used as base interface for specific view types for mvvmFX. The generic type defines the View Model
 * that is used.
 * </p>
 * 
 * <p>
 * This interface is for internal use only. Don't implement it directly when creating a view. Instead use
 * {@link FxmlView} for views that are using FXML or {@link JavaView} that are implemented with pure Java.
 * </p>
 * 
 * 
 * @author alexander.casall, manuel.mauky
 * 
 * @param <ViewModelType>
 *            type
 */
public interface View<ViewModelType extends ViewModel> {
	
}
