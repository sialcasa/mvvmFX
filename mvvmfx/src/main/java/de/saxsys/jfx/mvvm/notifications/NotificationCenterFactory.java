package de.saxsys.jfx.mvvm.notifications;

public class NotificationCenterFactory {

    private static NotificationCenter singleton = new DefaultNotificationCenter();

    public static NotificationCenter getNotificationCenter(){
        return singleton;
    }

}
