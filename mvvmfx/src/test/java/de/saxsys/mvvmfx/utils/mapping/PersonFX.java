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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.util.List;

public class PersonFX {
	
	private StringProperty name = new SimpleStringProperty();
	
	private IntegerProperty age = new SimpleIntegerProperty();

	private ListProperty<String> nicknames = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public String getName() {
		return name.get();
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public int getAge() {
		return age.get();
	}
	
	public IntegerProperty ageProperty() {
		return age;
	}
	
	public void setAge(int age) {
		this.age.set(age);
	}

	public List<String> getNicknames() {
		return nicknames.get();
	}

	public ListProperty<String> nicknamesProperty() {
		return nicknames;
	}

	public void setNicknames(List<String> nicknames) {
		this.nicknames.setAll(nicknames);
	}
}
