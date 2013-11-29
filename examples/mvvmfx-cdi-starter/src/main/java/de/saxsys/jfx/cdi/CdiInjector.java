package de.saxsys.jfx.cdi;

import javafx.util.Callback;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * This class is used as custom dependency injector for {@link de.saxsys.jfx.mvvm.api.MvvmFX#setCustomDependencyInjector(javafx.util.Callback)}.
 *
 * This way the mvvmFX framework can use CDI for it's dependency injection mechanism.
 *
 */
public class CdiInjector implements Callback<Class<?>, Object> {

    @Inject
    private Instance<Object> instances;

    @Override
    public Object call(Class<?> type) {
        return instances.select(type).get();
    }
}
