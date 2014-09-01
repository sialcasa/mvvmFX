package de.saxsys.mvvmfx.contacts.ui.master;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.events.ContactsUpdatedEvent;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.Repository;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Set;


@ApplicationScoped
public class MasterViewModel implements ViewModel {
	
	private static final Logger LOG = LoggerFactory.getLogger(MasterViewModel.class); 
	
	private ListProperty<MasterTableViewModel> contacts = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	
	@Inject
	private Repository repository;
	
	@PostConstruct
	public void init(){
		updateContactList();
	}
	
	public void onContactsUpdateEvent(@Observes ContactsUpdatedEvent event){
		updateContactList();
	}
	
	private void updateContactList(){
		LOG.debug("update contact list");
		
		Set<Contact> allContacts = repository.findAll();

		contacts.clear();
		allContacts.forEach(contact-> contacts.add(new MasterTableViewModel(contact)));
	}
	
	public ObservableList<MasterTableViewModel> contactList(){
		return contacts;
	}
}
