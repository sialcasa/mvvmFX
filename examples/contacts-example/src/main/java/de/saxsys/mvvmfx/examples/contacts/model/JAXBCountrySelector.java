package de.saxsys.mvvmfx.examples.contacts.model;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class JAXBCountrySelector implements CountrySelector {
	private static final Logger LOG = LoggerFactory.getLogger(JAXBCountrySelector.class);

	public static final String ISO_3166_LOCATION = "/countries/iso_3166.xml";
	public static final String ISO_3166_2_LOCATION = "/countries/iso_3166_2.xml";

	private ObservableList<Country> countries = FXCollections.observableArrayList();
	private ObservableList<Subdivision> subdivisions = FXCollections.observableArrayList();

	private ReadOnlyStringWrapper subdivisionLabel = new ReadOnlyStringWrapper();

	private ReadOnlyBooleanWrapper inProgress = new ReadOnlyBooleanWrapper(false);

	private Map<Country, List<Subdivision>> countryCodeSubdivisionMap = new HashMap<>();
	private Map<Country, String> countryCodeSubdivisionNameMap = new HashMap<>();

	public static void main(String[] args) {
		JAXBCountrySelector jaxbCountrySelector = new JAXBCountrySelector();
		jaxbCountrySelector.init();
	}

	@Override
	@PostConstruct
	public void init() {
		loadCountries();
	}

	private void loadCountries() {
		inProgress.setValue(true);

		try {
			loadCountriesFromXml();
			loadSubdivisionsFromXml();

		} catch (JAXBException e) {
			LOG.error("Cannot load Countries from XML file", e);
		}

		inProgress.set(false);
	}

	private void loadCountriesFromXml() throws JAXBException {
		InputStream iso_3166 = this.getClass().getResourceAsStream(ISO_3166_LOCATION);

		JAXBContext context = JAXBContext.newInstance(Countries.class);
		Countries countries = (Countries) context.createUnmarshaller().unmarshal(iso_3166);
		this.countries.addAll(countries.getCountries());

	}

	private void loadSubdivisionsFromXml() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ISO3166_2_Entries.class);
		InputStream iso_3166_2 = this.getClass().getResourceAsStream(ISO_3166_2_LOCATION);
		ISO3166_2_Entries iso3166_2_entries = (ISO3166_2_Entries) jaxbContext.createUnmarshaller()
				.unmarshal(iso_3166_2);

		iso3166_2_entries.countryEntities.stream()
				.filter(entity -> entity.subsets != null && !entity.subsets.isEmpty())
				.forEach(entity -> {
					Country country = findCountryByCode(entity.code);
					if (!countryCodeSubdivisionMap.containsKey(country)) {
						countryCodeSubdivisionMap.put(country, new ArrayList<>());
					}

					List<Subdivision> subdivisionList = countryCodeSubdivisionMap.get(country);

					entity.subsets.stream()
							.flatMap(subset -> subset.entryList.stream())
							.map(entry -> new Subdivision(entry.name, entry.code, country))
							.forEach(subdivisionList::add);


					String subdivisionName = entity.subsets.stream()
							.map(subset -> subset.subdivisionType)
							.collect(Collectors.joining("/"));


					countryCodeSubdivisionNameMap.put(country, subdivisionName);
				});

	}

	private Country findCountryByCode(String code) {
		return countries.stream()
				.filter(country -> country.getCountryCode().equals(code))
				.findFirst()
				.orElse(null);
	}

	@Override
	public void setCountry(Country country) {
		if (country == null) {
			subdivisionLabel.set(null);
			subdivisions.clear();
			return;
		}

		subdivisionLabel.set(countryCodeSubdivisionNameMap.get(country));

		subdivisions.clear();
		if (countryCodeSubdivisionMap.containsKey(country)) {
			subdivisions.addAll(countryCodeSubdivisionMap.get(country));
		}
	}

	@Override
	public ObservableList<Country> availableCountries() {
		return countries;
	}

	@Override
	public ReadOnlyStringProperty subdivisionLabel() {
		return subdivisionLabel.getReadOnlyProperty();
	}

	@Override
	public ObservableList<Subdivision> subdivisions() {
		return FXCollections.unmodifiableObservableList(subdivisions);
	}

	@Override
	public ReadOnlyBooleanProperty inProgressProperty() {
		return inProgress.getReadOnlyProperty();
	}
}
