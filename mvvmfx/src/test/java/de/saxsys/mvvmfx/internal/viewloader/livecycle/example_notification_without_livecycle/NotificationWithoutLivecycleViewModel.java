package de.saxsys.mvvmfx.internal.viewloader.livecycle.example_notification_without_livecycle;

import de.saxsys.mvvmfx.ViewModel;

public class NotificationWithoutLivecycleViewModel implements ViewModel {

	int notificationCounter = 0;

	public void initialize() {
		subscribe("test", (key, payload) -> {
			notificationCounter++;
		});
	}

}
