package de.saxsys.mvvmfx.examples.contacts.model;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.collections.ObservableList;

/**
 * Implementations of this interface are used to encapsulate the process of loading available countries
 * and their subdivisions (if available).
 *
 * Instances are meant to be a stateful wrapper around the existing countries.
 * You should create an instance of this class, call the {@link #init()} method
 * and then bind the UI to the provided observable lists (
 * {@link #availableCountries()} and {@link #subdivisions()}).
 *
 * To choose a country use the {@link #setCountry(Country)} method. This
 * will lead to a change of the {@link #subdivisions()} list.
 */
public interface CountrySelector {

	void init();

	void setCountry(Country country);

	ObservableList<Country> availableCountries();

	ReadOnlyStringProperty subdivisionLabel();

	ObservableList<Subdivision> subdivisions();

	ReadOnlyBooleanProperty inProgressProperty();
}
