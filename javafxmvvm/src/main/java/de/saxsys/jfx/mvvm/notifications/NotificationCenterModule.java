package de.saxsys.jfx.mvvm.notifications;

import com.google.inject.AbstractModule;

public class NotificationCenterModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(NotificationCenter.class).to(DefaultNotificationCenter.class);
	}

}
