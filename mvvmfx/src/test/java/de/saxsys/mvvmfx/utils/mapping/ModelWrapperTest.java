package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelWrapperTest {
	
	
	@Test
	public void testWithGetterAndSetter() {
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
	public void testIdentifiedFields() {
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


	@Test
	public void testDirtyFlag() {
		Person person = new Person();
		person.setName("horst");
		person.setAge(32);

		ModelWrapper<Person> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDirty()).isFalse();

        final StringProperty name = personWrapper.field(Person::getName, Person::setName);
        final IntegerProperty age = personWrapper.field(Person::getAge, Person::setAge);

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


        name.set("hans");
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


        name.set("hans");
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

        ModelWrapper<Person> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDifferent()).isFalse();

        final StringProperty name = personWrapper.field(Person::getName, Person::setName);
        final IntegerProperty age = personWrapper.field(Person::getAge, Person::setAge);


        name.set("hugo");
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.commit();
        assertThat(personWrapper.isDifferent()).isFalse();


        age.set(33);
        assertThat(personWrapper.isDifferent()).isTrue();

        age.set(32);
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

        ModelWrapper<PersonFX> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDifferent()).isFalse();

        final StringProperty name = personWrapper.field(PersonFX::nameProperty);
        final IntegerProperty age = personWrapper.field(PersonFX::ageProperty);


        name.set("hugo");
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.commit();
        assertThat(personWrapper.isDifferent()).isFalse();


        age.set(33);
        assertThat(personWrapper.isDifferent()).isTrue();

        age.set(32);
        assertThat(personWrapper.isDifferent()).isFalse();


        name.setValue("hans");
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDifferent()).isFalse();


        personWrapper.reset();
        assertThat(personWrapper.isDifferent()).isTrue();
	}



	
}
