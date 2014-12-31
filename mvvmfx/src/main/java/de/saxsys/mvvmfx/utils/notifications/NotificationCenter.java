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
 * @author sialcasa
 * 
 */
public interface NotificationCenter {
	
	
	
	/**
	 * Add an observer to the NotificationCenter which gets notifications for the given String.
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
	
}
