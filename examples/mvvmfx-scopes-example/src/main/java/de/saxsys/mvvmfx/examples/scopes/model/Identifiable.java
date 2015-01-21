package de.saxsys.mvvmfx.examples.scopes.model;

import java.util.UUID;

public abstract class Identifiable {
	private final String id;

	public Identifiable(){
		id = UUID.randomUUID().toString();
	}

	public String getId(){
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Identifiable other = (Identifiable) o;

		return id.equals(other.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
}
