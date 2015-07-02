package de.saxsys.jfx.exampleapplication.model;

import java.util.Random;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
	private final ObjectProperty<Gender> gender = new SimpleObjectProperty<>();
	
	/**
	 * Creates a person with given name.
	 * 
	 * @param firstName
	 *            of person
	 * @param lastName
	 *            of person
	 * @param gender
	 *            of person
	 */
	public Person(final String firstName, final String lastName, Gender gender) {
		this.firstName.set(firstName);
		this.lastName.set(lastName);
		this.gender.set(gender);
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
	 * @return the gender of the person as {@link ObjectProperty}.
	 */
	public ObjectProperty<Gender> genderProperty() {
		return gender;
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
	public void setLastName(final String lastName) {
		lastNameProperty().set(lastName);
	}
	
	/**
	 * @return the gender of the person as {@link String}
	 */
	public Gender getGender() {
		return gender.get();
	}
	
	/**
	 * @see #getGender()
	 */
	public void setGender(Gender gender) {
		this.gender.set(gender);
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
