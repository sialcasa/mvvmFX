package de.saxsys.jfx.exampleapplication.model;

import java.util.Random;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The class represents a Person with a firstname and a lastname. It provides access with JavaFX Properties.
 * 
 * @author alexander.casall
 * 
 */
public class Person {

    private int technicalID;
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final ReadOnlyBooleanWrapper male = new ReadOnlyBooleanWrapper();

    /**
     * Creates a person with given name.
     * 
     * @param firstName of person
     * @param lastName of person
     */
    public Person(final String firstName, final String lastName, final boolean male) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.male.set(male);
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
     * @return male as {@link ReadOnlyBooleanProperty}
     */
    public ReadOnlyBooleanProperty maleProperty() {
        return male.getReadOnlyProperty();
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

    /**
     * @return male as boolean
     */
    public boolean isMale() {
        return male.get();
    }

    /**
     * Gets the technical id.
     * 
     * @return technical id
     */
    public int getId() {
        if (technicalID == 0) {
            technicalID = new Random().nextInt();
        }
        return technicalID;
    }

}
