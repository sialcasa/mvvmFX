package de.saxsys.mvvmfx.contacts.model;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.application.Platform;
import org.datafx.reader.converter.XmlConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static eu.lestard.assertj.javafx.api.Assertions.assertThat;

@RunWith(JfxRunner.class)
public class CountryLoaderTest {
	
	private CountrySelector countrySelector;
	
	
	@Before
	public void setup(){
		countrySelector = new CountrySelector();
	}
	@Test
	public void testXmlConverter2() throws FileNotFoundException {
		XmlConverter<Country> converter = new XmlConverter<>("iso_3166_entry",Country.class);

		String iso_3166_xml = this.getClass().getResource("/countries/iso_3166.xml").getFile();

		assertThat(iso_3166_xml).isNotNull();

		converter.initialize(new FileInputStream(iso_3166_xml));

		Country country = converter.get();
		assertThat(country).isNotNull();
	}


	@Test
	public void test() throws InterruptedException, ExecutionException, TimeoutException {
		CompletableFuture<Boolean> blocker = new CompletableFuture<>();
		
		countrySelector.inProgressProperty().addListener((obs, oldV, newV)->{
			if(!newV){
				blocker.complete(true);
			}
		});

		Platform.runLater(countrySelector::loadCountries);
		
		blocker.get(1, TimeUnit.SECONDS);
		
		
		assertThat(countrySelector.availableCountries()).hasSize(3);
		assertThat(getCountryNames(countrySelector.availableCountries())).contains("Germany", "Austria", "Switzerland");
		
		assertThat(countrySelector.subdivisions()).isEmpty();
		assertThat(countrySelector.subdivisionLabel()).hasNullValue();


//		Country germany = getCountryByName(countrySelector.availableCountries(), "Germany");
//		
//		assertThat(germany).isNotNull();
//		
//		countrySelector.setCountry(germany);
//		
//		assertThat(countrySelector.subdivisions()).hasSize(16);
//		assertThat(getSubdivisionNames(countrySelector.subdivisions())).contains("Sachsen", "Bayern", "Hessen"); // only test some examples.
//		assertThat(countrySelector.subdivisionLabel()).hasValue("State");
//		
//		
//		Country switzerland = getCountryByName(countrySelector.availableCountries(), "Switzerland");
//		
//		countrySelector.setCountry(switzerland);
//		
//		assertThat(countrySelector.subdivisions()).hasSize(26);
//		assertThat(getSubdivisionNames(countrySelector.subdivisions())).contains("Zürich", "Jura", "Bern"); 
//		assertThat(countrySelector.subdivisionLabel()).hasValue("Canton");
//		
//		
//		countrySelector.setCountry(null);
//		
//		assertThat(countrySelector.subdivisions()).isEmpty();
//		assertThat(countrySelector.subdivisionLabel()).hasNullValue();
	}
	
	
	private Country getCountryByName(List<Country> countries, String name){
		return countries.stream().filter(country->country.getName().equals(name)).findFirst().orElse(null);
	}
	
	
	private List<String> getSubdivisionNames(List<Subdivision> subdivisions){
		return subdivisions.stream().map(Subdivision::getName).collect(Collectors.toList());
	}
	
	private List<String> getCountryNames(List<Country> countries){
		return countries.stream().map(Country::getName).collect(Collectors.toList());
	}
}
