package de.saxsys.mvvmfx.contacts.model;

public class Address extends Identity{
	
	private FederalState federalState;

	private String street;

	private String postalcode;
	
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

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
}
