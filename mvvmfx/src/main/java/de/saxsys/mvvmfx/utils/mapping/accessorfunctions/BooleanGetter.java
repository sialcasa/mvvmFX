package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.function.Function;

/**
 * A functional interface to define a getter method of type {@link Boolean}. 
 * 
 * @param <M> the generic type of the model.
 */
@FunctionalInterface
public interface BooleanGetter<M> extends Function<M, Boolean> {

	/**
	 * @param model the model instance.
	 * @return the value of the field.
	 */
	@Override
	Boolean apply(M model);
}
