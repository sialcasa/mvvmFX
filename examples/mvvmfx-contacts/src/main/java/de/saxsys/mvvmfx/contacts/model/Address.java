package de.saxsys.mvvmfx.contacts.model;

public class Address extends Identity{
	
	private Subdivision subdivision;

	private String street;

	private String postalcode;
	
	private String city;
	
	
	Address(){
	}
	
	
	public Subdivision getSubdivision() {
		return subdivision;
	}

	public void setSubdivision(Subdivision subdivision) {
		this.subdivision = subdivision;
	}

	public Country getCountry(){
		return subdivision == null ? null : subdivision.getCountry();
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
