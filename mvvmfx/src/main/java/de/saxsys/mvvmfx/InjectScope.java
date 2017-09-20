package de.saxsys.mvvmfx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to inject a {@link Scope} object into a {@link ViewModel}.
 * 
 * @author alexander.casall
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectScope {
}