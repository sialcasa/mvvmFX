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

import javafx.beans.property.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * This test is used to check the return values when fields are mapped. See Issue 211 <a
 * href="https://github.com/sialcasa/mvvmFX/issues/211">https://github.com/sialcasa/mvvmFX/issues/211</a>
 */
public class ReturnTypeTest {
	
	private ModelWrapper<ExampleModel> wrapper;
	
	private ExampleModel model;
	
	@Before
	public void setup() {
		wrapper = new ModelWrapper<>();
		model = new ExampleModel();
	}
	
	
	@Test
	public void integerProperty() {
		final IntegerProperty beanField = wrapper.field(ExampleModel::getInteger, ExampleModel::setInteger);
		final IntegerProperty fxField = wrapper.field(ExampleModel::integerProperty);
		final IntegerProperty beanFieldDefault = wrapper.field(ExampleModel::getInteger, ExampleModel::setInteger, 5);
		final IntegerProperty fxFieldDefault = wrapper.field(ExampleModel::integerProperty, 5);
		
		final IntegerProperty idBeanField = wrapper.field("int1", ExampleModel::getInteger, ExampleModel::setInteger);
		final IntegerProperty idFxField = wrapper.field("int2", ExampleModel::integerProperty);
		final IntegerProperty idBeanFieldDefault = wrapper.field("int3", ExampleModel::getInteger,
				ExampleModel::setInteger, 5);
		final IntegerProperty idFxFieldDefault = wrapper.field("int4", ExampleModel::integerProperty, 5);
		
		
	}
	
	@Test
	public void doubleProperty() {
		final DoubleProperty beanField = wrapper.field(ExampleModel::getDouble, ExampleModel::setDouble);
		final DoubleProperty fxField = wrapper.field(ExampleModel::doubleProperty);
		final DoubleProperty beanFieldDefault = wrapper.field(ExampleModel::getDouble, ExampleModel::setDouble, 5.1);
		final DoubleProperty fxFieldDefault = wrapper.field(ExampleModel::doubleProperty, 5.1);
		
		final DoubleProperty idBeanField = wrapper.field("double1", ExampleModel::getDouble, ExampleModel::setDouble);
		final DoubleProperty idFxField = wrapper.field("double2", ExampleModel::doubleProperty);
		final DoubleProperty idBeanFieldDefault = wrapper.field("double3", ExampleModel::getDouble,
				ExampleModel::setDouble, 5.1);
		final DoubleProperty idFxFieldDefault = wrapper.field("double4", ExampleModel::doubleProperty, 5.1);
	}
	
	
	@Test
	public void longProperty() {
		final LongProperty beanField = wrapper.field(ExampleModel::getLong, ExampleModel::setLong);
		final LongProperty fxField = wrapper.field(ExampleModel::longProperty);
		final LongProperty beanFieldDefault = wrapper.field(ExampleModel::getLong, ExampleModel::setLong, 5l);
		final LongProperty fxFieldDefault = wrapper.field(ExampleModel::longProperty, 5l);
		
		final LongProperty idBeanField = wrapper.field("long1", ExampleModel::getLong, ExampleModel::setLong);
		final LongProperty idFxField = wrapper.field("long2", ExampleModel::longProperty);
		final LongProperty idBeanFieldDefault = wrapper
				.field("long3", ExampleModel::getLong, ExampleModel::setLong, 5l);
		final LongProperty idFxFieldDefault = wrapper.field("long4", ExampleModel::longProperty, 5l);
	}
	
	
	@Test
	public void floatProperty() {
		final FloatProperty beanField = wrapper.field(ExampleModel::getFloat, ExampleModel::setFloat);
		final FloatProperty fxField = wrapper.field(ExampleModel::floatProperty);
		final FloatProperty beanFieldDefault = wrapper.field(ExampleModel::getFloat, ExampleModel::setFloat, 5.1f);
		final FloatProperty fxFieldDefault = wrapper.field(ExampleModel::floatProperty, 5.1f);
		
		final FloatProperty idBeanField = wrapper.field("float1", ExampleModel::getFloat, ExampleModel::setFloat);
		final FloatProperty idFxField = wrapper.field("float2", ExampleModel::floatProperty);
		final FloatProperty idBeanFieldDefault = wrapper.field("float3", ExampleModel::getFloat,
				ExampleModel::setFloat, 5.1f);
		final FloatProperty idFxFieldDefault = wrapper.field("float4", ExampleModel::floatProperty, 5.1f);
	}
	
	
	@Test
	public void booleanProperty() {
		final BooleanProperty beanField = wrapper.field(ExampleModel::getBoolean, ExampleModel::setBoolean);
		final BooleanProperty fxField = wrapper.field(ExampleModel::booleanProperty);
		final BooleanProperty beanFieldDefault = wrapper
				.field(ExampleModel::getBoolean, ExampleModel::setBoolean, true);
		final BooleanProperty fxFieldDefault = wrapper.field(ExampleModel::booleanProperty, true);
		
		final BooleanProperty idBeanField = wrapper.field("bool1", ExampleModel::getBoolean, ExampleModel::setBoolean);
		final BooleanProperty idFxField = wrapper.field("bool2", ExampleModel::booleanProperty);
		final BooleanProperty idBeanFieldDefault = wrapper.field("bool3", ExampleModel::getBoolean,
				ExampleModel::setBoolean, true);
		final BooleanProperty idFxFieldDefault = wrapper.field("bool4", ExampleModel::booleanProperty, true);
	}
	
	
	@Test
	public void stringProperty() {
		final StringProperty beanField = wrapper.field(ExampleModel::getString, ExampleModel::setString);
		final StringProperty fxField = wrapper.field(ExampleModel::stringProperty);
		final StringProperty beanFieldDefault = wrapper.field(ExampleModel::getString, ExampleModel::setString, "test");
		final StringProperty fxFieldDefault = wrapper.field(ExampleModel::stringProperty, "test");
		
		final StringProperty idBeanField = wrapper.field("string1", ExampleModel::getString, ExampleModel::setString);
		final StringProperty idFxField = wrapper.field("string2", ExampleModel::stringProperty);
		final StringProperty idBeanFieldDefault = wrapper.field("string3", ExampleModel::getString,
				ExampleModel::setString, "test");
		final StringProperty idFxFieldDefault = wrapper.field("string4", ExampleModel::stringProperty, "test");
	}
	
	@Test
	public void objectProperty() {
		final ObjectProperty<Person> beanField = wrapper.field(ExampleModel::getObject, ExampleModel::setObject);
		final ObjectProperty<Person> fxField = wrapper.field(ExampleModel::objectProperty);
		final ObjectProperty<Person> beanFieldDefault = wrapper.field(ExampleModel::getObject, ExampleModel::setObject,
				new Person());
		final ObjectProperty<Person> fxFieldDefault = wrapper.field(ExampleModel::objectProperty, new Person());
		
		final ObjectProperty<Person> idBeanField = wrapper.field("obj1", ExampleModel::getObject,
				ExampleModel::setObject);
		final ObjectProperty<Person> idFxField = wrapper.field("obj2", ExampleModel::objectProperty);
		final ObjectProperty<Person> idBeanFieldDefault = wrapper.field("obj3", ExampleModel::getObject,
				ExampleModel::setObject, new Person());
		final ObjectProperty<Person> idFxFieldDefault = wrapper.field("obj4", ExampleModel::objectProperty,
				new Person());
	}
	
	@Test
	public void listProperty() {
		final ListProperty<String> beanField = wrapper.field(ExampleModel::getList, ExampleModel::setList);
		final ListProperty<String> fxField = wrapper.field(ExampleModel::listProperty);
		final ListProperty<String> beanFieldDefault = wrapper.field(ExampleModel::getList, ExampleModel::setList,
				Collections.emptyList());
		final ListProperty<String> fxFieldDefault = wrapper.field(ExampleModel::listProperty, Arrays.asList());

		final ListProperty<String> idBeanField = wrapper.field("list1", ExampleModel::getList, ExampleModel::setList);
		final ListProperty<String> idFxField = wrapper.field("list2", ExampleModel::listProperty);
		final ListProperty<String> idBeanFieldDefault = wrapper.field("list3", ExampleModel::getList,
				ExampleModel::setList, Collections.emptyList());
		final ListProperty<String> idFxFieldDefault = wrapper.field("list4", ExampleModel::listProperty,
				Arrays.asList());
	}
}
