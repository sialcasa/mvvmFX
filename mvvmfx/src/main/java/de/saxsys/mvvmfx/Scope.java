package de.saxsys.mvvmfx;

import de.saxsys.mvvmfx.utils.notifications.NotificationObserver;

/**
 * Scope.
 * 
 * @author alexander.casall
 *
 */
public interface Scope {

    default void publish(String messageName, Object... payload) {
        MvvmFX.getNotificationCenter().publish(this, messageName, payload);
    }

    default void subscribe(String messageName, NotificationObserver observer) {
        MvvmFX.getNotificationCenter().subscribe(this, messageName, observer);
    }

    default void unsubscribe(String messageName, NotificationObserver observer) {
        MvvmFX.getNotificationCenter().unsubscribe(this, messageName, observer);
    }

    default void unsubscribe(NotificationObserver observer) {
        MvvmFX.getNotificationCenter().unsubscribe(this, observer);
    }

}
