package de.saxsys.mvvmfx.examples.contacts.model;

/**
 * An entity class that represents an address.
 */
public class Address extends Identity {
	
	private Country country;
	
	private Subdivision subdivision;
	
	private String street;
	
	private String postalcode;
	
	private String city;
	
	
	Address() {
	}
	
	
	public Subdivision getSubdivision() {
		return subdivision;
	}
	
	public void setSubdivision(Subdivision subdivision) {
		this.subdivision = subdivision;
	}
	
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
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
