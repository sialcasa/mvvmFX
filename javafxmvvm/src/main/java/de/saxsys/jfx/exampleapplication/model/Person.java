package de.saxsys.jfx.exampleapplication.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The class represents a Person with a firstname and a lastname. It provides access with JavaFX Properties.
 * 
 * @author alexander.casall
 * 
 */
public class Person {

    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();

    /**
     * Creates a person with given name.
     * 
     * @param firstName of person
     * @param lastName of person
     */
    public Person(final String firstName, final String lastName) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
    }

    /**
     * @return firstname as {@link StringProperty}
     */
    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * @return lastname as {@link StringProperty}
     */
    public StringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * @return firstname as {@link String}
     */
    public String getFirstName() {
        return firstNameProperty().get();
    }

    /**
     * @see #getFirstName()
     * @param firstName
     */
    public void setFirstName(final String firstName) {
        firstNameProperty().set(firstName);
    }

    /**
     * @return lastname as {@link String}
     */
    public String getLastName() {
        return lastNameProperty().get();
    }

    /**
     * @see #getLastName()
     */
    public void setLastName(final String firstName) {
        firstNameProperty().set(firstName);
    }
}
