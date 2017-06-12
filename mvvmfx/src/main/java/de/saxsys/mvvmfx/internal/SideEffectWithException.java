package de.saxsys.mvvmfx.internal;

/**
 * A functional interface that is used in this class to express callbacks that don't take any argument and don't
 * return anything. Such a callback has to work only by side effects.
 *
 * This interface allows the implementation to throw checked exceptions. Therefore the caller has to handle this exception.
 * If no checked exceptions are needed, use {@link SideEffect} instead.
 */
@FunctionalInterface
public interface SideEffectWithException {
	void call() throws Exception;
}
