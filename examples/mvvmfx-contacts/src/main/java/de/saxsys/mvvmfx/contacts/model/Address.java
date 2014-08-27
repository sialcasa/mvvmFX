package de.saxsys.mvvmfx.contacts.model;

public class Address extends Identity{
	
	private FederalState federalState;

	private String street;

	private String zipcode;
	
	private String city;
	
	
	Address(){
	}
	
	
	public FederalState getFederalState() {
		return federalState;
	}

	public void setFederalState(FederalState federalState) {
		this.federalState = federalState;
	}

	public Country getCountry(){
		return federalState == null ? null : federalState.getCountry();
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
}
