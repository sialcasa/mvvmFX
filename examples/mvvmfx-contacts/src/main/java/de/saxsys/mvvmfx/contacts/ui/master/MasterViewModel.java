package de.saxsys.mvvmfx.contacts.ui.master;

import java.util.Set;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.events.ContactsUpdatedEvent;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.Repository;


@ApplicationScoped
public class MasterViewModel implements ViewModel {
	
	private static final Logger LOG = LoggerFactory.getLogger(MasterViewModel.class);
	
	private ObservableList<MasterTableViewModel> contacts = FXCollections.observableArrayList();
	
	private ReadOnlyObjectWrapper<Contact> selectedContact = new ReadOnlyObjectWrapper<>();
	
	private ObjectProperty<MasterTableViewModel> selectedTableRow = new SimpleObjectProperty<>();
	
	@Inject
	Repository repository;
	
	@PostConstruct
	public void init() {
		updateContactList();
		
		selectedContact.bind(Bindings.createObjectBinding(() -> {
			if (selectedTableRow.get() == null) {
				return null;
			} else {
				return repository.findById(selectedTableRow.get().getId()).orElse(null);
			}
		}, selectedTableRow));
	}
	
	public void onContactsUpdateEvent(@Observes ContactsUpdatedEvent event) {
		updateContactList();
	}
	
	private void updateContactList() {
		LOG.debug("update contact list");
		
		Set<Contact> allContacts = repository.findAll();
		
		contacts.clear();
		allContacts.forEach(contact -> contacts.add(new MasterTableViewModel(contact)));
	}
	
	public ObservableList<MasterTableViewModel> contactList() {
		return contacts;
	}
	
	
	public ObjectProperty<MasterTableViewModel> selectedTableRowProperty() {
		return selectedTableRow;
	}
	
	public ReadOnlyObjectProperty<Contact> selectedContactProperty() {
		return selectedContact.getReadOnlyProperty();
	}
}
