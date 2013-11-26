package de.saxsys.jfx.exampleapplication.viewmodel.maincontainer;

import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class MainContainerViewModel implements ViewModel {

	private ListProperty<Integer> displayedPersons = new SimpleListProperty<>(
			FXCollections.<Integer> observableArrayList());

	public ListProperty<Integer> displayedPersonsProperty() {
		return displayedPersons;
	}

}
