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
package de.saxsys.mvvmfx.utils.viewlist;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.View;
import de.saxsys.mvvmfx.ViewTuple;

/**
 * Declares how to map from a {@code <T>} to a {@link ViewTuple}.
 * 
 * @author sialcasa
 * 
 * @param <T>
 *            from
 */
@FunctionalInterface
public interface ViewTupleMapper<T> {
	
	/**
	 * Map a {@code <T>} to a {@link ViewTuple}.
	 * 
	 * @param element
	 *            to map
	 * @return created {@link ViewTuple}
	 */
	ViewTuple<? extends View, ? extends ViewModel> map(T element);
	
}
