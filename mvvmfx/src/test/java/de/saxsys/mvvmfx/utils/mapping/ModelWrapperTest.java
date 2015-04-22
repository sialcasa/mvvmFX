package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelWrapperTest {
    

    @Test
    public void testWithGetterAndSetter(){
        Person person = new Person();
        person.setName("horst");
        person.setAge(32);

        ModelWrapper<Person> personWrapper = new ModelWrapper<>(person);
        
        final StringProperty nameProperty = personWrapper.field(Person::getName, Person::setName);
        final IntegerProperty ageProperty = personWrapper.field(Person::getAge, Person::setAge);
        
        assertThat(nameProperty.getValue()).isEqualTo("horst");
        assertThat(ageProperty.getValue()).isEqualTo(32);


        nameProperty.setValue("hugo");
        ageProperty.setValue(33);

        // still the old values
        assertThat(person.getName()).isEqualTo("horst");
        assertThat(person.getAge()).isEqualTo(32);


        personWrapper.commit();

        // now the new values are reflected in the wrapped person
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);



        nameProperty.setValue("luise");
        ageProperty.setValue(15);

        personWrapper.reset();

        assertThat(nameProperty.getValue()).isEqualTo(null);
        assertThat(ageProperty.getValue()).isEqualTo(0);

        // the wrapped object has still the values from the last commit.
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);


        personWrapper.reload();
        // now the properties have the values from the wrapped object
        assertThat(nameProperty.getValue()).isEqualTo("hugo");
        assertThat(ageProperty.getValue()).isEqualTo(33);


        Person otherPerson = new Person();
        otherPerson.setName("gisela");
        otherPerson.setAge(23);

        personWrapper.set(otherPerson);
        personWrapper.reload();

        assertThat(nameProperty.getValue()).isEqualTo("gisela");
        assertThat(ageProperty.getValue()).isEqualTo(23);

        nameProperty.setValue("georg");
        ageProperty.setValue(24);

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


        final StringProperty nameProperty = personWrapper.field(PersonFX::nameProperty);
        final IntegerProperty ageProperty = personWrapper.field(PersonFX::ageProperty);

        assertThat(nameProperty.getValue()).isEqualTo("horst");
        assertThat(ageProperty.getValue()).isEqualTo(32);


        nameProperty.setValue("hugo");
        ageProperty.setValue(33);

        // still the old values
        assertThat(person.getName()).isEqualTo("horst");
        assertThat(person.getAge()).isEqualTo(32);


        personWrapper.commit();

        // now the new values are reflected in the wrapped person
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);



        nameProperty.setValue("luise");
        ageProperty.setValue(15);

        personWrapper.reset();

        assertThat(nameProperty.getValue()).isEqualTo(null);
        assertThat(ageProperty.getValue()).isEqualTo(0);

        // the wrapped object has still the values from the last commit.
        assertThat(person.getName()).isEqualTo("hugo");
        assertThat(person.getAge()).isEqualTo(33);


        personWrapper.reload();
        // now the properties have the values from the wrapped object
        assertThat(nameProperty.getValue()).isEqualTo("hugo");
        assertThat(ageProperty.getValue()).isEqualTo(33);


        PersonFX otherPerson = new PersonFX();
        otherPerson.setName("gisela");
        otherPerson.setAge(23);

        personWrapper.set(otherPerson);
        personWrapper.reload();

        assertThat(nameProperty.getValue()).isEqualTo("gisela");
        assertThat(ageProperty.getValue()).isEqualTo(23);

        nameProperty.setValue("georg");
        ageProperty.setValue(24);

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

        final StringProperty nameProperty = personWrapper.field("name", Person::getName, Person::setName);
        final IntegerProperty ageProperty = personWrapper.field("age", Person::getAge, Person::setAge);


        final StringProperty nameProperty2 = personWrapper.field("name", Person::getName, Person::setName);
        final IntegerProperty ageProperty2 = personWrapper.field("age", Person::getAge, Person::setAge);


        assertThat(nameProperty).isSameAs(nameProperty2);
        assertThat(ageProperty).isSameAs(ageProperty2);
    }

}
