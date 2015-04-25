package de.saxsys.mvvmfx;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.saxsys.mvvmfx.utils.notifications.NotificationObserver;

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
	public void addObserverAndPublish() throws Exception {
		viewModel.subscribe(TEST_NOTIFICATION, observer1);
		viewModel.publish(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
		Mockito.verify(observer1).receivedNotification(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
	}
	
	@Test
	public void addAndRemoveObserverAndPublish() throws Exception {
		viewModel.subscribe(TEST_NOTIFICATION, observer1);
		viewModel.unsubscribe(observer1);
		viewModel.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
		
		viewModel.subscribe(TEST_NOTIFICATION, observer1);
		viewModel.unsubscribe(TEST_NOTIFICATION, observer1);
		viewModel.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
	}
	
	@Test
	public void addMultipleObserverAndPublish() throws Exception {
		viewModel.subscribe(TEST_NOTIFICATION, observer1);
		viewModel.subscribe(TEST_NOTIFICATION, observer2);
		viewModel.subscribe(TEST_NOTIFICATION, observer3);
		viewModel.publish(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
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
		
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION,
				OBJECT_ARRAY_FOR_NOTIFICATION);
		Mockito.verify(observer2).receivedNotification(TEST_NOTIFICATION,
				OBJECT_ARRAY_FOR_NOTIFICATION);
		Mockito.verify(observer3).receivedNotification(TEST_NOTIFICATION,
				OBJECT_ARRAY_FOR_NOTIFICATION);
	}
	
	private class DummyNotificationObserver implements NotificationObserver {
		@Override
		public void receivedNotification(String key, Object... payload) {
			
		}
	}
}
