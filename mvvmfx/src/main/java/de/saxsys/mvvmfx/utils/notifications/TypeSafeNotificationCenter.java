package de.saxsys.mvvmfx.utils.notifications;

import java.util.function.Consumer;

public interface TypeSafeNotificationCenter {
	
	<T extends Notification> Subscription<T> subscribe(Class<T> notificationType, Consumer<T> observer);
	
	<T extends Notification> void publish(T notification);
	
	Subscription<String> subscribe(String message, Consumer<String> observer);
	
	void publish(String message);
	
	void unsubscribeAll(Consumer ... consumers);
	
	void unsubscribeAll(String message);

	<T extends Notification> void unsubscribeAll(Class<T> notificationType);
	
	
	TypeSafeNotificationCenter onChannel(Object channel);
	
}
