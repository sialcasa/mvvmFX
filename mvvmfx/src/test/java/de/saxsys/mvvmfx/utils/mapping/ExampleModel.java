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

import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExampleModel {

	private IntegerProperty integerProperty = new SimpleIntegerProperty();
	private DoubleProperty doubleProperty = new SimpleDoubleProperty();
	private FloatProperty floatProperty = new SimpleFloatProperty();
	private LongProperty longProperty = new SimpleLongProperty();

	private StringProperty stringProperty = new SimpleStringProperty();

	private ObjectProperty<Person> objectProperty = new SimpleObjectProperty<>();

	private BooleanProperty booleanProperty = new SimpleBooleanProperty();

	private ListProperty<String> listProperty = new SimpleListProperty<>();

	private SetProperty<String> setProperty = new SimpleSetProperty<>();

	private MapProperty<String, String> mapProperty = new SimpleMapProperty<>();

	public int getInteger() {
		return integerProperty.get();
	}

	public IntegerProperty integerProperty() {
		return integerProperty;
	}

	public void setInteger(int integerProperty) {
		this.integerProperty.set(integerProperty);
	}

	public double getDouble() {
		return doubleProperty.get();
	}

	public DoubleProperty doubleProperty() {
		return doubleProperty;
	}

	public void setDouble(double doubleProperty) {
		this.doubleProperty.set(doubleProperty);
	}

	public float getFloat() {
		return floatProperty.get();
	}

	public FloatProperty floatProperty() {
		return floatProperty;
	}

	public void setFloat(float floatProperty) {
		this.floatProperty.set(floatProperty);
	}

	public long getLong() {
		return longProperty.get();
	}

	public LongProperty longProperty() {
		return longProperty;
	}

	public void setLong(long longProperty) {
		this.longProperty.set(longProperty);
	}

	public String getString() {
		return stringProperty.get();
	}

	public StringProperty stringProperty() {
		return stringProperty;
	}

	public void setString(String stringProperty) {
		this.stringProperty.set(stringProperty);
	}

	public Person getObject() {
		return objectProperty.get();
	}

	public ObjectProperty<Person> objectProperty() {
		return objectProperty;
	}

	public void setObject(Person objectProperty) {
		this.objectProperty.set(objectProperty);
	}

	public boolean getBoolean() {
		return booleanProperty.get();
	}

	public BooleanProperty booleanProperty() {
		return booleanProperty;
	}

	public void setBoolean(boolean booleanProperty) {
		this.booleanProperty.set(booleanProperty);
	}

	public List<String> getList() {
		return listProperty.get();
	}

	public ListProperty<String> listProperty() {
		return listProperty;
	}

	public void setList(List<String> listProperty) {
		this.listProperty.setAll(listProperty);
	}

	public Set<String> getSet() {
		return setProperty.get();
	}

	public SetProperty<String> setProperty() {
		return setProperty;
	}

	public void setSet(Set<String> set) {
		this.setProperty.retainAll(set);
		this.setProperty.addAll(set);
	}

	public Map<String, String> getMap() {
		return mapProperty.get();
	}

	public MapProperty<String, String> mapProperty() {
		return mapProperty;
	}

	public void setMap(Map<String, String> map) {
		this.mapProperty.get().clear();
		this.mapProperty.get().putAll(map);
	}
}
