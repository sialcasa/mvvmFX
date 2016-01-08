package de.saxsys.mvvmfx.utils.notifications;

import java.util.function.Consumer;

public class Subscription<T> {
	
	
	private final Class<T> notificationType;
	private final Consumer<T> observer;

	public Subscription(Class<T> notificationType, Consumer<T> observer) {
		this.notificationType = notificationType;
		this.observer = observer;
	}

	public void unsubscribe() {
	}
	
	public Class<T> getNotificationType() {
		return notificationType;
	}
	
	public Consumer<T> getObserver() {
		return observer;
	}
	
}
