package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import javafx.beans.property.FloatProperty;

import java.util.function.Function;

/**
 * A functional interface to define an accessor method for a property of type {@link Float}.
 *
 * @param <M>
 *            the generic type of the model.
 */
@FunctionalInterface
public interface FloatPropertyAccessor<M> extends Function<M, FloatProperty> {
	
	/**
	 * @param model
	 *            the model instance.
	 * @return the property field of the model.
	 */
	@Override
	FloatProperty apply(M model);
}
