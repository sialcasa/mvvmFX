package de.saxsys.jfx.exampleapplication.viewmodel.personwelcome;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.inject.Inject;

import de.saxsys.jfx.exampleapplication.model.Person;
import de.saxsys.jfx.exampleapplication.model.Repository;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;

/**
 * ViewModel for a welcome view for a person. It provides the data which should be visualized in the frontend. The
 * tests for it can be written first. Have a look on PersonWelcomeViewModelTest.
 * 
 * @author alexander.casall
 * 
 */
public class PersonWelcomeViewModel implements ViewModel {

    private final Repository repository;

    // Properties which are used by the view.
    private final StringProperty welcomeString = new SimpleStringProperty();

    /**
     * Create a {@link PersonWelcomeViewModel}.
     * 
     * @param repository repo which is used
     */
    @Inject
    public PersonWelcomeViewModel(final Repository repository) {
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
     * Set Person id for the screen * @param personId for the screen
     */
    public void setPersonId(final int personId) {
        final Person person = repository.getPersonById(personId);
        welcomeString.bind(Bindings.concat("Willkommen " + (person.isMale() ? "Herr " : "Frau "),
                person.lastNameProperty(), ", oder wollen Sie ", person.firstNameProperty(), " genannt werden?"));
    }
}
