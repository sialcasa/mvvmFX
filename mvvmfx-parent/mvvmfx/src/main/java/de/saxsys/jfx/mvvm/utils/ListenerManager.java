/*******************************************************************************
 * Copyright 2013 manuel.mauky, alexander.casall
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
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * The {@link ListenerManager} is used to be able to clean up listeners when
 * they are not used anymore. A typical use case is a component that registers
 * some listeners to its properties. When the component isn't used anymore the
 * listeners needs to be removed from the properties. Otherwise it could lead to
 * memory leaks as the garbage collector can't delete the objects as there are
 * still references available.
 * 
 * With the {@link ListenerManager} you can reduce the possibility to forget a
 * listener because all listeners can be removed with one single method
 * {@link ListenerManager#clean()}.
 * 
 * @author manuel.mauky
 * 
 */
public class ListenerManager implements ICleanable {

	@SuppressWarnings("rawtypes")
	private final Multimap<ObservableValue<?>, ChangeListener> simpleChangeListeners = ArrayListMultimap
			.<ObservableValue<?>, ChangeListener> create();
	@SuppressWarnings("rawtypes")
	private final Multimap<ObservableList<?>, ListChangeListener> listChangeListeners = ArrayListMultimap
			.<ObservableList<?>, ListChangeListener> create();

	private final Multimap<Observable, InvalidationListener> invalidationListeners = ArrayListMultimap
			.<Observable, InvalidationListener> create();

	/**
	 * Register the given {@link ChangeListener} to the {@link ObservableValue}.
	 * The listener is added to the observable and will be added for management
	 * so it can be cleaned up with the {@link #clean()} method.
	 * 
	 * @param observable
	 * @param listener
	 */
	public <T> void register(ObservableValue<T> observable,
			ChangeListener<? super T> listener) {
		observable.addListener(listener);
		simpleChangeListeners.put(observable, listener);
	}

	/**
	 * Register the given {@link ListChangeListener} to the
	 * {@link ObservableList}. The listener is added to the observable and will
	 * be added for management so it can be cleaned up with the {@link #clean()}
	 * method.
	 * 
	 * @param observable
	 * @param listener
	 */
	public <T> void register(ObservableList<T> observable,
			ListChangeListener<? super T> listener) {
		observable.addListener(listener);
		listChangeListeners.put(observable, listener);
	}

	/**
	 * Register the given {@link InvalidationListener} to the {@link Observable}
	 * . The listener is added to the observable and will be added for
	 * management so it can be cleaned up with the {@link #clean()} method.
	 * 
	 * @param observable
	 * @param listener
	 */
	public void register(Observable observable, InvalidationListener listener) {
		observable.addListener(listener);
		invalidationListeners.put(observable, listener);
	}

	@Override
	public void clean() {
		clearMap(simpleChangeListeners,
				new BiConsumer<ObservableValue<?>, ChangeListener>() {
					@Override
					public void accept(ObservableValue<?> observable,
							ChangeListener listener) {
						observable.removeListener(listener);
					}
				});
		clearMap(listChangeListeners,
				new BiConsumer<ObservableList<?>, ListChangeListener>() {
					@Override
					public void accept(ObservableList<?> observable,
							ListChangeListener listener) {
						observable.removeListener(listener);
					}
				});

		clearMap(invalidationListeners,
				new BiConsumer<Observable, InvalidationListener>() {
					@Override
					public void accept(Observable observable,
							InvalidationListener listener) {
						observable.removeListener(listener);
					}
				});

	}

	/**
	 * This method is used to clear the given map. To do this you need to
	 * implement a BiConsumer that calls the specific method to remove a
	 * listener from an observable.
	 * 
	 * This needs to be done as there is no common interface with a remove
	 * method that all types of observables are implementing. Therefore the
	 * method call to the specific removeListener method needs to be done in an
	 * extra function.
	 * 
	 * @param map
	 *            the multimap that contains the observables and listeners.
	 * @param consumer
	 *            a function that calls the specific remove method for the given
	 *            types.
	 */
	private <T, U> void clearMap(Multimap<T, U> map, BiConsumer<T, U> consumer) {
		for (T observable : map.keySet()) {
			for (U listener : map.get(observable)) {
				consumer.accept(observable, listener);
			}
		}
		map.clear();
	}

	/**
	 * This is a function interface to implement the cleanup for different types
	 * of listeners and observables.
	 * 
	 * The naming of this interface is based on the function interfaces of java
	 * version 8. This is done to support an easier migration in the future.
	 * 
	 * @author manuel.mauky
	 * 
	 * @param <T>
	 * @param <U>
	 */
	private static interface BiConsumer<T, U> {
		void accept(T t, U u);
	}

}
