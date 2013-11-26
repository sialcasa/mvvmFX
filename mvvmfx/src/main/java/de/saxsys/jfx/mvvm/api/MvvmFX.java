package de.saxsys.jfx.mvvm.api;

import de.saxsys.jfx.mvvm.di.DependencyInjector;
import de.saxsys.jfx.mvvm.notifications.NotificationCenter;
import de.saxsys.jfx.mvvm.notifications.NotificationCenterFactory;

/**
 * This class is a facade that is used by the user to access classes and services from the framework.
 */
public class MvvmFX {

    public static NotificationCenter getNotificationCenter(){
        return NotificationCenterFactory.getNotificationCenter();
    }

    public static DependencyInjector getDependencyInjector(){
        return DependencyInjector.getInstance();
    }
}
