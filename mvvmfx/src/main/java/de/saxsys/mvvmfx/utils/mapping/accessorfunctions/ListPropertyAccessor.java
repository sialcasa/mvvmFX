package de.saxsys.mvvmfx.utils.mapping.accessorfunctions;

import javafx.beans.property.ListProperty;

import java.util.function.Function;

/**
 * A functional interface to define an accessor method for a property of a list type.
 *
 * @param <M>
 *            the generic type of the model.
 * @param <E>
 *            the type of the list elements
 */
@FunctionalInterface
public interface ListPropertyAccessor<M, E> extends Function<M, ListProperty<E>> {

	/**
	 * @param model
	 *            the model instance.
	 * @return the property field of the model.
	 */
	@Override
	ListProperty<E> apply(M model);
}
