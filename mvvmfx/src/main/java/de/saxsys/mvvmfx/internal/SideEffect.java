package de.saxsys.mvvmfx.internal;

/**
 * A functional interface that is used in this class to express callbacks that don't take any argument and don't
 * return anything. Such a callback has to work only by side effects.
 *
 * This interface doesn't allow the implementation to throw checked exceptions.
 * If checked exceptions are needed, use {@link SideEffectWithException} instead.
 */
@FunctionalInterface
public interface SideEffect {
	void call();
}
