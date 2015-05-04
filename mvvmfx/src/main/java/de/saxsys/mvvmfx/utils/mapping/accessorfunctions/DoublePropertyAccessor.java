package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import java.util.function.Function;

import javafx.beans.property.DoubleProperty;

/**
 * A functional interface to define an accessor method for a property of type {@link Double}.
 *
 * @param <M>
 *            the generic type of the model.
 */
@FunctionalInterface
public interface DoublePropertyAccessor<M> extends Function<M, DoubleProperty> {
	
	/**
	 * @param model
	 *            the model instance.
	 * @return the property field of the model.
	 */
	@Override
	DoubleProperty apply(M model);
	
}
