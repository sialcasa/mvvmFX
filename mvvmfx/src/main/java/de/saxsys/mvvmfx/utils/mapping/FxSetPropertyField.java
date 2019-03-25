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
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.SetPropertyAccessor;
import javafx.beans.property.Property;
import javafx.beans.property.SetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

/**
 * An implementation of {@link PropertyField} that is used when the field of the model class is a {@link Set} and will be mapped to a JavaFX {@link
 * SetProperty}.
 *
 * @param <T>
 * @param <E> the type of the list elements.
 */
class FxSetPropertyField<M, E, T extends ObservableSet<E>, R extends Property<T>> implements PropertyField<T, M, R> {

	private Set<E> defaultValue;

	private final SetPropertyAccessor<M, E> accessor;

	private final SetProperty<E> targetProperty;

	public FxSetPropertyField(SideEffect updateFunction, SetPropertyAccessor<M, E> accessor, Supplier<SetProperty<E>> propertySupplier) {
		this(updateFunction, accessor, propertySupplier, Collections.emptySet());
	}

	public FxSetPropertyField(SideEffect updateFunction, SetPropertyAccessor<M, E> accessor, Supplier<SetProperty<E>> propertySupplier,
			Set<E> defaultValue) {
		this.accessor = accessor;
		this.defaultValue = defaultValue;
		this.targetProperty = propertySupplier.get();

		this.targetProperty.setValue(FXCollections.observableSet(new HashSet<>()));
		this.targetProperty.addListener((SetChangeListener<E>) change -> updateFunction.call());
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
		setAll(targetProperty, defaultValue);
	}

	@Override
	public void updateDefault(M wrappedObject) {
		defaultValue = new HashSet<>(accessor.apply(wrappedObject).getValue());
	}

	@Override
	public R getProperty() {
		return (R) targetProperty;
	}

	@Override
	public boolean isDifferent(M wrappedObject) {
		final Set<E> modelValue = accessor.apply(wrappedObject).getValue();
		final Set<E> wrapperValue = targetProperty;

		return !Objects.equals(modelValue, wrapperValue);
	}
}
