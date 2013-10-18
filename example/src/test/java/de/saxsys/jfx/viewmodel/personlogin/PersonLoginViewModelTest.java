package de.saxsys.jfx.viewmodel.personlogin;

import javafx.collections.ObservableList;

import org.junit.Test;

import de.saxsys.jfx.exampleapplication.model.Repository;
import de.saxsys.jfx.exampleapplication.viewmodel.personlogin.PersonLoginViewModel;

public class PersonLoginViewModelTest {

	@Test
	public void testShowAllPersons() throws Exception {
		final PersonLoginViewModel personLoginViewModel = new PersonLoginViewModel(
				new Repository());
		final ObservableList<String> persons = personLoginViewModel
				.personsProperty();
		persons.get(0).equals("Alexander Casall");
		persons.get(1).equals("Bernd Grams");
		persons.get(2).equals("Anna Schulze");
	}
}
