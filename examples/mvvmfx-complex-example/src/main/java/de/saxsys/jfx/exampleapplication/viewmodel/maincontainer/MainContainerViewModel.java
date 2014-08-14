package de.saxsys.jfx.exampleapplication.viewmodel.maincontainer;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import de.saxsys.mvvmfx.ViewModel;

public class MainContainerViewModel implements ViewModel {
	
	private ListProperty<Integer> displayedPersons = new SimpleListProperty<>(
			FXCollections.<Integer> observableArrayList());
	
	public MainContainerViewModel() {
		
	}
	
	public ListProperty<Integer> displayedPersonsProperty() {
		return displayedPersons;
	}
	
}
