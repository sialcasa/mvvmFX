package de.saxsys.mvvmfx.examples.contacts.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "iso_3166_entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class Country {

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "alpha_2_code")
	private String countryCode;

	Country() {

	}

	public Country(String name, String countryCode) {
		this.name = name;
		this.countryCode = countryCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		if (name == null || countryCode == null) {
			return false;
		}

		Country country = (Country) o;

		if (!name.equals(country.name)) {
			return false;
		}

		if (!countryCode.equals(country.countryCode)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = name == null ? 13 : name.hashCode();
		result += countryCode == null ? 13 : countryCode.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Country:" + name + ", code:" + countryCode;
	}
}
