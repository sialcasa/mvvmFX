package de.saxsys.mvvmfx.examples.contacts.model;

import de.saxsys.javafx.test.JfxRunner;
import javafx.application.Platform;
import org.datafx.reader.converter.XmlConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static eu.lestard.assertj.javafx.api.Assertions.assertThat;

@RunWith(JfxRunner.class)
public class CountrySelectorIntegrationTest {
	
	private CountrySelector countrySelector;
	
	
	@Before
	public void setup() {
		countrySelector = new CountrySelector();
	}
	
	@Test
	public void testXmlConverterForCountry() throws FileNotFoundException {
		XmlConverter<Country> converter = new XmlConverter<>("iso_3166_entry", Country.class);
		
		String iso_3166_xml = this.getClass().getResource("/countries/iso_3166.xml").getFile();
		
		assertThat(iso_3166_xml).isNotNull();
		
		converter.initialize(new FileInputStream(iso_3166_xml));
		
		Country country = converter.get();
		assertThat(country).isNotNull();
	}
	
	@Test
	public void testXmlConverterForSubdivision() throws Exception {
		XmlConverter<CountrySelector.ISO3166_2_CountryEntity> converter = new XmlConverter<>("iso_3166_country",
				CountrySelector.ISO3166_2_CountryEntity.class);
		
		String iso_3166_2_xml = this.getClass().getResource("/countries/iso_3166_2.xml").getFile();
		
		assertThat(iso_3166_2_xml).isNotNull();
		
		converter.initialize(new FileInputStream(iso_3166_2_xml));
		
		CountrySelector.ISO3166_2_CountryEntity entity = converter.get();
		
		assertThat(entity).isNotNull();
		assertThat(entity.code).isNotNull().isEqualTo("DE");
		
		assertThat(entity.subsets).isNotNull().hasSize(1);
		assertThat(entity.subsets.get(0).subdivisionType).isEqualTo("State");
		
		List<CountrySelector.ISO3166_2_EntryEntity> entryList = entity.subsets.get(0).entryList;
		
		assertThat(entryList).isNotNull().hasSize(16);
		
		CountrySelector.ISO3166_2_EntryEntity entry = entryList.get(0);
		
		assertThat(entry.code).isEqualTo("DE-BW");
		assertThat(entry.name).isEqualTo("Baden-Württemberg");
	}
	
	
	
	@Test
	public void testLoadSubdivisions() throws Exception {
		runBlocked(countrySelector::init);
		
		assertThat(countrySelector.subdivisionLabel()).hasNullValue();
		assertThat(countrySelector.subdivisions()).isEmpty();
		
		countrySelector.setCountry(new Country("Germany", "DE"));
		
		assertThat(countrySelector.subdivisionLabel()).hasValue("State");
		assertThat(countrySelector.subdivisions()).hasSize(16);
		
		countrySelector.setCountry(null);
		
		assertThat(countrySelector.subdivisionLabel()).hasNullValue();
		assertThat(countrySelector.subdivisions()).isEmpty();
		
	}
	
	
	@Test
	public void testLoadCountries() throws InterruptedException, ExecutionException, TimeoutException {
		runBlocked(countrySelector::init);
		
		assertThat(countrySelector.availableCountries()).hasSize(3);
		assertThat(getCountryNames(countrySelector.availableCountries())).contains("Germany", "Austria", "Switzerland");
		
		assertThat(countrySelector.subdivisions()).isEmpty();
		assertThat(countrySelector.subdivisionLabel()).hasNullValue();
		
		
		Country germany = getCountryByName(countrySelector.availableCountries(), "Germany");
		
		assertThat(germany).isNotNull();
		
		countrySelector.setCountry(germany);
		
		assertThat(countrySelector.subdivisions()).hasSize(16);
		assertThat(getSubdivisionNames(countrySelector.subdivisions())).contains("Sachsen", "Bayern", "Hessen"); // only
																													// test
																													// some
																													// examples.
		assertThat(countrySelector.subdivisionLabel()).hasValue("State");
		
		
		Country switzerland = getCountryByName(countrySelector.availableCountries(), "Switzerland");
		
		countrySelector.setCountry(switzerland);
		
		assertThat(countrySelector.subdivisions()).hasSize(26);
		assertThat(getSubdivisionNames(countrySelector.subdivisions())).contains("Zürich", "Jura", "Bern");
		assertThat(countrySelector.subdivisionLabel()).hasValue("Canton");
		
		
		countrySelector.setCountry(null);
		
		assertThat(countrySelector.subdivisions()).isEmpty();
		assertThat(countrySelector.subdivisionLabel()).hasNullValue();
	}
	
	
	private void runBlocked(Runnable function) {
		CompletableFuture<Boolean> blocker = new CompletableFuture<>();
		
		countrySelector.inProgressProperty().addListener((obs, oldV, newV) -> {
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
	
	
	private Country getCountryByName(List<Country> countries, String name) {
		return countries.stream().filter(country -> country.getName().equals(name)).findFirst().orElse(null);
	}
	
	
	private List<String> getSubdivisionNames(List<Subdivision> subdivisions) {
		return subdivisions.stream().map(Subdivision::getName).collect(Collectors.toList());
	}
	
	private List<String> getCountryNames(List<Country> countries) {
		return countries.stream().map(Country::getName).collect(Collectors.toList());
	}
}
