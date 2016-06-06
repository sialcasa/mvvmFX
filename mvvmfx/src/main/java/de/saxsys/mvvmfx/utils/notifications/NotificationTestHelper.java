/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
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

import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * The {@link NotificationTestHelper} is used to simplify the testing of 
 * notifications. It is especially useful when notifications are send from
 * different thread and when testing the direct notification between a viewModel and the View 
 * (via {@link ViewModel#publish(String, Object...)} and {@link ViewModel#subscribe(String, NotificationObserver)})
 * <p>
 * This class implements {@link NotificationObserver} and therefore can be added as subscriber. It will record
 * every received notification and can be tested afterwards.
 * <p>
 *     
 * The {@link ViewModel#publish(String, Object...)} method will send all notifications on the JavaFX UI thread.
 * Therefore when testing the publishing of notifications JavaFX has to be running which isn't the case 
 * with plain JUnit tests. The {@link NotificationTestHelper} will take care for thread handling.
 * 
 * <p>
 * Example: 
 * <p>
 *     
 * <pre>
 *     
 *     public class MyViewModel implements ViewModel {
 *         public static final String ACTION_KEY = "my-action";
 *     
 *         public void someAction() {
 *             ...
 *             publish(ACTION_KEY);
 *         }
 *     }
 *     
 *     // unit test
 *     {@code @Test}
 *     public void testSomething() {
 *         MyViewModel viewModel = new MyViewModel();
 *         
 *         NotificationTestHelper helper = new NotificationTestHelper();
 *         viewModel.subscribe(MyViewModel.ACTION_KEY, helper);
 *         
 *         
 *         viewModel.someAction();
 *         
 *         assertEquals(1, helper.numberOfReceivedNotifications());
 *     }
 * </pre>
 * 
 * 
 * 
 * You can provide a timeout as constructor parameter. 
 * This is useful in case of asynchronous code (f.e. when notifications are send from another Thread).
 * 
 * By default the timeout is set to {@value #DEFAULT_TIMEOUT}. When you have a long running thread
 * you should use a higher timeout.
 * 
 * @author manuel.mauky
 */
public class NotificationTestHelper implements NotificationObserver {
	
	public static final long DEFAULT_TIMEOUT = 0l;
	
	private List<Pair<String, Object[]>> notifications = new ArrayList<>();
	
	private long timeout = DEFAULT_TIMEOUT;

	/**
	 * Create a test helper with a default timeout of {@value #DEFAULT_TIMEOUT} millis.
	 */
	public NotificationTestHelper() {
		new JFXPanel();
	}

	/**
	 * Create a test helper with the given timeout in millis.
	 * 
	 * @param timeoutInMillis the timeout.
	 */
	public NotificationTestHelper(long timeoutInMillis) {
		this();
		this.timeout = timeoutInMillis;
	}
	
	@Override
	public void receivedNotification(String key, Object... payload) {
		notifications.add(new Pair<>(key, payload));
	}

	/**
	 * @return the number of received notifications.
	 */
	public int numberOfReceivedNotifications() {
		waitForUiThread();
		return notifications.size();
	}

	/**
	 * @param key the key of the notification.
	 * @return the number of received notifications for the given key.
	 */
	public int numberOfReceivedNotifications(String key) {
		waitForUiThread();
		return (int) notifications.stream()
				.filter(pair -> pair.getKey().equals(key))
				.count();
	}
	
	private void waitForUiThread() {
		CompletableFuture<Void> future = new CompletableFuture<>();

		Platform.runLater(() -> {
			if(timeout > 0) {
				try {
					Thread.sleep(timeout);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			future.complete(null);
		});

		try {
			future.get(timeout+50, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
