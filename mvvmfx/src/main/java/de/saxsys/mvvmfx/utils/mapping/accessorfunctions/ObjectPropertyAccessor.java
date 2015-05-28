package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import javafx.beans.property.Property;

import java.util.function.Function;

/**
 * A functional interface to define an accessor method for a property of a generic type.
 *
 * @param <M>
 *            the generic type of the model.
 * @param <T>
 *            the generic type of the field.
 */
@FunctionalInterface
public interface ObjectPropertyAccessor<M, T> extends Function<M, Property<T>> {
	
	/**
	 * @param model
	 *            the model instance.
	 * @return the property field of the model.
	 */
	@Override
	Property<T> apply(M model);
}
