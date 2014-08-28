package de.saxsys.mvvmfx.contacts.ui.master;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.Repository;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Set;

public class MasterViewModel implements ViewModel {
	
	private ListProperty<MasterTableViewModel> contacts = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	
	@Inject
	private Repository repository;
	
	@PostConstruct
	public void updateContactList(){

		Set<Contact> allContacts = repository.findAll();

		contacts.clear();
		allContacts.forEach(contact-> contacts.add(new MasterTableViewModel(contact)));
	}
	
	public ObservableList<MasterTableViewModel> contactList(){
		return contacts;
	}
}
