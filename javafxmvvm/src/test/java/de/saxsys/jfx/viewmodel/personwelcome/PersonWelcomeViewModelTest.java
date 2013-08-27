package de.saxsys.jfx.viewmodel.personwelcome;

import org.junit.Test;

import de.saxsys.jfx.exampleapplication.viewmodel.personwelcome.PersonWelcomeViewModel;

import static junit.framework.Assert.assertEquals;

public class PersonWelcomeViewModelTest {

    @Test
    public void testWelcomeStringInViewModel() throws Exception {
        final PersonWelcomeViewModel personWelcomeViewModel = new PersonWelcomeViewModel(0);
        assertEquals("Willkommen Herr Casall, oder wollen Sie Alexander genannt werden?", personWelcomeViewModel
                .welcomeStringProperty().get());
    }
}
