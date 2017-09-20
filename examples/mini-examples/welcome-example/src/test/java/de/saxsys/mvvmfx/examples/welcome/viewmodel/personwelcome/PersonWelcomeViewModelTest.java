package de.saxsys.mvvmfx.examples.welcome.viewmodel.personwelcome;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.examples.welcome.model.Gender;
import de.saxsys.mvvmfx.examples.welcome.model.Person;
import de.saxsys.mvvmfx.examples.welcome.model.Repository;

public class PersonWelcomeViewModelTest {

	private Repository repository;
	private PersonWelcomeViewModel personWelcomeViewModel;

	@Before
	public void setup() {
		// TODO: this should be mocked
		repository = new Repository();
		personWelcomeViewModel = new PersonWelcomeViewModel(
				repository);
	}

	@Test
	public void testWelcomeStringInViewModel() throws Exception {
		personWelcomeViewModel.setPersonId(repository.getPersons().get(0)
				.getId());
		assertEquals(
				"Willkommen Herr Alexander Casall",
				personWelcomeViewModel.welcomeStringProperty().get());

		assertEquals(repository.getPersons().get(0).getId(),
				personWelcomeViewModel.getPersonId());
	}

	@Test
	public void welcomeStringForFemalePersonIsDifferent() throws Exception {
		personWelcomeViewModel.setPersonId(repository.getPersons().get(2)
				.getId());
		assertEquals(
				"Willkommen Frau Anna Schulze",
				personWelcomeViewModel.welcomeStringProperty().get());

		assertEquals(repository.getPersons().get(2).getId(),
				personWelcomeViewModel.getPersonId());
	}

	@Test
	public void changeFirstNameOfPersonIsReflectedInViewModel() throws Exception {
		final Person person = repository.getPersons().get(0);
		personWelcomeViewModel.setPersonId(person.getId());
		person.setFirstName(person.getFirstName() + 'X');
		assertEquals(
				"Willkommen Herr AlexanderX Casall",
				personWelcomeViewModel.welcomeStringProperty().get());
	}

	@Test
	public void changeLastNameOfPersonIsReflectedInViewModel() throws Exception {
		final Person person = repository.getPersons().get(0);
		personWelcomeViewModel.setPersonId(person.getId());
		person.setLastName(person.getLastName() + 'X');
		assertEquals(
				"Willkommen Herr Alexander CasallX",
				personWelcomeViewModel.welcomeStringProperty().get());
	}

	@Test
	public void changeGenderOfPersonIsReflectedInViewModel() throws Exception {
		final Person person = repository.getPersons().get(0);
		personWelcomeViewModel.setPersonId(person.getId());
		person.setGender(Gender.FEMALE);
		assertEquals(
				"Willkommen Frau Alexander Casall",
				personWelcomeViewModel.welcomeStringProperty().get());
	}

}
