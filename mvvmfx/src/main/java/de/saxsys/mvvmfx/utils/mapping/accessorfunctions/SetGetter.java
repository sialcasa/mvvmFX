package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.Set;
import java.util.function.Function;

/**
 * A functional interface to define a getter method of a set type.
 *
 * @param <M>
 *            the generic type of the model.
 * @param <E>
 *            the type of the list elements.
 */
@FunctionalInterface
public interface SetGetter<M, E> extends Function<M, Set<E>> {

	/**
	 * @param model the model instance
	 * @return the value of the field
	 */
	@Override
	Set<E> apply(M model);
}
