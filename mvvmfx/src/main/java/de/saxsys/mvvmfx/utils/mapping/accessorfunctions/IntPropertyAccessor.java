package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import javafx.beans.property.IntegerProperty;

import java.util.function.Function;

/**
 * A functional interface to define an accessor method for a property of type {@link Integer}.
 *
 * @param <M>
 *            the generic type of the model.
 */
@FunctionalInterface
public interface IntPropertyAccessor<M> extends Function<M, IntegerProperty> {
	
	/**
	 * @param model
	 *            the model instance.
	 * @return the property field of the model.
	 */
	@Override
	IntegerProperty apply(M model);
}
