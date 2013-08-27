package de.saxsys.jfx.exampleapplication.model;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static Repository INSTANCE = new Repository();

    List<Person> persons = new ArrayList<Person>();

    private Repository() {
        persons.add(new Person("Alexander", "Casall"));
        persons.add(new Person("Bernd", "Grams"));
        persons.add(new Person("Anna", "Schulze"));
    }

    public static Repository getInstance() {
        return INSTANCE;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public Person getPersonById(final int id) {
        return persons.get(id);
    }

}
