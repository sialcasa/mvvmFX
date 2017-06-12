package de.saxsys.mvvmfx.utils.mapping;

import de.saxsys.mvvmfx.internal.SideEffect;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.ListPropertyAccessor;
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
 * will be mapped to a JavaFX {@link ListProperty}.
 *
 * @param <T>
 * @param <E>
 *            the type of the list elements.
 */
class FxListPropertyField<M, E, T extends ObservableList<E>, R extends Property<T>>
		implements PropertyField<T, M, R> {

	private List<E> defaultValue;
	private final ListPropertyAccessor<M, E> accessor;
	private final ListProperty<E> targetProperty;

	public FxListPropertyField(SideEffect updateFunction, ListPropertyAccessor<M, E> accessor, Supplier<ListProperty<E>> propertySupplier) {
		this(updateFunction, accessor, propertySupplier, Collections.emptyList());
	}

	public FxListPropertyField(SideEffect updateFunction, ListPropertyAccessor<M, E> accessor, Supplier<ListProperty<E>> propertySupplier, List<E> defaultValue) {
		this.accessor = accessor;
		this.defaultValue = defaultValue;

		this.targetProperty = propertySupplier.get();
		this.targetProperty.setValue(FXCollections.observableArrayList());

		this.targetProperty.addListener((ListChangeListener<E>) change -> updateFunction.call());
	}

	@Override
	public void commit(M wrappedObject) {
		accessor.apply(wrappedObject).setAll(targetProperty.getValue());
	}

	@Override
	public void reload(M wrappedObject) {
		targetProperty.setAll(accessor.apply(wrappedObject).getValue());
	}

	@Override
	public void resetToDefault() {
		targetProperty.setAll(defaultValue);
	}

	@Override
	public void updateDefault(final M wrappedObject) {
		defaultValue = new ArrayList<>(accessor.apply(wrappedObject).getValue());
	}

	@Override
	public R getProperty() {
		return (R) targetProperty;
	}

	@Override
	public boolean isDifferent(M wrappedObject) {
		final List<E> modelValue = accessor.apply(wrappedObject).getValue();
		final List<E> wrapperValue = targetProperty;

		return !Objects.equals(modelValue, wrapperValue);
	}
}
