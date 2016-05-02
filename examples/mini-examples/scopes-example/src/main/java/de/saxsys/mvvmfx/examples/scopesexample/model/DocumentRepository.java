package de.saxsys.mvvmfx.examples.scopesexample.model;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class DocumentRepository {


	private Map<String, Document> entities = new HashMap<>();


	public Optional<Document> findById(String id) {
		return Optional.ofNullable(entities.get(id));
	}

	public Collection<Document> findAll() {
		return entities.values();
	}

	public void persist(Document document) {
		entities.put(document.getId(), document);
	}

	public void remove(Document document) {
		remove(document.getId());
	}

	public void remove(String id) {
		entities.remove(id);
	}

}
