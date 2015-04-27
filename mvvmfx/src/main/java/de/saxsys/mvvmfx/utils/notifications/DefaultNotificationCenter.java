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
import java.util.stream.Collectors;

import de.saxsys.mvvmfx.ViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of {@link NotificationCenter}.
 * 
 * @author sialcasa
 * 
 */
class DefaultNotificationCenter implements NotificationCenter {
	
	private static final Logger LOG = LoggerFactory.getLogger(DefaultNotificationCenter.class);
	
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
		removeObserverFromObserverMap(observer, globalObservers);
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
	public void unsubscribe(ViewModel viewModel, NotificationObserver observer) {
		ObserverMap observerMap = viewModelObservers.get(viewModel);
		removeObserverFromObserverMap(observer, observerMap);
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
		
		if(observers.contains(observer)) {
			LOG.warn("Subscribe the observer ["+ observer + "] for the message [" + messageName + 
					"], but the same observer was already added for this message in the past.");	
		}
		observers.add(observer);
	}
	
	
	
	private void removeObserverFromObserverMap(NotificationObserver observer, ObserverMap observerMap) {
		for (String key : observerMap.keySet()) {
			final List<NotificationObserver> observers = observerMap.get(key);
			
			final List<NotificationObserver> observersToBeRemoved = observers
					.stream()
					.filter(actualObserver -> actualObserver.equals(observer))
					.collect(Collectors.toList());
			
			observers.removeAll(observersToBeRemoved);
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
