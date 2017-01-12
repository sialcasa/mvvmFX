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
package de.saxsys.mvvmfx.utils.itemlist;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;

/**
 * Binds an {@link javafx.collections.ObservableList} that contains elements of {@link TargetType} to another
 * {@link javafx.collections.ObservableList} that contains elements of {@link SourceType}. This is different to the
 * normal list binding offered by JavaFX where the lists have to contain elements of the same type.
 *
 * @param <SourceType>
 *            the generic type of the source list.
 * @param <TargetType>
 *            the generic type of the target list.
 */
public class ListTransformation<SourceType, TargetType> {
	
	// Converter
	private final Function<SourceType, TargetType> function;
	
	// The two lists - List which was provided and the TargetType representation of
	// the list
	private ReadOnlyListWrapper<TargetType> viewModelList = new ReadOnlyListWrapper<>(
			FXCollections.<TargetType> observableArrayList());
	private ListProperty<SourceType> sourceList = new SimpleListProperty<>();
	
	// Reference to the listener to use it by a wrapped listChangeListener
	private ListChangeListener<SourceType> listChangeListener;
	
	/**
	 * Creates a {@link ListTransformation} by a given list of items and a function.
	 *
	 * @param modelList
	 *            which should be transformed for the UI
	 * @param function
	 *            which is used for transformation
	 */
	public ListTransformation(ObservableList<SourceType> modelList,
			final Function<SourceType, TargetType> function) {
		this.function = function;
		initListEvents();
		this.modelListProperty().set(modelList);
	}
	
	/**
	 * Creates a {@link ListTransformation} by with a given function.
	 *
	 * @param function
	 *            which is used for transformation
	 */
	public ListTransformation(final Function<SourceType, TargetType> function) {
		this(FXCollections.observableArrayList(), function);
	}
	
	// If the list changed we want the recreate the targetType representation
	private void initListEvents() {
		this.listChangeListener = new ListChangeListener<SourceType>() {
			@Override
			public void onChanged(
					Change<? extends SourceType> listEvent) {
				
				// We have to stage delete events, because if we process them
				// separately, there will be unwanted ChangeEvents on the
				// targetList
				List<TargetType> deleteStaging = new ArrayList<>();
				
				
				while (listEvent.next()) {
					if (listEvent.wasUpdated()) {
						processUpdateEvent(listEvent);
					} else if (listEvent.wasReplaced()) {
						processReplaceEvent(listEvent, deleteStaging);
					} else if (listEvent.wasAdded()) {
						processAddEvent(listEvent);
					} else if (listEvent.wasRemoved()) {
						processRemoveEvent(listEvent, deleteStaging);
					}
				}
				
				// Process the staged elements
				processStagingLists(deleteStaging);
			}
		};
		modelListProperty().addListener(
				new WeakListChangeListener<>(listChangeListener));
		
	}
	
	/**
	 * Maps an add event of the model list to new elements of the {@link #viewModelList}.
	 *
	 * @param listEvent
	 *            to analyze
	 */
	private void processAddEvent(
			ListChangeListener.Change<? extends SourceType> listEvent) {

		final List<TargetType> toAdd = new ArrayList<>();
		for (int index = listEvent.getFrom(); index < listEvent.getTo(); index++) {
			final SourceType item = listEvent.getList().get(index);
			toAdd.add(function.apply(item));
		}
		viewModelList.addAll(listEvent.getFrom(), toAdd);
	}
	
	/**
	 * Maps an remove event of the model list to new elements of the {@link #viewModelList}.
	 *
	 * @param listEvent
	 *            to process
	 * @param deleteStaging
	 *            for staging the delete events
	 */
	private void processRemoveEvent(
			ListChangeListener.Change<? extends SourceType> listEvent,
			List<TargetType> deleteStaging) {
		for (int i = 0; i < listEvent.getRemovedSize(); i++) {
			deleteStaging.add(viewModelList.get(listEvent.getFrom() + i));
		}
	}
	
	/**
	 * Maps an update event of the model list to new elements of the {@link #viewModelList}.
	 *
	 * @param listEvent
	 *            to process
	 */
	private void processUpdateEvent(ListChangeListener.Change<? extends SourceType> listEvent) {
		for (int i = listEvent.getFrom(); i < listEvent.getTo(); i++) {
			SourceType item = listEvent.getList().get(i);
			viewModelList.set(i, ListTransformation.this.function.apply(item));
		}
	}
	
	/**
	 * Maps an replace event of the model list to new elements of the {@link #viewModelList}.
	 *
	 * @param listEvent
	 *            to process
	 */
	private void processReplaceEvent(
			ListChangeListener.Change<? extends SourceType> listEvent, List<TargetType> deletedStaging) {
		processRemoveEvent(listEvent, deletedStaging);
		processStagingLists(deletedStaging);
		processAddEvent(listEvent);
	}
	
	/**
	 * Process staging events.
	 *
	 * @param deleteStaging
	 *            to process
	 */
	private void processStagingLists(List<TargetType> deleteStaging) {
		viewModelList.removeAll(deleteStaging);
		deleteStaging.clear();
	}
	
	/**
	 * @deprecated Please use {@link #sourceListProperty()} instead.
	 * @return List of elements which should be transformed.
	 */
	@Deprecated
	public ListProperty<SourceType> modelListProperty() {
		return sourceList;
	}
	
	/**
	 * Set the model list that should be synchronized with the target list.
	 * @deprecated Please use {@link #setSourceList(ObservableList)} instead.
	 *
	 * @param modelList
	 *            the source list
	 */
	@Deprecated
	public void setModelList(ObservableList<SourceType> modelList) {
		this.sourceList.set(modelList);
	}
	
	
	/**
	 * @deprecated Please use {@link #getSourceList()} instead.
	 * @return the model list that should be synchronized with the target list.
	 */
	@Deprecated
	public ObservableList<SourceType> getModelList() {
		return sourceList.get();
	}

	/**
	 *
	 * @return List of elements which should be transformed.
	 */
	public ListProperty<SourceType> sourceListProperty() {
		return sourceList;
	}

	/**
	 * Set the source list that should be synchronized with the target list.
	 *
	 * @param sourceList
	 *            the source list
	 */
	public void setSourceList(ObservableList<SourceType> sourceList) {
		this.sourceList.set(sourceList);
	}

	/**
	 * @return the source list that should be synchronized with the target list.
	 */
	public ObservableList<SourceType> getSourceList() {
		return sourceList.get();
	}

	/**
	 * @return {@link TargetType} representation of {@link #modelListProperty()}.
	 */
	public ReadOnlyListProperty<TargetType> targetListProperty() {
		return viewModelList.getReadOnlyProperty();
	}
	
	
	/**
	 * @return {@link TargetType} representation of {@link #modelListProperty()}.
	 */
	public ObservableList<TargetType> getTargetList() {
		return viewModelList.getReadOnlyProperty().get();
	}
	
}
