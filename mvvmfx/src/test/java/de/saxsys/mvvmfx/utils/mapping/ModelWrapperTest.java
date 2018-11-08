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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelWrapperTest {

    @Test
    public void testCopyValuesTo() {
        // given
        Person personA = new Person();
        personA.setName("horst");
        personA.setAge(32);
        personA.setNicknames(Collections.singletonList("captain"));
        personA.setEmailAddresses(Collections.singleton("test@example.org"));
        Map<String, String> phoneNumberMapHorst = new HashMap<>();
        phoneNumberMapHorst.put("private", "0351 1234567");
        personA.setPhoneNumbers(phoneNumberMapHorst);

        ModelWrapper<Person> personWrapper = new ModelWrapper<>(personA);

        final StringProperty nameProperty = personWrapper.field(Person::getName, Person::setName);
        final IntegerProperty ageProperty = personWrapper.field(Person::getAge, Person::setAge);
        final ListProperty<String> nicknamesProperty = personWrapper.field(Person::getNicknames, Person::setNicknames);
        final SetProperty<String> emailAddressesProperty = personWrapper.field(Person::getEmailAddresses,
                Person::setEmailAddresses);
        final MapProperty<String, String> phoneNumbersProperty = personWrapper.field(Person::getPhoneNumbers,
                Person::setPhoneNumbers);

        assertThat(nameProperty.getValue()).isEqualTo("horst");
        assertThat(ageProperty.getValue()).isEqualTo(32);
        assertThat(nicknamesProperty.getValue()).containsOnly("captain");
        assertThat(emailAddressesProperty.getValue()).containsOnly("test@example.org");
        assertThat(phoneNumbersProperty.getValue()).containsOnly(MapEntry.entry("private", "0351 1234567"));

        // when
        Person personB = new Person();
        personB.setName("Luise");
        personB.setAge(23);
        personB.setNicknames(Collections.singletonList("lui"));
        personB.setEmailAddresses(Collections.singleton("luise@example.org"));
        personB.setPhoneNumbers(Collections.singletonMap("private", "03581 7654321"));

        personWrapper.copyValuesTo(personB);

        // then
        // person b has new values
        assertThat(personB.getName()).isEqualTo("horst");
        assertThat(personB.getAge()).isEqualTo(32);
        assertThat(personB.getNicknames()).containsExactly("captain");
        assertThat(personB.getEmailAddresses()).containsExactly("test@example.org");
        assertThat(personB.getPhoneNumbers()).containsExactly(MapEntry.entry("private", "0351 1234567"));

        // the properties have still the old values
        assertThat(nameProperty.getValue()).isEqualTo("horst");
        assertThat(ageProperty.getValue()).isEqualTo(32);
        assertThat(nicknamesProperty.getValue()).containsOnly("captain");
        assertThat(emailAddressesProperty.getValue()).containsOnly("test@example.org");
        assertThat(phoneNumbersProperty.getValue()).containsOnly(MapEntry.entry("private", "0351 1234567"));

        // and of cause the old person a has it's old values too
        assertThat(personA.getName()).isEqualTo("horst");
        assertThat(personA.getAge()).isEqualTo(32);
        assertThat(personA.getNicknames()).containsExactly("captain");
        assertThat(personA.getEmailAddresses()).containsExactly("test@example.org");
        assertThat(personA.getPhoneNumbers()).containsExactly(MapEntry.entry("private", "0351 1234567"));
    }

    @Test
    public void testWithGetterAndSetter() {
        Person person = new Person();
        person.setName("horst");
        person.setAge(32);
        person.setNicknames(Arrays.asList("captain"));
        person.setEmailAddresses(Collections.singleton("test@example.org"));
        person.setPhoneNumbers(Collections.singletonMap("private", "0351 1234567"));

        ModelWrapper<Person> personWrapper = new ModelWrapper<>(person);

        final StringProperty nameProperty = personWrapper.field(Person::getName, Person::setName);
        final IntegerProperty ageProperty = personWrapper.field(Person::getAge, Person::setAge);
        final ListProperty<String> nicknamesProperty = personWrapper.field(Person::getNicknames, Person::setNicknames);
        final SetProperty<String> emailAddressesProperty = personWrapper.field(Person::getEmailAddresses,
                Person::setEmailAddresses);
        final MapProperty<String, String> phoneNumbersProperty = personWrapper.field(Person::getPhoneNumbers,
                Person::setPhoneNumbers);

        assertThat(nameProperty.getValue()).isEqualTo("horst");
        assertThat(ageProperty.getValue()).isEqualTo(32);
        assertThat(nicknamesProperty.getValue()).containsOnly("captain");
        assertThat(emailAddressesProperty.getValue()).containsOnly("test@example.org");
        assertThat(phoneNumbersProperty.getValue()).containsOnly(MapEntry.entry("private", "0351 1234567"));

        nameProperty.setValue("hugo");
        ageProperty.setValue(33);
        nicknamesProperty.add("player");
        emailAddressesProperty.add("test2@example.org");
        phoneNumbersProperty.putIfAbsent("mobile", "0123 4567890");

        // still the old values
        assertThat(person.getName()).isEqualTo("horst");
        assertThat(person.getAge()).isEqualTo(32);
        assertThat(person.getNicknames()).containsOnly("captain");
        assertThat(person.getEmailAddresses()).containsOnly("test@example.org");
        assertThat(person.getPhoneNumbers()).containsOnly(MapEntry.entry("private", "0351 1234567"));

        personWrapper.commit();

        // now the new values are reflected in the wrapped person
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);
        assertThat(person.getNicknames()).containsOnly("captain", "player");
        assertThat(person.getEmailAddresses()).containsOnly("test@example.org", "test2@example.org");
        assertThat(person.getPhoneNumbers()).containsOnly(
                MapEntry.entry("private", "0351 1234567"),
                MapEntry.entry("mobile", "0123 4567890"));

        nameProperty.setValue("luise");
        ageProperty.setValue(15);
        nicknamesProperty.setValue(FXCollections.observableArrayList("student"));
        emailAddressesProperty.setValue(FXCollections.observableSet("luise@example.org"));
        Map<String, String> phoneNumberMapLuise = new HashMap<String, String>();
        phoneNumberMapLuise.put("mobile", "0124 9876543");
        phoneNumbersProperty.setValue(FXCollections.observableMap(phoneNumberMapLuise));

        personWrapper.reset();

        assertThat(nameProperty.getValue()).isEqualTo(null);
        assertThat(ageProperty.getValue()).isEqualTo(0);
        assertThat(nicknamesProperty.getValue()).isEmpty();
        assertThat(emailAddressesProperty.getValue()).isEmpty();
        assertThat(phoneNumbersProperty.getValue()).isEmpty();

        // the wrapped object has still the values from the last commit.
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);
        assertThat(person.getNicknames()).containsOnly("captain", "player");
        assertThat(person.getEmailAddresses()).containsOnly("test@example.org", "test2@example.org");
        assertThat(person.getPhoneNumbers()).containsOnly(
                MapEntry.entry("private", "0351 1234567"),
                MapEntry.entry("mobile", "0123 4567890"));

        personWrapper.reload();

        // now the properties have the values from the wrapped object
        assertThat(nameProperty.getValue()).isEqualTo("hugo");
        assertThat(ageProperty.getValue()).isEqualTo(33);
        assertThat(nicknamesProperty.get()).containsOnly("captain", "player");
        assertThat(emailAddressesProperty.get()).containsOnly("test@example.org", "test2@example.org");
        assertThat(phoneNumbersProperty.get()).containsOnly(
                MapEntry.entry("private", "0351 1234567"),
                MapEntry.entry("mobile", "0123 4567890"));

        Person otherPerson = new Person();
        otherPerson.setName("gisela");
        otherPerson.setAge(23);
        otherPerson.setNicknames(Arrays.asList("referee"));
        otherPerson.setEmailAddresses(Collections.singleton("gisela@example.org"));
        otherPerson.setPhoneNumbers(Collections.singletonMap("private", "030 112233"));

        personWrapper.set(otherPerson);
        personWrapper.reload();

        assertThat(nameProperty.getValue()).isEqualTo("gisela");
        assertThat(ageProperty.getValue()).isEqualTo(23);
        assertThat(nicknamesProperty.getValue()).containsOnly("referee");
        assertThat(emailAddressesProperty.getValue()).containsOnly("gisela@example.org");

        nameProperty.setValue("georg");
        ageProperty.setValue(24);
        nicknamesProperty.setValue(FXCollections.observableArrayList("spectator"));
        emailAddressesProperty.setValue(FXCollections.observableSet("georg@example.org"));
        phoneNumbersProperty.setValue(FXCollections.observableMap(Collections.singletonMap("private", "0351 7654321")));

        personWrapper.commit();

        // old person has still the old values
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);
        assertThat(person.getNicknames()).containsOnly("captain", "player");
        assertThat(person.getEmailAddresses()).containsOnly("test@example.org", "test2@example.org");
        assertThat(person.getPhoneNumbers()).containsOnly(
                MapEntry.entry("private", "0351 1234567"),
                MapEntry.entry("mobile", "0123 4567890"));

        // new person has the new values
        assertThat(otherPerson.getName()).isEqualTo("georg");
        assertThat(otherPerson.getAge()).isEqualTo(24);
        assertThat(otherPerson.getNicknames()).containsOnly("spectator");
        assertThat(otherPerson.getEmailAddresses()).containsOnly("georg@example.org");
        assertThat(otherPerson.getPhoneNumbers()).containsOnly(MapEntry.entry("private", "0351 7654321"));
    }

    @Test
    public void testWithJavaFXPropertiesField() {
        PersonFX person = new PersonFX();
        person.setName("horst");
        person.setAge(32);
        person.setNicknames(Arrays.asList("captain"));
        person.setEmailAddresses(Collections.singleton("test@example.org"));
        Map<String, String> phoneNumberMap = new HashMap<>();
        phoneNumberMap.put("private", "0351 1234567");
        person.setPhoneNumbers(FXCollections.observableMap(phoneNumberMap));

        ModelWrapper<PersonFX> personWrapper = new ModelWrapper<>(person);

        final StringProperty nameProperty = personWrapper.field(PersonFX::nameProperty);
        final IntegerProperty ageProperty = personWrapper.field(PersonFX::ageProperty);
        final ListProperty<String> nicknamesProperty = personWrapper.field(PersonFX::nicknamesProperty);
        final SetProperty<String> emailAddressesProperty = personWrapper.field(PersonFX::emailAddressesProperty);
        final MapProperty<String, String> phoneNumbersProperty = personWrapper.field(PersonFX::phoneNumbersProperty);

        assertThat(nameProperty.getValue()).isEqualTo("horst");
        assertThat(ageProperty.getValue()).isEqualTo(32);
        assertThat(nicknamesProperty.getValue()).containsOnly("captain");
        assertThat(emailAddressesProperty.getValue()).containsOnly("test@example.org");
        assertThat(phoneNumbersProperty.getValue()).containsOnly(MapEntry.entry("private", "0351 1234567"));

        nameProperty.setValue("hugo");
        ageProperty.setValue(33);
        nicknamesProperty.add("player");
        emailAddressesProperty.add("test2@example.org");
        phoneNumbersProperty.putIfAbsent("mobile", "0123 4567890");

        // still the old values
        assertThat(person.getName()).isEqualTo("horst");
        assertThat(person.getAge()).isEqualTo(32);
        assertThat(person.getNicknames()).containsOnly("captain");
        assertThat(person.getEmailAddresses()).containsOnly("test@example.org");
        assertThat(person.getPhoneNumbers()).containsOnly(MapEntry.entry("private", "0351 1234567"));

        personWrapper.commit();

        // now the new values are reflected in the wrapped person
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);
        assertThat(person.getNicknames()).containsOnly("captain", "player");
        assertThat(person.getEmailAddresses()).containsOnly("test@example.org", "test2@example.org");
        assertThat(person.getPhoneNumbers()).containsOnly(
                MapEntry.entry("private", "0351 1234567"),
                MapEntry.entry("mobile", "0123 4567890"));

        nameProperty.setValue("luise");
        ageProperty.setValue(15);
        nicknamesProperty.setValue(FXCollections.observableArrayList("student"));
        emailAddressesProperty.setValue(FXCollections.observableSet("luise@example.org"));
        Map<String, String> phoneNumberMap2 = new HashMap<>();
        phoneNumberMap2.put("mobile", "0124 9876543");
        phoneNumbersProperty.setValue(FXCollections.observableMap(phoneNumberMap2));

        personWrapper.reset();

        assertThat(nameProperty.getValue()).isEqualTo(null);
        assertThat(ageProperty.getValue()).isEqualTo(0);
        assertThat(nicknamesProperty.getValue()).isEmpty();
        assertThat(emailAddressesProperty.getValue()).isEmpty();
        assertThat(phoneNumbersProperty.getValue()).isEmpty();

        // the wrapped object has still the values from the last commit.
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);
        assertThat(person.getNicknames()).containsOnly("captain", "player");
        assertThat(person.getEmailAddresses()).containsOnly("test@example.org", "test2@example.org");
        assertThat(person.getPhoneNumbers()).containsOnly(
                MapEntry.entry("private", "0351 1234567"),
                MapEntry.entry("mobile", "0123 4567890"));

        personWrapper.reload();
        // now the properties have the values from the wrapped object
        assertThat(nameProperty.getValue()).isEqualTo("hugo");
        assertThat(ageProperty.getValue()).isEqualTo(33);
        assertThat(nicknamesProperty.get()).containsOnly("captain", "player");
        assertThat(emailAddressesProperty.get()).containsOnly("test@example.org", "test2@example.org");
        assertThat(person.getPhoneNumbers()).containsOnly(
                MapEntry.entry("private", "0351 1234567"),
                MapEntry.entry("mobile", "0123 4567890"));

        PersonFX otherPerson = new PersonFX();
        otherPerson.setName("gisela");
        otherPerson.setAge(23);
        otherPerson.setNicknames(Arrays.asList("referee"));
        otherPerson.setEmailAddresses(Collections.singleton("gisela@example.org"));
        Map<String, String> phoneNumberMap3 = new HashMap<>();
        phoneNumberMap3.put("private", "030 112233");
        otherPerson.setPhoneNumbers(FXCollections.observableMap(phoneNumberMap3));

        personWrapper.set(otherPerson);
        personWrapper.reload();

        assertThat(nameProperty.getValue()).isEqualTo("gisela");
        assertThat(ageProperty.getValue()).isEqualTo(23);
        assertThat(nicknamesProperty.getValue()).containsOnly("referee");
        assertThat(emailAddressesProperty.getValue()).containsOnly("gisela@example.org");
        assertThat(phoneNumbersProperty.getValue()).containsOnly(MapEntry.entry("private", "030 112233"));

        nameProperty.setValue("georg");
        ageProperty.setValue(24);
        nicknamesProperty.setValue(FXCollections.observableArrayList("spectator"));
        emailAddressesProperty.setValue(FXCollections.observableSet("georg@example.org"));
        Map<String, String> phoneNumberMap4 = new HashMap<>();
        phoneNumberMap4.put("private", "0351 7654321");
        phoneNumbersProperty.setValue(FXCollections.observableMap(phoneNumberMap4));

        personWrapper.commit();

        // old person has still the old values
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);
        assertThat(person.getNicknames()).containsOnly("captain", "player");
        assertThat(person.getEmailAddresses()).containsOnly("test@example.org", "test2@example.org");
        assertThat(person.getPhoneNumbers()).containsOnly(
                MapEntry.entry("private", "0351 1234567"),
                MapEntry.entry("mobile", "0123 4567890"));

        // new person has the new values
        assertThat(otherPerson.getName()).isEqualTo("georg");
        assertThat(otherPerson.getAge()).isEqualTo(24);
        assertThat(otherPerson.getNicknames()).containsOnly("spectator");
        assertThat(otherPerson.getEmailAddresses()).containsOnly("georg@example.org");
        assertThat(otherPerson.getPhoneNumbers()).containsOnly(MapEntry.entry("private", "0351 7654321"));
    }

    @Test
    public void testWithImmutables() {

        PersonImmutable person1 = PersonImmutable.create()
                .withName("horst")
                .withAge(32)
                .withNicknames(Collections.singletonList("captain"))
                .withEmailAddresses(Collections.singleton("test@example.org"))
                .withPhoneNumbers(Collections.singletonMap("private", "0351 1234567"));

        ModelWrapper<PersonImmutable> personWrapper = new ModelWrapper<>(person1);

        final StringProperty nameProperty = personWrapper
                .immutableField(PersonImmutable::getName, PersonImmutable::withName);
        final IntegerProperty ageProperty = personWrapper.immutableField(PersonImmutable::getAge,
                PersonImmutable::withAge);
        final ListProperty<String> nicknamesProperty = personWrapper.immutableField(PersonImmutable::getNicknames,
                PersonImmutable::withNicknames);
        final SetProperty<String> emailAddressesProperty = personWrapper.immutableField(
                PersonImmutable::getEmailAddresses,
                PersonImmutable::withEmailAddresses);
        final MapProperty<String, String> phoneNumbersProperty = personWrapper.immutableField(
                PersonImmutable::getPhoneNumbers, PersonImmutable::withPhoneNumbers);

        assertThat(nameProperty.getValue()).isEqualTo("horst");
        assertThat(ageProperty.getValue()).isEqualTo(32);
        assertThat(nicknamesProperty.getValue()).containsOnly("captain");
        assertThat(emailAddressesProperty.getValue()).containsOnly("test@example.org");
        assertThat(phoneNumbersProperty.getValue()).containsOnly(MapEntry.entry("private", "0351 1234567"));

        nameProperty.setValue("hugo");
        ageProperty.setValue(33);
        nicknamesProperty.add("player");
        emailAddressesProperty.add("hugo@example.org");
        phoneNumbersProperty.putIfAbsent("mobile", "0123 4567890");

        personWrapper.commit();

        // old person has still the same old values.
        assertThat(person1.getName()).isEqualTo("horst");
        assertThat(person1.getAge()).isEqualTo(32);
        assertThat(person1.getNicknames()).containsOnly("captain");
        assertThat(person1.getEmailAddresses()).containsOnly("test@example.org");
        assertThat(person1.getPhoneNumbers()).containsOnly(MapEntry.entry("private", "0351 1234567"));

        PersonImmutable person2 = personWrapper.get();

        assertThat(person2).isNotEqualTo(person1);
        assertThat(person2.getName()).isEqualTo("hugo");
        assertThat(person2.getAge()).isEqualTo(33);
        assertThat(person2.getNicknames()).containsOnly("captain", "player");
        assertThat(person2.getEmailAddresses()).containsOnly("test@example.org", "hugo@example.org");
        assertThat(person2.getPhoneNumbers()).containsOnly(
                MapEntry.entry("mobile", "0123 4567890"),
                MapEntry.entry("private", "0351 1234567"));

        nameProperty.setValue("luise");
        ageProperty.setValue(33);
        nicknamesProperty.setValue(FXCollections.observableArrayList("student"));
        emailAddressesProperty.setValue(FXCollections.observableSet("luise@example.org"));
        Map<String, String> phoneNumberMapLuise = new HashMap<>();
        phoneNumberMapLuise.put("mobile", "0124 9876543");
        phoneNumbersProperty.setValue(FXCollections.observableMap(phoneNumberMapLuise));

        personWrapper.reset();

        assertThat(nameProperty.getValue()).isEqualTo(null);
        assertThat(ageProperty.getValue()).isEqualTo(0);
        assertThat(nicknamesProperty.getValue()).isEmpty();
        assertThat(emailAddressesProperty.getValue()).isEmpty();
        assertThat(phoneNumbersProperty.getValue()).isEmpty();

        personWrapper.reload();

        // now the properties have the values from the wrapped object
        assertThat(nameProperty.getValue()).isEqualTo("hugo");
        assertThat(ageProperty.getValue()).isEqualTo(33);
        assertThat(nicknamesProperty.get()).containsOnly("captain", "player");
        assertThat(emailAddressesProperty.get()).containsOnly("test@example.org", "hugo@example.org");
        assertThat(phoneNumbersProperty.get()).containsOnly(
                MapEntry.entry("mobile", "0123 4567890"),
                MapEntry.entry("private", "0351 1234567"));

        PersonImmutable person3 = person1.withName("gisela")
                .withAge(23)
                .withNicknames(Collections.singletonList("referee"))
                .withEmailAddresses(Collections.singleton("gisela@example.org"))
                .withPhoneNumbers(Collections.singletonMap("private", "030 112233"));

        personWrapper.set(person3);
        personWrapper.reload();

        assertThat(nameProperty.getValue()).isEqualTo("gisela");
        assertThat(ageProperty.getValue()).isEqualTo(23);
        assertThat(nicknamesProperty.getValue()).containsOnly("referee");
        assertThat(emailAddressesProperty.getValue()).containsOnly("gisela@example.org");
        assertThat(phoneNumbersProperty.getValue()).containsOnly(MapEntry.entry("private", "030 112233"));
    }

    @Test
    public void testIdentifiedFields() {
        Person person = new Person();
        person.setName("horst");
        person.setAge(32);
        person.setNicknames(Arrays.asList("captain"));
        person.setEmailAddresses(Collections.singleton("test@example.org"));
        person.setPhoneNumbers(Collections.singletonMap("private", "0351 1234567"));

        ModelWrapper<Person> personWrapper = new ModelWrapper<>();

        final StringProperty nameProperty = personWrapper.field("name", Person::getName, Person::setName);
        final IntegerProperty ageProperty = personWrapper.field("age", Person::getAge, Person::setAge);
        final ListProperty<String> nicknamesProperty = personWrapper.field("nicknames", Person::getNicknames,
                Person::setNicknames);
        final SetProperty<String> emailAddressesProperty = personWrapper.field("emailAddresses",
                Person::getEmailAddresses,
                Person::setEmailAddresses);
        final MapProperty<String, String> phoneNumbersProperty = personWrapper.field("phoneNumbers",
                Person::getPhoneNumbers,
                Person::setPhoneNumbers);

        final StringProperty nameProperty2 = personWrapper.field("name", Person::getName, Person::setName);
        final IntegerProperty ageProperty2 = personWrapper.field("age", Person::getAge, Person::setAge);
        final ListProperty<String> nicknamesProperty2 = personWrapper.field("nicknames", Person::getNicknames,
                Person::setNicknames);
        final SetProperty<String> emailAddressesProperty2 = personWrapper.field("emailAddresses",
                Person::getEmailAddresses,
                Person::setEmailAddresses);
        final MapProperty<String, String> phoneNumbersProperty2 = personWrapper.field("phoneNumbers",
                Person::getPhoneNumbers,
                Person::setPhoneNumbers);

        assertThat(nameProperty).isSameAs(nameProperty2);
        assertThat(ageProperty).isSameAs(ageProperty2);
        assertThat(nicknamesProperty).isSameAs(nicknamesProperty2);
        assertThat(emailAddressesProperty).isSameAs(emailAddressesProperty2);
        assertThat(phoneNumbersProperty).isSameAs(phoneNumbersProperty2);

        // with identified fields the "name" of the created properties should be set
        assertThat(nameProperty.getName()).isEqualTo("name");
        assertThat(ageProperty.getName()).isEqualTo("age");
        assertThat(nicknamesProperty.getName()).isEqualTo("nicknames");
        assertThat(emailAddressesProperty.getName()).isEqualTo("emailAddresses");
        assertThat(phoneNumbersProperty.getName()).isEqualTo("phoneNumbers");
    }

    @Test
    public void testIdentifiedFieldsWithImmutables() {

        PersonImmutable person1 = PersonImmutable.create()
                .withName("horst")
                .withAge(32)
                .withNicknames(Collections.singletonList("captain"))
                .withEmailAddresses(Collections.singleton("test@example.org"))
                .withPhoneNumbers(Collections.singletonMap("private", "0351 1234567"));

        ModelWrapper<PersonImmutable> personWrapper = new ModelWrapper<>(person1);

        final StringProperty nameProperty = personWrapper
                .immutableField("name", PersonImmutable::getName, PersonImmutable::withName);
        final IntegerProperty ageProperty = personWrapper.immutableField("age", PersonImmutable::getAge,
                PersonImmutable::withAge);
        final ListProperty<String> nicknamesProperty =
                personWrapper.immutableField("nicknames", PersonImmutable::getNicknames,
                        PersonImmutable::withNicknames);
        final SetProperty<String> emailAddressesProperty = personWrapper.immutableField("emailAddresses",
                PersonImmutable::getEmailAddresses,
                PersonImmutable::withEmailAddresses);
        final MapProperty<String, String> phoneNumbersProperty = personWrapper.immutableField("phoneNumbers",
                PersonImmutable::getPhoneNumbers,
                PersonImmutable::withPhoneNumbers);

        final StringProperty nameProperty2 = personWrapper
                .immutableField("name", PersonImmutable::getName, PersonImmutable::withName);
        final IntegerProperty ageProperty2 = personWrapper.immutableField("age", PersonImmutable::getAge,
                PersonImmutable::withAge);
        final ListProperty<String> nicknamesProperty2 =
                personWrapper.immutableField("nicknames", PersonImmutable::getNicknames,
                        PersonImmutable::withNicknames);
        final SetProperty<String> emailAddressesProperty2 = personWrapper.immutableField("emailAddresses",
                PersonImmutable::getEmailAddresses,
                PersonImmutable::withEmailAddresses);
        final MapProperty<String, String> phoneNumbersProperty2 = personWrapper.immutableField("phoneNumbers",
                PersonImmutable::getPhoneNumbers,
                PersonImmutable::withPhoneNumbers);

        assertThat(nameProperty).isSameAs(nameProperty2);
        assertThat(ageProperty).isSameAs(ageProperty2);
        assertThat(nicknamesProperty).isSameAs(nicknamesProperty2);
        assertThat(emailAddressesProperty).isSameAs(emailAddressesProperty2);
        assertThat(phoneNumbersProperty).isSameAs(phoneNumbersProperty2);

        assertThat(nameProperty.getName()).isEqualTo("name");
        assertThat(ageProperty.getName()).isEqualTo("age");
        assertThat(nicknamesProperty.getName()).isEqualTo("nicknames");
        assertThat(emailAddressesProperty.getName()).isEqualTo("emailAddresses");
        assertThat(phoneNumbersProperty.getName()).isEqualTo("phoneNumbers");
    }

    @Test
    public void testDirtyFlag() {
        Person person = new Person();
        person.setName("horst");
        person.setAge(32);
        person.setNicknames(Arrays.asList("captain"));

        ModelWrapper<Person> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDirty()).isFalse();

        final StringProperty name = personWrapper.field(Person::getName, Person::setName);
        final IntegerProperty age = personWrapper.field(Person::getAge, Person::setAge);
        final ListProperty<String> nicknames = personWrapper.field(Person::getNicknames, Person::setNicknames);

        name.set("hugo");

        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.commit();
        assertThat(personWrapper.isDirty()).isFalse();

        age.set(33);
        assertThat(personWrapper.isDirty()).isTrue();

        age.set(32);
        assertThat(personWrapper.isDirty()).isTrue(); // dirty is still true

        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();

        nicknames.add("player");
        assertThat(personWrapper.isDirty()).isTrue();

        nicknames.remove("player");
        assertThat(personWrapper.isDirty()).isTrue(); // dirty is still true

        personWrapper.commit();
        assertThat(personWrapper.isDirty()).isFalse();

        name.set("hans");
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reset();
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();

        nicknames.set(FXCollections.observableArrayList("player"));
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reset();
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();

    }

    @Test
    public void testDirtyFlagWithFxProperties() {
        PersonFX person = new PersonFX();
        person.setName("horst");
        person.setAge(32);

        ModelWrapper<PersonFX> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDirty()).isFalse();

        final StringProperty name = personWrapper.field(PersonFX::nameProperty);
        final IntegerProperty age = personWrapper.field(PersonFX::ageProperty);
        final ListProperty<String> nicknames = personWrapper.field(PersonFX::nicknamesProperty);

        name.set("hugo");

        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.commit();
        assertThat(personWrapper.isDirty()).isFalse();

        age.set(33);
        assertThat(personWrapper.isDirty()).isTrue();

        age.set(32);
        assertThat(personWrapper.isDirty()).isTrue(); // dirty is still true

        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();

        nicknames.add("player");
        assertThat(personWrapper.isDirty()).isTrue();

        nicknames.remove("player");
        assertThat(personWrapper.isDirty()).isTrue(); // dirty is still true

        personWrapper.commit();
        assertThat(personWrapper.isDirty()).isFalse();

        name.set("hans");
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reset();
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();

        nicknames.set(FXCollections.observableArrayList("player"));
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reset();
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();
    }

    @Test
    public void testDirtyFlagWithImmutables() {

        PersonImmutable person = PersonImmutable.create()
                .withName("horst")
                .withAge(32)
                .withNicknames(Collections.singletonList("captain"));

        ModelWrapper<PersonImmutable> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDirty()).isFalse();

        final StringProperty name = personWrapper
                .immutableField(PersonImmutable::getName, PersonImmutable::withName);
        final IntegerProperty age = personWrapper.immutableField(PersonImmutable::getAge,
                PersonImmutable::withAge);
        final ListProperty<String> nicknames = personWrapper.immutableField(PersonImmutable::getNicknames,
                PersonImmutable::withNicknames);

        name.set("hugo");

        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.commit();
        assertThat(personWrapper.isDirty()).isFalse();

        age.set(33);
        assertThat(personWrapper.isDirty()).isTrue();

        age.set(32);
        assertThat(personWrapper.isDirty()).isTrue(); // dirty is still true

        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();

        nicknames.add("player");
        assertThat(personWrapper.isDirty()).isTrue();

        nicknames.remove("player");
        assertThat(personWrapper.isDirty()).isTrue(); // dirty is still true

        personWrapper.commit();
        assertThat(personWrapper.isDirty()).isFalse();

        name.set("hans");
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reset();
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();

        nicknames.set(FXCollections.observableArrayList("player"));
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reset();
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();
    }

    @Test
    public void testDifferentFlag() {
        Person person = new Person();
        person.setName("horst");
        person.setAge(32);
        person.setNicknames(Arrays.asList("captain"));
        person.setEmailAddresses(Collections.singleton("test@example.org"));

        ModelWrapper<Person> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDifferent()).isFalse();

        final StringProperty name = personWrapper.field(Person::getName, Person::setName);
        final IntegerProperty age = personWrapper.field(Person::getAge, Person::setAge);
        final ListProperty<String> nicknames = personWrapper.field(Person::getNicknames, Person::setNicknames);
        //final SetProperty<String> emailAddresses = personWrapper.field(Person::getEmailAddresses, Person::setEmailAddresses);

        name.set("hugo");
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.commit();
        assertThat(personWrapper.isDifferent()).isFalse();

        age.set(33);
        assertThat(personWrapper.isDifferent()).isTrue();

        age.set(32);
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.remove("captain");
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.add("captain");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.add("player");
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.remove("player");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.setValue(FXCollections.observableArrayList("spectator"));
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.add("captain"); // duplicate captain
        assertThat(personWrapper.isDifferent()).isTrue();

        person.getNicknames()
                .add("captain"); // now both have 2x "captain" but the modelWrapper has no chance to realize this
        // change in the model element...
        // ... for this reason the different flag will still be true
        assertThat(personWrapper.isDifferent()).isTrue();

        // ... but if we add another value to the nickname-Property, the modelWrapper can react to this change
        person.getNicknames().add("other");
        nicknames.add("other");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.add("player");
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.remove("player");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.setValue(FXCollections.observableArrayList("spectator"));
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDifferent()).isFalse();

        name.setValue("hans");
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDifferent()).isFalse();

        personWrapper.reset();
        assertThat(personWrapper.isDifferent()).isTrue();
    }

    @Test
    public void testDifferentFlagWithFxProperties() {
        PersonFX person = new PersonFX();
        person.setName("horst");
        person.setAge(32);
        person.setNicknames(Arrays.asList("captain"));

        ModelWrapper<PersonFX> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDifferent()).isFalse();

        final StringProperty name = personWrapper.field(PersonFX::nameProperty);
        final IntegerProperty age = personWrapper.field(PersonFX::ageProperty);
        final ListProperty<String> nicknames = personWrapper.field(PersonFX::nicknamesProperty);

        name.set("hugo");
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.commit();
        assertThat(personWrapper.isDifferent()).isFalse();

        age.set(33);
        assertThat(personWrapper.isDifferent()).isTrue();

        age.set(32);
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.remove("captain");
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.add("captain");
        assertThat(personWrapper.isDifferent()).isFalse();

        person.getNicknames().add("captain"); // duplicate value
        nicknames.add("captain");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.add("player");
        assertThat(personWrapper.isDifferent()).isTrue();

        person.getNicknames().add("player");
        assertThat(personWrapper.isDifferent()).isTrue(); // still true because the modelWrapper can't detect the change
        // in the model

        person.setName("luise");
        name.set("luise"); // this triggers the recalculation of the different-flag which will now detect the previous
        // change to the nicknames list
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.setValue(FXCollections.observableArrayList("spectator"));
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDifferent()).isFalse();

        name.setValue("hans");
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDifferent()).isFalse();

        personWrapper.reset();
        assertThat(personWrapper.isDifferent()).isTrue();
    }

    @Test
    public void testDifferentFlagWithImmutables() {
        PersonImmutable person = PersonImmutable.create()
                .withName("horst")
                .withAge(32)
                .withNicknames(Collections.singletonList("captain"));

        ModelWrapper<PersonImmutable> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDirty()).isFalse();

        final StringProperty name = personWrapper
                .immutableField(PersonImmutable::getName, PersonImmutable::withName);
        final IntegerProperty age = personWrapper.immutableField(PersonImmutable::getAge,
                PersonImmutable::withAge);
        final ListProperty<String> nicknames = personWrapper.immutableField(PersonImmutable::getNicknames,
                PersonImmutable::withNicknames);

        name.set("hugo");
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.commit();
        assertThat(personWrapper.isDifferent()).isFalse();

        age.set(33);
        assertThat(personWrapper.isDifferent()).isTrue();

        age.set(32);
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.remove("captain");
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.add("captain");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.add("player");
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.remove("player");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.setValue(FXCollections.observableArrayList("spectator"));
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.add("captain"); // duplicate captain
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.add("player");
        assertThat(personWrapper.isDifferent()).isTrue();
    }

    @Test
    public void defaultValuesCanBeUpdatedToCurrentValues() {
        final Person person = new Person();
        person.setName("horst");
        person.setAge(32);
        person.setNicknames(Arrays.asList("captain"));
        person.setEmailAddresses(Collections.singleton("test@example.org"));
        person.setPhoneNumbers(Collections.singletonMap("private_Horst", "0351 1234567"));

        final ModelWrapper<Person> personWrapper = new ModelWrapper<>(person);

        final StringProperty name = personWrapper.field(Person::getName, Person::setName, person.getName());
        name.set("test");
        personWrapper.commit();
        personWrapper.useCurrentValuesAsDefaults();
        personWrapper.reset();
        assertThat(person.getName()).isEqualTo("test");
        assertThat(name.get()).isEqualTo("test");

        final IntegerProperty age = personWrapper.field(Person::getAge, Person::setAge, person.getAge());
        age.set(42);
        personWrapper.commit();
        personWrapper.useCurrentValuesAsDefaults();
        personWrapper.reset();
        assertThat(person.getAge()).isEqualTo(42);
        assertThat(age.get()).isEqualTo(42);

        final ListProperty<String> nicknames = personWrapper.field(Person::getNicknames, Person::setNicknames,
                person.getNicknames());
        nicknames.add("myname");
        nicknames.remove("captain");
        personWrapper.commit();
        personWrapper.useCurrentValuesAsDefaults();
        personWrapper.reset();
        assertThat(person.getNicknames()).containsExactly("myname");
        assertThat(nicknames.get()).containsExactly("myname");

        final SetProperty<String> emailAddresses = personWrapper.field(Person::getEmailAddresses,
                Person::setEmailAddresses,
                person.getEmailAddresses());
        emailAddresses.add("hugo@example.org");
        emailAddresses.remove("test@example.org");
        personWrapper.commit();
        personWrapper.useCurrentValuesAsDefaults();
        personWrapper.reset();
        assertThat(person.getEmailAddresses()).containsExactly("hugo@example.org");

        final MapProperty<String, String> phoneNumbers = personWrapper.field(Person::getPhoneNumbers,
                Person::setPhoneNumbers,
                person.getPhoneNumbers());
        phoneNumbers.putIfAbsent("mobile_Hugo", "0123 4567890");
        phoneNumbers.remove("private_Horst", "0351 1234567");
    }

    @Test
    public void defaultValuesCanBeUpdatedToCurrentValuesWithImmutables() {
        PersonImmutable person = PersonImmutable.create()
                .withName("Luise")
                .withAge(32)
                .withNicknames(Collections.singletonList("captain"))
                .withEmailAddresses(Collections.singleton("test@example.org"))
                .withPhoneNumbers(Collections.singletonMap("mobile_Luise", "0124 9876543"));

        ModelWrapper<PersonImmutable> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDirty()).isFalse();

        final StringProperty name = personWrapper
                .immutableField(PersonImmutable::getName, PersonImmutable::withName, person.getName());
        final IntegerProperty age = personWrapper.immutableField(PersonImmutable::getAge,
                PersonImmutable::withAge, person.getAge());
        final ListProperty<String> nicknames = personWrapper.immutableField(PersonImmutable::getNicknames,
                PersonImmutable::withNicknames,
                person.getNicknames());

        final SetProperty<String> emailAddresses = personWrapper.immutableField(PersonImmutable::getEmailAddresses,
                PersonImmutable::withEmailAddresses, person.getEmailAddresses());

        final MapProperty<String, String> phoneNumbers = personWrapper.immutableField(PersonImmutable::getPhoneNumbers,
                PersonImmutable::withPhoneNumbers, person.getPhoneNumbers());

        name.set("test");
        personWrapper.commit();
        personWrapper.useCurrentValuesAsDefaults();
        personWrapper.reset();
        assertThat(name.get()).isEqualTo("test");

        age.set(42);
        personWrapper.commit();
        personWrapper.useCurrentValuesAsDefaults();
        personWrapper.reset();
        assertThat(age.get()).isEqualTo(42);

        nicknames.add("myname");
        nicknames.remove("captain");
        personWrapper.commit();
        personWrapper.useCurrentValuesAsDefaults();
        personWrapper.reset();
        assertThat(nicknames.get()).containsExactly("myname");

        emailAddresses.add("hugo@example.org");
        emailAddresses.remove("test@example.org");
        personWrapper.commit();
        personWrapper.useCurrentValuesAsDefaults();
        personWrapper.reset();
        assertThat(emailAddresses.get()).containsExactly("hugo@example.org");

        phoneNumbers.putIfAbsent("mobile_Hugo", "0123 4567890");
        phoneNumbers.remove("mobile_Luise");
    }

    @Test
    public void valuesShouldBeUpdatedWhenModelInstanceChanges() {
        final Person person1 = new Person();
        person1.setName("horst");
        person1.setAge(32);
        person1.setNicknames(Arrays.asList("captain"));
        person1.setEmailAddresses(Collections.singleton("test@example.org"));
        person1.setPhoneNumbers(Collections.singletonMap("private_Horst", "0351 1234567"));

        final Person person2 = new Person();
        person2.setName("dieter");
        person2.setAge(42);
        person2.setNicknames(Arrays.asList("robin"));
        person2.setEmailAddresses(Collections.singleton("dieter@example.org"));
        person2.setPhoneNumbers(Collections.singletonMap("private_Dieter", "030 001199"));

        final SimpleObjectProperty<Person> modelProp = new SimpleObjectProperty<>(person1);

        final ModelWrapper<Person> personWrapper = new ModelWrapper<>(modelProp);

        final StringProperty nameField = personWrapper.field(Person::getName, Person::setName, person1.getName());
        final IntegerProperty ageField = personWrapper.field(Person::getAge, Person::setAge, person1.getAge());
        final ListProperty<String> nicknames = personWrapper
                .field(Person::getNicknames, Person::setNicknames, person1.getNicknames());
        final SetProperty<String> emailAddresses = personWrapper.field(Person::getEmailAddresses,
                Person::setEmailAddresses,
                person1.getEmailAddresses());
        final MapProperty<String, String> phoneNumbers = personWrapper.field(Person::getPhoneNumbers,
                Person::setPhoneNumbers,
                person1.getPhoneNumbers());

        assertThat(nameField.get()).isEqualTo(person1.getName());
        assertThat(ageField.get()).isEqualTo(person1.getAge());
        assertThat(nicknames.get()).containsExactlyElementsOf(person1.getNicknames());
        assertThat(emailAddresses.get()).containsExactlyElementsOf(person1.getEmailAddresses());
        assertThat(phoneNumbers.get().entrySet()).containsExactlyElementsOf(person1.getPhoneNumbers().entrySet());

        modelProp.set(person2);
        assertThat(nameField.get()).isEqualTo(person2.getName());
        assertThat(ageField.get()).isEqualTo(person2.getAge());
        assertThat(nicknames.get()).containsExactlyElementsOf(person2.getNicknames());
        assertThat(emailAddresses.get()).containsExactlyElementsOf(person2.getEmailAddresses());
        assertThat(phoneNumbers.get().entrySet()).containsExactlyElementsOf(person2.getPhoneNumbers().entrySet());

        personWrapper.reset();
        assertThat(nameField.get()).isEqualTo(person1.getName());
        assertThat(ageField.get()).isEqualTo(person1.getAge());
        assertThat(nicknames.get()).containsExactlyElementsOf(person1.getNicknames());
        assertThat(emailAddresses.get()).containsExactlyElementsOf(person1.getEmailAddresses());
        assertThat(phoneNumbers.get().entrySet()).containsExactlyElementsOf(person1.getPhoneNumbers().entrySet());
    }

    @Test
    public void valuesShouldBeUpdatedWhenModelInstanceChangesWithImmutables() {
        PersonImmutable person1 = PersonImmutable.create()
                .withName("horst")
                .withAge(32)
                .withNicknames(Collections.singletonList("captain"))
                .withEmailAddresses(Collections.singleton("test@example.org"))
                .withPhoneNumbers(Collections.singletonMap("private_Horst", "0351 1234567"));

        PersonImmutable person2 = PersonImmutable.create()
                .withName("dieter")
                .withAge(42)
                .withNicknames(Collections.singletonList("robin"))
                .withEmailAddresses(Collections.singleton("dieter@example.org"))
                .withPhoneNumbers(Collections.singletonMap("private_Dieter", "030 001199"));

        final SimpleObjectProperty<PersonImmutable> modelProp = new SimpleObjectProperty<>(person1);

        ModelWrapper<PersonImmutable> personWrapper = new ModelWrapper<>(modelProp);

        final StringProperty nameField = personWrapper
                .immutableField(PersonImmutable::getName, PersonImmutable::withName, person1.getName());
        final IntegerProperty ageField = personWrapper.immutableField(PersonImmutable::getAge,
                PersonImmutable::withAge, person1.getAge());
        final ListProperty<String> nicknames =
                personWrapper.immutableField(PersonImmutable::getNicknames, PersonImmutable::withNicknames,
                        person1.getNicknames());
        final SetProperty<String> emailAddresses = personWrapper.immutableField(PersonImmutable::getEmailAddresses,
                PersonImmutable::withEmailAddresses, person1.getEmailAddresses());
        final MapProperty<String, String> phoneNumbers = personWrapper.immutableField(PersonImmutable::getPhoneNumbers,
                PersonImmutable::withPhoneNumbers,
                person1.getPhoneNumbers());

        assertThat(nameField.get()).isEqualTo(person1.getName());
        assertThat(ageField.get()).isEqualTo(person1.getAge());
        assertThat(nicknames.get()).containsExactlyElementsOf(person1.getNicknames());
        assertThat(emailAddresses.get()).containsExactlyElementsOf(person1.getEmailAddresses());
        assertThat(phoneNumbers.get().entrySet()).containsExactlyElementsOf(person1.getPhoneNumbers().entrySet());

        modelProp.set(person2);

        assertThat(nameField.get()).isEqualTo(person2.getName());
        assertThat(ageField.get()).isEqualTo(person2.getAge());
        assertThat(nicknames.get()).containsExactlyElementsOf(person2.getNicknames());
        assertThat(emailAddresses.get()).containsExactlyElementsOf(person2.getEmailAddresses());
        assertThat(phoneNumbers.get().entrySet()).containsExactlyElementsOf(person2.getPhoneNumbers().entrySet());

        personWrapper.reset();
        assertThat(nameField.get()).isEqualTo(person1.getName());
        assertThat(ageField.get()).isEqualTo(person1.getAge());
        assertThat(nicknames.get()).containsExactlyElementsOf(person1.getNicknames());
        assertThat(emailAddresses.get()).containsExactlyElementsOf(person1.getEmailAddresses());
        assertThat(phoneNumbers.get().entrySet()).containsExactlyElementsOf(person1.getPhoneNumbers().entrySet());
    }

    @Test
    public void testUseCurrentValuesAsDefaultWhenModelIsNull() {

        ModelWrapper<Person> wrapper = new ModelWrapper<>();

        StringProperty name = wrapper.field("name", Person::getName, Person::setName, "empty");
        IntegerProperty age = wrapper.field("age", Person::getAge, Person::setAge, 12);
        ListProperty<String> nicknames = wrapper.field("nicknames", Person::getNicknames, Person::setNicknames,
                Collections.singletonList("captain"));
        SetProperty<String> emailAddresses = wrapper.field("emailAddresses", Person::getEmailAddresses,
                Person::setEmailAddresses, Collections.singleton("test@example.org"));
        MapProperty<String, String> phoneNumbers = wrapper.field("phoneNumbers", Person::getPhoneNumbers,
                Person::setPhoneNumbers, Collections.singletonMap("private_Horst", "0351 1234567"));

        wrapper.reset();

        assertThat(name.get()).isEqualTo("empty");
        assertThat(age.get()).isEqualTo(12);
        assertThat(nicknames.get()).containsExactly("captain");
        assertThat(emailAddresses.get()).containsExactly("test@example.org");
        assertThat(phoneNumbers.get()).containsOnly(MapEntry.entry("private_Horst", "0351 1234567"));

        wrapper.useCurrentValuesAsDefaults();

        wrapper.reset();

        // still the old default values
        assertThat(name.get()).isEqualTo("empty");
        assertThat(age.get()).isEqualTo(12);
        assertThat(nicknames.get()).containsExactly("captain");
        assertThat(emailAddresses.get()).containsExactly("test@example.org");
        assertThat(phoneNumbers.get()).containsOnly(MapEntry.entry("private_Horst", "0351 1234567"));
    }

    @Test
    public void testDefaultValues() {
        final Person person = new Person();
        person.setName("horst");
        person.setAge(32);
        person.setNicknames(Arrays.asList("captain"));

        ModelWrapper<Person> wrapperWithDefaults = new ModelWrapper<>();
        ModelWrapper<Person> wrapperWithoutDefaults = new ModelWrapper<>();

        StringProperty nameWithDefault = wrapperWithDefaults.field("name", Person::getName, Person::setName, "empty");
        IntegerProperty ageWithDefault = wrapperWithDefaults.field("age", Person::getAge, Person::setAge, 12);

        StringProperty nameWithoutDefault = wrapperWithoutDefaults.field("name", Person::getName, Person::setName);
        IntegerProperty ageWithoutDefault = wrapperWithoutDefaults.field("age", Person::getAge, Person::setAge);

        // default values are only used when #reset is called.
        assertThat(nameWithDefault.get()).isNull();
        assertThat(ageWithDefault.get()).isEqualTo(0);

        assertThat(nameWithoutDefault.get()).isNull();
        assertThat(ageWithoutDefault.get()).isEqualTo(0);

        wrapperWithDefaults.reset();
        wrapperWithoutDefaults.reset();

        assertThat(nameWithDefault.get()).isEqualTo("empty");
        assertThat(ageWithDefault.get()).isEqualTo(12);

        assertThat(nameWithoutDefault.get()).isNull();
        assertThat(ageWithoutDefault.get()).isEqualTo(0);

        wrapperWithDefaults.set(person);
        wrapperWithoutDefaults.set(person);

        assertThat(nameWithDefault.get()).isEqualTo("horst");
        assertThat(ageWithDefault.get()).isEqualTo(32);

        assertThat(nameWithoutDefault.get()).isEqualTo("horst");
        assertThat(ageWithoutDefault.get()).isEqualTo(32);

        wrapperWithDefaults.reset();
        wrapperWithoutDefaults.reset();

        assertThat(nameWithDefault.get()).isEqualTo("empty");
        assertThat(ageWithDefault.get()).isEqualTo(12);

        assertThat(nameWithoutDefault.get()).isNull();
        assertThat(ageWithoutDefault.get()).isEqualTo(0);
    }
}
