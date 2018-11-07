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

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import de.saxsys.mvvmfx.internal.SideEffect;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.SetGetter;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.SetSetter;
import javafx.beans.property.Property;
import javafx.beans.property.SetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

/**
 * An implementation of {@link PropertyField} that is used when the field of the model class is a {@link Set} and is <b>not</b> a JavaFX {@link
 * javafx.beans.property.SetProperty} but is following the old Java-Beans standard, i.e. there is getter and setter method for the field.
 *
 * @param <T>
 * @param <E> the type of the list elements.
 */
public class BeanSetPropertyField<M, E, T extends ObservableSet<E>, R extends Property<T>> implements PropertyField<T, M, R> {

	private final SetGetter<M, E> getter;

	private final SetSetter<M, E> setter;

	private Set<E> defaultValue;

	private final SetProperty<E> targetProperty;

	public BeanSetPropertyField(SideEffect updateFunction, SetGetter<M, E> getter, SetSetter<M, E> setter,
			Supplier<SetProperty<E>> propertySupplier) {
		this(updateFunction, getter, setter, propertySupplier, Collections.emptySet());
	}

	public BeanSetPropertyField(SideEffect updateFunction, SetGetter<M, E> getter, SetSetter<M, E> setter, Supplier<SetProperty<E>> propertySupplier,
			Set<E> defaultValue) {
		this.defaultValue = defaultValue;
		this.getter = getter;
		this.setter = setter;
		this.targetProperty = propertySupplier.get();
		this.targetProperty.setValue(FXCollections.observableSet(new HashSet<>()));
		this.targetProperty.addListener((SetChangeListener<E>) change -> updateFunction.call());
	}

	static <E> void setAll(Set<E> target, Set<E> newValues) {
		target.retainAll(newValues);
		target.addAll(newValues);
	}

	@Override
	public void commit(M wrappedObject) {
		setter.accept(wrappedObject, targetProperty.getValue());
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
		final Set<E> wrapperValue = targetProperty;

		return !Objects.equals(modelValue, wrapperValue);
	}
}
