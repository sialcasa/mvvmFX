package de.saxsys.mvvmfx.utils.notifications;

import org.junit.Test;


/**
 * Test-case to reproduce <a href="https://github.com/sialcasa/mvvmFX/issues/289">bug #289</a>.
 */
public class ConcurrentModificationBugTest {
	@Test
	public void shouldAllowSubscribeDuringPublish() throws Exception {
		NotificationCenter notificationCenter = NotificationCenterFactory.getNotificationCenter();
		
		/* Minimal example (triggers a ConcurrentModificationException in NotificationCenter */
		notificationCenter.subscribe("MSG", (key1, payload1) -> {
			notificationCenter.subscribe("MSG", (key2, payload2) -> {
				System.out.println("I want to subscribe during a publish()");
			});
		});
		
		notificationCenter.publish("MSG");
		
		/*
		 * Real use case:
		 * 
		 * ParentView | | ChildView1 ChildView2
		 * 
		 * 
		 * ChildView1 publishes a global event that causes ParentView to create a new instance of ChildView2. ChildView2
		 * tries to subscribe to another global event in its initialize() method.
		 * 
		 * Because NotificationCenter.publish() does not make a copy before publishing events, we get a
		 * ConcurrentModificationException.
		 */
	}
}