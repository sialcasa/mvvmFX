package de.saxsys.mvvmfx.examples.contacts.model;

import java.util.Optional;
import java.util.Set;

public interface Repository {

	Set<Contact> findAll();

	Optional<Contact> findById(String id);

	void save(Contact contact);

	void delete(Contact contact);

}
