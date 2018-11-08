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
import javafx.beans.property.MapProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PersonFX {

    private StringProperty name = new SimpleStringProperty();

    private IntegerProperty age = new SimpleIntegerProperty();

    private ListProperty<String> nicknames = new SimpleListProperty<>(FXCollections.observableArrayList());

    private SetProperty<String> emailAddresses = new SimpleSetProperty<>(FXCollections.observableSet(new HashSet<>()));

    private MapProperty<String, String> phoneNumbers = new SimpleMapProperty<>(
            FXCollections.observableMap(new HashMap<>()));

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

    public Set<String> getEmailAddresses() {
        return emailAddresses.get();
    }

    public SetProperty<String> emailAddressesProperty() {
        return emailAddresses;
    }

    public void setEmailAddresses(Set<String> emailAddresses) {
        this.emailAddresses.addAll(emailAddresses);
    }

    public Map<String, String> getPhoneNumbers() { return phoneNumbers.get(); }

    public MapProperty<String, String> phoneNumbersProperty() { return phoneNumbers; }

    public void setPhoneNumbers(ObservableMap<String, String> phoneNumbers) { this.phoneNumbers.set(phoneNumbers); }
}
