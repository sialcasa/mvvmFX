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

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
	private final ChannelObserverMap channelObserverMap = new ChannelObserverMap();
	
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

	/**
	 *  This notification will be send to the UI-Thread (if the UI-toolkit was bootstrapped).
	 *  If no UI-Toolkit is available the notification will be directly published. This is typically the case in unit tests.
	 *
	 * @param channel 	the channel
	 * @param messageName 	the message to sent
	 * @param payload 		additional arguments to the message
	 */
	@Override
	public void publish(Object channel, String messageName, Object[] payload) {
		if(channelObserverMap.containsKey(channel)) {
			final ObserverMap observerMap = channelObserverMap.get(channel);
			
			if (Platform.isFxApplicationThread()) {
				publish(messageName, payload, observerMap);
			} else {
				try {
					Platform.runLater(() -> publish(messageName, payload, observerMap));
				} catch(IllegalStateException e) {

					// If the toolkit isn't initialized yet we will publish the notification directly.
					// In most cases this means that we are in a unit test and not JavaFX application is running.
					if(e.getMessage().equals("Toolkit not initialized")) {
						publish(messageName, payload, observerMap);
					} else {
						throw e;
					}
				}
			}
		}
	}
	
	
	@Override
	public void subscribe(Object channel, String messageName, NotificationObserver observer) {
		if(!channelObserverMap.containsKey(channel)) {
			channelObserverMap.put(channel, new ObserverMap());
		}
		
		final ObserverMap observerMap = channelObserverMap.get(channel);
		addObserver(messageName, observer, observerMap);
	}
	
	@Override
	public void unsubscribe(Object channel, String messageName, NotificationObserver observer) {
		if(channelObserverMap.containsKey(channel)) {
			final ObserverMap observerMap = channelObserverMap.get(channel);
			removeObserversForMessageName(messageName, observer, observerMap);
		}
	}
	
	
	@Override
	public void unsubscribe(Object channel, NotificationObserver observer) {
		if(channelObserverMap.containsKey(channel)){
			ObserverMap observerMap = channelObserverMap.get(channel);
			removeObserverFromObserverMap(observer, observerMap);
		}
	}
	
	/*
	 * Helper
	 */
	
	private void publish(String messageName, Object[] payload, ObserverMap observerMap) {
		Collection<NotificationObserver> notificationReceivers = observerMap.get(messageName);
		if (notificationReceivers != null) {
			
			// make a copy to prevent ConcurrentModificationException if inside of an observer a new observer is subscribed.
			final Collection<NotificationObserver> copy = new ArrayList<>(notificationReceivers);
			
			for (NotificationObserver observer : copy) {
				observer.receivedNotification(messageName, payload);
			}
		}
	}
	
	private void addObserver(String messageName, NotificationObserver observer, ObserverMap observerMap) {
		if(!observerMap.containsKey(messageName)) {
			observerMap.put(messageName, new ArrayList<>());
		}
		
		final List<NotificationObserver> observers = observerMap.get(messageName);
		
		if(observers.contains(observer)) {
			LOG.warn("Subscribe the observer ["+ observer + "] for the message [" + messageName + 
					"], but the same observer was already added for this message in the past.");	
		}
		observers.add(observer);
	}
	
	
	
	private void removeObserverFromObserverMap(NotificationObserver observer, ObserverMap observerMap) {
		for (String key : observerMap.keySet()) {
			final List<NotificationObserver> observers = observerMap.get(key);
			
			observers.removeIf(actualObserver -> actualObserver.equals(observer));
		}
	}
	
	private void removeObserversForMessageName(String messageName, NotificationObserver observer,
			ObserverMap observerMap) {
		
		if(observerMap.containsKey(messageName)) {
			final List<NotificationObserver> observers = observerMap.get(messageName);
			observers.removeIf(actualObserver -> actualObserver.equals(observer));
			if (observers.size() == 0) {
				observerMap.remove(messageName);
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class ObserverMap extends HashMap<String, List<NotificationObserver>> {
	}
	
	@SuppressWarnings("serial")
	private class ChannelObserverMap extends HashMap<Object, ObserverMap> {
	}
	
	
	
}
