package de.saxsys.mvvmfx.examples.contacts.ui.master;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.events.ContactsUpdatedEvent;
import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.MasterDetailScope;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MasterViewModel implements ViewModel {
	
	private static final Logger LOG = LoggerFactory.getLogger(MasterViewModel.class);
	
	private final ObservableList<MasterTableViewModel> contacts = FXCollections.observableArrayList();
	
	private final ReadOnlyObjectWrapper<Contact> selectedContact = new ReadOnlyObjectWrapper<>();
	
	private final ObjectProperty<MasterTableViewModel> selectedTableRow = new SimpleObjectProperty<>();
	
	private Optional<Consumer<MasterTableViewModel>> onSelect = Optional.empty();
	
	@Inject
	Repository repository;
	
	@InjectScope
	MasterDetailScope mdScope;
	
	
	public void initialize() {
		updateContactList();
		
		mdScope.selectedContactProperty().bind(selectedContact);
		
		selectedContact.bind(Bindings.createObjectBinding(() -> {
			if (selectedTableRow.get() == null) {
				return null;
			} else {
				return repository.findById(selectedTableRow.get().getId()).orElse(null);
			}
		} , selectedTableRow));
	}
	
	public void onContactsUpdateEvent(@Observes ContactsUpdatedEvent event) {
		updateContactList();
	}
	
	private void updateContactList() {
		LOG.debug("update contact list");
		
		
		// when there is a selected row, persist the id of this row, otherwise use null
		final String selectedContactId = (selectedTableRow.get() == null) ? null : selectedTableRow.get().getId();
		
		
		Set<Contact> allContacts = repository.findAll();
		
		contacts.clear();
		allContacts.forEach(contact -> contacts.add(new MasterTableViewModel(contact)));
		
		if (selectedContactId != null) {
			Optional<MasterTableViewModel> selectedRow = contacts.stream()
					.filter(row -> row.getId().equals(selectedContactId)).findFirst();
					
			if (selectedRow.isPresent()) {
				onSelect.ifPresent(consumer -> consumer.accept(selectedRow.get()));
			} else {
				onSelect.ifPresent(consumer -> consumer.accept(null));
			}
		}
	}
	
	public ObservableList<MasterTableViewModel> getContactList() {
		return contacts;
	}
	
	
	public void setOnSelect(Consumer<MasterTableViewModel> consumer) {
		onSelect = Optional.of(consumer);
	}
	
	public ObjectProperty<MasterTableViewModel> selectedTableRowProperty() {
		return selectedTableRow;
	}
	
	
	
	
}
