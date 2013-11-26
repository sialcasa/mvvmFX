package de.saxsys.jfx.cdi;

import de.saxsys.jfx.mvvm.api.MvvmFX;
import de.saxsys.jfx.mvvm.notifications.NotificationCenter;
import de.saxsys.jfx.mvvm.notifications.NotificationCenterFactory;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;

import javax.enterprise.inject.Produces;

public class MvvmfxProducer {

    @Produces
    public NotificationCenter produceNotificationCenter(){
        return MvvmFX.getNotificationCenter();
    }

    @Produces
    public ViewLoader produceViewLoader(){
        return new ViewLoader();
    }


}
