package de.saxsys.mvvmfx.utils.mapping;

import de.saxsys.mvvmfx.internal.SideEffect;
import javafx.beans.property.Property;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An implementation of {@link PropertyField} that is used when the fields of the model class are JavaFX Properties
 * too.
 *
 * @param <T>
 */
class FxPropertyField<T, M, R extends Property<T>> implements PropertyField<T, M, R> {

	private T defaultValue;
	private final Function<M, Property<T>> accessor;
	private final R targetProperty;

	public FxPropertyField(SideEffect updateFunction, Function<M, Property<T>> accessor, Supplier<Property<T>> propertySupplier) {
		this(updateFunction, accessor, null, propertySupplier);
	}

	@SuppressWarnings("unchecked")
	public FxPropertyField(SideEffect updateFunction, Function<M, Property<T>> accessor, T defaultValue,
			Supplier<Property<T>> propertySupplier) {
		this.accessor = accessor;
		this.defaultValue = defaultValue;
		this.targetProperty = (R) propertySupplier.get();

		this.targetProperty.addListener((observable, oldValue, newValue) -> updateFunction.call());
	}

	@Override
	public void commit(M wrappedObject) {
		accessor.apply(wrappedObject).setValue(targetProperty.getValue());
	}

	@Override
	public void reload(M wrappedObject) {
		targetProperty.setValue(accessor.apply(wrappedObject).getValue());
	}

	@Override
	public void resetToDefault() {
		targetProperty.setValue(defaultValue);
	}

	@Override
	public void updateDefault(final M wrappedObject) {
		defaultValue = accessor.apply(wrappedObject).getValue();
	}

	@Override
	public R getProperty() {
		return targetProperty;
	}

	@Override
	public boolean isDifferent(M wrappedObject) {
		final T modelValue = accessor.apply(wrappedObject).getValue();
		final T wrapperValue = targetProperty.getValue();

		return !Objects.equals(modelValue, wrapperValue);
	}
}
