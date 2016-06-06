package de.saxsys.mvvmfx.examples.scopesexample.model;

import java.util.UUID;

public abstract class Entity {

	private final String id;

	public Entity() {
		id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Entity entity = (Entity) o;

		return id != null ? id.equals(entity.id) : entity.id == null;

	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
