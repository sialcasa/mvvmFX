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
package de.saxsys.mvvmfx.utils.listener;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;

/**
 * The {@link ListenerManager} is used to be able to clean up listeners when they are not used anymore. A typical use
 * case is a component that registers some listeners to its properties. When the component isn't used anymore the
 * listeners needs to be removed from the properties. Otherwise it could lead to memory leaks as the garbage collector
 * can't delete the objects as there are still references available.
 * 
 * With the {@link ListenerManager} you can reduce the possibility to forget a listener because all listeners can be
 * removed with one single method {@link ListenerManager#clean()}.
 * 
 * @author manuel.mauky
 * 
 */
public class ListenerManager implements ICleanable {
	
	@SuppressWarnings("rawtypes")
	private final Map<ObservableValue<?>, Set<ChangeListener>> simpleChangeListeners = new WeakHashMap<>();
	@SuppressWarnings("rawtypes")
	private final Map<ObservableList<?>, Set<ListChangeListener>> listChangeListeners = new WeakHashMap<>();
	
	private final Map<Observable, Set<InvalidationListener>> invalidationListeners = new WeakHashMap<>();
	
	/**
	 * Register the given {@link ChangeListener} to the {@link ObservableValue}. The listener is added to the observable
	 * and will be added for management so it can be cleaned up with the {@link #clean()} method.
	 * 
	 * @param observable
	 *            the observable that the listener is added to.
	 * @param listener
	 *            the listener that is registered.
	 * @param <T>
	 *            the generic type of the observable value.
	 */
	public <T> void register(ObservableValue<T> observable, ChangeListener<? super T> listener) {
		if (!simpleChangeListeners.containsKey(observable)) {
			this.simpleChangeListeners.put(observable, Collections.newSetFromMap(new WeakHashMap<>()));
		}
		
		Set<ChangeListener> observers = this.simpleChangeListeners.get(observable);
		observers.add(listener);
		observable.addListener(listener);
	}
	
	/**
	 * Register the given {@link ListChangeListener} to the {@link ObservableList}. The listener is added to the
	 * observable and will be added for management so it can be cleaned up with the {@link #clean()} method.
	 * 
	 * @param observable
	 *            the observable list that the listener is added to.
	 * @param listener
	 *            the listener that is registered.
	 * @param <T>
	 *            the generic type of the observable list.
	 */
	public <T> void register(ObservableList<T> observable, ListChangeListener<? super T> listener) {
		if (!listChangeListeners.containsKey(observable)) {
			this.listChangeListeners.put(observable, Collections.newSetFromMap(new WeakHashMap<>()));
		}
		
		Set<ListChangeListener> observers = this.listChangeListeners.get(observable);
		observers.add(listener);
		observable.addListener(listener);
	}
	
	/**
	 * Register the given {@link InvalidationListener} to the {@link Observable} . The listener is added to the
	 * observable and will be added for management so it can be cleaned up with the {@link #clean()} method.
	 * 
	 * @param observable
	 *            the observable that the listener is added to.
	 * @param listener
	 *            the listener that is registered.
	 */
	public void register(Observable observable, InvalidationListener listener) {
		if (!invalidationListeners.containsKey(observable)) {
			this.invalidationListeners.put(observable, Collections.newSetFromMap(new WeakHashMap<>()));
		}
		
		Set<InvalidationListener> observers = this.invalidationListeners.get(observable);
		observers.add(listener);
		observable.addListener(listener);
	}
	
	@Override
	public void clean() {
		clearMap(simpleChangeListeners, (observable, listener) -> observable.removeListener(listener));
		clearMap(listChangeListeners, (observable, listener) -> observable.removeListener(listener));
		clearMap(invalidationListeners, (observable, listener) -> observable.removeListener(listener));
	}
	
	/**
	 * This method is used to clear the given map. To do this you need to implement a BiConsumer that calls the specific
	 * method to remove a listener from an observable.
	 * 
	 * This needs to be done as there is no common interface with a remove method that all types of observables are
	 * implementing. Therefore the method call to the specific removeListener method needs to be done in an extra
	 * function.
	 * 
	 * @param map
	 *            the multimap that contains the observables and listeners.
	 * @param consumer
	 *            a function that calls the specific remove method for the given types.
	 */
	private <T, U> void clearMap(Map<T, Set<U>> map, BiConsumer<T, U> consumer) {
		for (T observable : map.keySet()) {
			for (U listener : map.get(observable)) {
				consumer.accept(observable, listener);
			}
		}
		map.clear();
	}
}
