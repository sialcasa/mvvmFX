package de.saxsys.mvvmfx.examples.welcome.viewmodel.personlogin;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.welcome.model.Person;
import de.saxsys.mvvmfx.examples.welcome.model.Repository;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import de.saxsys.mvvmfx.utils.items.ItemList;
import de.saxsys.mvvmfx.utils.items.ViewItemList;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

import javax.inject.Inject;

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
	private ItemList<Person, Integer> personsList;
	private ReadOnlyIntegerWrapper loggedInPersonId;
	private Command loginCommand;

	// Repo
	private final Repository repository;

	@Inject
	public PersonLoginViewModel(Repository repository) {
		this.repository = repository;

		personsList = new ItemList<>(Person::getId);
		personsList.getSourceList().addAll(repository.getPersons());
		personsList.setLabelFunction(person -> person.getFirstName() + " " + person.getLastName());
	}

	private BooleanBinding createLoginPossibleBinding() {
		return personsList.selectedItemProperty().isNotNull();
	}

	public ViewItemList<Integer> personsListProperty() {
		return personsList;
	}

	// for testing purposes only
	ItemList<Person, Integer> getPersonList() {
		return personsList;
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
			Thread.sleep(1000);
		} catch (Exception e) {
		}

		Platform.runLater(() -> {
			loggedInPersonId.set(personsList.getSelectedItem().getId());
		});
		publish(PersonLoginViewModelNotifications.OK.getId(), PersonLoginViewModelNotifications.OK.getMessage());
	}

}
