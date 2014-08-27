package de.saxsys.mvvmfx.contacts.model;

import java.util.Optional;
import java.util.Set;

public interface Repository {
	
	
	Set<Contact> findAll();
	
	Optional<Contact> findById(String id);
	
	void save(Contact contact);
	
	void delete(Contact contact);
	
	Set<Country> findAllCountries();
	
	void save(Country country);
	
	void delete(Country country);
}
