package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.function.BiConsumer;


/**
 * A functional interface to define a setter method of a generic type.
 *
 * @param <M>
 *            the generic type of the model.
 * @param <T>
 *            the generic type of the field.
 */
@FunctionalInterface
public interface ObjectSetter<M,T> extends BiConsumer<M, T> {

	/**
	 * @param model the model instance.
	 * @param value the new value to be set.
	 */
	@Override
	void accept(M model, T value);
}
