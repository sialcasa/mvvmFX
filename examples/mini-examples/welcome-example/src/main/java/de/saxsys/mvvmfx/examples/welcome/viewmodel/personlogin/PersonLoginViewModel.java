package de.saxsys.mvvmfx.examples.welcome.viewmodel.personlogin;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.FXCollections;

import javax.inject.Inject;

import de.saxsys.mvvmfx.examples.welcome.model.Person;
import de.saxsys.mvvmfx.examples.welcome.model.Repository;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
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
	private SelectableItemList<Person> selectablePersons;
	private ReadOnlyIntegerWrapper loggedInPersonId;
	private Command loginCommand;
	
	// Repo
	private final Repository repository;
	
	@Inject
	public PersonLoginViewModel(Repository repository) {
		this.repository = repository;
	}
	
	private BooleanBinding createLoginPossibleBinding() {
		return selectablePersonsProperty().selectedIndexProperty().isNotEqualTo(-1);
	}
	
	/**
	 * Persons in string representation.
	 * 
	 * @return persons
	 */
	public SelectableStringList selectablePersonsProperty() {
		if (selectablePersons == null) {
			selectablePersons =
					new SelectableItemList<Person>(FXCollections.observableArrayList(repository.getPersons()),
							person -> person.getFirstName() + " "
									+ person.getLastName());
		}
		return selectablePersons;
	}
	
	/**
	 * Person which is logged in.
	 * 
	 * @return person
	 */
	public ReadOnlyIntegerProperty loggedInPersonIdProperty() {
		if (loggedInPersonId == null) {
			loggedInPersonId = new ReadOnlyIntegerWrapper();
		}
		return loggedInPersonId.getReadOnlyProperty();
	}
	
	public Command getLoginCommand() {
		if (loginCommand == null) {
			loginCommand = new DelegateCommand(() -> new Action() {
				@Override
				protected void action() throws Exception {
					performLogin();
				}
			}, createLoginPossibleBinding(), true);
		}
		return loginCommand;
	}
	
	private void performLogin() {
		try {
			// fakesleep, simulating latency
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		
		Platform.runLater(() -> {
			loggedInPersonId.set(selectablePersons.getSelectedItem().getId());
		});
		publish(PersonLoginViewModelNotifications.OK.getId(), PersonLoginViewModelNotifications.OK.getMessage());
	}
}
