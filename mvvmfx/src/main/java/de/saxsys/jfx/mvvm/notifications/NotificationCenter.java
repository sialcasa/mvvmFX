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
package de.saxsys.jfx.mvvm.notifications;


/**
 * Central component to provide a notification mechanism. You can add observers by using keys to get notifications for
 * it. If you want you can pass an
 * 
 * @Object[] with a notification.
 * 
 * @author sialcasa
 * 
 */
public interface NotificationCenter {
	
	/**
	 * Add an observer to the @MVVMNotificationCenter which gets notifications for the given @String.
	 * 
	 * @param name
	 *            key of the notification to listen
	 * @param observer
	 *            which listens for the notification
	 */
	public abstract void addObserverForName(String name,
			NotificationObserver observer);
	
	/**
	 * Removes an observer from the @MVVMNotificationCenter.
	 * 
	 * @param name
	 *            key of the notification to remove
	 * @param observer
	 *            which listens for the notification
	 */
	public abstract void removeObserverForName(String name,
			NotificationObserver observer);
	
	/**
	 * Remove all registrations of an @MVVMNotificationObserver.
	 * 
	 * @param observer
	 *            for remove all notifications
	 */
	public abstract void removeObserver(NotificationObserver observer);
	
	/**
	 * Post a notification to all @MVVMNotificationObserver which are registered with the given @String. You can pass
	 * 
	 * @Object[].
	 * 
	 * @param name
	 *            of the notification which sould be send
	 * @param objects
	 *            which should be passed
	 */
	public abstract void postNotification(String name, Object... objects);
	
}
