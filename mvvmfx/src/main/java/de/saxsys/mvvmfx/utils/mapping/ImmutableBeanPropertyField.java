package de.saxsys.mvvmfx.utils.mapping;

import de.saxsys.mvvmfx.internal.SideEffect;
import javafx.beans.property.Property;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ImmutableBeanPropertyField<T, M, R extends Property<T>> implements ImmutablePropertyField<T, M, R> {

	private T defaultValue;
	private final R targetProperty;

	private final Function<M, T> getter;
	private final BiFunction<M, T, M> immutableSetter;


	public ImmutableBeanPropertyField(SideEffect updateFunction, Function<M, T> getter,
			BiFunction<M, T, M> immutableSetter, Supplier<R> propertySupplier) {
		this(updateFunction, getter, immutableSetter, null, propertySupplier);
	}

	public ImmutableBeanPropertyField(SideEffect updateFunction, Function<M, T> getter,
			BiFunction<M, T, M> immutableSetter, T defaultValue, Supplier<R> propertySupplier){
		this.getter = getter;
		this.immutableSetter = immutableSetter;
		this.defaultValue = defaultValue;

		this.targetProperty = propertySupplier.get();
		this.targetProperty.addListener(((observable, oldValue, newValue) -> updateFunction.call()));
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
		targetProperty.setValue(getter.apply(wrappedObject));
	}


	@Override
	public void resetToDefault() {
		targetProperty.setValue(defaultValue);
	}

	@Override
	public void updateDefault(M wrappedObject) {
		this.defaultValue = getter.apply(wrappedObject);
	}

	@Override
	public R getProperty() {
		return targetProperty;
	}

	@Override
	public boolean isDifferent(M wrappedObject) {
		final T modelValue = getter.apply(wrappedObject);
		final T wrapperValue = targetProperty.getValue();

		return !Objects.equals(modelValue, wrapperValue);
	}
}
