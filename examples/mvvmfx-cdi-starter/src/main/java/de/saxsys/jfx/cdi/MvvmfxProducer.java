package de.saxsys.jfx.cdi;

import de.saxsys.jfx.mvvm.api.MvvmFX;
import de.saxsys.jfx.mvvm.notifications.NotificationCenter;
import de.saxsys.jfx.mvvm.notifications.NotificationCenterFactory;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;

import javax.enterprise.inject.Produces;


/**
 * This class collects CDI producer methods to be able to inject classes of the mvvmFX framework.
 * This is needed as the core mvvmFX library isn't enabled for CDI (no beans.xml).
 * An additional reasons for this class are that for some interfaces there may be more than one implementation or there is only
 * a factory to create an instances for a specific class/interface. In both cases a solution is to create producers.
 */
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
