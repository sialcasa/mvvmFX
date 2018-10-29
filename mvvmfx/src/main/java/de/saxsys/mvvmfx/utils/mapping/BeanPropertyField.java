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
import javafx.beans.property.Property;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An implementation of {@link PropertyField} that is used when the fields of the model class are <b>not</b> JavaFX
 * Properties but are following the old Java-Beans standard, i.e. there are getter and setter method for each field.
 *
 * @param <T>
 */
class BeanPropertyField<T, M, R extends Property<T>> implements PropertyField<T, M, R> {

	private final R targetProperty;
	private T defaultValue;

	private final Function<M, T> getter;
	private final BiConsumer<M, T> setter;

	public BeanPropertyField(SideEffect updateFunction, Function<M, T> getter,
			BiConsumer<M, T> setter, Supplier<R> propertySupplier) {
		this(updateFunction, getter, setter, null, propertySupplier);
	}

	public BeanPropertyField(SideEffect updateFunction, Function<M, T> getter,
			BiConsumer<M, T> setter, T defaultValue, Supplier<R> propertySupplier) {
		this.defaultValue = defaultValue;
		this.getter = getter;
		this.setter = setter;
		this.targetProperty = propertySupplier.get();

		this.targetProperty.addListener((observable, oldValue, newValue) -> updateFunction.call());
	}

	@Override
	public void commit(M wrappedObject) {
		setter.accept(wrappedObject, targetProperty.getValue());
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
	public void updateDefault(final M wrappedObject) {
	  defaultValue = getter.apply(wrappedObject);
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
