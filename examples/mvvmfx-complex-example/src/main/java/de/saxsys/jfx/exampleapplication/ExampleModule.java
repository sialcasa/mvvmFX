package de.saxsys.jfx.exampleapplication;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import de.saxsys.jfx.mvvm.api.MvvmFX;
import de.saxsys.jfx.mvvm.notifications.NotificationCenter;

public class ExampleModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(NotificationCenter.class).toProvider(new Provider<NotificationCenter>(){
            @Override
            public NotificationCenter get() {
                return MvvmFX.getNotificationCenter();
            }
        });
    }
}
