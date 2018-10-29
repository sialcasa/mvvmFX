/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Sets;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SetProperty;
import javafx.beans.property.StringProperty;

/**
 * This test class is <strong>not</strong> indented to verify the behaviour of the {@link ModelWrapper} class in detail with all
 * possible edge cases. For this purpose there is the test class {@link ModelWrapperTest} that has detailed test cases
 * for the behaviour of the wrapper.
 *
 * The intention of this test class is instead to verify the correct overloading of the field-methods for all property types.
 * This is needed because most of the fields-methods are more or less the same and therefor are copy+pasted.
 * However, due to the poor type system of Java this copy+paste work is inevitable.
 * As most of the implementation code for most of the actual features is reused internally it is safe to only test
 * the implementation details for some examples like it is done in {@link ModelWrapperTest}.
 * The overloaded methods mostly pass through the given arguments.
 *
 * There are 2 use cases that we are checking here: </p>
 * 1. Verify that all overloaded methods have the correct return type.
 * Because of Javas poor type system this isn't always as easy as it sounds.<p/>
 * 2. Verify that the basic handling of default values and identifiers works for all overloaded methods.
 * The overloaded methods pass through most of the arguments but it can happen that for one of the methods an argument
 * is missing due to copy+paste errors (see <a href="https://github.com/sialcasa/mvvmFX/pull/393">issue 393</a> for an example)
 * or due to a refactoring. This test class should point at such "simple" errors.
 *
 */
public class FieldMethodOverloadingTest {

	private ModelWrapper<ExampleModel> wrapper;

	@BeforeEach
	public void setup() {
		wrapper = new ModelWrapper<>();
	}


	/**
	 * A test helper method that verifies some default behaviour of all properties.
	 */
	private <T> void verify(T defaultValue,
							T alternativeValue,
							Property<T> beanField,
							Property<T> fxField,
							Property<T> beanFieldDefault,
							Property<T> fxFieldDefault,
							Property<T> idBeanField,
							Property<T> idFxField,
							Property<T> idBeanFieldDefault,
							Property<T> idFxFieldDefault) {

		verifyDefaultValue(beanFieldDefault, defaultValue, alternativeValue);
		verifyDefaultValue(fxFieldDefault, defaultValue, alternativeValue);
		verifyDefaultValue(idBeanFieldDefault, defaultValue, alternativeValue);
		verifyDefaultValue(idFxFieldDefault, defaultValue, alternativeValue);

		verifyId(idBeanField, "idBeanField");
		verifyId(idFxField, "idFxField");
		verifyId(idBeanFieldDefault, "idBeanFieldDefault");
		verifyId(idFxFieldDefault, "idFxFieldDefault");

	}

	private <T> void verifyDefaultValue(Property<T> property, T defaultValue, T otherValue) {
		property.setValue(otherValue);

		wrapper.commit();
		assertThat(property.getValue()).isEqualTo(otherValue);

		wrapper.reset();
		assertThat(property.getValue()).isEqualTo(defaultValue);
	}

	private <T> void verifyId(Property<T> property, String id) {
		assertThat(property.getName()).isEqualTo(id);
	}


	@Test
	public void integerProperty() {
		int defaultValue = 5;

		final IntegerProperty beanField = wrapper.field(ExampleModel::getInteger, ExampleModel::setInteger);
		final IntegerProperty fxField = wrapper.field(ExampleModel::integerProperty);
		final IntegerProperty beanFieldDefault = wrapper.field(ExampleModel::getInteger, ExampleModel::setInteger, defaultValue);
		final IntegerProperty fxFieldDefault = wrapper.field(ExampleModel::integerProperty, defaultValue);

		final IntegerProperty idBeanField = wrapper.field("idBeanField", ExampleModel::getInteger, ExampleModel::setInteger);
		final IntegerProperty idFxField = wrapper.field("idFxField", ExampleModel::integerProperty);
		final IntegerProperty idBeanFieldDefault = wrapper.field("idBeanFieldDefault", ExampleModel::getInteger,
				ExampleModel::setInteger, defaultValue);
		final IntegerProperty idFxFieldDefault = wrapper.field("idFxFieldDefault", ExampleModel::integerProperty, defaultValue);

		verify(defaultValue, 10, beanField, fxField, beanFieldDefault, fxFieldDefault, idBeanField, idFxField, idBeanFieldDefault, idFxFieldDefault);
	}

	@Test
	public void doubleProperty() {
		double defaultValue = 5.1;

		final DoubleProperty beanField = wrapper.field(ExampleModel::getDouble, ExampleModel::setDouble);
		final DoubleProperty fxField = wrapper.field(ExampleModel::doubleProperty);
		final DoubleProperty beanFieldDefault = wrapper.field(ExampleModel::getDouble, ExampleModel::setDouble, defaultValue);
		final DoubleProperty fxFieldDefault = wrapper.field(ExampleModel::doubleProperty, defaultValue);

		final DoubleProperty idBeanField = wrapper.field("idBeanField", ExampleModel::getDouble, ExampleModel::setDouble);
		final DoubleProperty idFxField = wrapper.field("idFxField", ExampleModel::doubleProperty);
		final DoubleProperty idBeanFieldDefault = wrapper.field("idBeanFieldDefault", ExampleModel::getDouble,
				ExampleModel::setDouble, defaultValue);
		final DoubleProperty idFxFieldDefault = wrapper.field("idFxFieldDefault", ExampleModel::doubleProperty, defaultValue);

		verify(defaultValue, 10.71, beanField, fxField, beanFieldDefault, fxFieldDefault, idBeanField, idFxField, idBeanFieldDefault, idFxFieldDefault);
	}


	@Test
	public void longProperty() {
		long defaultValue = 5L;

		final LongProperty beanField = wrapper.field(ExampleModel::getLong, ExampleModel::setLong);
		final LongProperty fxField = wrapper.field(ExampleModel::longProperty);
		final LongProperty beanFieldDefault = wrapper.field(ExampleModel::getLong, ExampleModel::setLong, defaultValue);
		final LongProperty fxFieldDefault = wrapper.field(ExampleModel::longProperty, defaultValue);

		final LongProperty idBeanField = wrapper.field("idBeanField", ExampleModel::getLong, ExampleModel::setLong);
		final LongProperty idFxField = wrapper.field("idFxField", ExampleModel::longProperty);
		final LongProperty idBeanFieldDefault = wrapper
				.field("idBeanFieldDefault", ExampleModel::getLong, ExampleModel::setLong, defaultValue);
		final LongProperty idFxFieldDefault = wrapper.field("idFxFieldDefault", ExampleModel::longProperty, defaultValue);

		verify(defaultValue, 10L, beanField, fxField, beanFieldDefault, fxFieldDefault, idBeanField, idFxField, idBeanFieldDefault, idFxFieldDefault);
	}


	@Test
	public void floatProperty() {
		float defaultValue = 5.1F;

		final FloatProperty beanField = wrapper.field(ExampleModel::getFloat, ExampleModel::setFloat);
		final FloatProperty fxField = wrapper.field(ExampleModel::floatProperty);
		final FloatProperty beanFieldDefault = wrapper.field(ExampleModel::getFloat, ExampleModel::setFloat, defaultValue);
		final FloatProperty fxFieldDefault = wrapper.field(ExampleModel::floatProperty, defaultValue);

		final FloatProperty idBeanField = wrapper.field("idBeanField", ExampleModel::getFloat, ExampleModel::setFloat);
		final FloatProperty idFxField = wrapper.field("idFxField", ExampleModel::floatProperty);
		final FloatProperty idBeanFieldDefault = wrapper.field("idBeanFieldDefault", ExampleModel::getFloat,
				ExampleModel::setFloat, defaultValue);
		final FloatProperty idFxFieldDefault = wrapper.field("idFxFieldDefault", ExampleModel::floatProperty, defaultValue);

		verify(defaultValue, 10.52F, beanField, fxField, beanFieldDefault, fxFieldDefault, idBeanField, idFxField, idBeanFieldDefault, idFxFieldDefault);
	}


	@Test
	public void booleanProperty() {
		boolean defaultValue = true;

		final BooleanProperty beanField = wrapper.field(ExampleModel::getBoolean, ExampleModel::setBoolean);
		final BooleanProperty fxField = wrapper.field(ExampleModel::booleanProperty);
		final BooleanProperty beanFieldDefault = wrapper
				.field(ExampleModel::getBoolean, ExampleModel::setBoolean, defaultValue);
		final BooleanProperty fxFieldDefault = wrapper.field(ExampleModel::booleanProperty, defaultValue);

		final BooleanProperty idBeanField = wrapper.field("idBeanField", ExampleModel::getBoolean, ExampleModel::setBoolean);
		final BooleanProperty idFxField = wrapper.field("idFxField", ExampleModel::booleanProperty);
		final BooleanProperty idBeanFieldDefault = wrapper.field("idBeanFieldDefault", ExampleModel::getBoolean,
				ExampleModel::setBoolean, defaultValue);
		final BooleanProperty idFxFieldDefault = wrapper.field("idFxFieldDefault", ExampleModel::booleanProperty, defaultValue);

		verify(defaultValue, false, beanField, fxField, beanFieldDefault, fxFieldDefault, idBeanField, idFxField, idBeanFieldDefault, idFxFieldDefault);
	}


	@Test
	public void stringProperty() {
		String defaultValue = "test";

		final StringProperty beanField = wrapper.field(ExampleModel::getString, ExampleModel::setString);
		final StringProperty fxField = wrapper.field(ExampleModel::stringProperty);
		final StringProperty beanFieldDefault = wrapper.field(ExampleModel::getString, ExampleModel::setString, defaultValue);
		final StringProperty fxFieldDefault = wrapper.field(ExampleModel::stringProperty, defaultValue);

		final StringProperty idBeanField = wrapper.field("idBeanField", ExampleModel::getString, ExampleModel::setString);
		final StringProperty idFxField = wrapper.field("idFxField", ExampleModel::stringProperty);
		final StringProperty idBeanFieldDefault = wrapper.field("idBeanFieldDefault", ExampleModel::getString,
				ExampleModel::setString, defaultValue);
		final StringProperty idFxFieldDefault = wrapper.field("idFxFieldDefault", ExampleModel::stringProperty, defaultValue);

		verify(defaultValue, "hello world", beanField, fxField, beanFieldDefault, fxFieldDefault, idBeanField, idFxField, idBeanFieldDefault, idFxFieldDefault);

	}

	@Test
	public void objectProperty() {
		Person defaultValue = new Person();
		defaultValue.setName("Luise");

		final ObjectProperty<Person> beanField = wrapper.field(ExampleModel::getObject, ExampleModel::setObject);
		final ObjectProperty<Person> fxField = wrapper.field(ExampleModel::objectProperty);
		final ObjectProperty<Person> beanFieldDefault = wrapper.field(ExampleModel::getObject, ExampleModel::setObject,
				defaultValue);
		final ObjectProperty<Person> fxFieldDefault = wrapper.field(ExampleModel::objectProperty, defaultValue);

		final ObjectProperty<Person> idBeanField = wrapper.field("idBeanField", ExampleModel::getObject,
				ExampleModel::setObject);
		final ObjectProperty<Person> idFxField = wrapper.field("idFxField", ExampleModel::objectProperty);
		final ObjectProperty<Person> idBeanFieldDefault = wrapper.field("idBeanFieldDefault", ExampleModel::getObject,
				ExampleModel::setObject, defaultValue);
		final ObjectProperty<Person> idFxFieldDefault = wrapper.field("idFxFieldDefault", ExampleModel::objectProperty,
				defaultValue);

		Person alternativeValue = new Person();
		alternativeValue.setName("Horst");

		verify(defaultValue, alternativeValue, beanField, fxField, beanFieldDefault, fxFieldDefault, idBeanField, idFxField, idBeanFieldDefault, idFxFieldDefault);
	}

	@Test
	public <T> void listProperty() {
		List<String> defaultValue = Collections.emptyList();
		final ListProperty<String> beanField = wrapper.field(ExampleModel::getList, ExampleModel::setList);
		final ListProperty<String> fxField = wrapper.field(ExampleModel::listProperty);
		final ListProperty<String> beanFieldDefault = wrapper.field(ExampleModel::getList, ExampleModel::setList,
				defaultValue);
		final ListProperty<String> fxFieldDefault = wrapper.field(ExampleModel::listProperty, defaultValue);

		final ListProperty<String> idBeanField = wrapper.field("idBeanField", ExampleModel::getList, ExampleModel::setList);
		final ListProperty<String> idFxField = wrapper.field("idFxField", ExampleModel::listProperty);
		final ListProperty<String> idBeanFieldDefault = wrapper.field("idBeanFieldDefault", ExampleModel::getList,
				ExampleModel::setList, defaultValue);
		final ListProperty<String> idFxFieldDefault = wrapper.field("idFxFieldDefault", ExampleModel::listProperty,
				defaultValue);


		// for listProperty we can't use the other "verify" method because of type mismatch.
		verifyId(idBeanField, "idBeanField");
		verifyId(idFxField, "idFxField");
		verifyId(idBeanFieldDefault, "idBeanFieldDefault");
		verifyId(idFxFieldDefault, "idFxFieldDefault");

		List<String> alternativeValue = Arrays.asList("1", "2");
		verifyDefaultValues(beanFieldDefault, defaultValue, alternativeValue);
		verifyDefaultValues(fxFieldDefault, defaultValue, alternativeValue);
		verifyDefaultValues(idBeanFieldDefault, defaultValue, alternativeValue);
		verifyDefaultValues(idFxFieldDefault, defaultValue, alternativeValue);
	}

	@Test
	public <T> void lsetProperty() {
		Set<String> defaultValue = Collections.emptySet();
		final SetProperty<String> beanField = wrapper.field(ExampleModel::getSet, ExampleModel::setSet);
		final SetProperty<String> fxField = wrapper.field(ExampleModel::setProperty);
		final SetProperty<String> beanFieldDefault = wrapper.field(ExampleModel::getSet, ExampleModel::setSet,
				defaultValue);
		final SetProperty<String> fxFieldDefault = wrapper.field(ExampleModel::setProperty, defaultValue);

		final SetProperty<String> idBeanField = wrapper.field("idBeanField", ExampleModel::getSet, ExampleModel::setSet);
		final SetProperty<String> idFxField = wrapper.field("idFxField", ExampleModel::setProperty);
		final SetProperty<String> idBeanFieldDefault = wrapper.field("idBeanFieldDefault", ExampleModel::getSet,
				ExampleModel::setSet, defaultValue);
		final SetProperty<String> idFxFieldDefault = wrapper.field("idFxFieldDefault", ExampleModel::setProperty,
				defaultValue);

		// for listProperty we can't use the other "verify" method because of type mismatch.
		verifyId(idBeanField, "idBeanField");
		verifyId(idFxField, "idFxField");
		verifyId(idBeanFieldDefault, "idBeanFieldDefault");
		verifyId(idFxFieldDefault, "idFxFieldDefault");

		Set<String> alternativeValue = Sets.newHashSet("1", "2");
		verifyDefaultValues(beanFieldDefault, defaultValue, alternativeValue);
		verifyDefaultValues(fxFieldDefault, defaultValue, alternativeValue);
		verifyDefaultValues(idBeanFieldDefault, defaultValue, alternativeValue);
		verifyDefaultValues(idFxFieldDefault, defaultValue, alternativeValue);
	}

	private <T> void verifyDefaultValues(Collection<T> property, Collection<T> defaultValue, Collection<T> otherValue) {
		property.addAll(otherValue);

		wrapper.commit();
		assertThat(property).containsAll(otherValue);

		wrapper.reset();
		assertThat(property).containsAll(defaultValue);
	}
}
