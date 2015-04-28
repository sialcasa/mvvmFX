package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.function.BiConsumer;

/**
 * A functional interface to define a setter method of type {@link Double}.
 *
 * @param <M>
 *            the generic type of the model.
 */
@FunctionalInterface
public interface DoubleSetter<M> extends BiConsumer<M, Double> {
	
	/**
	 * @param model
	 *            the model instance.
	 * @param value
	 *            the new value to be set.
	 */
	@Override
	void accept(M model, Double value);
}
