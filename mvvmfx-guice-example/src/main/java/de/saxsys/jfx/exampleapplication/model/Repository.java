package de.saxsys.jfx.exampleapplication.model;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;

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
		persons.add(new Person("Alexander", "Casall"));
		persons.add(new Person("Bernd", "Grams"));
		persons.add(new Person("Anna", "Schulze"));
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
		return persons.get(id);
	}

}
