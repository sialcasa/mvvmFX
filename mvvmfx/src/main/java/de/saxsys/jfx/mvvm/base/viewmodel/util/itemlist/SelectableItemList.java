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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;

/**
 * Element that you can use in a View Model to transform any list to a string
 * representation which can be bound to UI Elements like {@link ChoiceBox} or
 * {@link ListView}. <b>You should only expose the {@link #stringListProperty()}
 * and/or the {@link #selectedIndexProperty()} to the view, otherwise you create
 * a visibility of the view to the model. If you want to expose it more
 * convenient, use the {@link SelectableStringList} interface to hide all
 * dependencies to the model. Create something like this in your View Model:
 * 
 * <code>
 * public SelectableStringList stringListProperty(){
 * 		return selectableItemListInstance;
 * }
 * </code>
 * 
 * </b> You have to provide a {@link ModelToStringMapper} to define how to map
 * from your model representation to a string. In addition you have properties
 * which represents the actual selection state of a list. You can set either the
 * {@link #selectedIndexProperty()} or the {@link #selectedItemProperty()} and
 * the other will change automatically.
 * 
 * @author sialcasa
 * 
 * @param <ListType>
 *            type of the list elements which should be transformed to a string
 *            list
 */
public class SelectableItemList<ListType> extends ItemList<ListType> implements
		SelectableStringList {

	// Indeces
	private SingleSelectionModel<ListType> selectionModel = new SingleSelectionModel<ListType>() {
		@Override
		protected int getItemCount() {
			return itemListProperty().size();
		}

		@Override
		protected ListType getModelItem(int arg0) {
			return itemListProperty().get(arg0);
		}
	};

	private ObjectProperty<ListType> selectedItem = new SimpleObjectProperty<>();

	/**
	 * Creates a {@link SelectableItemList} by a given list of items and a
	 * {@link ModelToStringMapper}.
	 * 
	 * @param itemList
	 *            which should be transformed for the UI
	 * @param modelToStringMapper
	 *            which is used for transformation
	 */
	public SelectableItemList(ObservableList<ListType> itemList,
			final ModelToStringMapper<ListType> modelToStringMapper) {
		super(itemList, modelToStringMapper);
		// Order of processing is important!

		selectedItem.set(null);
		createIndexEvents();
	}

	// When the index property changed we have to change the selected item too
	// When the selected item changed we want to set the index property too
	private void createIndexEvents() {
		selectionModel.selectedIndexProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> bean,
							Number oldVal, Number newVal) {
							int index = newVal.intValue();
							ListType item = itemListProperty().get(index);
							selectedItem.set(item);
					}
				});

		selectedItem.addListener(new ChangeListener<ListType>() {
			@Override
			public void changed(ObservableValue<? extends ListType> arg0,
					ListType oldVal, ListType newVal) {

				int index = itemListProperty().get().indexOf(newVal);
				if (index != -1) {
					selectionModel.select(index);
				} else {
					// If item not found - Rollback
					selectedItem.set(oldVal);
				}

			}
		});
	}

	/**
	 * Represents an {@link Integer} which is the current selection index. If
	 * you set another value to the property {@link #selectedItemProperty()}
	 * will change automatically. If the value was an invalid index, the change
	 * will be rollbacked.
	 * 
	 * @return the index property
	 */
	@Override
	public ReadOnlyIntegerProperty selectedIndexProperty() {
		return this.selectionModel.selectedIndexProperty();
	}

	@Override
	public int getSelectedIndex() {
		return selectedIndexProperty().get();
	}

	/**
	 * Represents the current selected item. If you set another value to the
	 * property {@link #selectedIndexProperty()} will change automatically. If
	 * the value was an invalid item which is not in the itemlist, the change
	 * will be rollbacked.
	 * 
	 * @return the item property
	 */
	public ObjectProperty<ListType> selectedItemProperty() {
		return this.selectedItem;
	}

	/**
	 * @see #selectedItemProperty()
	 */
	public void select(ListType item) {
		this.selectedItem.set(item);
	}

	/**
	 * @see #selectedItemProperty()
	 */
	public ListType getSelectedItem() {
		return this.selectedItem.get();
	}

	@Override
	public void select(int index) {
		this.selectionModel.select(index);
	}

}
