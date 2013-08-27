package de.saxsys.jfx.exampleapplication.viewmodel.personwelcome;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import de.saxsys.jfx.exampleapplication.model.Person;
import de.saxsys.jfx.exampleapplication.model.Repository;
import de.saxsys.jfx.mvvm.MVVMViewModel;
import de.saxsys.jfx.viewmodel.personwelcome.PersonWelcomeViewModelTest;

/**
 * ViewModel for a welcome view for a person. It provides the data which should be visualized in the frontend. The
 * tests for it can be written first. Have a look on {@link PersonWelcomeViewModelTest}.
 * 
 * @author alexander.casall
 * 
 */
public class PersonWelcomeViewModel implements MVVMViewModel {

    private final StringProperty welcomeString = new SimpleStringProperty();

    /**
     * Create a {@link PersonWelcomeViewModel} for a given id of a person.
     * 
     * @param personId for the screen
     */
    public PersonWelcomeViewModel(final int personId) {
        final Person person = Repository.getInstance().getPersonById(personId);
        welcomeString.bind(Bindings.concat("Willkommen Herr ", person.lastNameProperty(), ", oder wollen Sie ",
                person.firstNameProperty(), " genannt werden?"));
    }

    /**
     * Provides the information.
     * 
     * @return
     */
    public StringProperty welcomeStringProperty() {
        return welcomeString;
    }
}
