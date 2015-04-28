package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.function.Function;

/**
 * A functional interface to define a getter method of type {@link String}.
 *
 * @param <M>
 *            the generic type of the model.
 */
@FunctionalInterface
public interface StringGetter<M> extends Function<M, String> {
	
	/**
	 * @param model
	 *            the model instance.
	 * @return the value of the field.
	 */
	@Override
	String apply(M model);
}
