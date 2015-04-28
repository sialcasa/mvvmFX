package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import javafx.beans.property.LongProperty;

import java.util.function.Function;

/**
 * A functional interface to define an accessor method for a property of type {@link Long}. 
 *
 * @param <M> the generic type of the model.
 */
@FunctionalInterface
public interface LongPropertyAccessor<M> extends Function<M, LongProperty> {

	/**
	 * @param model
	 *            the model instance.
	 * @return the property field of the model.
	 */
	@Override
	LongProperty apply(M model);
}
