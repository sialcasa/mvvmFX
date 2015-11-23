package de.saxsys.mvvmfx.guice.interceptiontest;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;

public class InterceptorModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bindInterceptor(Matchers.subclassesOf(InterceptedView.class), Matchers.any(), new TestInterceptor());
    }
}
