package de.saxsys.mvvmfx.examples.contacts.model;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import org.datafx.provider.ListDataProvider;
import org.datafx.reader.DataReader;
import org.datafx.reader.InputStreamDataReader;
import org.datafx.reader.converter.InputStreamConverter;
import org.datafx.reader.converter.XmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to encapsulate the process of loading available countries
 * and there subdivisions (if available).
 *
 * This class is meant to be a stateful wrapper around the existing countries.
 * You should create an instance of this class, call the {@link #init()} method
 * and then bind the UI to the provided observable lists (
 * {@link #availableCountries()} and {@link #subdivisions()}).
 *
 * To choose a country have to use the {@link #setCountry(Country)} method. This
 * will lead to a change of the {@link #subdivisions()} list.
 *
 *
 * At the moment this class used two XML files ({@link #ISO_3166_LOCATION} and
 * {@link #ISO_3166_2_LOCATION}) that contain information about countries,
 * country-codes and subdivisions according to ISO 3166 and ISO 3166-2.
 *
 * The loading process is implemented with the DataFX framework.
 */
public class CountrySelector {

	private static final Logger LOG = LoggerFactory.getLogger(CountrySelector.class);

	public static final String ISO_3166_LOCATION = "/countries/iso_3166.xml";
	public static final String ISO_3166_2_LOCATION = "/countries/iso_3166_2.xml";
	private ObservableList<Country> countries = FXCollections.observableArrayList();
	private ObservableList<Subdivision> subdivisions = FXCollections.observableArrayList();

	private ReadOnlyStringWrapper subdivisionLabel = new ReadOnlyStringWrapper();

	private ReadOnlyBooleanWrapper inProgress = new ReadOnlyBooleanWrapper(false);

	private Map<Country, List<Subdivision>> countryCodeSubdivisionMap = new HashMap<>();
	private Map<Country, String> countryCodeSubdivisionNameMap = new HashMap<>();

	/**
	 * This method triggers the loading of the available countries and
	 * subdivisions.
	 */
	public void init() {
		inProgress.set(true);
		loadCountries();
	}

	/**
	 * Set the currently selected country. This will lead to an update of the
	 * {@link #subdivisions()} observable list and the
	 * {@link #subdivisionLabel()}.
	 *
	 * @param country the country that will be selected or <code>null</code> if
	 * no country is selected.
	 */
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

	/**
	 * Load all countries from the XML file source with DataFX.
	 */
	void loadCountries() {
		InputStream iso3166Resource = this.getClass().getResourceAsStream(ISO_3166_LOCATION);
		if (iso3166Resource == null) {
			throw new IllegalStateException("Can't find the list of countries! Expected location was:"
					+ ISO_3166_LOCATION);
		}

		XmlConverter<Country> countryConverter = new XmlConverter<>("iso_3166_entry", Country.class);

		try {
			DataReader<Country> dataSource = new InputStreamSource<>( iso3166Resource, countryConverter );

			ListDataProvider<Country> listDataProvider = new ListDataProvider<>(dataSource);

			listDataProvider.setResultObservableList(countries);

			Worker<ObservableList<Country>> worker = listDataProvider.retrieve();
			// when the countries are loaded we start the loading of the subdivisions.
			worker.stateProperty().addListener(obs -> {
				if (worker.getState() == Worker.State.SUCCEEDED) {
					loadSubdivisions();
				}
			});
		} catch (IOException e) {
			LOG.error("A problem was detected while loading the XML file with the available countries.", e);
		}
	}

	static class InputStreamSource<T> extends InputStreamDataReader<T> {
		public InputStreamSource(InputStream is, InputStreamConverter converter) throws IOException {
			super(converter);
			setInputStream(is);
		}
	}

	/**
	 * Load all subdivisions from the XML file source with DataFX.
	 */
	void loadSubdivisions() {

		InputStream iso3166_2Resource = this.getClass().getResourceAsStream(ISO_3166_2_LOCATION);

		if (iso3166_2Resource == null) {
			throw new IllegalStateException("Can't find the list of subdivisions! Expected location was:"
					+ ISO_3166_2_LOCATION);
		}

		XmlConverter<ISO3166_2_CountryEntity> converter = new XmlConverter<>("iso_3166_country",
				ISO3166_2_CountryEntity.class);

		ObservableList<ISO3166_2_CountryEntity> subdivisionsEntities = FXCollections.observableArrayList();

		try {

			DataReader<ISO3166_2_CountryEntity> dataSource =
					new InputStreamSource<>( iso3166_2Resource, converter );

			ListDataProvider<ISO3166_2_CountryEntity> listDataProvider = new ListDataProvider<>(dataSource);

			listDataProvider.setResultObservableList(subdivisionsEntities);

			Worker<ObservableList<ISO3166_2_CountryEntity>> worker = listDataProvider.retrieve();
			worker.stateProperty().addListener(obs -> {
				if (worker.getState() == Worker.State.SUCCEEDED) {

					subdivisionsEntities.forEach(entity -> {
						if (entity.subsets != null && !entity.subsets.isEmpty()) {

							Country country = findCountryByCode(entity.code);

							if (!countryCodeSubdivisionMap.containsKey(country)) {
								countryCodeSubdivisionMap.put(country, new ArrayList<>());
							}

							List<Subdivision> subdivisionList = countryCodeSubdivisionMap.get(country);

							entity.subsets.get(0).entryList.forEach(entry -> {
								subdivisionList.add(new Subdivision(entry.name, entry.code, country));
							});

							countryCodeSubdivisionNameMap.put(country, entity.subsets.get(0).subdivisionType);
						}
					});

					inProgress.set(false);
				}
			});
		} catch (IOException e) {
			LOG.error("A problem was detected while loading the XML file with the available subdivisions.", e);
		}

	}

	private Country findCountryByCode(String code) {
		return countries.stream().filter(country -> country.getCountryCode().equals(code)).findFirst().orElse(null);
	}

	/**
	 * XML entity class. These classes represent the structure of the XML files
	 * to be loaded.
	 */
	@XmlRootElement(name = "iso_3166_subset")
	@XmlAccessorType(XmlAccessType.FIELD)
	static class ISO3166_2_EntryEntity {

		@XmlAttribute(name = "code")
		public String code;
		@XmlAttribute(name = "name")
		public String name;
	}

	/**
	 * XML entity class. These classes represent the structure of the XML files
	 * to be loaded.
	 */
	@XmlRootElement(name = "iso_3166_subset")
	@XmlAccessorType(XmlAccessType.FIELD)
	static class ISO3166_2_SubsetEntity {

		@XmlElement(name = "iso_3166_2_entry")
		public List<ISO3166_2_EntryEntity> entryList;

		@XmlAttribute(name = "type")
		public String subdivisionType;
	}

	/**
	 * XML entity class. These classes represent the structure of the XML files
	 * to be loaded.
	 */
	@XmlRootElement(name = "iso_3166_country")
	@XmlAccessorType(XmlAccessType.FIELD)
	static class ISO3166_2_CountryEntity {

		@XmlAttribute(name = "code")
		public String code;

		@XmlElement(name = "iso_3166_subset")
		public List<ISO3166_2_SubsetEntity> subsets;

		@Override
		public String toString() {
			return "CountryEntity " + code;
		}
	}

	public ObservableList<Country> availableCountries() {
		return countries;
	}

	public ReadOnlyStringProperty subdivisionLabel() {
		return subdivisionLabel.getReadOnlyProperty();
	}

	public ObservableList<Subdivision> subdivisions() {
		return FXCollections.unmodifiableObservableList(subdivisions);
	}

	public ReadOnlyBooleanProperty inProgressProperty() {
		return inProgress.getReadOnlyProperty();
	}
}
