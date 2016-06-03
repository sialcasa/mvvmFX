package de.saxsys.mvvmfx.examples.contacts.model;

public class Subdivision {

	private final String name;
	private final String abbr;

	private final Country country;

	public Subdivision(String name, String abbr, Country country) {
		this.name = name;
		this.abbr = abbr;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public String getAbbr() {
		return abbr;
	}

	public Country getCountry() {
		return country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Subdivision that = (Subdivision) o;

		if (!abbr.equals(that.abbr)) {
			return false;
		}
		if (!country.equals(that.country)) {
			return false;
		}
		if (!name.equals(that.name)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + abbr.hashCode();
		result = 31 * result + country.hashCode();
		return result;
	}

}
