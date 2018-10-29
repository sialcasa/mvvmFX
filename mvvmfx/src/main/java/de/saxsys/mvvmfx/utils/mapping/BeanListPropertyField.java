package de.saxsys.mvvmfx.utils.mapping;

import de.saxsys.mvvmfx.internal.SideEffect;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ListGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ListSetter;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * An implementation of {@link PropertyField} that is used when the field of the model class is a {@link List} and
 * is <b>not</b> a JavaFX {@link ListProperty} but is following the old Java-Beans standard, i.e. there is getter and setter
 * method for the field.
 *
 * @param <T>
 * @param <E>
 *            the type of the list elements.
 */
class BeanListPropertyField<M, E, T extends ObservableList<E>, R extends Property<T>>
		implements PropertyField<T, M, R> {

	private final ListGetter<M, E> getter;
	private final ListSetter<M, E> setter;

	private List<E> defaultValue;
	private final ListProperty<E> targetProperty;

	public BeanListPropertyField(SideEffect updateFunction, ListGetter<M, E> getter, ListSetter<M, E> setter, Supplier<ListProperty<E>> propertySupplier) {
		this(updateFunction, getter, setter, propertySupplier, Collections.emptyList());
	}

	public BeanListPropertyField(SideEffect updateFunction, ListGetter<M, E> getter, ListSetter<M, E> setter, Supplier<ListProperty<E>> propertySupplier, List<E> defaultValue) {
		this.defaultValue = defaultValue;
		this.getter = getter;
		this.setter = setter;
		this.targetProperty = propertySupplier.get();
		this.targetProperty.setValue(FXCollections.observableArrayList());

		this.targetProperty.addListener((ListChangeListener<E>) change -> updateFunction.call());
	}

	@Override
	public void commit(M wrappedObject) {
		setter.accept(wrappedObject, targetProperty.getValue());
	}

	@Override
	public void reload(M wrappedObject) {
		targetProperty.setAll(getter.apply(wrappedObject));
	}

	@Override
	public void resetToDefault() {
		targetProperty.setAll(defaultValue);
	}

	@Override
	public void updateDefault(final M wrappedObject) {
		defaultValue = new ArrayList<>(getter.apply(wrappedObject));
	}

	@Override
	public R getProperty() {
		return (R) targetProperty;
	}

	@Override
	public boolean isDifferent(M wrappedObject) {
		final List<E> modelValue = getter.apply(wrappedObject);
		final List<E> wrapperValue = targetProperty;

		return !Objects.equals(modelValue, wrapperValue);
	}
}
