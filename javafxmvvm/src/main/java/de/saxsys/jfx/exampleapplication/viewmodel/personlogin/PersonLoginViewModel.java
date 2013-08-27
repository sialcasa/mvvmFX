package de.saxsys.jfx.exampleapplication.viewmodel.personlogin;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import de.saxsys.jfx.exampleapplication.model.Person;
import de.saxsys.jfx.exampleapplication.model.Repository;
import de.saxsys.jfx.mvvm.MVVMViewModel;
import de.saxsys.jfx.viewmodel.personlogin.PersonLoginViewModelTest;

/**
 * ViewModel for a login view for the persons. It provides the data which should be visualized in the frontend e.g.
 * the list of persons in string representations. The tests for it can be written first. Have a look on
 * {@link PersonLoginViewModelTest}.
 * 
 * @author alexander.casall
 * 
 */
public class PersonLoginViewModel implements MVVMViewModel {

    private final ListProperty<String> persons = new SimpleListProperty<>(FXCollections.<String> observableArrayList());

    public PersonLoginViewModel() {
        initPersons();
    }

    private void initPersons() {
        final List<Person> personsInRepo = Repository.getInstance().getPersons();
        for (final Person person : personsInRepo) {
            persons.add(person.getFirstName() + " " + person.getLastName());
        }
    }

    /**
     * Persons in string representation.
     * 
     * @return persons
     */
    public ListProperty<String> personsProperty() {
        return persons;
    }
}
