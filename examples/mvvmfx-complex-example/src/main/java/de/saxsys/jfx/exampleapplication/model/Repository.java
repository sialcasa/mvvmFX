package de.saxsys.jfx.exampleapplication.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

/**
 * Service class for providing some dummy data.
 * 
 * @author sialcasa
 */
@Singleton
public class Repository {

	List<Person> persons = new ArrayList<Person>();

	/**
	 * Creates the Repo.
	 */
	public Repository() {
		persons.add(new Person("Alexander", "Casall", true));
		persons.add(new Person("Bernd", "Grams", true));
		persons.add(new Person("Anna", "Schulze", false));
	}

	/**
	 * @return available {@link Person}s
	 */
	public List<Person> getPersons() {
		return persons;
	}

	/**
	 * Gets a Person.s
	 * 
	 * @param id
	 *            of the person
	 * @return person
	 */
	public Person getPersonById(final int id) {
		for (Person person : persons)
			if (id == person.getId())
				return person;
		return null;
	}

}
