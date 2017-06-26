package de.saxsys.mvvmfx.examples.contacts.model.countries;

import de.saxsys.mvvmfx.examples.contacts.model.Country;
import de.saxsys.mvvmfx.examples.contacts.model.Subdivision;
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

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is used to encapsulate the process of loading available countries
 * and their subdivisions (if available).
 *
 * This class is meant to be a stateful wrapper around the existing countries.
 * You should create an instance of this class, call the {@link #init()} method
 * and then bind the UI to the provided observable lists (
 * {@link #availableCountries()} and {@link #subdivisions()}).
 *
 * To choose a country use the {@link #setCountry(Country)} method. This
 * will lead to a change of the {@link #subdivisions()} list.
 *
 *
 * At the moment this class uses two XML files ({@link #ISO_3166_LOCATION} and
 * {@link #ISO_3166_2_LOCATION}) that contain information about countries,
 * country-codes and subdivisions according to ISO 3166 and ISO 3166-2.
 *
 * The loading process is implemented with the DataFX framework.
 */
@Singleton
@Alternative
public class DataFxCountrySelector implements CountrySelector {

	private static final Logger LOG = LoggerFactory.getLogger(DataFxCountrySelector.class);

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
	@PostConstruct
	@Override
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

							entity.subsets.forEach(subset -> {
								subset.entryList.forEach(entry -> {
									subdivisionList.add(new Subdivision(entry.name, entry.code, country));
								});
							});

							String subdivisionName = entity.subsets.stream()
									.map(subset -> subset.subdivisionType)
									.collect(Collectors.joining("/"));

							countryCodeSubdivisionNameMap.put(country, subdivisionName);
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
