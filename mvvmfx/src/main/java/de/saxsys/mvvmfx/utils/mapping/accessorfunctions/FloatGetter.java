package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.function.Function;

/**
 * A functional interface to define a getter method of type {@link Float}.
 *
 * @param <M>
 *            the generic type of the model.
 */
@FunctionalInterface
public interface FloatGetter<M> extends Function<M, Float> {
	
	/**
	 * @param model
	 *            the model instance.
	 * @return the value of the field.
	 */
	@Override
	Float apply(M model);
}
