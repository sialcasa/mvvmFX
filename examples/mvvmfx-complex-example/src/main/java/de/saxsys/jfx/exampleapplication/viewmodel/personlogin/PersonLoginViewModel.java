package de.saxsys.jfx.exampleapplication.viewmodel.personlogin;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;

import javax.inject.Inject;

import de.saxsys.jfx.exampleapplication.model.Person;
import de.saxsys.jfx.exampleapplication.model.Repository;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import de.saxsys.mvvmfx.utils.itemlist.ModelToStringFunction;
import de.saxsys.mvvmfx.utils.itemlist.SelectableItemList;
import de.saxsys.mvvmfx.utils.itemlist.SelectableStringList;

/**
 * ViewModel for a login view for the persons. It provides the data which should be visualized in the frontend e.g. the
 * list of persons in string representations. The tests for it can be written first. Have a look on
 * PersonLoginViewModelTest.
 * 
 * @author alexander.casall
 * 
 */

public class PersonLoginViewModel implements ViewModel {
	
	// Properties which are used by the view.
	private final SelectableItemList<Person> selectablePersons;
	
	private final IntegerProperty loggedInPersonId = new SimpleIntegerProperty();
	
	private final Command loginCommand;
	
	@Inject
	public PersonLoginViewModel(Repository repository) {
		ModelToStringFunction<Person> personMapper = person -> person.getFirstName() + " " + person.getLastName();
		selectablePersons =
				new SelectableItemList<Person>(FXCollections.observableArrayList(repository.getPersons()), personMapper);
		
		loginCommand = new DelegateCommand(() -> {
			try {
				// fakesleep, simulating latency
				Thread.sleep(2000);
			} catch (Exception e) {
			}
			Platform.runLater(() -> {
				loggedInPersonId.set(selectablePersons.getSelectedItem().getId());
				System.out.println("test");
			});
		},
				createLoginPossibleBinding(), true);
	}
	
	private BooleanBinding createLoginPossibleBinding() {
		return selectablePersons.selectedIndexProperty().isNotEqualTo(-1);
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
	
	public Command getLoginCommand() {
		return loginCommand;
	}
}
