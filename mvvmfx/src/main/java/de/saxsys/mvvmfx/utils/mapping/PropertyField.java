package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;

/**
 * This interface defines the operations that are possible for each field of a wrapped class.
 *
 * @param <T>
 *            target type. The base type of the returned property, f.e. {@link String}.
 * @param <M>
 *            model type. The type of the Model class, that is wrapped by this ModelWrapper instance.
 * @param <R>
 *            return type. The type of the Property that is returned via {@link #getProperty()}, f.e.
 *            {@link StringProperty} or {@link Property <String>}.
 */
interface PropertyField<T, M, R extends Property<T>> {
	void commit(M wrappedObject);

	void reload(M wrappedObject);

	void resetToDefault();

	void updateDefault(final M wrappedObject);

	R getProperty();

	/**
	 * Determines if the value in the model object and the property field are different or not.
	 *
	 * This method is used to implement the {@link ModelWrapper#differentProperty()} flag.
	 *
	 * @param wrappedObject
	 *            the wrapped model object
	 * @return <code>false</code> if both the wrapped model object and the property field have the same value,
	 *         otherwise <code>true</code>
	 */
	boolean isDifferent(M wrappedObject);
}
