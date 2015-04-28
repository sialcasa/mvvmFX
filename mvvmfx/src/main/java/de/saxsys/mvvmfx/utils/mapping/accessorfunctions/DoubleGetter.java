package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.function.Function;

/**
 * A functional interface to define a getter method of type {@link Double}.
 *
 * @param <M>
 *            the generic type of the model.
 */
@FunctionalInterface
public interface DoubleGetter<M> extends Function<M, Double> {
	
	/**
	 * @param model
	 *            the model instance.
	 * @return the value of the field.
	 */
	@Override
	Double apply(M model);
}
