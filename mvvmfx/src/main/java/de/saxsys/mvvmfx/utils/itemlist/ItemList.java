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
package de.saxsys.mvvmfx.utils.itemlist;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Element that you can use in a View Model to transform any list to a string representation which can be bound to UI
 * Elements like {@link ListView}. <strong>You should only expose the {@link #stringListProperty()} to the
 * view</strong>, otherwise you create a visibility of the view to the model. Create something like this in your View
 * Model: <br>
 * <br>
 * 
 * <pre>
 * public ObservableList{@code <String>} stringListProperty(){
 * 		return itemList.stringListProperty();
 * }
 * </pre>
 * 
 * <br>
 * You have to provide a {@link ModelToStringFunction} to define how to map between the model type and a string and
 * back. In addition you have properties which represents the actual selection state of a list.
 * 
 * @author sialcasa
 * 
 * @param <ListType>
 *            type of the list elements which should be transformed to a string list
 */
public class ItemList<ListType> extends ListTransformation<ListType, String> {
	/**
	 * Creates a {@link ItemList} by a given list of items and a string converter.
	 * 
	 * @param modelList
	 *            which should be transformed for the UI
	 * @param modelToStringMapper
	 *            which is used for transformation
	 */
	public ItemList(ObservableList<ListType> modelList,
			final ModelToStringFunction<ListType> modelToStringMapper) {
		super(modelList, modelToStringMapper);
	}
	
	/**
	 * @return String representation of {@link #modelListProperty()}.
	 */
	public ReadOnlyListProperty<String> stringListProperty() {
		return targetListProperty();
	}
	
}
