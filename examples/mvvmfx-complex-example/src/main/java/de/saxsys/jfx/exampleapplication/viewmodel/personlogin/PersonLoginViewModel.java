package de.saxsys.jfx.exampleapplication.viewmodel.personlogin;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;

import javax.inject.Inject;

import de.saxsys.jfx.exampleapplication.model.Person;
import de.saxsys.jfx.exampleapplication.model.Repository;
import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist.ModelToStringMapper;
import de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist.SelectableItemList;
import de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist.SelectableStringList;

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
	private final SelectableItemList<Person> selectablePersons;

	private final IntegerProperty loggedInPersonId = new SimpleIntegerProperty();

	@Inject
	public PersonLoginViewModel(Repository repository) {
		ModelToStringMapper<Person> personMapper = new ModelToStringMapper<Person>() {
			@Override
			public String toString(Person person) {
				return person.getFirstName() + " " + person.getLastName();
			}
		};
		selectablePersons = new SelectableItemList<Person>(
				FXCollections.observableArrayList(repository.getPersons()),
				personMapper);
	}

	/**
	 * Persons in string representation.
	 * 
	 * @return persons
	 */
	public SelectableStringList selectablePersonsProperty() {
		return selectablePersons;
	}

	/**
	 * Person which is logged in.
	 * 
	 * @return person
	 */
	public IntegerProperty loggedInPersonIdProperty() {
		return loggedInPersonId;
	}

	/**
	 * Action when the login button was clicked.
	 */
	public void login() {
		loggedInPersonId.set(selectablePersons.getSelectedItem().getId());
	}
}
