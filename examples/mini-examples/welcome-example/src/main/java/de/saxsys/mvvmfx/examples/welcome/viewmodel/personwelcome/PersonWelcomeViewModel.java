package de.saxsys.mvvmfx.examples.welcome.viewmodel.personwelcome;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.inject.Inject;

import de.saxsys.mvvmfx.examples.welcome.model.Gender;
import de.saxsys.mvvmfx.examples.welcome.model.Person;
import de.saxsys.mvvmfx.examples.welcome.model.Repository;
import de.saxsys.mvvmfx.ViewModel;

/**
 * ViewModel for a welcome view for a person. It provides the data which should be visualized in the frontend. The tests
 * for it can be written first. Have a look on PersonWelcomeViewModelTest.
 * 
 * @author alexander.casall
 * 
 */
public class PersonWelcomeViewModel implements ViewModel {
	
	private final Repository repository;
	
	// Properties which are used by the view.
	private final StringProperty welcomeString = new SimpleStringProperty();
	
	private Person person;
	
	/**
	 * Create a {@link PersonWelcomeViewModel}.
	 * 
	 * @param repository
	 *            repo which is used
	 */
	@Inject
	public PersonWelcomeViewModel(Repository repository) {
		this.repository = repository;
	}
	
	/**
	 * Provides the the concated string.
	 * 
	 * @return
	 */
	public StringProperty welcomeStringProperty() {
		return welcomeString;
	}
	
	/**
	 * Set Person id for the screen
	 * 
	 * @param personId
	 *            for the screen
	 */
	public void setPersonId(int personId) {
		person = repository.getPersonById(personId);
		
		StringBinding salutationBinding =
				Bindings.when(person.genderProperty().isEqualTo(Gender.NOT_SPECIFIED))
						.then("Herr/Frau/* ")
						.otherwise(
								Bindings.when(person.genderProperty().isEqualTo(Gender.MALE))
										.then("Herr ").otherwise("Frau "));
		
		welcomeString.unbind();
		welcomeString.bind(Bindings.concat("Willkommen ", salutationBinding,
				person.firstNameProperty(), " ",
				person.lastNameProperty()));
	}
	
	/**
	 * Returns the id of the displayed person.
	 * 
	 * @return id
	 */
	public int getPersonId() {
		return person.getId();
	}
}
