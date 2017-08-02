package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.List;
import java.util.function.BiFunction;

/**
 * A functional interface to define an immutable "setter" method of type {@link List}.
 * As the model element is immutable this method is not a real "setter".
 * Instead it returns a new immutable copy of the original model element that has the
 * specified field updated to the new value.
 *
 * @param <M>
 *            the generic type of the model.
 */
@FunctionalInterface
public interface ListImmutableSetter<M, E> extends BiFunction<M, List<E>, M> {

	/**
	 * @param model
	 *            the model instance.
	 * @param newElements
	 *            the new elements of this list field.
	 */
	@Override
	M apply(M model, List<E> newElements);
}
