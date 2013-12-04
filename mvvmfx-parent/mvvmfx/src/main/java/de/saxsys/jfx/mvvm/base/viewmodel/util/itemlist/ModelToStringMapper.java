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
package de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist;

import de.saxsys.jfx.mvvm.base.view.View;

public interface ModelToStringMapper<ModelType> {

	/**
	 * Defines how an model element is represented by a String to display it in
	 * the {@link View}.
	 * 
	 * @param object
	 *            to map
	 * @return string representation
	 */
	public abstract String toString(ModelType object);

}
