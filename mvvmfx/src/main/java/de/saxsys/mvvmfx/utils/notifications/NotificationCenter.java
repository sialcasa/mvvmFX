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

/**
 * Central component to provide a notification mechanism. You can add observers by using keys to get notifications for
 * it. If you want you can pass an
 * 
 * {@code Object[]} with a notification.
 * 
 * <p>
 *    
 * There is a util {@link NotificationTestHelper} that's purpose is to simplify testing of notifications in Unit-Tests.
 * 
 * 
 * @author sialcasa
 * 
 */
public interface NotificationCenter {
	
	
	
	/**
	 * Add an observer to the NotificationCenter which gets notifications for the given String.
	 * 
	 * Please note: It is possible (but yet untypical) to add the same observer for the same message multiple times. 
	 * In this case the observer will be invoked multiple times too. 
	 * As this behaviour is unusual, the default notification center will log a warning message when the same observer
	 * is added multiple times for the same message.
	 * 
	 * @param messageName
	 *            key of the notification to listen
	 * @param observer
	 *            which listens for the notification
	 */
	void subscribe(String messageName,
			NotificationObserver observer);
	
	/**
	 * Removes an observer from the NotificationCenter.
	 * 
	 * @param messageName
	 *            key of the notification to remove
	 * @param observer
	 *            which listens for the notification
	 */
	void unsubscribe(String messageName,
			NotificationObserver observer);
	
	/**
	 * Remove all registrations of an NotificationObserver.
	 * 
	 * @param observer
	 *            for remove all notifications
	 */
	void unsubscribe(NotificationObserver observer);
	
	/**
	 * Post a notification to all NotificationObserver which are registered with the given String.
	 *
	 * You can additionally add a varying number of Objects as a payload that the observer will receive.
	 * 
	 * @param messageName
	 *            of the notification which sould be send
	 * @param payload
	 *            which should be passed
	 */
	void publish(String messageName, Object... payload);
	
	
	/**
	 * Publishes a notification on a specific channel. The channel can be any object that can be distinguished by the equals method.
     * Only subscribers that use the same channel object can receive the published message.
	 *
     * @param channel
     *            a channel object.
     *
	 * @param messageName
	 *            of the notification
	 * @param payload
	 *            to be send
	 */
	void publish(Object channel, String messageName, Object[] payload);
	
	/**
     *
	 * Subscribe to a notification with a given {@link NotificationObserver} on a specific channel.
     * See {@link #publish(Object, String, Object[])} for more information on channels.
	 * 
	 * @param channel a channel object
	 * 
	 * @param messageName
	 *            of the Notification
	 * @param observer
	 *            which should execute when the notification occurs
	 */
	void subscribe(Object channel, String messageName,
			NotificationObserver observer);
	
	/**
	 * Removes a {@link NotificationObserver} for a given messageName.
	 * 
	 * @param channel
	 * @param messageName
	 * @param observer
	 */
	void unsubscribe(Object channel, String messageName,
			NotificationObserver observer);
	
	/**
	 * Removes a {@link NotificationObserver} for all messageName.
	 * 
	 * @param channel
	 * @param observer
	 */
	void unsubscribe(Object channel,
			NotificationObserver observer);


	/**
	 * Clears all {@link NotificationObserver} subscriptions in the current {@link NotificationCenter}
	 * for session aware applications that needs to perform a complete reset on logout.
	 */
	void clear();
}
