package de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_notification;

import de.saxsys.mvvmfx.SceneLifecycle;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.notifications.NotificationObserver;

public class LifecycleNotificationViewModel implements ViewModel, SceneLifecycle {

	int notificationCounter = 0;


	// to be able to remove the observer we can't define it inline but instead
	// need to keep a reference
	private NotificationObserver notificationObserver = (key, payload) -> {
		notificationCounter++;
	};

	public void initialize() {
		subscribe("test", notificationObserver);
	}

	@Override
	public void onViewAdded() {
	}

	@Override
	public void onViewRemoved() {
		unsubscribe("test", notificationObserver);
	}
}
