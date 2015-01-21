package de.saxsys.mvvmfx.examples.scopes.model;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractStore<T extends Identifiable> {
	
	private Map<String, T> storedElements = new HashMap<>();
	
	public Collection<T> get(){
		return storedElements.values();
	}
	
	public Optional<T> get(String id){
		return Optional.of(storedElements.get(id));
	}
	
	public void put(T element) {
		storedElements.put(element.getId(), element);
	}
	
	public void delete(T element){
		storedElements.remove(element.getId());
	}
	
	public void delete(T...elements){
		Arrays.stream(elements).forEach(this::delete);
	}
	
}
