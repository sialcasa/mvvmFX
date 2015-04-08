package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.ObjectProperty;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelWrapperTest {

    @Test
    public void testWithGetterAndSetter(){
        Person person = new Person();
        person.setName("horst");
        person.setAge(32);

        ModelWrapper<Person> personWrapper = new ModelWrapper<>();
        personWrapper.set(person);


        final ObjectProperty<String> nameProperty = personWrapper.field(Person::getName, Person::setName);
        final ObjectProperty<Integer> ageProperty = personWrapper.field(Person::getAge, Person::setAge);

        assertThat(nameProperty.get()).isEqualTo("horst");
        assertThat(ageProperty.get()).isEqualTo(32);


        nameProperty.set("hugo");
        ageProperty.set(33);

        // still the old values
        assertThat(person.getName()).isEqualTo("horst");
        assertThat(person.getAge()).isEqualTo(32);


        personWrapper.commit();

        // now the new values are reflected in the wrapped person
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);



        nameProperty.set("luise");
        ageProperty.set(15);

        personWrapper.reset();

        assertThat(nameProperty.get()).isEqualTo(null);
        assertThat(ageProperty.get()).isEqualTo(null);

        // the wrapped object has still the values from the last commit.
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);


        personWrapper.reload();
        // now the properties have the values from the wrapped object
        assertThat(nameProperty.get()).isEqualTo("hugo");
        assertThat(ageProperty.get()).isEqualTo(33);


        Person otherPerson = new Person();
        otherPerson.setName("gisela");
        otherPerson.setAge(23);

        personWrapper.set(otherPerson);

        assertThat(nameProperty.get()).isEqualTo("gisela");
        assertThat(ageProperty.get()).isEqualTo(23);

        nameProperty.set("georg");
        ageProperty.set(24);

        personWrapper.commit();

        // old person has still the old values
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);

        // new person has the new values
        assertThat(otherPerson.getName()).isEqualTo("georg");
        assertThat(otherPerson.getAge()).isEqualTo(24);

    }


    @Test
    public void testWithJavaFXPropertiesField() {
        PersonFX person = new PersonFX();
        person.setName("horst");
        person.setAge(32);

        ModelWrapper<PersonFX> personWrapper = new ModelWrapper<>(person);


        final ObjectProperty<String> nameProperty = personWrapper.field(PersonFX::nameProperty);
        final ObjectProperty<Number> ageProperty = personWrapper.field(PersonFX::ageProperty);

        assertThat(nameProperty.get()).isEqualTo("horst");
        assertThat(ageProperty.get()).isEqualTo(32);


        nameProperty.set("hugo");
        ageProperty.set(33);

        // still the old values
        assertThat(person.getName()).isEqualTo("horst");
        assertThat(person.getAge()).isEqualTo(32);


        personWrapper.commit();

        // now the new values are reflected in the wrapped person
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);



        nameProperty.set("luise");
        ageProperty.set(15);

        personWrapper.reset();

        assertThat(nameProperty.get()).isEqualTo(null);
        assertThat(ageProperty.get()).isEqualTo(null);

        // the wrapped object has still the values from the last commit.
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);


        personWrapper.reload();
        // now the properties have the values from the wrapped object
        assertThat(nameProperty.get()).isEqualTo("hugo");
        assertThat(ageProperty.get()).isEqualTo(33);


        PersonFX otherPerson = new PersonFX();
        otherPerson.setName("gisela");
        otherPerson.setAge(23);

        personWrapper.set(otherPerson);

        assertThat(nameProperty.get()).isEqualTo("gisela");
        assertThat(ageProperty.get()).isEqualTo(23);

        nameProperty.set("georg");
        ageProperty.set(24);

        personWrapper.commit();

        // old person has still the old values
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);

        // new person has the new values
        assertThat(otherPerson.getName()).isEqualTo("georg");
        assertThat(otherPerson.getAge()).isEqualTo(24);
    }

    @Test
    public void testIdentifiedFields(){
        Person person = new Person();
        person.setName("horst");
        person.setAge(32);

        ModelWrapper<Person> personWrapper = new ModelWrapper<>();

        final ObjectProperty<String> nameProperty = personWrapper.field("name", Person::getName, Person::setName);
        final ObjectProperty<Integer> ageProperty = personWrapper.field("age", Person::getAge, Person::setAge);


        final ObjectProperty<String> nameProperty2 = personWrapper.field("name", Person::getName, Person::setName);
        final ObjectProperty<Integer> ageProperty2 = personWrapper.field("age", Person::getAge, Person::setAge);


        assertThat(nameProperty).isSameAs(nameProperty2);
        assertThat(ageProperty).isSameAs(ageProperty2);
    }

}
