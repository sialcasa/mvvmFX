package de.saxsys.mvvmfx.contacts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Country {
	
	private final String name;
	
	private final List<FederalState> federalStates = new ArrayList<>();
	
	public Country(String name){
		this.name = name;
	}

	public void addFederalState(String name, String abbr){
		this.federalStates.add(new FederalState(name, abbr, this));
	}
	
	public List<FederalState> getFederalStates(){
		return Collections.unmodifiableList(federalStates);
	}
	
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Country country = (Country) o;

		if (!name.equals(country.name)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		return result;
	}
}
