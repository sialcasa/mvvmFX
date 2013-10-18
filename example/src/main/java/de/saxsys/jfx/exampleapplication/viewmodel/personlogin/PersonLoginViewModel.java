package de.saxsys.jfx.exampleapplication.viewmodel.personlogin;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import com.google.inject.Inject;

import de.saxsys.jfx.exampleapplication.model.Person;
import de.saxsys.jfx.exampleapplication.model.Repository;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;

/**
 * ViewModel for a login view for the persons. It provides the data which should
 * be visualized in the frontend e.g. the list of persons in string
 * representations. The tests for it can be written first. Have a look on
 * PersonLoginViewModelTest.
 * 
 * @author alexander.casall
 * 
 */

public class PersonLoginViewModel implements ViewModel {


	// Properties which are used by the view.
	private final ListProperty<String> persons = new SimpleListProperty<>(
			FXCollections.<String> observableArrayList());
	private IntegerProperty pickedPerson = new SimpleIntegerProperty(-1);

	@Inject
	public PersonLoginViewModel(Repository repository) {
		final List<Person> personsInRepo = repository.getPersons();
		for (final Person person : personsInRepo) {
			persons.add(person.getFirstName() + " " + person.getLastName());
		}
	}

	/**
	 * Persons in string representation.
	 * 
	 * @return persons
	 */
	public ListProperty<String> personsProperty() {
		return persons;
	}

	/**
	 * Person ID which was picked.
	 * 
	 * @return id
	 */
	public IntegerProperty pickedPersonProperty() {
		return pickedPerson;
	}
}
