package de.saxsys.mvvmfx.utils.mapping;

import de.saxsys.mvvmfx.internal.SideEffect;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.MapPropertyAccessor;
import javafx.beans.property.MapProperty;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static de.saxsys.mvvmfx.utils.mapping.BeanMapPropertyField.setAll;

/**
 * An implementation of {@link PropertyField} that is used when the field of the model class is a {@link Map} and will
 * be mapped to a JavaFX {@link MapProperty}.
 *
 * @param <K> the type of the key elements.
 * @param <V> the type of the value elements.
 */
class FxMapPropertyField<M, K, V, T extends ObservableMap<K, V>, R extends Property<T>>
		implements PropertyField<T, M, R> {

	private Map<K, V> defaultValue;

	private final MapPropertyAccessor<M, K, V> accessor;

	private final MapProperty<K, V> targetProperty;

	FxMapPropertyField(
			SideEffect updateFunction, MapPropertyAccessor<M, K, V> accessor,
			Supplier<MapProperty<K, V>> propertySupplier) {
		this(updateFunction, accessor, propertySupplier, Collections.emptyMap());
	}

	FxMapPropertyField(SideEffect updateFunction, MapPropertyAccessor<M, K, V> accessor,
			Supplier<MapProperty<K, V>> propertySupplier,
			Map<K, V> defaultValue) {
		this.accessor = accessor;
		this.defaultValue = defaultValue;
		this.targetProperty = propertySupplier.get();

		this.targetProperty.setValue(FXCollections.observableMap(new HashMap<>()));
		this.targetProperty.addListener((MapChangeListener<K, V>) change -> updateFunction.call());
	}

	@Override
	public void commit(M wrappedObject) {
		setAll(accessor.apply(wrappedObject), targetProperty.getValue());
	}

	@Override
	public void reload(M wrappedObject) {
		setAll(targetProperty, accessor.apply(wrappedObject).getValue());
	}

	@Override
	public void resetToDefault() {
		try	{
			targetProperty.get().clear();
			setAll(targetProperty, defaultValue);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void updateDefault(M wrappedObject) {
		defaultValue = new HashMap<>(accessor.apply(wrappedObject).getValue());
	}

	@Override
	public R getProperty() {
		return (R) targetProperty;
	}

	@Override
	public boolean isDifferent(M wrappedObject) {
		final Map<K, V> modelValue = accessor.apply(wrappedObject).getValue();
		final Map<K, V> wrapperValue = targetProperty;

		return !Objects.equals(modelValue, wrapperValue);
	}
}