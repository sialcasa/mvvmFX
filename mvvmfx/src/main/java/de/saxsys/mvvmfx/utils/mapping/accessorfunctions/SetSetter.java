package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.Set;
import java.util.function.BiConsumer;

/**
 * A functional interface to define a setter method of a set type.
 *
 * @param <M> the generic type of the model.
 * @param <E> the type of the list elements.
 */
@FunctionalInterface
public interface SetSetter<M, E> extends BiConsumer<M, Set<E>> {

	/**
	 * @param model the model instance.
	 * @param value the new value to be set
	 */
	@Override
	void accept(M model, Set<E> value);
}
