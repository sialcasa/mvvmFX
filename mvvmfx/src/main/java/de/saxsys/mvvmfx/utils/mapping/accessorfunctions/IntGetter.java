package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.function.Function;

/**
 * A functional interface to define a getter method of type {@link Integer}. 
 *
 * @param <M> the generic type of the model.
 */
@FunctionalInterface
public interface IntGetter<M> extends Function<M, Integer> {

	/**
	 * @param model the model instance.
	 * @return the value of the field.
	 */
	@Override
	Integer apply(M model);
}
