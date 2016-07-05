package de.saxsys.mvvmfx.examples.itemlist.model;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Singleton
public class IceCreamRepository {

	private Set<IceCreamFlavor> entities = new HashSet<>();

	public Set<IceCreamFlavor> get() {
		return Collections.unmodifiableSet(entities);
	}

	public Optional<IceCreamFlavor> get(String id) {
		return entities.stream()
				.filter(entity -> entity.getId().equals(id))
				.findAny();
	}


	public void persist(IceCreamFlavor entity) {
		if(entities.contains(entity)) {
			entities.remove(entity);
		}

		entities.add(entity);
	}

}
