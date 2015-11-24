package de.saxsys.mvvmfx.guice.interceptiontest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TestInterceptor implements MethodInterceptor {
    public static boolean wasIntercepted = false;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        wasIntercepted = true;

        return methodInvocation.proceed();
    }
}
