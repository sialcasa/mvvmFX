package de.saxsys.mvvmfx.contacts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Country {
	
	private final String name;


	private final String countryCode;
	
	public Country(String name, String countryCode){
		this.name = name;
		this.countryCode = countryCode;
	}

	public String getName() {
		return name;
	}


	public String getCountryCode() {
		return countryCode;
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
		
		if(!countryCode.equals(country.countryCode)){
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result += countryCode.hashCode();
		return result;
	}
}
