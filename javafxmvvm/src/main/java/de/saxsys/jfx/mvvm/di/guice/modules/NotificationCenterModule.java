package de.saxsys.jfx.mvvm.di.guice.modules;

import com.google.inject.AbstractModule;

import de.saxsys.jfx.mvvm.notifications.DefaultNotificationCenter;
import de.saxsys.jfx.mvvm.notifications.NotificationCenter;


public class NotificationCenterModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(NotificationCenter.class).to(DefaultNotificationCenter.class);
	}

}
