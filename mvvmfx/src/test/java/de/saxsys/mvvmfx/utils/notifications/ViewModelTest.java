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

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import javafx.application.Platform;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(JfxRunner.class)
public class ViewModelTest {
	
	private static final String TEST_NOTIFICATION = "test_notification";
	private static final Object[] OBJECT_ARRAY_FOR_NOTIFICATION = new String[] { "test" };
	
	ViewModel viewModel;
	DummyNotificationObserver observer1;
	DummyNotificationObserver observer2;
	DummyNotificationObserver observer3;
	
	@Before
	public void init() {
		observer1 = Mockito.mock(DummyNotificationObserver.class);
		observer2 = Mockito.mock(DummyNotificationObserver.class);
		observer3 = Mockito.mock(DummyNotificationObserver.class);
		viewModel = new ViewModel() {
		};
	}

	@Test
	public void observerFromOutsideDoesNotReceiveNotifications() {
		MvvmFX.getNotificationCenter().subscribe(TEST_NOTIFICATION, observer1);
		viewModel.publish(TEST_NOTIFICATION);
		
		waitForUiThread();
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
	}
	
	@Test
	public void addObserverAndPublish() throws Exception {
		viewModel.subscribe(TEST_NOTIFICATION, observer1);
		viewModel.publish(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
		
		waitForUiThread();
		Mockito.verify(observer1).receivedNotification(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
	}
	
	@Test
	public void addAndRemoveObserverAndPublish() throws Exception {
		viewModel.subscribe(TEST_NOTIFICATION, observer1);
		viewModel.unsubscribe(observer1);
		viewModel.publish(TEST_NOTIFICATION);
		
		waitForUiThread();
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
		
		viewModel.subscribe(TEST_NOTIFICATION, observer1);
		viewModel.unsubscribe(TEST_NOTIFICATION, observer1);
		viewModel.publish(TEST_NOTIFICATION);
		
		waitForUiThread();
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
	}
	
	@Test
	public void addMultipleObserverAndPublish() throws Exception {
		viewModel.subscribe(TEST_NOTIFICATION, observer1);
		viewModel.subscribe(TEST_NOTIFICATION, observer2);
		viewModel.subscribe(TEST_NOTIFICATION, observer3);
		viewModel.publish(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
		
		waitForUiThread();
		
		Mockito.verify(observer1).receivedNotification(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
		Mockito.verify(observer2).receivedNotification(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
		Mockito.verify(observer3).receivedNotification(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
	}
	
	
	@Test
	public void addMultipleObserverAndRemoveOneAndPublish() throws Exception {
		viewModel.subscribe(TEST_NOTIFICATION, observer1);
		viewModel.subscribe(TEST_NOTIFICATION, observer2);
		viewModel.subscribe(TEST_NOTIFICATION, observer3);
		viewModel.unsubscribe(observer1);
		viewModel.publish(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
		
		waitForUiThread();
		
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION,
				OBJECT_ARRAY_FOR_NOTIFICATION);
		Mockito.verify(observer2).receivedNotification(TEST_NOTIFICATION,
				OBJECT_ARRAY_FOR_NOTIFICATION);
		Mockito.verify(observer3).receivedNotification(TEST_NOTIFICATION,
				OBJECT_ARRAY_FOR_NOTIFICATION);
	}


	/**
	 * See {@link DefaultNotificationCenterTest#removeObserverThatWasNotRegisteredYet()}.
	 */
	@Test
	public void removeObserverThatWasNotRegisteredYet() {
		viewModel.unsubscribe(observer1);
		
		viewModel.unsubscribe(TEST_NOTIFICATION, observer1);
	}
	
	/**
	 * This method is used to wait until the UI thread has done all work that was queued via
	 * {@link Platform#runLater(Runnable)}.
	 */
	private void waitForUiThread() {
		CompletableFuture<Void> future = new CompletableFuture<>();
		Platform.runLater(() -> future.complete(null));
		try {
			future.get(1l, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private class DummyNotificationObserver implements NotificationObserver {
		@Override
		public void receivedNotification(String key, Object... payload) {
			
		}
	}
}
