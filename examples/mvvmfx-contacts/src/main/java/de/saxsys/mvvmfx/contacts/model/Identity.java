package de.saxsys.mvvmfx.contacts.model;

import java.util.UUID;


/**
 * This is a base class for model entities that represent identities. This means that they are distinguished by there
 * identity (id) and not by there values. Two instances can be considered "the same" when they have the same ID,
 * independently what values they have.
 */
public abstract class Identity {
	
	private String id;
	
	public Identity() {
		id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Identity identity = (Identity) o;
		
		if (!id.equals(identity.id)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
