/*******************************************************************************
 * Copyright 2013 Alexander Casall
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Default implementation of {@link NotificationCenter}.
 *
 * @author sialcasa
 *
 */
public class DefaultNotificationCenter implements NotificationCenter {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultNotificationCenter.class);

	private final ObserverMap globalObservers = new ObserverMap();
	private final ChannelObserverMap channelObserverMap = new ChannelObserverMap();

	@Override
	public void subscribe(String messageName, NotificationObserver observer) {
		if(observer==null) {
			throw new IllegalArgumentException("The observer must not be null.");
		}
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
	 * @param channel    the channel
	 * @param messageName    the message to sent
	 * @param payload        additional arguments to the message
	 */
	@Override
	public void publish(Object channel, String messageName, Object[] payload) {
		if (channelObserverMap.containsKey(channel)) {
			final ObserverMap observerMap = channelObserverMap.get(channel);
			
			if (shouldPublishInThisThread()) {
				publish(messageName, payload, observerMap);
			} else {
				try {
					Platform.runLater(() -> publish(messageName, payload, observerMap));
				} catch (IllegalStateException e) {

					// If the toolkit isn't initialized yet we will publish the notification directly.
					// In most cases this means that we are in a unit test and not JavaFX application is running.
					if (e.getMessage().equals("Toolkit not initialized")) {
						publish(messageName, payload, observerMap);
					} else {
						throw e;
					}
				}
			}
		}
	}


	private boolean shouldPublishInThisThread() {
		try {
			return Platform.isFxApplicationThread();
		} catch (final RuntimeException e) {
			if (e.getMessage().equals("No toolkit found")) {
				// If the toolkit is not even available, we publish the notification directly.
				// In most cases this means that we are in an environment where no JavaFX 
				// application is running (probably also in a JUnit test).
				return true;
			} else {
				throw e;
			}
		}
	}

	@Override
	public void subscribe(Object channel, String messageName, NotificationObserver observer) {
		if (!channelObserverMap.containsKey(channel)) {
			channelObserverMap.put(channel, new ObserverMap());
		}

		final ObserverMap observerMap = channelObserverMap.get(channel);
		addObserver(messageName, observer, observerMap);
	}

	@Override
	public void unsubscribe(Object channel, String messageName, NotificationObserver observer) {
		if (channelObserverMap.containsKey(channel)) {
			final ObserverMap observerMap = channelObserverMap.get(channel);
			removeObserversForMessageName(messageName, observer, observerMap);

			if (observerMap.isEmpty()) {
				channelObserverMap.remove(channel);
			}
		}
	}


	@Override
	public void unsubscribe(Object channel, NotificationObserver observer) {
		if (channelObserverMap.containsKey(channel)) {
			ObserverMap observerMap = channelObserverMap.get(channel);
			removeObserverFromObserverMap(observer, observerMap);

			if (observerMap.isEmpty()) {
				channelObserverMap.remove(channel);
			}
		}
	}

	@Override
	public void clear() {
		this.globalObservers.clear();
		this.channelObserverMap.clear();
	}

	/*
	 * Helper
	 */

	private static void publish(String messageName, Object[] payload, ObserverMap observerMap) {
		Collection<NotificationObserver> notificationReceivers = observerMap.get(messageName);
		if (notificationReceivers != null) {

			// make a copy to prevent ConcurrentModificationException if inside of an observer a new observer is subscribed.

			for (NotificationObserver observer : notificationReceivers) {
				observer.receivedNotification(messageName, payload);
			}
		}
	}

	private static void addObserver(String messageName, NotificationObserver observer, ObserverMap observerMap) {
		if (!observerMap.containsKey(messageName)) {
			// use CopyOnWriteArrayList to prevent ConcurrentModificationException if inside of an observer a new observer is subscribed.
			observerMap.put(messageName, new CopyOnWriteArrayList<>());
		}

		final List<NotificationObserver> observers = observerMap.get(messageName);

		if (observers.contains(observer)) {
			LOG.warn("Subscribe the observer [" + observer + "] for the message [" + messageName +
					"], but the same observer was already added for this message in the past.");
		}
		observers.add(observer);
	}


	private static void removeObserverFromObserverMap(NotificationObserver observer, ObserverMap observerMap) {
		for (String key : observerMap.keySet()) {
			final List<NotificationObserver> observers = observerMap.get(key);

			removeObserverFromObserverList(observer, observers);

			if (observers.isEmpty()) {
				observerMap.remove(key);
			}
		}
	}

	private static void removeObserverFromObserverList(NotificationObserver observer, List<NotificationObserver> observerList) {
		observerList.removeIf(actualObserver -> actualObserver.equals(observer));

		observerList.removeIf(actualObserver -> {
			if(actualObserver instanceof WeakNotificationObserver) {
				WeakNotificationObserver weakObserver = (WeakNotificationObserver) actualObserver;

				NotificationObserver wrappedObserver = weakObserver.getWrappedObserver();

				if(wrappedObserver == null) { // if reference was GCed we can remove the weakObserver
					return true;
				} else {
					return wrappedObserver.equals(observer);
				}
			}

			return false;
		});
	}

	private static void removeObserversForMessageName(String messageName, NotificationObserver observer,
											   ObserverMap observerMap) {

		if (observerMap.containsKey(messageName)) {
			final List<NotificationObserver> observers = observerMap.get(messageName);
			removeObserverFromObserverList(observer, observers);

			if (observers.isEmpty()) {
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
