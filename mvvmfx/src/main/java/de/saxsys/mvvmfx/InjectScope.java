package de.saxsys.mvvmfx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author alexander.casall
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectScope {

    /**
     * the id of the scope.
     * Default is "" which means that the scope is global.
     */
    String value() default "";

}