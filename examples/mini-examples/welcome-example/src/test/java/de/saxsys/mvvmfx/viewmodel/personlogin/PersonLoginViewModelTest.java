package de.saxsys.mvvmfx.viewmodel.personlogin;

import static org.junit.Assert.assertEquals;

import javafx.collections.ObservableList;

import org.junit.Test;

import de.saxsys.mvvmfx.examples.welcome.model.Repository;
import de.saxsys.mvvmfx.examples.welcome.viewmodel.personlogin.PersonLoginViewModel;

public class PersonLoginViewModelTest {

    @Test
    public void testShowAllPersons() throws Exception {
        final PersonLoginViewModel personLoginViewModel = new PersonLoginViewModel(
                new Repository());
        final ObservableList<String> persons = personLoginViewModel
                .selectablePersonsProperty().stringListProperty();
        assertEquals("Alexander Casall", persons.get(0));
        assertEquals("Bernd Grams", persons.get(1));
        assertEquals("Anna Schulze", persons.get(2));
    }
}
