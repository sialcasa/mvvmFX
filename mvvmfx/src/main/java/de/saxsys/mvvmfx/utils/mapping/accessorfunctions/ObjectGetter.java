package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.function.Function;

/**
 * A functional interface to define a getter method of a generic type.
 *
 * @param <M>
 *            the generic type of the model.
 * @param <T>
 *            the generic type of the field.
 */
@FunctionalInterface
public interface ObjectGetter<M, T> extends Function<M, T> {

	/**
	 * @param model the model instance.
	 * @return the value of the field.
	 */
	@Override
	T apply(M model);
}
