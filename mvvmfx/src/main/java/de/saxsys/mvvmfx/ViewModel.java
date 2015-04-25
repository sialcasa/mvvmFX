/*******************************************************************************
 * Copyright 2014 Alexander Casall, Manuel Mauky
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
package de.saxsys.mvvmfx;

import javafx.application.Platform;
import de.saxsys.mvvmfx.internal.viewloader.View;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import de.saxsys.mvvmfx.utils.notifications.NotificationObserver;

/**
 * <p>
 * Marker interface for a View Model. Some additional hints to this layer:
 * </p>
 * 
 * <p>
 * Never create a dependency to the view in this layer - if you have to create a new view after a business step, notify
 * the view with the {@link NotificationCenter} or a callback that the view can create the new view. When you write a
 * new {@link View} you should create the associated {@link ViewModel} with tests before.
 * </p>
 * 
 * @author alexander.casall
 * 
 */
public interface ViewModel {
	
	/**
	 * Publishes a notification to the subscribers of the notificationId. This notification will be send to the
	 * UI-Thread.
	 * 
	 * @param messageName
	 *            of the notification
	 * @param payload
	 *            to be send
	 */
	default void publish(String messageName, Object... payload) {
		if (!Platform.isFxApplicationThread()) {
			MvvmFX.getNotificationCenter().publish(this, messageName, payload);
		} else {
			MvvmFX.getNotificationCenter().publish(this, messageName, payload);
		}
	}
	
	/**
	 * Subscribe to a notification with a given {@link NotificationObserver}.
	 * 
	 * @param messageName
	 *            of the Notification
	 * @param observer
	 *            which should execute when the notification occurs
	 */
	default void subscribe(String messageName, NotificationObserver observer) {
		MvvmFX.getNotificationCenter().subscribe(this, messageName, observer);
	}
	
	/**
	 * Remove the observer for a specific notification by a given messageName.
	 * 
	 * @param messageName
	 *            of the notification for that the observer should be removed
	 * @param observer
	 *            to remove
	 */
	default void unsubscribe(String messageName, NotificationObserver observer) {
		MvvmFX.getNotificationCenter().unsubscribe(this, messageName, observer);
	}
	
	/**
	 * Removes the observer for all messages.
	 * 
	 * @param observer
	 *            to be removed
	 */
	default void unsubscribe(NotificationObserver observer) {
		MvvmFX.getNotificationCenter().unsubscribe(this, observer);
	}
}
