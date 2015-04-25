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
package de.saxsys.mvvmfx.utils.notifications;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.saxsys.mvvmfx.ViewModel;

/**
 * Default implementation of {@link NotificationCenter}.
 * 
 * @author sialcasa
 * 
 */
class DefaultNotificationCenter implements NotificationCenter {
	
	DefaultNotificationCenter() {
	}
	
	private final ObserverMap globalObservers = new ObserverMap();
	private final ViewModelObservers viewModelObservers = new ViewModelObservers();
	
	@Override
	public void subscribe(String messageName, NotificationObserver observer) {
		addObserver(messageName, observer, globalObservers);
	}
	
	@Override
	public void unsubscribe(String messageName, NotificationObserver observer) {
		removeObserversForMessageName(messageName, observer, globalObservers);
	}
	
	@Override
	public void unsubscribe(NotificationObserver observer) {
		removeAllObserver(observer, globalObservers);
	}
	
	@Override
	public void publish(String messageName, Object... payload) {
		publish(messageName, payload, globalObservers);
	}
	
	@Override
	public void publish(ViewModel viewModel, String messageName, Object[] payload) {
		ObserverMap observerMap = viewModelObservers.get(viewModel);
		if (observerMap != null) {
			publish(messageName, payload, observerMap);
		}
	}
	
	
	@Override
	public void subscribe(ViewModel view, String messageName, NotificationObserver observer) {
		ObserverMap observerMap = viewModelObservers.get(view);
		if (observerMap == null) {
			observerMap = new ObserverMap();
			viewModelObservers.put(view, observerMap);
		}
		addObserver(messageName, observer, observerMap);
	}
	
	@Override
	public void unsubscribe(ViewModel view, String messageName, NotificationObserver observer) {
		ObserverMap observerMap = viewModelObservers.get(view);
		if (observerMap != null) {
			removeObserversForMessageName(messageName, observer, observerMap);
		}
	}
	
	
	@Override
	public void unsubscribe(ViewModel view, NotificationObserver observer) {
		ObserverMap observerMap = viewModelObservers.get(view);
		
		removeAllObserver(observer, observerMap);
		
	}
	
	/*
	 * Helper
	 */
	
	private void publish(String messageName, Object[] payload, ObserverMap observerMap) {
		Collection<NotificationObserver> notificationReceivers = observerMap.get(messageName);
		if (notificationReceivers != null) {
			for (NotificationObserver observer : notificationReceivers) {
				observer.receivedNotification(messageName, payload);
			}
		}
	}
	
	private void addObserver(String messageName, NotificationObserver observer, ObserverMap observerMap) {
		List<NotificationObserver> observers = observerMap.get(messageName);
		if (observers == null) {
			observerMap.put(messageName, new ArrayList<NotificationObserver>());
		}
		observers = observerMap.get(messageName);
		observers.add(observer);
	}
	
	
	
	private void removeAllObserver(NotificationObserver observer, ObserverMap observerMap) {
		Iterator<String> iterator = observerMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Iterator<NotificationObserver> iterator2 = observerMap.get(key).iterator();
			while (iterator2.hasNext()) {
				NotificationObserver actualObserver = iterator2.next();
				if (actualObserver == observer) {
					observerMap.remove(key);
					break;
				}
			}
		}
	}
	
	private void removeObserversForMessageName(String messageName, NotificationObserver observer,
			ObserverMap observerMap) {
		List<NotificationObserver> observers = observerMap.get(messageName);
		observers.remove(observer);
		if (observers.size() == 0) {
			observerMap.remove(messageName);
		}
	}
	
	@SuppressWarnings("serial")
	private class ObserverMap extends HashMap<String, List<NotificationObserver>> {
	}
	
	@SuppressWarnings("serial")
	private class ViewModelObservers extends HashMap<ViewModel, ObserverMap> {
	}
	
	
	
}
