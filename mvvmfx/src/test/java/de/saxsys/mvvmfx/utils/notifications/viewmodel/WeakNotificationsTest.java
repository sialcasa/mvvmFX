package de.saxsys.mvvmfx.utils.notifications.viewmodel;

import de.saxsys.mvvmfx.utils.notifications.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * This test is used to verify the usage of the {@link WeakNotificationObserver}.
 * To do this most test cases of {@link DefaultNotificationCenterTest}
 * are reproduced with the weak variant of notifications.
 */
public class WeakNotificationsTest {


	private static final String TEST_NOTIFICATION = "test_notification";
	private static final String TEST_NOTIFICATION_2 = TEST_NOTIFICATION + "shouldnotget";
	private static final Object[] OBJECT_ARRAY_FOR_NOTIFICATION = new String[]{"test"};

	private NotificationCenter defaultCenter;

	NotificationObserver observer1;
	NotificationObserver observer2;
	NotificationObserver observer3;

	@BeforeEach
	public void init() {
		observer1 = Mockito.mock(NotificationObserver.class);
		observer2 = Mockito.mock(NotificationObserver.class);
		observer3 = Mockito.mock(NotificationObserver.class);
		defaultCenter = new DefaultNotificationCenter();
	}

	@Test
	public void weakObserverCanBeDirectlyUnsubscribed() throws Exception {
		WeakNotificationObserver weakObserver1 = new WeakNotificationObserver(observer1);

		defaultCenter.subscribe(TEST_NOTIFICATION, weakObserver1);
		defaultCenter.unsubscribe(TEST_NOTIFICATION, weakObserver1);

		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
	}

	@Test
	public void weakObserverCanBeUnsubscibedByWrappedObserver() throws Exception {
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));

		defaultCenter.unsubscribe(TEST_NOTIFICATION, observer1);

		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
	}









	@Test
	public void addObserverToDefaultNotificationCenterAndPostNotification() throws Exception {
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));
		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1).receivedNotification(TEST_NOTIFICATION);
	}

	@Test
	public void addObserverToDefaultNotificationCenterAndPostObjectNotification() throws Exception {
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));
		defaultCenter.publish(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
		Mockito.verify(observer1).receivedNotification(TEST_NOTIFICATION, OBJECT_ARRAY_FOR_NOTIFICATION);
	}

	@Test
	public void addAndRemoveObserverToDefaultNotificationCenterAndPostNotification() throws Exception {
		WeakNotificationObserver weakObserver1 = new WeakNotificationObserver(observer1);
		defaultCenter.subscribe(TEST_NOTIFICATION, weakObserver1);
		WeakNotificationObserver weakObserver2 = new WeakNotificationObserver(observer2);
		defaultCenter.subscribe(TEST_NOTIFICATION, weakObserver2);
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer3));
		defaultCenter.unsubscribe(weakObserver1);
		defaultCenter.unsubscribe(weakObserver2);
		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
		Mockito.verify(observer2, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
		Mockito.verify(observer3).receivedNotification(TEST_NOTIFICATION);
	}

	@Test
	public void addAndRemoveWeakObserverToDefaultNotificationCenterAndPostNotification() throws Exception {
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer2));
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer3));
		defaultCenter.unsubscribe(observer1);
		defaultCenter.unsubscribe(observer2);
		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
		Mockito.verify(observer2, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
		Mockito.verify(observer3).receivedNotification(TEST_NOTIFICATION);
	}

	@Test
	public void addObserversToDefaultNotificationCenterAndPostNotification() throws Exception {
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));
		defaultCenter.subscribe(TEST_NOTIFICATION_2, new WeakNotificationObserver(observer2));
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer3));

		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.only()).receivedNotification(TEST_NOTIFICATION);
		Mockito.verify(observer2, Mockito.never()).receivedNotification(TEST_NOTIFICATION_2);
		Mockito.verify(observer3, Mockito.only()).receivedNotification(TEST_NOTIFICATION);
	}


	@Test
	public void subscribeSameObserverMultipleTimes() {
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));

		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.times(2)).receivedNotification(TEST_NOTIFICATION);
	}

	@Test
	public void unsubscribeObserverThatWasSubscribedMultipleTimes() {
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));

		defaultCenter.unsubscribe(observer1);

		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
	}

	@Test
	public void unsubscribeWeakObserverThatWasSubscribedMultipleTimes() {
		WeakNotificationObserver weakObserver1 = new WeakNotificationObserver(observer1);
		defaultCenter.subscribe(TEST_NOTIFICATION, weakObserver1);
		defaultCenter.subscribe(TEST_NOTIFICATION, weakObserver1);
		defaultCenter.subscribe(TEST_NOTIFICATION, weakObserver1);

		defaultCenter.unsubscribe(weakObserver1);

		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
	}


	/**
	 * This is the same as {@link #unsubscribeObserverThatWasSubscribedMultipleTimes()} with the
	 * difference that here we use the overloaded unsubscribe method {@link NotificationCenter#unsubscribe(String, NotificationObserver)} that takes
	 * the message key as first parameter.
	 */
	@Test
	public void unsubscribeObserverThatWasSubscribedMultipleTimesViaMessageName() {
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));
		defaultCenter.subscribe(TEST_NOTIFICATION, new WeakNotificationObserver(observer1));

		defaultCenter.unsubscribe(TEST_NOTIFICATION, observer1);

		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
	}

	/**
	 * This is the same as {@link #unsubscribeObserverThatWasSubscribedMultipleTimes()} with the
	 * difference that here we use the overloaded unsubscribe method {@link NotificationCenter#unsubscribe(String, NotificationObserver)} that takes
	 * the message key as first parameter.
	 */
	@Test
	public void unsubscribeWeakObserverThatWasSubscribedMultipleTimesViaMessageName() {
		WeakNotificationObserver weakObserver1 = new WeakNotificationObserver(observer1);
		defaultCenter.subscribe(TEST_NOTIFICATION, weakObserver1);
		defaultCenter.subscribe(TEST_NOTIFICATION, weakObserver1);
		defaultCenter.subscribe(TEST_NOTIFICATION, weakObserver1);

		defaultCenter.unsubscribe(TEST_NOTIFICATION, weakObserver1);

		defaultCenter.publish(TEST_NOTIFICATION);
		Mockito.verify(observer1, Mockito.never()).receivedNotification(TEST_NOTIFICATION);
	}
}
