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

import static de.saxsys.mvvmfx.utils.mapping.BeanSetPropertyField.setAll;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import de.saxsys.mvvmfx.internal.SideEffect;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.SetGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.SetImmutableSetter;
import javafx.beans.property.Property;
import javafx.beans.property.SetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

class ImmutableSetPropertyField<M, E, T extends ObservableSet<E>, R extends Property<T>> implements ImmutablePropertyField<T, M, R> {

	private final SetGetter<M, E> getter;

	private final SetImmutableSetter<M, E> immutableSetter;

	private Set<E> defaultValue;

	private final SetProperty<E> targetProperty;

	public ImmutableSetPropertyField(
			SideEffect updateFunction,
			SetGetter<M, E> getter, SetImmutableSetter<M, E> immutableSetter,
			Supplier<SetProperty<E>> propertySupplier) {
		this(updateFunction, getter, immutableSetter, propertySupplier, Collections.emptySet());
	}

	public ImmutableSetPropertyField(SideEffect updateFunction,
			SetGetter<M, E> getter, SetImmutableSetter<M, E> immutableSetter,
			Supplier<SetProperty<E>> propertySupplier, Set<E> defaultValue) {
		this.defaultValue = defaultValue;
		this.getter = getter;
		this.immutableSetter = immutableSetter;
		this.targetProperty = propertySupplier.get();
		this.targetProperty.setValue(FXCollections.observableSet(new HashSet<>()));
		this.targetProperty.addListener((SetChangeListener<E>) change -> updateFunction.call());
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
		defaultValue = new HashSet<>(getter.apply(wrappedObject));
	}

	@Override
	public R getProperty() {
		return (R) targetProperty;
	}

	@Override
	public boolean isDifferent(M wrappedObject) {
		final Set<E> modelValue = getter.apply(wrappedObject);
		final Set<E> wrappedValue = targetProperty;

		return !Objects.equals(modelValue, wrappedValue);
	}
}
