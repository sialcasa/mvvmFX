package de.saxsys.mvvmfx.testingutils.jfxrunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used together with {@link JfxRunner} to execute
 * an annotated test method on the JavaFX Thread.
 *
 * Please notices that this only works with JUnit 4.
 * For a solution for JUnit 5 see {@link de.saxsys.mvvmfx.testingutils.FxTestingUtils#runInFXThread(Runnable)}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestInJfxThread {
}