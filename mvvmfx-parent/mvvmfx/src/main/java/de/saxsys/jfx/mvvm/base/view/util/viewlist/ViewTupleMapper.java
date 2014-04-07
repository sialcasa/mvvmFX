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
package de.saxsys.jfx.mvvm.base.view.util.viewlist;

import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

/**
 * Delares how to map from a <T> to a {@link ViewTuple}.
 * 
 * @author sialcasa
 * 
 * @param <T>
 *            from
 */
public interface ViewTupleMapper<T> {

	/**
	 * Map a <T> to a {@link ViewTuple}.
	 * 
	 * @param element
	 *            to map
	 * @return created {@link ViewTuple}
	 */
	public abstract ViewTuple<? extends ViewModel> map(T element);

}
