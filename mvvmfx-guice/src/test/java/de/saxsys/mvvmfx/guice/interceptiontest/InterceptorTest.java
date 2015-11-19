package de.saxsys.mvvmfx.guice.interceptiontest;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test case to check AOP features (Interceptors) of Guice with mvvmFX.
 */
public class InterceptorTest {


    @Test
//    @Ignore("until fixed")
    public void test() {

        InterceptedView.vmWasInjected = false;
        InterceptedView.wasInitCalled = false;
        TestInterceptor.wasIntercepted = false;


        InterceptorTestApp.main();

        final InterceptedView codeBehind = InterceptorTestApp.viewTuple.getCodeBehind();


        assertThat(TestInterceptor.wasIntercepted).isTrue();


        assertThat(InterceptedView.wasInitCalled).isTrue();
        assertThat(InterceptedView.vmWasInjected).isTrue();
    }

}
