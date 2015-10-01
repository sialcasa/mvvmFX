package de.saxsys.mvvmfx.examples.contacts.model;

import de.saxsys.mvvmfx.examples.contacts.events.ContactsUpdatedEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Singleton
public class InmemoryRepository implements Repository {
	
	private Set<Contact> contacts = new HashSet<>();
	
	@Inject
	private Event<ContactsUpdatedEvent> contactsUpdatedEvent;
	
	
	@Override
	public Set<Contact> findAll() {
		return Collections.unmodifiableSet(contacts);
	}
	
	@Override
	public Optional<Contact> findById(String id) {
		return contacts.stream().filter(contact -> contact.getId().equals(id)).findFirst();
	}
	
	@Override
	public void save(Contact contact) {
		contacts.add(contact);
		fireUpdateEvent();
	}
	
	@Override
	public void delete(Contact contact) {
		contacts.remove(contact);
		fireUpdateEvent();
	}
	
	private void fireUpdateEvent() {
		if (contactsUpdatedEvent != null) {
			contactsUpdatedEvent.fire(new ContactsUpdatedEvent());
		}
	}
}
