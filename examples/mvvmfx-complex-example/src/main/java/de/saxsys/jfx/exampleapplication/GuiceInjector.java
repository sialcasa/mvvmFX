package de.saxsys.jfx.exampleapplication;

import com.google.inject.Injector;
import javafx.util.Callback;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class is used as custom dependency injector for {@link de.saxsys.jfx.mvvm.api.MvvmFX#setCustomDependencyInjector(javafx.util.Callback)}.
 *
 * This way the mvvmFX framework can use Guice for it's dependency injection mechanism.
 */
@Singleton
public class GuiceInjector implements Callback<Class<?>,Object>{

    @Inject
    private Injector injector;

    @Override
    public Object call(Class<?> type) {
        return injector.getInstance(type);
    }
}
