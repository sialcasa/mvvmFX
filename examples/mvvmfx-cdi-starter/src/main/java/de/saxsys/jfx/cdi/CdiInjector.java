package de.saxsys.jfx.cdi;

import javafx.util.Callback;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

public class CdiInjector implements Callback<Class<?>, Object> {

    @Inject
    private Instance<Object> instances;

    @Override
    public Object call(Class<?> aClass) {
        return instances.select(aClass).get();
    }
}
