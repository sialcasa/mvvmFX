package de.saxsys.mvvmfx.utils.mapping;

import de.saxsys.mvvmfx.internal.SideEffect;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ListGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ListImmutableSetter;
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

public class ImmutableListPropertyField<M, E, T extends ObservableList<E>, R extends Property<T>>
		implements ImmutablePropertyField<T, M, R> {

	private final ListGetter<M, E> getter;
	private final ListImmutableSetter<M, E> immutableSetter;

	private List<E> defaultValue;
	private final ListProperty<E> targetProperty;

	public ImmutableListPropertyField(
			SideEffect updateFunction,
			ListGetter<M, E> getter, ListImmutableSetter<M, E> immutableSetter,
			Supplier<ListProperty<E>> propertySupplier) {
		this(updateFunction, getter, immutableSetter, propertySupplier, Collections.emptyList());
	}

	public ImmutableListPropertyField(SideEffect updateFunction, ListGetter<M, E> getter, ListImmutableSetter<M, E> immutableSetter, Supplier<ListProperty<E>> propertySupplier, List<E> defaultValue) {
		this.defaultValue = defaultValue;
		this.getter = getter;
		this.immutableSetter = immutableSetter;
		this.targetProperty = propertySupplier.get();
		this.targetProperty.setValue(FXCollections.observableArrayList());

		this.targetProperty.addListener((ListChangeListener<E>) change -> updateFunction.call());
	}

	@Override
	public void commit(M wrappedObject) {
		// commit is not supported because the model instance is immutable.
	}

	@Override
	public M commitImmutable(M wrappedObject) {
		return immutableSetter.apply(wrappedObject, targetProperty.getValue());
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
	public void updateDefault(M wrappedObject) {
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
