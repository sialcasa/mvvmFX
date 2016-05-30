/*******************************************************************************
 * Copyright 2016 Alexander Casall, Manuel Mauky
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

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Objects;


/**
 *
 * This class is a wrapper of a {@link NotificationObserver} that only
 * holds a weak reference to the observer.
 * <p>
 * When using normal {@link NotificationObserver} in combination with the global {@link NotificationObserver}
 * the notification center will hold a reference to the observer as long as it isn't unregistered.
 * The observer itself will most likely hold a reference to the parent class which can prevent
 * the parent class from being garbage collected.
 * In many use cases this is not a problem but in some situations this can cause memory leaks.
 *
 *
 * <p>
 * For such situations this class can be used as a Wrapper around the normal notification observer.
 * The usage should look like this:
 *
 * <pre>
 *
 * public class MyClass {
 *
 *     private NotificationObserver observer;
 *
 *     ...
 *
 *     public void someMethod() {
 *         observer = (key, payload) -> {
 *           // do something when observer is called.
 *
 *         };
 *
 *         notificationCenter.subscribe("some_topic", new WeakNotificationObserver(observer));
 *     }
 * }
 * </pre>
 *
 * The example shows the following steps:
 *
 * <ul>
 *     <li>Create a field for your observer of type {@link NotificationObserver}</li>
 *     <li>Create an instance of your observer, for example as a lambda.</li>
 *     <li>subscribe to a topic by creating a new instance of {@link WeakNotificationObserver}
 *     and pass the normal observer as constructor argument</li>
 * </ul>
 *
 * It's important to hold a hard reference to the normal observer in your class. This can be done
 * by creating a field for the observer. This way you prevent the observer from being garbage collected to early.
 * Creating  a local variable in the body of a method will <strong>not</strong> work!
 * <p>
 *
 * Using the pattern mentioned above will:
 * <ul>
 *     <li>prevent the observer from being garbage collected to early</li>
 *     <li>allow the garbage collector to collect the parent class as soon as it isn't references in other places</li>
 * </ul>
 *
 * @author manuel.mauky
 */
public final class WeakNotificationObserver implements NotificationObserver {

	private final Reference<NotificationObserver> reference;

	public WeakNotificationObserver(NotificationObserver notificationObserver) {
		reference = new WeakReference<>(Objects.requireNonNull(notificationObserver));
	}

	@Override
	public void receivedNotification(String key, Object... payload) {
		NotificationObserver observer = reference.get();
		if (observer != null) {
			observer.receivedNotification(key, payload);
		}
	}

	/**
	 * @return the reference of the wrapped {@link NotificationObserver}.
	 * If the wrapped observer was already garbage collected, this returns <code>null</code>
	 */
	NotificationObserver getWrappedObserver() {
		return reference.get();
	}

}