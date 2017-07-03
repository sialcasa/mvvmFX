package de.saxsys.mvvmfx.examples.contacts.model.countries;

import de.saxsys.mvvmfx.examples.contacts.model.Country;
import de.saxsys.mvvmfx.examples.contacts.model.Subdivision;
import de.saxsys.mvvmfx.examples.contacts.model.countries.CountrySelector;
import javafx.application.Platform;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;

public abstract class AbstractCountrySelectorTest {

	protected abstract CountrySelector getCountrySelector();

	@Test
	public void testLoadSubdivisions() throws Exception {
		CountrySelector countrySelector = getCountrySelector();
		runBlocked(countrySelector::init);

		assertThat(countrySelector.subdivisionLabel()).hasNullValue();
		Assertions.assertThat(countrySelector.subdivisions()).isEmpty();

		countrySelector.setCountry(new Country("Germany", "DE"));

		assertThat(countrySelector.subdivisionLabel()).hasValue("State");
		Assertions.assertThat(countrySelector.subdivisions()).hasSize(16);

		countrySelector.setCountry(new Country("Australia", "AU"));
		assertThat(countrySelector.subdivisionLabel()).hasValue("State/Territory");
		
		countrySelector.setCountry(new Country("China", "CN"));
		assertThat(countrySelector.subdivisionLabel()).hasValue("Municipality/Province/Autonomous region/Special administrative region");
		
		countrySelector.setCountry(null);

		assertThat(countrySelector.subdivisionLabel()).hasNullValue();
		Assertions.assertThat(countrySelector.subdivisions()).isEmpty();

	}

	@Test
	public void testLoadCountries() throws InterruptedException, ExecutionException, TimeoutException {
		CountrySelector countrySelector = getCountrySelector();

		runBlocked(countrySelector::init);

		Assertions.assertThat(countrySelector.availableCountries()).hasSize(5);
		Assertions.assertThat(getCountryNames(countrySelector.availableCountries())).contains("Germany", "Austria", "Switzerland", "Australia", "China");

		Assertions.assertThat(countrySelector.subdivisions()).isEmpty();
		assertThat(countrySelector.subdivisionLabel()).hasNullValue();

		Country germany = getCountryByName(countrySelector.availableCountries(), "Germany");

		Assertions.assertThat(germany).isNotNull();

		countrySelector.setCountry(germany);

		Assertions.assertThat(countrySelector.subdivisions()).hasSize(16);
		Assertions
				.assertThat(getSubdivisionNames(countrySelector.subdivisions())).contains("Sachsen", "Bayern", "Hessen"); // only
		// test
		// some
		// examples.
		assertThat(countrySelector.subdivisionLabel()).hasValue("State");

		Country switzerland = getCountryByName(countrySelector.availableCountries(), "Switzerland");

		countrySelector.setCountry(switzerland);

		Assertions.assertThat(countrySelector.subdivisions()).hasSize(26);
		Assertions.assertThat(getSubdivisionNames(countrySelector.subdivisions())).contains("Zürich", "Jura", "Bern");
		assertThat(countrySelector.subdivisionLabel()).hasValue("Canton");

		countrySelector.setCountry(null);

		Assertions.assertThat(countrySelector.subdivisions()).isEmpty();
		assertThat(countrySelector.subdivisionLabel()).hasNullValue();
	}

	protected void runBlocked(Runnable function) {
		CompletableFuture<Boolean> blocker = new CompletableFuture<>();

		getCountrySelector().inProgressProperty().addListener((obs, oldV, newV) -> {
			if (!newV) {
				blocker.complete(true);
			}
		});

		Platform.runLater(function);

		try {
			blocker.get(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Country getCountryByName(List<Country> countries, String name) {
		return countries.stream().filter(country -> country.getName().equals(name)).findFirst().orElse(null);
	}

	protected List<String> getSubdivisionNames(List<Subdivision> subdivisions) {
		return subdivisions.stream().map(Subdivision::getName).collect(Collectors.toList());
	}

	protected List<String> getCountryNames(List<Country> countries) {
		return countries.stream().map(Country::getName).collect(Collectors.toList());
	}
}
