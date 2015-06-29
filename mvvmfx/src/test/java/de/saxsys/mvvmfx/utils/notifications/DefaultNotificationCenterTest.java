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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

public class DefaultNotificationCenterTest {
	
	private static final String TEST_NOTIFICATION = "test_notification";
	private static final String TEST_NOTIFICATION_2 = TEST_NOTIFICATION + "shouldnotget";
	private static final Object[] OBJECT_ARRAY_FOR_NOTIFICATION = new String[] { "test" };
	
	private NotificationCenter defaultCenter;
	
	DummyNotificationObserver observer1;
	DummyNotificationObserver observer2;
	DummyNotificationObserver observer3;
	
	@Before
	public void init() {
		observer1 = Mockito.mock(DummyNotificationObserver.class);
		observer2 = Mockito.mock(DummyNotificationObserver.class);
		observer3 = Mockito.mock(DummyNotificationObserver.class);
		defaultCenter = Mockito.spy(new DefaultNotificationCenter());
	}
	
	@Test
	public void addObserverToDefaultNotificationCenterAndPostNotification() throws Exception {
		defaultCenter.subscribe(TEST_NOTIFICATION, observer1);
		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1).receivedNotification(TEST_NOTIFICATION);
	}
	
	@Test
	public void addObserverToDefaultNotificationCenterAndPostObjectNotification() throws Exception {
		defaultCenter.subscribe(TEST_NOTIFICATION, observer1);
		defaultCenter.publish(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
		Mockito.verify(observer1).receivedNotification(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
	}
	
	@Test
	public void addAndRemoveObserverToDefaultNotificationCenterAndPostNotification() throws Exception {
		defaultCenter.subscribe(TEST_NOTIFICATION, observer1);
		defaultCenter.subscribe(TEST_NOTIFICATION, observer2);
		defaultCenter.subscribe(TEST_NOTIFICATION, observer3);
		defaultCenter.unsubscribe(observer1);
		defaultCenter.unsubscribe(observer2);
		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
		Mockito.verify(observer2, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
		Mockito.verify(observer3).receivedNotification(TEST_NOTIFICATION);
	}
	
	@Test
	public void addObserversToDefaultNotificationCenterAndPostNotification() throws Exception {
		defaultCenter.subscribe(TEST_NOTIFICATION, observer1);
		defaultCenter.subscribe(TEST_NOTIFICATION_2, observer2);
		defaultCenter.subscribe(TEST_NOTIFICATION, observer3);
		
		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.only()).receivedNotification(TEST_NOTIFICATION);
		Mockito.verify(observer2, Mockito.never()).receivedNotification(TEST_NOTIFICATION_2);
		Mockito.verify(observer3, Mockito.only()).receivedNotification(TEST_NOTIFICATION);
	}
	
	@Test
	public void addAndRemoveObserverForNameToDefaultNotificationCenterAndPostNotification() throws Exception {
		defaultCenter.subscribe(TEST_NOTIFICATION, observer1);
		defaultCenter.unsubscribe(TEST_NOTIFICATION, observer1);
		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
	}
	
	@Test
	public void subscribeSameObserverMultipleTimes() {
		defaultCenter.subscribe(TEST_NOTIFICATION, observer1);
		defaultCenter.subscribe(TEST_NOTIFICATION, observer1);
		
		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.times(2)).receivedNotification(TEST_NOTIFICATION);
	}
	
	@Test
	public void unsubscribeObserverThatWasSubscribedMultipleTimes() {
		defaultCenter.subscribe(TEST_NOTIFICATION, observer1);
		defaultCenter.subscribe(TEST_NOTIFICATION, observer1);
		defaultCenter.subscribe(TEST_NOTIFICATION, observer1);
		
		defaultCenter.unsubscribe(observer1);
		
		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
	}
	
	private class DummyNotificationObserver implements NotificationObserver {
		@Override
		public void receivedNotification(String key, Object... payload) {
			
		}
	}
	
}
