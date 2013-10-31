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
package de.saxsys.jfx.mvvm.utils;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * @author alexander.casall
 * 
 *         Worker for caching created listener dependencies to clean them up
 *         when clean method is called.
 */

@SuppressWarnings("rawtypes")
public class ListenerCleaner implements ICleanable {

	private final Multimap<ReadOnlyProperty, Object> map = ArrayListMultimap
			.<ReadOnlyProperty, Object> create();

	/*
	 * API
	 */
	// FIXME CHECK HOW THE HELL OBSERVABLELISTS CAN BE THE PARAM
	/**
	 * Add a {@link ReadOnlyProperty}'s {@link ListChangeListener}.
	 * 
	 * @param property
	 *            the {@link ReadOnlyProperty}
	 * @param listener
	 *            the {@link ListChangeListener}
	 * @return <code>true</code>
	 */
	public boolean put(final ReadOnlyProperty property,
			final ListChangeListener listener) {

		if (!(property instanceof ObservableList)) {
			throw new IllegalArgumentException(
					"An ListChangeListener has to be added to an ObservableList");
		}
		return map.put(property, listener);
	}

	/**
	 * Add a {@link ReadOnlyProperty}'s {@link ChangeListener}.
	 * 
	 * @param property
	 *            the {@link ReadOnlyProperty}
	 * @param listener
	 *            the {@link ChangeListener}
	 * @return <code>true</code>
	 */
	public boolean put(final ReadOnlyProperty property,
			final ChangeListener listener) {
		return map.put(property, listener);
	}

	/**
	 * Add a {@link ReadOnlyProperty}'s {@link InvalidationListener}.
	 * 
	 * @param property
	 *            the {@link ReadOnlyProperty}
	 * @param listener
	 *            the {@link InvalidationListener}
	 * @return <code>true</code>
	 */
	public boolean put(final ReadOnlyProperty property,
			final InvalidationListener listener) {
		return map.put(property, listener);
	}

	/**
	 * Remove a {@link ReadOnlyProperty}'s {@link ListChangeListener}.
	 * 
	 * @param property
	 *            the {@link ReadOnlyProperty}
	 * @param listener
	 *            the {@link ListChangeListener}
	 * @return <code>true</code>
	 */
	public boolean remove(final ReadOnlyProperty property,
			final ListChangeListener listener) {
		return map.remove(property, listener);
	}

	/**
	 * Remove a {@link ReadOnlyProperty}'s {@link ChangeListener}.
	 * 
	 * @param property
	 *            the {@link ReadOnlyProperty}
	 * @param listener
	 *            the {@link ChangeListener}
	 * @return <code>true</code>
	 */
	public boolean remove(final ReadOnlyProperty property,
			final ChangeListener listener) {
		return map.remove(property, listener);
	}

	/**
	 * Remove a {@link ReadOnlyProperty}'s {@link InvalidationListener}.
	 * 
	 * @param property
	 *            the {@link ReadOnlyProperty}
	 * @param listener
	 *            the {@link InvalidationListener}
	 * @return <code>true</code>
	 */
	public boolean remove(final ReadOnlyProperty property,
			final InvalidationListener listener) {
		return map.remove(property, listener);
	}

	@Override
	public void clean() {
		for (final ReadOnlyProperty property : map.keySet()) {
			for (final Object listener : map.get(property)) {
				removeListener(property, listener);
			}
		}
		map.clear();
	}

	/*
	 * Internal
	 */
	@SuppressWarnings("unchecked")
	private void removeListener(final ReadOnlyProperty property,
			final Object event) {
		if (event instanceof InvalidationListener) {
			property.removeListener((InvalidationListener) event);
		} else if (event instanceof ChangeListener) {
			property.removeListener((ChangeListener) event);
		} else if (event instanceof ListChangeListener) {
			((ObservableList) property)
					.removeListener((ListChangeListener) event);
			if (property instanceof ListProperty) {
				((ObservableList) ((ListProperty) property).get())
						.removeListener((ListChangeListener) event);
			}
		}
	}

}
