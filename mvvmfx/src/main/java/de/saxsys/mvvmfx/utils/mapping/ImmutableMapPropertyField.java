/*******************************************************************************
 * Copyright 2018 Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.utils.mapping;

import de.saxsys.mvvmfx.internal.SideEffect;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.MapGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.MapImmutableSetter;
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

class ImmutableMapPropertyField<M, K, V, T extends ObservableMap<K, V>, R extends Property<T>>
		implements ImmutablePropertyField<T, M, R> {

	private final MapGetter<M, K, V> getter;

	private final MapImmutableSetter<M, K, V> immutableSetter;

	private Map<K, V> defaultValue;

	private final MapProperty<K, V> targetProperty;

	ImmutableMapPropertyField(
			SideEffect updateFunction,
			MapGetter<M, K, V> getter, MapImmutableSetter<M, K, V> immutableSetter,
			Supplier<MapProperty<K, V>> propertySupplier) {
		this(updateFunction, getter, immutableSetter, propertySupplier, Collections.emptyMap());
	}

	ImmutableMapPropertyField(SideEffect updateFunction,
			MapGetter<M, K, V> getter, MapImmutableSetter<M, K, V> immutableSetter,
			Supplier<MapProperty<K, V>> propertySupplier, Map<K, V> defaultValue) {
		this.defaultValue = defaultValue;
		this.getter = getter;
		this.immutableSetter = immutableSetter;
		this.targetProperty = propertySupplier.get();
		this.targetProperty.setValue(FXCollections.observableMap(new HashMap<>()));
		this.targetProperty.addListener((MapChangeListener<K, V>) change -> updateFunction.call());
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
		setAll(targetProperty, getter.apply(wrappedObject));
	}

	@Override
	public void resetToDefault() {
		setAll(targetProperty, defaultValue);
	}

	@Override
	public void updateDefault(M wrappedObject) {
		defaultValue = new HashMap<>(getter.apply(wrappedObject));
	}

	@Override
	public R getProperty() {
		return (R) targetProperty;
	}

	@Override
	public boolean isDifferent(M wrappedObject) {
		final Map<K, V> modelValue = getter.apply(wrappedObject);
		final Map<K, V> wrappedValue = targetProperty;

		return !Objects.equals(modelValue, wrappedValue);
	}
}