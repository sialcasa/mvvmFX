package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import javafx.beans.property.Property;

import java.util.function.Function;

/**
 * A functional interface to define an accessor method for a property of type {@link Boolean}. 
 *
 * @param <M> the generic type of the model.
 */
@FunctionalInterface
public interface BooleanPropertyAccessor<M> extends Function<M, Property<Boolean>> {
	/**
	 * @param model the model instance.
	 * @return the property field of the model.
	 */
	@Override
	Property<Boolean> apply(M model);
}
