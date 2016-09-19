package de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_notification_without_lifecycle;

import de.saxsys.mvvmfx.ViewModel;

public class NotificationWithoutLifecycleViewModel implements ViewModel {

	int notificationCounter = 0;

	public void initialize() {
		subscribe("test", (key, payload) -> {
			notificationCounter++;
		});
	}

}
