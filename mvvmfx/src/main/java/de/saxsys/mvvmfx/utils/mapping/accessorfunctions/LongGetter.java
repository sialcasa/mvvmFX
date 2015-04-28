package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.function.Function;

/**
 * A functional interface to define a getter method of type {@link Long}. 
 *
 * @param <M> the generic type of the model.
 */
@FunctionalInterface
public interface LongGetter<M> extends Function<M, Long> {

	/**
	 * @param model the model instance.
	 * @return the value of the field.
	 */
	@Override
	Long apply(M model);
}
