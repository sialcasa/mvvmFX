package de.saxsys.mvvmfx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScopeProvider {
	/**
	 * The scopes provided by this scope provider.
	 */
    Class<? extends Scope>[] scopes() default {};
    
    /**
	 * The scopes provided by this scope provider. This is an alias for {@link #scopes()}. 
	 * If both {@link #value()} and {@link #scopes()} are provided, the content of
	 * {@link #value()} is preferred.
	 */
    Class<? extends Scope>[] value() default {};
}