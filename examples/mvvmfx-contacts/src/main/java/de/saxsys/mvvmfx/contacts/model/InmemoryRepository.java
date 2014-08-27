package de.saxsys.mvvmfx.contacts.model;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Singleton
public class InmemoryRepository implements Repository {
	
	private Set<Contact> contacts = new HashSet<>();
	private Set<Country> countries = new HashSet<>();

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
	}

	@Override 
	public void delete(Contact contact) {
		contacts.remove(contact);
	}

	@Override 
	public Set<Country> findAllCountries() {
		return Collections.unmodifiableSet(countries);
	}

	@Override 
	public void save(Country country) {
		countries.add(country);
	}

	@Override 
	public void delete(Country country) {
		countries.remove(country);
	}
}
